package pt.isel.ls.Model.Commands.UserManagement;

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
import pt.isel.ls.Model.Entities.Teacher;
import pt.isel.ls.Model.Mappers.Courses;
import pt.isel.ls.Model.Mappers.CoursesClasses;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Results.UserManagementResults.GetTeachersNumResult;
import pt.isel.ls.Model.Validators.Validation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GetTeachersNum implements Command {
    private Integer num;
    private Teacher teacher;
    private static final Logger _logger = LoggerFactory.getLogger(GetTeachersNum.class);

    public GetTeachersNum() {
    }

    public GetTeachersNum(String description, String template) {
        commands.add(new EntityCommand(template, description));
    }

    /**
     * shows the teacher with number num (e.g. GET /teachers/1207).
     * @param pathParameters
     * @param parameters
     * @param conn
     * @return
     * @throws SQLException
     * @throws NotExistInformationException
     * @throws ParametersException
     * @throws InvalidTypeException
     */
    public Result execute(CustomMap<String, String> pathParameters, CustomMap<String, CustomList<String>> parameters, Connection conn) throws InvalidTypeException, ParametersException, SQLException, NotExistInformationException {
        _logger.info("Validate Information to execute the command.");

        /* Validate if its everything correct to proceed with the command. */
        validatePathParameters(pathParameters);
        validateParameters(parameters);
        validateExistingInformationInDb(conn);

        _logger.info("Beginning to execute the command GET /teachers/{}", num);

        String coursesClassesInfo = "SELECT CT.cId, CT.cName, CT.aYear, CT.aSemester, Cour.acronym \n" +
                                    "FROM classTeacher AS CT\n" +
                                    "JOIN course AS Cour ON (Cour.name = CT.cName)\n" +
                                    "WHERE CT.tNumber = ?\n" +
                                    "ORDER BY CT.cId";

        PreparedStatement ps = conn.prepareStatement(coursesClassesInfo);
        ps.setInt(1, num);

        CoursesClasses coursesClasses = new CoursesClasses();
        CustomList<Entity> coursesClassesTable = coursesClasses.getData(ps.executeQuery());


        /* Get the teacher with number num information. */
        String coursesInformation = "SELECT *\n" +
                "FROM course\n"+
                "WHERE tNumber = ?\n" +
                "ORDER BY tNumber";

        /* Get the courses that teacher with number num is regent of.*/
        ps = conn.prepareStatement(coursesInformation);
        ps.setInt(1, num);

        /* Get the courses that teacher with number num lectures. */
        Courses coursesMapper = new Courses();
        CustomList<Entity> coursesTable = coursesMapper.getData(ps.executeQuery());

        _logger.info("End the execute of the command GET /teachers/{}", num);

        /* Return the query result. */
        return new GetTeachersNumResult(teacher, coursesClassesTable, coursesTable);
    }

    /**
     * Check if all the values in the pathParameters are of the correct type
     * @param pathParameters
     * @throws InvalidTypeException
     */
    private void validatePathParameters(CustomMap<String, String> pathParameters) throws InvalidTypeException {
        num = pathParameters.getInt("{num}");
        if(num == null)
            throw new InvalidTypeException("{num} must be an Integer value.");
    }

    /**
     * Check that doesn't have parameters.
     * @param parameters
     */
    private void validateParameters(CustomMap<String, CustomList<String>> parameters) throws ParametersException {
        if(parameters != null)
            throw new ParametersException("This command can't support parameters.");
    }

    private void validateExistingInformationInDb(Connection conn) throws SQLException, NotExistInformationException {
        teacher = Validation.validateExistentTeachersNum(conn, num);
    }
}