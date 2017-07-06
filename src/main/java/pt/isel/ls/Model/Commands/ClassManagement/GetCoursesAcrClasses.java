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
import pt.isel.ls.Model.Results.ClassManagementResults.GetCoursesAcrClassesResult;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Validators.Validation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetCoursesAcrClasses implements Command {
    private static final Logger _logger = LoggerFactory.getLogger(GetCoursesAcrClasses.class);
    private String acr;
    private Integer skip, top;

    public GetCoursesAcrClasses(){
    }

    public GetCoursesAcrClasses(String description, String template) {
        commands.add(new EntityCommand(template, description));
    }

    /**
     * Get all classes for a course.
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

       /* Verify that everything is correct to proceed with the command execution. */
       validateParameters(parameters);
       validatePathParameters(pathParameters);
       validateExistingInformationInDb(conn);

        _logger.info("Beginning to execute the command GET /courses/{}/classes.", acr);

       /* Query to select all the classes of a course with acronym acr. */
       String getClasses = "SELECT r.id, r.cName, r.aYear, r.aSemester\n" +
                           "FROM (SELECT Cl.id, Cl.cName, Cl.aYear, Cl.aSemester, ROW_NUMBER() OVER(ORDER BY Cl.cName) AS Row\n" +
                                 "FROM course as Cour\n" +
                                 "JOIN class as Cl ON (Cour.name = Cl.cName)\n" +
                                 "WHERE Cour.acronym = ?) AS r\n" +
                           "WHERE r.Row > ? AND r.Row <= ?";

       /* Build prepared statement. */
       PreparedStatement ps = conn.prepareStatement(getClasses);
       ps.setString(1,acr);
       ps.setInt(2, skip);
       ps.setInt(3, skip + top);

       /* Map the information resultant of the query into classes. */
       Classes classesMapper = new Classes();
       CustomList<Entity> classes = classesMapper.getData(ps.executeQuery());

        _logger.info("End the execute of the command GET /courses/{}/classes.", acr);

       /* Return the associated result to this command. */
       return new GetCoursesAcrClassesResult(classes, skip, top, getNumberRows(conn), acr);
    }

    /**
     * Get the number of rows that the database has associated to this command.
     * @param conn Connection with DataBase.
     * @return Number of rows.
     * @throws SQLException
     */
    private int getNumberRows(Connection conn) throws SQLException {
        /* Query to select the number of rows of the classes of a course with acronym acr. */
        String getNumberRows = "SELECT count(*)\n" +
                               "FROM class AS cl\n" +
                               "JOIN course AS cs ON cl.cName = cs.name\n" +
                               "WHERE cs.acronym = ?";

        /* Build prepared statement. */
        PreparedStatement ps = conn.prepareStatement(getNumberRows);
        ps.setString(1, acr);

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

            if (skipAux != null) {
                skip = skipAux.getInt(0);
                if(skip == null)
                    throw new InvalidTypeException( "Skip isn't a valid Integer value.");
            }
            if (topAux != null){
                top = topAux.getInt(0);
                if(top == null)
                    throw new InvalidTypeException( "Top isn't a valid Integer value.");
            }
        }
    }

    /**
     * Validate if exists the path parameter and if it is in its correct form.
     * @param pathParameters CustomMap<String, String> that will have the parameters passed in the path.
     * @throws InvalidTypeException in case the path parameter acr isn't a string
     */
    private void validatePathParameters(CustomMap<String, String> pathParameters) throws InvalidTypeException {
        acr = pathParameters.getString("{acr}");
        if(acr == null)
            throw new InvalidTypeException("{acr} must be a text value.");
    }

    /**
     * Validate if it's possible to perform the query without happening a sql exception.
     * @param conn Connection with DataBase.
     * @throws SQLException in case the server or the connection fails
     * @throws NotExistInformationException in case there isn't a course with the acronym acr
     */
    private void validateExistingInformationInDb(Connection conn) throws SQLException, NotExistInformationException {
        /* Validate if the course with acronym acr exits. */
        Validation.validateExistentCoursesAcr(conn, acr);
    }
}