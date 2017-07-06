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
import pt.isel.ls.Model.Entities.Student;
import pt.isel.ls.Model.Mappers.CoursesClasses;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Results.UserManagementResults.GetStudentsNumResult;
import pt.isel.ls.Model.Validators.Validation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GetStudentsNum implements Command {
    private Integer num;
    private Student student;
    private static final Logger _logger = LoggerFactory.getLogger(GetStudentsNum.class);

    public GetStudentsNum() {
    }

    public GetStudentsNum(String description, String template) {
        commands.add(new EntityCommand(template, description));
    }

    /**
     * shows the student with the number num.
     * @param pathParameters
     * @param parameters
     * @param conn
     * @return
     * @throws SQLException
     * @throws NotExistInformationException
     * @throws ParametersException
     * @throws InvalidTypeException
     */
    public Result execute(CustomMap<String, String> pathParameters, CustomMap<String, CustomList<String>> parameters, Connection conn) throws InvalidTypeException, SQLException, NotExistInformationException, ParametersException {
        _logger.info("Validate Information to execute the command.");

        /* Validate if everything is correct to proceed with the command. */
        validatePathParameters(pathParameters);
        validateParameters(parameters);

        validateExistingInformationInDb(conn);

        _logger.info("Beginning to execute the command GET /students/{}", num);

        String coursesClassesInfo = "SELECT CS.cId, CS.cName, CS.aYear, CS.aSemester, Cour.acronym \n" +
                                    "FROM classStudent AS CS\n" +
                                    "JOIN course AS Cour ON (Cour.name = CS.cName)\n" +
                                    "WHERE CS.sNumber = ?\n" +
                                    "ORDER BY CS.cId";

        PreparedStatement ps = conn.prepareStatement(coursesClassesInfo);
        ps.setInt(1, num);

        CoursesClasses coursesClasses = new CoursesClasses();
        CustomList<Entity> coursesClassesTable = coursesClasses.getData(ps.executeQuery());

        _logger.info("End the execute of the command GET /students/{}", num);

        /* Return the query result. */
        return new GetStudentsNumResult(student, coursesClassesTable);
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
            throw new ParametersException("this command can't support parameters.");
    }

    private void validateExistingInformationInDb(Connection conn) throws SQLException, NotExistInformationException {
        student = Validation.validateExistentStudentNum(conn, num);
    }
}