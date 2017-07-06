package pt.isel.ls.Model.Commands.TeacherManagement;

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
import pt.isel.ls.Model.Mappers.CoursesClasses;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Results.TeacherManagementResults.GetTeachersNumClassesResult;
import pt.isel.ls.Model.Validators.Validation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Show all classes for the teacher with num number.
 */
public class GetTeachersNumClasses implements Command {
    private Integer num, skip, top;
    private static final Logger _logger = LoggerFactory.getLogger(GetTeachersNumClasses.class);

    public GetTeachersNumClasses() {
    }

    public GetTeachersNumClasses(String description, String template) {
        commands.add(new EntityCommand(template, description));
    }

    /**
     *
     * Perform the select of the classes of the teacher with number numDoc if possible.
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
        _logger.info("Validate information to execute the command.");

        /* Verify if everything is correct to proceed with the command execution. */
        validateParameters(parameters);
        validatePathParameters(pathParameters);
        validateExistingInformationInDb(conn);

        _logger.info("Beginning to execute the command GET /teachers/{}/classes", num);

        /* Query that selects the classes that a teacher lectures */
        String coursesClassesInfo = "SELECT r.cId, r.cName, r.aYear, r.aSemester, r.acronym\n" +
                                    "FROM (SELECT CT.cId, CT.cName, CT.aYear, CT.aSemester, Cour.acronym, ROW_NUMBER() OVER(ORDER BY CT.cId) AS Row\n" +
                                          "FROM classTeacher AS CT\n" +
                                          "JOIN course AS Cour ON (Cour.name = CT.cName)\n" +
                                          "WHERE CT.tNumber = ?) AS r\n" +
                                    "WHERE r.Row > ? AND r.Row <= ?";

        /* Set the prepared statement to perform the query. */
        PreparedStatement ps = conn.prepareStatement(coursesClassesInfo);
        ps.setInt(1, num);
        ps.setInt(2, skip);
        ps.setInt(3, top);

        /* Map the information resultant of the query into coursesClasses. */
        CoursesClasses coursesClassesMapper = new CoursesClasses();
        CustomList<Entity> coursesClasses = coursesClassesMapper.getData(ps.executeQuery());

        _logger.info("End of the execution of the command GET /teachers/{}/classes", num);

        /* Return the associated result to this command. */
        return new GetTeachersNumClassesResult(coursesClasses, skip, top, getNumberRows(conn), num);
    }

    private int getNumberRows(Connection conn) throws SQLException {
        String getNumberRows = "SELECT count(*)\n" +
                               "FROM teacher as T\n" +
                               "JOIN classTeacher as CT ON (T.number = CT.tNumber)\n" +
                               "WHERE T.number = ?";

        PreparedStatement ps = conn.prepareStatement(getNumberRows);
        ps.setInt(1, num);
        ResultSet rs = ps.executeQuery();
        rs.next();

        return rs.getInt(1);
    }

    /**
     * Verify in case of containing parameters if it's the skip and top parameters,
     * the only parameters supported by this command, and affects them.
     * @param parameters CustomMap<String, CustomList<String>> that contains the parameters.
     * @throws ParametersException
     * @throws InvalidTypeException
     */
    private void validateParameters(CustomMap<String, CustomList<String>> parameters) throws ParametersException, InvalidTypeException {
        skip = 0;
        top = 20;

        /* Verify if exists parameters and if they are acceptable. Also verify their types. */
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
     * Verify if there's the parameters needed to this command and validate's its types
     * Supported path parameters and its types:
     * @param pathParameters CustomMap<String, String> that will have the parameters passed in the path.
     * @throws InvalidTypeException
     */
    private void validatePathParameters(CustomMap<String, String> pathParameters) throws InvalidTypeException {
        /* Verify path parameters types. */
        num = pathParameters.getInt("{num}");
        if(num == null)
            throw new InvalidTypeException("{num} must be an Integer value.");
    }

    /**
     * Validate if it's possible to perform the query without happening a sql exception.
     * @param conn Connection with DataBase.
     * @throws SQLException
     * @throws NotExistInformationException
     */
    private void validateExistingInformationInDb(Connection conn) throws SQLException, NotExistInformationException {
        /* Validate if exists the teacher with number num. */
        Validation.validateExistentTeachersNum(conn, num);
    }
}