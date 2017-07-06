package pt.isel.ls.Model.Commands.CourseManagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.CustomExceptions.InvalidTypeException;
import pt.isel.ls.Model.CustomExceptions.ParametersException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.DataStructures.CustomMap;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Entities.EntityCommand;
import pt.isel.ls.Model.Mappers.Courses;
import pt.isel.ls.Model.Results.CourseManagementResults.GetCoursesResult;
import pt.isel.ls.Model.Results.Result;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetCourses implements Command {
    private Integer skip, top;
    private static final Logger _logger = LoggerFactory.getLogger(GetCourses.class);

    public GetCourses(){ }

    public GetCourses(String description, String template) {
        commands.add(new EntityCommand(template, description));
    }

    /**
     * Get all courses.
     * @param pathParameters CustomMap<String, String> that will have the parameters passed in the path.
     * @param parameters CustomMap<String, CustomList<String>> that contains the parameters.
     * @param conn Connection with DataBase.
     * @return The result associated to this command.
     * @throws SQLException
     * @throws ParametersException
     * @throws InvalidTypeException
     */
    @Override
    public Result execute(CustomMap<String, String> pathParameters, CustomMap<String, CustomList<String>> parameters, Connection conn) throws SQLException, ParametersException, InvalidTypeException {
        _logger.info("Validate Information to execute the command.");

        /* Verify that everything is correct to proceed with the command execution. */
        validateParameters(parameters);

        _logger.info("Beginning to execute the command GET /courses.");

        /* Query to select all courses. */
        String get = "SELECT r.name, r.acronym, r.tNumber\n" +
                     "FROM (SELECT name, acronym, tNumber, ROW_NUMBER() OVER(ORDER BY name) AS Row\n" +
                           "FROM Course) AS r\n" +
                     "WHERE r.Row > ? AND r.Row <= ?";

        /* Build prepared statement. */
        PreparedStatement ps = conn.prepareStatement(get);
        ps.setInt(1, skip);
        ps.setInt(2, skip + top);

        /* Map the information resultant of the query into courses. */
        Courses coursesMapper = new Courses();
        CustomList<Entity> courses = coursesMapper.getData(ps.executeQuery());

        _logger.info("End the execute of the command GET /courses.");

        /* Return the associated result to this command. */
        return new GetCoursesResult(courses, skip, top, getNumberRows(conn));
    }

    /**
     * Get the number of rows that the database has associated to this command.
     * @param conn Connection with DataBase.
     * @return Number of rows.
     * @throws SQLException
     */
    private int getNumberRows(Connection conn) throws SQLException {
        /* Query to select the number of rows of courses. */
        String getNumberRows = "SELECT count(*)\n" +
                               "FROM course";

        /* Build prepared statement. */
        PreparedStatement ps = conn.prepareStatement(getNumberRows);

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
}