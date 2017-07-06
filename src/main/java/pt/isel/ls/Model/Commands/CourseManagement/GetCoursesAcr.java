package pt.isel.ls.Model.Commands.CourseManagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.CustomExceptions.InvalidTypeException;
import pt.isel.ls.Model.CustomExceptions.NotExistInformationException;
import pt.isel.ls.Model.CustomExceptions.ParametersException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.DataStructures.CustomMap;
import pt.isel.ls.Model.Entities.Course;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Entities.EntityCommand;
import pt.isel.ls.Model.Mappers.Programmes;
import pt.isel.ls.Model.Results.CourseManagementResults.GetCoursesAcrResult;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Validators.Validation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GetCoursesAcr implements Command {
    private String acr;
    private Course course;
    private static final Logger _logger = LoggerFactory.getLogger(GetCoursesAcr.class);

    public GetCoursesAcr(){ }

    public GetCoursesAcr(String description, String template) {
        commands.add(new EntityCommand(template, description));
    }

    /**
     * Get the course with acronym acr.
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
    public Result execute(CustomMap<String, String> pathParameters, CustomMap<String, CustomList<String>> parameters, Connection conn) throws SQLException, NotExistInformationException, InvalidTypeException, ParametersException {
        _logger.info("Validate Information to execute the command.");

        /* Validate if there is everything correct to proceed with the command.  */
        validatePathParameters(pathParameters);
        validateParameters(parameters);
        validateExistingInformationInDb(conn);

        _logger.info("Beginning to execute the command GET /courses/{}.", acr);

        /* Query to select the information the course with acronym acr. */
        String programmesInformation = "SELECT DISTINCT p.acronym, p.name, p.numSemester \n" +
                                       "FROM programme AS p\n" +
                                       "JOIN courProgrCurr AS cpc ON p.acronym = cpc.pid\n" +
                                       "JOIN course AS cs ON cpc.cName = cs.name\n" +
                                       "WHERE cs.acronym = ?\n" +
                                       "ORDER BY p.acronym";

        /* Build prepared statement. */
        PreparedStatement ps = conn.prepareStatement(programmesInformation);
        ps.setString(1, acr);

        /* Map the information resultant of the query into courses. */
        Programmes programmes =  new Programmes();
        CustomList<Entity> courseProgrammes = programmes.getData(ps.executeQuery());

        _logger.info("End the execute of the command GET /courses/{}.", acr);

        /* Return the associated result to this command. */
        return new GetCoursesAcrResult(course, courseProgrammes, acr);
    }

    /**
     * Validate that doesn't exist parameters.
     * @param parameters CustomMap<String, CustomList<String>> that contains the parameters.
     * @throws ParametersException
     */
    private void validateParameters(CustomMap<String, CustomList<String>> parameters) throws ParametersException {
        if(parameters != null)
            throw new ParametersException("This command doesn't support parameters.");
    }

    /**
     * Validate if exist the path parameter and if it is in its correct form.
     * @param pathParameters CustomMap<String, String> that will have the parameters passed in the path.
     * @throws InvalidTypeException
     */
    private void validatePathParameters(CustomMap<String, String> pathParameters) throws InvalidTypeException {
        acr = pathParameters.getString("{acr}");
        if(acr == null)
            throw new InvalidTypeException("{acr} must be a text value.");
    }

    /**
     * Validate if it's possible to perform the query without happening a sql exception.
     * @param conn Connection with DataBase.
     * @throws SQLException
     * @throws NotExistInformationException
     */
    private void validateExistingInformationInDb(Connection conn) throws SQLException, NotExistInformationException {
        /* Validate and get the course with acronym acr. */
        course = Validation.validateExistentCoursesAcr(conn, acr);
        if(course == null)
            throw new NotExistInformationException("The course with acronym " +  acr + " doesn't exist.");
    }
}