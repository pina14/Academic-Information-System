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
import pt.isel.ls.Model.Entities.Programme;
import pt.isel.ls.Model.Mappers.CoursesCurrSem;
import pt.isel.ls.Model.Results.ProgrammeManagementResults.GetProgrammesPidResult;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Validators.Validation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GetProgrammesPid implements Command {
    private String pid;
    private Programme programme;
    private static final Logger _logger = LoggerFactory.getLogger(GetProgrammesPid.class);

    public GetProgrammesPid(){
    }

    public GetProgrammesPid(String description, String template) {
        commands.add(new EntityCommand(template, description));
    }

    /**
     * Get the programme with ID pid.
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

        /* Validate if there is everything correct to proceed with the command. */
        validatePathParameters(pathParameters);
        validateParameters(parameters);
        validateExistingInformationInDb(conn);

        _logger.info("Beginning to execute the command GET /programmes/{}", pid);

        /* Query to select the information about the programme with ID pid. */
        String getProgrammeCourse = "SELECT c.name, c.acronym, c.tNumber, cp.curricularSemester FROM courProgrcurr AS cp\n" +
                                    "JOIN course AS c on cp.cName = c.name\n" +
                                    "WHERE cp.pid = ?\n" +
                                    "ORDER BY c.acronym";

        /* Build prepared statement. */
        PreparedStatement ps = conn.prepareStatement(getProgrammeCourse);
        ps.setString(1, pid);

        /* Map the information resultant of the query into courses. */
        CoursesCurrSem coursesCurrSemMapper = new CoursesCurrSem();
        CustomList<Entity> courses = coursesCurrSemMapper.getData(ps.executeQuery());

        _logger.info("End of the execution of the command GET /programmes/{}", pid);

        /* Return the associated result to this command. */
        return new GetProgrammesPidResult(programme, courses, pid);
    }

    /**
     * Validate if exist the path parameter and if it is in its correct form.
     * @param pathParameters CustomMap<String, String> that will have the parameters passed in the path.
     * @throws InvalidTypeException
     */
    private void validatePathParameters(CustomMap<String, String> pathParameters) throws InvalidTypeException {
        pid = pathParameters.getString("{pid}");
        if(pid == null)
            throw new InvalidTypeException("{pid} must be a text value.");
    }

    /**
     * Validate that doesn't exist parameters.
     * @param parameters CustomMap<String, CustomList<String>> that contains the parameters.
     * @throws ParametersException
     */
    private void validateParameters(CustomMap<String, CustomList<String>> parameters) throws ParametersException {
        if(parameters != null)
            throw new ParametersException("This command can't support parameters.");
    }

    /**
     * Validate if it's possible to perform the query without happening a sql exception.
     * @param conn Connection with DataBase.
     * @throws SQLException
     * @throws NotExistInformationException
     */
    private void validateExistingInformationInDb(Connection conn) throws SQLException, NotExistInformationException {
        /* Validate and get programme with ID pid. */
        programme = Validation.validateExistentProgrammesPid(conn, pid);
    }
}