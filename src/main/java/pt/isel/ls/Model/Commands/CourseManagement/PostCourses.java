package pt.isel.ls.Model.Commands.CourseManagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.DataStructures.CustomMap;
import pt.isel.ls.Model.Entities.EntityCommand;
import pt.isel.ls.Model.Results.CourseManagementResults.PostCoursesResultError;
import pt.isel.ls.Model.Results.RedirectResult;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Validators.ErrorValidator;
import pt.isel.ls.Model.Validators.Validation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PostCourses implements Command {
    private String name, acr;
    private Integer tNumber;
    private ErrorValidator errorValidator;
    private static final Logger _logger = LoggerFactory.getLogger(PostCourses.class);

    public PostCourses(){
    }

    public PostCourses(String description, String template) {
        commands.add(new EntityCommand(template, description));
    }

    /**
     * Create a new course.
     * @param pathParameters CustomMap<String, String> that will have the parameters passed in the path.
     * @param parameters CustomMap<String, CustomList<String>> that contains the parameters.
     * @param conn Connection with DataBase.
     * @return The result associated to this command.
     * @throws SQLException
     */
    @Override
    public Result execute(CustomMap<String, String> pathParameters, CustomMap<String, CustomList<String>> parameters, Connection conn) throws SQLException {
        _logger.info("Validate Information to execute the command.");

        errorValidator = new ErrorValidator();

        /* Verify that everything is correct to proceed with the command execution. */
        validateParameters(parameters);
        validateExistingInformationInDb(conn);

        _logger.info("Beginning to execute the command POST /courses name={}&acr={}&teacherNum={}.", name, acr, tNumber);

        if(errorValidator.hasError())
            return new PostCoursesResultError(errorValidator.getErrors());



        /* Query to insert a new course. */
        String insertCourse = "INSERT INTO course VALUES(?, ?, ?)";

        /* Build prepared statement. */
        PreparedStatement ps = conn.prepareStatement(insertCourse);
        ps.setString(1, name);
        ps.setString(2, acr);
        ps.setInt(3, tNumber);
        ps.execute();

        _logger.info("End the execute of the command POST /courses name={}&acr={}&teacherNum={}.", name, acr, tNumber);

        /* Return the result associated to this command. */
        return new RedirectResult("/courses/" + acr);
    }

    /**
     * Validate if every parameter exists and its in is correct type.
     * @param parameters CustomMap<String, CustomList<String>> that contains the parameters.
     */
    private void validateParameters(CustomMap<String, CustomList<String>> parameters) {
        /* Verify if the parameters are correct. */
        if(parameters == null || parameters.size() > 3) {
            errorValidator.addError("name", "This parameter must exist.");
            errorValidator.addError("acr", "This parameter must exist.");
            errorValidator.addError("teacher", "This parameter must exist.");
            return;
        }

        CustomList nameAux = parameters.get("name"), acrAux = parameters.get("acr"), numAux = parameters.get("teacher");
        if(nameAux == null)
            errorValidator.addError("name", "This parameter must exist.");

        if(acrAux == null)
            errorValidator.addError("acr", "This parameter must exist.");

        if(numAux == null)
            errorValidator.addError("teacher", "This parameter must exist.");

        /* If it has error stop the validation. */
        if(errorValidator.hasError())
            return;

        /* Verify name, acr and num parameters type. */
        name = nameAux.getVerifiedString(0, errorValidator, "name");
        acr = acrAux.getVerifiedString(0, errorValidator, "acr");
        tNumber = numAux.getVerifiedInt(0, errorValidator, "teacher");

        if(acr.contains(" "))
            errorValidator.addError("acr", "This parameter cannot have spaces.");

    }

    /**
     * Validate if it's possible to perform the query without happening a sql exception.
     * @param conn Connection with DataBase.
     * @throws SQLException
     */
    private void validateExistingInformationInDb(Connection conn) throws SQLException {
        /* Validate if doesn't exist the course with acr already. */
        Validation.validateNotExistentCoursesAcr(conn, acr, errorValidator, "acr");
        if (tNumber != null)
            /* Validate if exist the teacher with number tNumber. */
            Validation.validateExistentTeachersNum(conn, tNumber, errorValidator, "teacher");
    }
}