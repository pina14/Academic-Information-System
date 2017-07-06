package pt.isel.ls.Model.Commands.ClassManagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.CustomExceptions.InvalidTypeException;
import pt.isel.ls.Model.CustomExceptions.NotExistInformationException;
import pt.isel.ls.Model.CustomExceptions.ParametersException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.DataStructures.CustomMap;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Entities.EntityCommand;
import pt.isel.ls.Model.Mappers.Classes;
import pt.isel.ls.Model.Results.ClassManagementResults.GetCoursesAcrClassesSemResult;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Validators.Validation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetCoursesAcrClassesSem implements Command {
    private  CustomMap<String, String> semesterTime = new CustomMap<>();
    private String acr, sem;
    private Integer year, skip, top;
    private static final Logger _logger = LoggerFactory.getLogger(GetCoursesAcrClassesSem.class);

    public GetCoursesAcrClassesSem(){
    }

    public GetCoursesAcrClassesSem(String description, String template) {
        commands.add(new EntityCommand(template, description));
    }

    /**
     * Put the corresponding string of the SQL to the one given by the user
     */
    private void fillSemesterTime(){
        semesterTime.put("v","summer");
        semesterTime.put("i", "winter");
    }

    /**
     * Get all classes of the acr course on the sem semester.
     * @param pathParameters CustomMap<String, String> that will have the parameters passed in the path.
     * @param parameters CustomMap<String, CustomList<String>> that contains the parameters.
     * @param conn Connection with DataBase.
     * @return The result associated to this command.
     * @throws SQLException
     * @throws NotExistInformationException
     * @throws ParametersException
     * @throws InvalidTypeException
     */
    @Override
    public Result execute(CustomMap<String, String> pathParameters, CustomMap<String, CustomList<String>> parameters, Connection conn) throws SQLException, NotExistInformationException, ParametersException, InvalidTypeException {
        _logger.info("Validate Information to execute the command.");

        /* Initiate the semester map. */
        fillSemesterTime();

        /* Verify that everything is correct to proceed with the command execution. */
        validateParameters(parameters);
        validatePathParameters(pathParameters);
        validateExistingInformationInDb(conn);

        _logger.info("Beginning to execute the command GET /courses/{}/classes/{}.", acr, sem);

        /* Query to select all the classes of course with acronym acr on the semester sem. */
        String getClasses = "SELECT r.id, r.cName, r.aYear, r.aSemester\n"+
                            "FROM (SELECT Cl.id, Cl.cName, Cl.aYear, Cl.aSemester, ROW_NUMBER() OVER(ORDER BY Cl.id) AS Row\n" +
                                  "FROM class AS Cl\n" +
                                  "JOIN course AS Cour ON (Cour.name = Cl.cName)\n" +
                                  "WHERE Cour.acronym = ? AND Cl.aYear = ? and Cl.aSemester = ?) AS r\n" +
                            "WHERE r.Row > ? AND r.Row <= ?";

        /* Build prepared statement.*/
        PreparedStatement ps = conn.prepareStatement(getClasses);
        ps.setString(1, acr);
        ps.setInt(2, year);
        ps.setString(3, sem);
        ps.setInt(4, skip);
        ps.setInt(5, skip + top);

        /* Map the information resultant of the query into classes. */
        Classes classesMapper = new Classes();
        CustomList<Entity> classes =  classesMapper.getData(ps.executeQuery());

        _logger.info("End the execute of the command GET /courses/{}/classes/{}.", acr, sem);

        /* Return the associated result to this command. */
        return new GetCoursesAcrClassesSemResult(classes, skip, top, getNumberRows(conn), acr, pathParameters.get("{sem}"), sem, year);
    }

    /**
     * Get the number of rows that the database has associated to this command .
     * @param conn Connection with DataBase.
     * @return Number of rows.
     * @throws SQLException
     */
    private int getNumberRows(Connection conn) throws SQLException {
        /* Query to select the number of rows of the classes of course with acronym acr on the semester sem. */
        String getNumberRows = "SELECT count(*)\n" +
                               "FROM class AS cl\n" +
                               "JOIN course AS cs ON cl.cName = cs.name\n" +
                               "WHERE cs.acronym = ? AND aYear = ? AND aSemester = ?";

        /* Build prepared statement. */
        PreparedStatement ps = conn.prepareStatement(getNumberRows);
        ps.setString(1, acr);
        ps.setInt(2, year);
        ps.setString(3, sem);

        /* Get the number of rows. */
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    /**
     * Validate in case of existing parameters if are the correct ones and if they are in their correct form.
     * @param parameters CustomMap<String, CustomList<String>> that contains the parameters.
     * @throws ParametersException
     * @throws InvalidTypeException
     */
    private void validateParameters(CustomMap<String, CustomList<String>> parameters) throws ParametersException, InvalidTypeException {
        skip = 0;
        top = 20;
        if(parameters != null){
            CustomList<String> skipAux = parameters.get("skip"), topAux = parameters.get("top");

            if (parameters.size() > 2 || parameters.size() <= 2 && skipAux == null && topAux == null)
                throw new ParametersException("This command can't support other parameters than skip and top.");

            if (skipAux != null){
                skip = skipAux.getInt(0);
                if(skip == null)
                    throw new InvalidTypeException("Skip value wasn't properly inserted.");

            }
            if (topAux != null){
                top = topAux.getInt(0);
                if(top == null)
                    throw new InvalidTypeException("Top value wasn't properly inserted.");
            }
        }
    }

    /**
     * Validate if exist the path parameters and if they are in their correct form.
     * @param pathParameters CustomMap<String, String> that will have the parameters passed in the path.
     * @throws InvalidTypeException
     */
    private void validatePathParameters(CustomMap<String, String> pathParameters) throws ParametersException, InvalidTypeException {
        String semester = pathParameters.get("{sem}");
        if(semester == null || semester.length() != 5)
            throw new ParametersException("{sem} must be a four character number indicating the year and 'v' or 'i' to indicate the semester.");

        String semesterChar = semester.substring(semester.length() - 1);
        if(!semesterChar.equals("i") && !semesterChar.equals("v"))
            throw new ParametersException(semester + " the last letter must be either i or v, to represent the semester.\n");
        sem = semesterTime.getString(semesterChar);

        String textYear = semester.substring(0, semester.length() - 1);
        if(!pathParameters.isNumeric(textYear) )
            throw new InvalidTypeException("The first 4 characters in {sem} must be a year.");
        year =  Integer.parseInt(textYear);

        acr = pathParameters.getString("{acr}");
        if(acr == null)
            throw new InvalidTypeException("{acr} must be a text value. ");

    }

    /**
     * Validate if it's possible to perform the query without happening a sql exception.
     * @param conn Connection with DataBase.
     * @throws SQLException
     * @throws NotExistInformationException
     */
    private void validateExistingInformationInDb(Connection conn) throws SQLException, NotExistInformationException {
        /* Verify if course exists. */
        Validation.validateExistentCoursesAcr(conn, acr);

        /* Verify if classes in semester sem exists. */
        String getClasses = "SELECT * FROM class WHERE aYear = ? AND aSemester = ? " +
                            "ORDER BY id";

        /* Build prepared statement. */
        PreparedStatement ps;
        ps = conn.prepareStatement(getClasses);
        ps.setInt(1, year);
        ps.setString(2, sem);

        /* Map the information resultant of the query into classes. */
        Classes classes = new Classes();
        CustomList<Entity> result =  classes.getData(ps.executeQuery());

        /* If doesn't exist any classes in that semester throw exception. */
        if(result.size() == 0)
            throw new NotExistInformationException("The class represented by the year " + year + " on semester " + sem  + " doesn't exist.");
    }
}