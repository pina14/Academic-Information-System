package pt.isel.ls.Model.Commands.UserManagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.DataStructures.CustomMap;
import pt.isel.ls.Model.Entities.EntityCommand;
import pt.isel.ls.Model.Results.RedirectResult;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Results.UserManagementResults.PostTeachersResultError;
import pt.isel.ls.Model.Validators.ErrorValidator;
import pt.isel.ls.Model.Validators.Validation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PostTeachers implements Command {
    private Integer num;
    private String email, name;
    private static final Logger _logger = LoggerFactory.getLogger(PostTeachers.class);
    private ErrorValidator errorValidator;

    public PostTeachers() {
    }

    public PostTeachers(String description, String template) {
        commands.add(new EntityCommand(template, description));
    }

    /**
     * creates a new teacher, given the following parameters
     * @param pathParameters
     * @param parameters
     * @param conn
     * @return
     * @throws SQLException
     */
    public Result execute(CustomMap<String, String> pathParameters, CustomMap<String, CustomList<String>> parameters, Connection conn) throws SQLException {
        _logger.info("Validate Information to execute the command.");

        errorValidator =  new ErrorValidator();

        validateParameters(parameters);

        _logger.info("Beginning to execute the command POST /teachers num={}&name={}&email={}", num, name, email);

        validateExistingInformationInDb(conn);

        if(errorValidator.hasError())
            return new PostTeachersResultError(errorValidator.getErrors());

        /* Initialize variables. */
        String insertTeacher = "INSERT INTO teacher(number, email, name) VALUES(?,?,?)";

        /* Build prepared statement. */
        PreparedStatement ps = conn.prepareStatement(insertTeacher);
        ps.setInt(1, num);
        ps.setString(2, email);
        ps.setString(3, name);
        ps.execute();

        _logger.info("End the execute of the command POST /teachers num={}&name={}&email={}", num, name, email);

        /* Return query result. */
        return new RedirectResult("/teachers/" + num);
    }

    /**
     * checks if all the parameters expected were passed
     * @param parameters
     */
    private void validateParameters(CustomMap<String, CustomList<String>> parameters) {
        /* Verify if the parameters are the students number. */
        if(parameters == null || parameters.size() > 3){
            errorValidator.addError("name", "This parameter must exist.");
            errorValidator.addError("num", "This parameter must exist.");
            errorValidator.addError("email", "This parameter must exist.");
            return;
        }

        /* Verify if the parameters are correct. */
        CustomList nameAux = parameters.get("name"), numAux = parameters.get("num"), emailAux = parameters.get("email");

        if(nameAux == null)
            errorValidator.addError("name", "This parameter must exist.");

        if(numAux == null)
            errorValidator.addError("num", "This parameter must exist.");

        if(emailAux == null)
            errorValidator.addError("email", "This parameter must exist.");

        if(errorValidator.hasError())
            return;

        /* Verify num, pid email and name parameters type. */
        num = numAux.getVerifiedInt(0, errorValidator, "num");
        email = emailAux.getVerifiedString(0, errorValidator, "email");
        name = nameAux.getVerifiedString(0, errorValidator, "name");
    }

    private void validateExistingInformationInDb(Connection conn) throws SQLException {
        if (num != null)
            Validation.validateNotExistentTeachersNum(conn, num, errorValidator, "num");
    }
}