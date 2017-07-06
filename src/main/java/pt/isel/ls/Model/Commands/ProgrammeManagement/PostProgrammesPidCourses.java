package pt.isel.ls.Model.Commands.ProgrammeManagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.CustomExceptions.InvalidTypeException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.DataStructures.CustomMap;
import pt.isel.ls.Model.Entities.Course;
import pt.isel.ls.Model.Entities.EntityCommand;
import pt.isel.ls.Model.Results.ProgrammeManagementResults.PostProgrammesPidCoursesResultError;
import pt.isel.ls.Model.Results.RedirectResult;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Validators.ErrorValidator;
import pt.isel.ls.Model.Validators.Validation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostProgrammesPidCourses implements Command {
    private String courseName, pid, acr;
    private Integer[] semesters;
    private Integer numSemesters;
    private Boolean mandatory;
    private ErrorValidator errorValidator;
    private static final Logger _logger = LoggerFactory.getLogger(PostProgrammesPidCourses.class);

    public PostProgrammesPidCourses() {
    }

    public PostProgrammesPidCourses(String description, String template) {
        commands.add(new EntityCommand(template, description));
    }

    /**
     * Create a new course in programme with ID pid.
     * @param pathParameters CustomMap<String, String> that will have the parameters passed in the path.
     * @param parameters CustomMap<String, CustomList<String>> that contains the parameters.
     * @param conn Connection with DataBase.
     * @return The result associated to this command.
     * @throws SQLException
     * @throws InvalidTypeException
     */
    @Override
    public Result execute(CustomMap<String, String> pathParameters, CustomMap<String, CustomList<String>> parameters, Connection conn) throws InvalidTypeException, SQLException {
        _logger.info("Validate Information to execute the command.");

        errorValidator = new ErrorValidator();

        /* Verify that everything is correct to proceed with the command execution. */
        validatePathParameters(pathParameters);
        validateParameters(parameters);
        validateExistingInformationInDb(conn);

        if(errorValidator.hasError())
            return new PostProgrammesPidCoursesResultError(errorValidator.getErrors(), pid);

        _logger.info("Beginning to execute the command POST /programmes/{}/courses acr={}&mandatory={}&numSemesters={}", pid, acr, mandatory,numSemesters);

        /* Query to insert the course into programme with ID pid. */
        String insertCourProgrCurr = "INSERT INTO CourProgrCurr(pid, cName, curricularSemester, mandatory) VALUES (?, ?, ?, ?)";

        PreparedStatement ps;
        for (int i = 0; i < numSemesters; i++) {
            ps = conn.prepareStatement(insertCourProgrCurr);
            ps.setString(1, pid);
            ps.setString(2, courseName);
            ps.setInt(3, semesters[i]);
            ps.setBoolean(4, mandatory);
            ps.execute();
        }

        _logger.info("End the execute of the command POST /programmes/{}/courses acr={}&mandatory={}&numSemesters={}", pid, acr, mandatory,numSemesters);

        /* Return the result associated to this command. */
        return new RedirectResult("/programmes/" + pid + "/courses");
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
     * Validate if every parameter exists and its in is correct type.
     * @param parameters CustomMap<String, CustomList<String>> that contains the parameters.
     */
    private void validateParameters(CustomMap<String, CustomList<String>> parameters) {
        if(parameters == null || parameters.size() > 3) {
            errorValidator.addError("semesters", "This parameter must exist.");
            errorValidator.addError("acr", "This parameter must exist.");
            errorValidator.addError("mandatory", "This parameter must exist.");
            return;
        }

        CustomList mandatoryAux = parameters.get("mandatory"), acrAux = parameters.get("acr"), semesterAux = parameters.get("semesters");
        if(mandatoryAux == null)
            errorValidator.addError("mandatory", "This parameter must exist.");

        if(acrAux == null)
            errorValidator.addError("acr", "This parameter must exist.");

        if(semesterAux == null)
            errorValidator.addError("semesters", "This parameter must exist.");

        /* If it has error stop the validation. */
        if(errorValidator.hasError())
            return;

        /* Verify mandatory, acr and semesters parameters type. */
        mandatory = mandatoryAux.getVerifiedBoolean(0, errorValidator, "mandatory");
        String sems =  (String)semesterAux.get(0);
        acr = acrAux.getVerifiedString(0, errorValidator, "acr");

        if(acr.contains(" "))
            errorValidator.addError("pid","This parameter cannot have spaces.");


        /* Filter semester spaces and add to int[] */
        String aux[] = sems.split(",");
        semesters = new Integer[aux.length];

        if (aux.length <= 1) {
            if (parameters.isNumeric(sems))
                semesters[0] = Integer.parseInt(sems);
            else errorValidator.addError("semesters", "Semester must be an Integer or set of Integers");
        }
        else {
            for (int i = 0; i < aux.length; i++) {
                if (!parameters.isNumeric(aux[i])) {
                    errorValidator.addError("semesters", "Contained a not valid semester value.");
                    break;
                }
                semesters[i] = Integer.parseInt(aux[i]);
            }
        }

        numSemesters = semesters.length;
    }

    /**
     * Validate if it's possible to perform the query without happening a sql exception.
     * @param conn Connection with DataBase.
     * @throws SQLException
     */
    private void validateExistingInformationInDb(Connection conn) throws SQLException {
        boolean exist = false;
        boolean existInSemester = false;
        String semestersString = "";

        Course course = Validation.validateExistentCoursesAcr(conn, acr, errorValidator, "acr");

        if(errorValidator.hasError())
            return;

        courseName =  course.getName();

        /* Query to select information from the table that is the joining of courses, programmes and curricular semesters
         * about the programme with ID pid and with course name courseName.*/
        String select = "SELECT pid, cName, curricularSemester, mandatory FROM CourProgrCurr WHERE pid = ? and cName = ?";

        /* Build prepared statement. */
        PreparedStatement ps = conn.prepareStatement(select);
        ps.setString(1, pid);
        ps.setString(2, courseName);
        ResultSet resultSet = ps.executeQuery();

        /* Verify if the course doesn't exist already or if exist if its possible to insert it again. */
        while (resultSet.next()) {
            exist = true;
            for (int i = 0; i < numSemesters; i++) {
                if (resultSet.getInt(3) == semesters[i]){
                    existInSemester = true;
                    semestersString += semesters[i] + " ";
                }
            }
            if (Boolean.getBoolean(resultSet.getString(4)))
                errorValidator.addError("acr", "This course already exists as mandatory.");
        }

        if(existInSemester)
            errorValidator.addError("semesters", "This courses already exists in the semester: " +  semestersString);

        if (exist && mandatory)
            errorValidator.addError("mandatory", "Can't add this courses since it already exists as an optional course");

        if (numSemesters > 1 && mandatory)
            errorValidator.addError("mandatory", "This course can only be mandatory in one semester.");
    }
}