package pt.isel.ls.Model.Commands.ProgrammeManagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.CustomExceptions.NotExistInformationException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.DataStructures.CustomMap;
import pt.isel.ls.Model.Entities.EntityCommand;
import pt.isel.ls.Model.Results.ProgrammeManagementResults.PostProgrammesResultError;
import pt.isel.ls.Model.Results.RedirectResult;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Validators.ErrorValidator;
import pt.isel.ls.Model.Validators.Validation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostProgrammes implements Command {
    private String pid;
    private String name;
    private Integer numSemester;
    private ErrorValidator errorValidator;
    private static final Logger _logger = LoggerFactory.getLogger(PostProgrammes.class);

    public PostProgrammes(){
    }

    public PostProgrammes(String description, String template) {
        commands.add(new EntityCommand(template, description));
    }

    /**
     * Create a new programme.
     * @param pathParameters CustomMap<String, String> that will have the parameters passed in the path.
     * @param parameters CustomMap<String, CustomList<String>> that contains the parameters.
     * @param conn Connection with DataBase.
     * @return The result associated to this command.
     * @throws SQLException
     * @throws NotExistInformationException
     */
    public Result execute(CustomMap<String, String> pathParameters, CustomMap<String, CustomList<String>> parameters, Connection conn) throws SQLException, NotExistInformationException {
        _logger.info("Validate Information to execute the command.");

        errorValidator = new ErrorValidator();

        /* Verify that everything is correct to proceed with the command execution. */
        validateParameters(parameters);
        validateExistingInformationInDb(conn);

        if(errorValidator.hasError())
            return new PostProgrammesResultError(errorValidator.getErrors());

        _logger.info("Beginning to execute the command POST /programmes pid={}&name={}&length={}", pid, name, numSemester);

        /* Query to insert new programme. */
        String insertProgramme = "INSERT INTO programme VALUES(?, ?, ?)";

        /* Build prepared statement. */
        PreparedStatement ps = conn.prepareStatement(insertProgramme);
        ps.setString(1, pid);
        ps.setString(2, name);
        ps.setInt(3, numSemester);
        ps.execute();

        /* In case of not exist any semester that was received in parameters create it.*/
        resolveCurricularSemester(conn);

        _logger.info("End the execute of the command POST /programmes pid={}&name={}&length={}", pid, name, numSemester);

        /* Return the result associated to this command. */
        return new RedirectResult("/programmes/" + pid);
    }

    /**
     * Validate if every parameter exists and its in is correct type.
     * @param parameters CustomMap<String, CustomList<String>> that contains the parameters.
     */
    private void validateParameters(CustomMap<String, CustomList<String>> parameters) {
        /* Verify if the parameters are correct. */
        if(parameters == null || parameters.size() > 3) {
            errorValidator.addError("name", "This parameter must exist.");
            errorValidator.addError("pid", "This parameter must exist.");
            errorValidator.addError("length", "This parameter must exist.");
            return;
        }

        /* Verify if the parameters are the students number. */
        CustomList nameAux = parameters.get("name"), pidAux = parameters.get("pid"), numAux = parameters.get("length");
        if(nameAux == null)
            errorValidator.addError("name", "This parameter must exist.");

        if(pidAux == null)
            errorValidator.addError("pid", "This parameter must exist.");

        if(numAux == null)
            errorValidator.addError("length","This parameter must exist.");

        /* If it has error stop the validation. */
        if(errorValidator.hasError())
            return;

        /* Verify name, pid and num parameters type. */
        name = nameAux.getVerifiedString(0, errorValidator, "name");
        pid = pidAux.getVerifiedString(0, errorValidator, "pid");
        numSemester = numAux.getVerifiedInt(0, errorValidator, "length");

        if(pid.contains(" "))
            errorValidator.addError("pid","This parameter cannot have spaces.");

    }

    /**
     * Validate if it's possible to perform the query without happening a sql exception.
     * @param conn Connection with DataBase.
     * @throws SQLException
     */
    private void validateExistingInformationInDb(Connection conn) throws SQLException, NotExistInformationException {
        /* Validate that doesn't exist programme with ID pid already. */
        Validation.validateNotExistentProgrammesPid(conn, pid, errorValidator, "pid");
    }

    /**
     * Insert each curricular semester that doesn't exist that was received by this command.
     * @param conn Connection with DataBase.
     * @throws SQLException
     */
    private void resolveCurricularSemester(Connection conn) throws SQLException {
        /* Query to select every curricular semester. */
        String selectCSemester = "SELECT * FROM curricularSemester";
        PreparedStatement ps;
        ps = conn.prepareStatement(selectCSemester);
        ResultSet rs = ps.executeQuery();

        int count = 0;
        while (rs.next())
            count++;

        /* Insert each curricular semester that doesn't exist. */
        if(count < numSemester) {
            String insertCSemesters = "INSERT INTO curricularSemester VALUES (?)";
            for (int i = count + 1; i <= numSemester; i++) {
                ps = conn.prepareStatement(insertCSemesters);
                ps.setInt(1, i);
                ps.execute();
            }
        }
    }
}