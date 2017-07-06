package pt.isel.ls.Model.Commands.TeacherManagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.Commands.QueryOperations.QueryOperations;
import pt.isel.ls.Model.CustomExceptions.InvalidTypeException;
import pt.isel.ls.Model.CustomExceptions.NotExistInformationException;
import pt.isel.ls.Model.CustomExceptions.ParametersException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.DataStructures.CustomMap;
import pt.isel.ls.Model.Entities.Class;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Entities.EntityCommand;
import pt.isel.ls.Model.Mappers.Teachers;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Results.TeacherManagementResults.GetCoursesAcrClassesSemNumTeachersResult;
import pt.isel.ls.Model.Validators.Validation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetCoursesAcrClassesSemNumTeachers implements Command {
    private CustomMap<String, String> semesterTime = new CustomMap<>();
    private String acr, sem, num;
    private Integer year, skip, top;
    private static final Logger _logger = LoggerFactory.getLogger(GetCoursesAcrClassesSemNumTeachers.class);

    public GetCoursesAcrClassesSemNumTeachers() {
    }

    public GetCoursesAcrClassesSemNumTeachers(String description, String template) {
        commands.add(new EntityCommand(template, description));
    }

    /**
     * Put the corresponding string of the SQL to the one given by the user.
     */
    private void fillSemesterTime() {
        semesterTime.put("v", "summer");
        semesterTime.put("i", "winter");
    }

    /**
     *
     * @param pathParameters CustomMap<String, String> that will have the parameters passed in the path.
     * @param parameters CustomMap<String, CustomList<String>> that contains the parameters.
     * @param conn Connection with DataBase.
     * @return The result associated to this command.
     * @throws ParametersException
     * @throws InvalidTypeException
     * @throws SQLException
     * @throws NotExistInformationException
     */
    @Override
    public Result execute(CustomMap<String, String> pathParameters, CustomMap<String, CustomList<String>> parameters, Connection conn) throws ParametersException, InvalidTypeException, SQLException, NotExistInformationException {
        /* Initiate the semester map. */
        fillSemesterTime();

        _logger.info("Validate information to execute the command.");

        /* Verify if everything is correct to proceed with the command execution. */
        validateParameters(parameters);
        validatePathParameters(pathParameters);
        validateExistingInformationInDb(conn);

        _logger.info("Beginning to execute the command GET /courses/{}/classes/{}/{}/teachers", acr, sem, num);

        /* Query that selects the teachers from a class of the Acr course, in the Sem semester and with the num ID. */
        String getTeacher = "SELECT r.number, r.email, r.name\n" +
                            "FROM (SELECT T.number, T.email, T.name, ROW_NUMBER() OVER(ORDER BY T.number) AS Row\n" +
                                  "FROM classTeacher as CT\n" +
                                  "JOIN teacher AS T ON CT.tNumber = T.number \n" +
                                  "JOIN class AS Cl ON CT.cId = Cl.id AND CT.cName = Cl.cName AND CT.aYear = Cl.aYear AND CT.aSemester = Cl.aSemester\n" +
                                  "JOIN course AS Cour ON Cour.name = Cl.cName\n" +
                                  "WHERE Cour.acronym = ? AND Cl.aYear = ? AND Cl.aSemester = ? AND Cl.id = ?) AS r\n" +
                            "WHERE r.Row > ? AND r.Row <= ?";

        /* Set the prepared statement to perform the query. */
        PreparedStatement ps = conn.prepareStatement(getTeacher);
        ps.setString(1, acr);
        ps.setInt(2, year);
        ps.setString(3, sem);
        ps.setString(4, num);
        ps.setInt(5, skip);
        ps.setInt(6, skip + top);

        /* Map the information resultant of the query into teachers and class. */
        Teachers teachersMapper = new Teachers();
        CustomList<Entity> teachers = teachersMapper.getData(ps.executeQuery());

        Class _class = QueryOperations.queryCoursesAcrClassesSemNum(conn, num, acr, year, sem);

        _logger.info("End of the execution of the command GET /courses/{}/classes/{]/{}", acr, pathParameters.get("{sem}"), num);

        /* Return the associated result to this command. */
        return new GetCoursesAcrClassesSemNumTeachersResult(teachers, _class, skip, top, getNumberRows(conn), acr);
    }

    /**
     *
     * @param conn Connection with DataBase.
     * @return
     * @throws SQLException
     */
    private int getNumberRows(Connection conn) throws SQLException {
        String getNumberRows = "SELECT count(*)\n" +
                               "FROM classTeacher AS CT\n" +
                               "JOIN class AS Cl ON CT.cId = Cl.id AND CT.cName = Cl.cName AND CT.aYear = Cl.aYear AND CT.aSemester = Cl.aSemester\n" +
                               "JOIN course AS Cour ON Cour.name = Cl.cName\n" +
                               "WHERE Cour.acronym = ? AND Cl.aYear = ? AND Cl.aSemester = ? AND Cl.id = ?";

        PreparedStatement ps = conn.prepareStatement(getNumberRows);
        ps.setString(1, acr);
        ps.setInt(2, year);
        ps.setString(3, sem);
        ps.setString(4, num);
        ResultSet rs = ps.executeQuery();
        rs.next();

        return rs.getInt(1);
    }

    /**
     *
     * @param parameters CustomMap<String, CustomList<String>> that contains the parameters.
     * @throws ParametersException
     * @throws InvalidTypeException
     */
    private void validateParameters(CustomMap<String, CustomList<String>> parameters) throws ParametersException, InvalidTypeException {
        skip = 0;
        top = 20;
        /* Verify if exists parameters and if they are acceptable. Also verify their types. */
        if (parameters != null) {
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
     *
     * Verify if there's the parameters needed to this command and validate's it's types
     * @param pathParameters CustomMap<String, String> that will have the parameters passed in the path.
     * @throws ParametersException
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
            throw new InvalidTypeException("{acr} must be a text value.");

        num = pathParameters.getString("{num}");
        if(num == null || num.length() != 2)
            throw new ParametersException("{num} must be a 2 characters value.");
    }

    /**
     * Validate if it's possible to perform the query without happening a sql exception.
     * @param conn Connection with DataBase.
     * @throws SQLException
     * @throws NotExistInformationException
     */
    private void validateExistingInformationInDb(Connection conn) throws SQLException, NotExistInformationException {
        Validation.validateExistentCoursesAcr(conn, acr);
        Validation.validateExistentClassesSemNum(conn, num, year, sem);
        Validation.validateExistentCoursesAcrClassesSemNum(conn, num, acr, year, sem);
    }
}