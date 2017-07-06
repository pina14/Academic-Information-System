package pt.isel.ls.Model.Commands.StudentManagement;

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

public class PutStudentsNum implements Command {
    private Integer num;
    private String email, pid, name;
    private static final Logger _logger = LoggerFactory.getLogger(PutStudentsNum.class);

    public PutStudentsNum() {
    }

    public PutStudentsNum(String description, String template) {
        commands.add(new EntityCommand(template, description));
    }

    /**
     * Perform the update of the giving Student.
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

        /* Validate if it's everything fine to continue the command. */
        validateParameters(parameters);
        validatePathParameters(pathParameters);
        validateExistingInformationInDb(conn);

        _logger.info("Beginning to execute the command PUT /students/{} email={}&pid={}&name={}", num, email, pid, name);

        /* Initialize variables.
         * Query that update the email , pid and name from the student with the number num. */
        String update = "UPDATE student \n" +
                        "SET email = ? , pid = ?, name = ?\n" +
                        "WHERE number = ?";

        /* Build the prepared statement. */
        PreparedStatement ps = conn.prepareStatement(update);
        ps.setString(1, email);
        ps.setString(2, pid);
        ps.setString(3, name);
        ps.setInt(4, num);

        /* Return the associated result to this command. */
        ps.execute();

        _logger.info("End of the execution the command PUT /students/{} email={}&pid={}&name={}", num, email, pid, name);

        return new NullResult();
    }

    /**
     * Verify if it contain the supported parameters and also verifies it's types.
     * @param parameters CustomMap<String, CustomList<String>> that contains the parameters.
     * @throws ParametersException
     * @throws InvalidTypeException
     */
    private void validateParameters(CustomMap<String, CustomList<String>> parameters) throws ParametersException, InvalidTypeException {
        /* Validate if the needed parameters exists and if there's not other parameters. */
        if(parameters == null)
            throw new ParametersException("This command has to have parameters.");

        CustomList auxEmail = parameters.get("email"), auxPid = parameters.get("pid"), auxName = parameters.get("name");

        if(auxEmail == null || auxPid == null || auxName == null || parameters.size() != 3)
            throw new ParametersException("This command can only support num, pid and email parameters.");

        /* Validate parameters type. */
        email = auxEmail.getString(0);
        if(email == null)
            throw new InvalidTypeException("email must be a text value.");

        pid = auxPid.getString(0);
        if(pid == null)
            throw new InvalidTypeException("pid must be a text value.");

        name = auxName.getString(0);
        if(name == null)
            throw new InvalidTypeException("name must be a text value..");
    }

    /**
     * Verify if there's the parameter needed to this command and validate's it's type.
     * @param pathParameters CustomMap<String, String> that will have the parameters passed in the path.
     */
    private void validatePathParameters(CustomMap<String, String> pathParameters) throws ParametersException {
        /* Validate pathParameter type. */
        num = pathParameters.getInt("{num}");
        if(num == null)
            throw new ParametersException("{num} wasn't properly inserted.");
    }

    /**
     * Validate if it's possible to perform the query without happening a sql exception.
     * @param conn Connection with DataBase.
     * @throws SQLException
     * @throws NotExistInformationException
     */
    private void validateExistingInformationInDb(Connection conn) throws SQLException, NotExistInformationException {
        Validation.validateExistentStudentNum(conn, num);
    }
}