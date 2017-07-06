package pt.isel.ls.Model.Commands.ProgrammeManagement;

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
import pt.isel.ls.Model.Mappers.CoursesCurrSem;
import pt.isel.ls.Model.Results.ProgrammeManagementResults.GetProgrammesPidCoursesResult;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Validators.Validation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetProgrammesPidCourses implements Command {
    private String pid;
    private Integer skip, top;
    private static final Logger _logger = LoggerFactory.getLogger(GetProgrammesPidCourses.class);

    public GetProgrammesPidCourses(){ }

    public GetProgrammesPidCourses(String description, String template) {
        commands.add(new EntityCommand(template, description));
    }

    /**
     * Get the course structure of programme pid.
     * @param pathParameters CustomMap<String, String> that will have the parameters passed in the path.
     * @param parameters CustomMap<String, CustomList<String>> that contains the parameters.
     * @param conn Connection with DataBase.
     * @return The result associated to this command.
     * @throws SQLException
     * @throws ParametersException
     * @throws InvalidTypeException
     */
    public Result execute(CustomMap<String, String> pathParameters, CustomMap<String, CustomList<String>> parameters, Connection conn) throws InvalidTypeException, SQLException, NotExistInformationException, ParametersException {
        _logger.info("Validate Information to execute the command.");

        /* Validate if there is everything correct to proceed with the command. */
        validatePathParameters(pathParameters);
        validateParameters(parameters);
        validateExistingInformationInDb(conn);

        _logger.info("Beginning the execution of the command GET /programmes/{}/courses", pid);

        /* Query to select all the courses of the programme with ID pid. */
        String getProgrammeCourse = "SELECT r.name, r.acronym, r.tNumber, r.curricularSemester\n" +
                                    "FROM (SELECT c.name, c.acronym, c.tNumber, cp.curricularSemester, ROW_NUMBER() OVER(ORDER BY c.acronym) AS Row\n" +
                                          "FROM courProgrcurr AS cp\n" +
                                          "JOIN course AS c on cp.cName = c.name\n" +
                                          "WHERE cp.pid = ?) AS r\n" +
                                    "WHERE r.Row > ? AND r.Row <= ?";

        /* Build prepared statement. */
        PreparedStatement ps = conn.prepareStatement(getProgrammeCourse);
        ps.setString(1, pid);
        ps.setInt(2, skip);
        ps.setInt(3, skip + top);

        /* Map the information resultant of the query into courses with curricular semesters. */
        CoursesCurrSem coursesCurrSemMapper = new CoursesCurrSem();
        CustomList<Entity> coursesWithCurricularSemester = coursesCurrSemMapper.getData(ps.executeQuery());

        _logger.info("End the execution of the command GET /programmes/{}/courses", pid);

        /* Return the associated result to this command. */
        return new GetProgrammesPidCoursesResult(coursesWithCurricularSemester, skip, top, getNumberRows(conn), pid);
    }

    /**
     * Get the number of rows that the database has associated to this command.
     * @param conn Connection with DataBase.
     * @return Number of rows.
     * @throws SQLException
     */
    private int getNumberRows(Connection conn) throws SQLException {
        /* Query to select the number of courses the programme with ID pid has.*/
        String getNumberRows = "SELECT count(*)\n" +
                               "FROM courProgrcurr AS cp\n"+
                               "JOIN course AS c on cp.cName = c.name\n" +
                               "WHERE cp.pid = ?";

        /* Build prepared statement. */
        PreparedStatement ps = conn.prepareStatement(getNumberRows);
        ps.setString(1, pid);

        /* Get the number of rows. */
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    /**
     * Validate if exist the path parameters and if they are in their correct form.
     * @param pathParameters CustomMap<String, String> that will have the parameters passed in the path.
     * @throws InvalidTypeException
     */
    private void validatePathParameters(CustomMap<String, String> pathParameters) throws InvalidTypeException {
        pid = pathParameters.getString("{pid}");
        if(pid == null)
            throw new InvalidTypeException("{pid} must be a text value.");
    }

    /**
     * Validate in case of existing parameters if are the correct ones and if they are in their correct form.
     * @param parameters CustomMap<String, CustomList<String>> that contains the parameters.
     * @throws ParametersException
     * @throws InvalidTypeException
     */
    private void validateParameters(CustomMap<String, CustomList<String>> parameters) throws ParametersException, NotExistInformationException, InvalidTypeException {
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
     * Validate if it's possible to perform the query without happening a sql exception.
     * @param conn Connection with DataBase.
     * @throws SQLException
     * @throws NotExistInformationException
     */
    private void validateExistingInformationInDb(Connection conn) throws SQLException, NotExistInformationException {
        /* Validate that the programme with ID pid exists. */
        Validation.validateExistentProgrammesPid(conn, pid);
    }
}