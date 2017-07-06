package pt.isel.ls.Model.Commands.TeacherManagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.CustomExceptions.InvalidTypeException;
import pt.isel.ls.Model.CustomExceptions.NotExistInformationException;
import pt.isel.ls.Model.CustomExceptions.ParametersException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.DataStructures.CustomMap;
import pt.isel.ls.Model.Entities.EntityCommand;
import pt.isel.ls.Model.Results.NullResult;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Validators.Validation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PutTeachersNum implements Command {
    private Integer number;
    private String email, name;
    private static final Logger _logger = LoggerFactory.getLogger(PutTeachersNum.class);

    public PutTeachersNum() {
    }

    public PutTeachersNum(String description, String template) {
        commands.add(new EntityCommand(template, description));
    }

    @Override
    /**
     * Perform the update of the giving teacher.
     */
    public Result execute(CustomMap<String, String> pathParameters, CustomMap<String, CustomList<String>> parameters, Connection conn) throws ParametersException, InvalidTypeException, SQLException, NotExistInformationException {
        _logger.info("Validate information to execute the command.");

        /* Validate if it's everything fine to continue the command. */
        validateParameters(parameters);
        validatePathParameters(pathParameters);
        validateExistingInformationInDb(conn);

        _logger.info("Beginning to execute the command PUT /teachers/{} name={}&email={}", number, name, email);

        /* Initialize variables.
         * Query that update the email and name from the teacher with the number num.
         */
        String update = "UPDATE teacher\n" +
                        "SET email= ?, name = ?\n" +
                        "WHERE number=?";

        /* Set the prepared statement to perform the query. */
        PreparedStatement ps = conn.prepareStatement(update);
        ps.setString(1, email);
        ps.setString(2, name);
        ps.setInt(3, number);
        ps.execute();

        _logger.info("End the execution of the command PUT /teachers/{} name={}&email={}", number, name, email);

        /* Return query result. */
        return new NullResult();
    }

    /**
     * Verify if it contain the supported parameters and also verifies it's types.
     * Supported parameters and its types:
     * -> email - String
     * -> name - String
     * @param parameters
     * @throws ParametersException
     * @throws InvalidTypeException
     * @throws NotExistInformationException
     */
    private void validateParameters(CustomMap<String, CustomList<String>> parameters) throws ParametersException, InvalidTypeException {
        /* Validate if the needed parameters exists and if there's not other parameters. */
        if(parameters == null)
            throw new ParametersException("This command must to have parameters.");

        CustomList auxEmail = parameters.get("email"), auxName = parameters.get("name");

        if(auxEmail == null || auxName == null || parameters.size() != 2)
            throw new ParametersException("This command can only support name and email parameters.");

        /* Validate parameters type. */
        email = auxEmail.getString(0);
        if(email == null)
            throw new InvalidTypeException("email must be a text value.");

        name = auxName.getString(0);
        if(name == null)
            throw new InvalidTypeException("name must be a text value.");
    }

    /**
     * Verify if there's the parameter needed to this command and validate's it's type.
     * Supported path parameter:
     * -> num - Int
     * @param pathParameters
     * @throws InvalidTypeException
     * @throws SQLException
     * @throws NotExistInformationException
     */
    private void validatePathParameters(CustomMap<String, String> pathParameters) throws InvalidTypeException {
        /* Validate pathParameter type */
        number = pathParameters.getInt("{num}");
        if(number == null)
            throw new InvalidTypeException("{num} must be an Integer value.");

    }

    /**
     * Validate if it's possible to perform the query without happening a sql exception.
     * Must verify:
     * -> If the student with number Num doesn't exist already.
     * @param conn
     * @throws SQLException
     * @throws NotExistInformationException
     */
    private void validateExistingInformationInDb(Connection conn) throws SQLException, NotExistInformationException {
        /* Validate if exists the teacher with number num. */
        Validation.validateExistentTeachersNum(conn, number);
    }
}