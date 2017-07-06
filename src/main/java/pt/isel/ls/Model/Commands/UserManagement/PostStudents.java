package pt.isel.ls.Model.Commands.UserManagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.CustomExceptions.NotExistInformationException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.DataStructures.CustomMap;
import pt.isel.ls.Model.Entities.EntityCommand;
import pt.isel.ls.Model.Results.RedirectResult;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Results.UserManagementResults.PostStudentsResultError;
import pt.isel.ls.Model.Validators.ErrorValidator;
import pt.isel.ls.Model.Validators.Validation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PostStudents implements Command {
    private String pid, email, name;
    private Integer num;
    private ErrorValidator errorValidator;
    private static final Logger _logger = LoggerFactory.getLogger(PostStudents.class);

    public PostStudents() {
    }

    public PostStudents(String description, String template) {
        commands.add(new EntityCommand(template, description));
    }

    /**
     * creates a new student, given its parameters
     * @param pathParameters
     * @param parameters
     * @param conn
     * @return
     * @throws SQLException
     * @throws NotExistInformationException
     */
    public Result execute(CustomMap<String, String> pathParameters, CustomMap<String, CustomList<String>> parameters, Connection conn) throws SQLException, NotExistInformationException {
        _logger.info("Validate Information to execute the command.");

        errorValidator =  new ErrorValidator();

        validateParameters(parameters);

        _logger.info("Beginning to execute the command POST /students num={}&name={}&email={}&pid={}", num, name, email, pid);

        validateExistingInformationInDb(conn);

        if(errorValidator.hasError())
            return new PostStudentsResultError(errorValidator.getErrors());

        /* Initialize variables. */
        String insertStudent = "INSERT INTO student(number, pid, email, name) VALUES(?,?,?,?)";

        /* Build prepared statement. */
        PreparedStatement ps = conn.prepareStatement(insertStudent);
        ps.setInt(1, num);
        ps.setString(2, pid);
        ps.setString(3, email);
        ps.setString(4, name);
        ps.execute();

        _logger.info("End the execute of the command POST /students num={}&name={}&email={}&pid={}", num, name, email, pid);

        /* Return query result . */
        return new RedirectResult("/students/" + num);
    }

    /**
     * checks if all the parameters expected were passed
     * @param parameters
     */
    private void validateParameters(CustomMap<String, CustomList<String>> parameters) {
        /* Verify if the parameters are the students number. */
        if(parameters == null || parameters.size() > 4){
            errorValidator.addError("name", "This parameter must exist.");
            errorValidator.addError("pid", "This parameter must exist.");
            errorValidator.addError("num", "This parameter must exist.");
            errorValidator.addError("email", "This parameter must exist.");
            return;
        }

        /* Verify if the parameters are correct. */
        CustomList nameAux = parameters.get("name"), pidAux = parameters.get("pid"),
                   numAux = parameters.get("num"), emailAux = parameters.get("email");

        if(nameAux == null)
            errorValidator.addError("name", "This parameter must exist.");

        if(pidAux == null)
            errorValidator.addError("pid", "This parameter must exist.");

        if(numAux == null)
            errorValidator.addError("num", "This parameter must exist.");

        if(emailAux == null)
            errorValidator.addError("email", "This parameter must exist.");

        if(errorValidator.hasError())
            return;

        /* Verify num, pid email and name parameters type. */
        num = numAux.getVerifiedInt(0, errorValidator, "num");
        pid = pidAux.getVerifiedString(0, errorValidator, "pid");
        email = emailAux.getVerifiedString(0, errorValidator, "email");
        name = nameAux.getVerifiedString(0, errorValidator, "name");
    }

    private void validateExistingInformationInDb(Connection conn) throws SQLException, NotExistInformationException {
        if (num != null)
            Validation.validateNotExistentStudentNum(conn, num, errorValidator, "num");
        Validation.validateExistentProgrammesPid(conn, pid, errorValidator, "pid");
    }
}