package pt.isel.ls.Model.Commands.ClassManagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.CustomExceptions.InvalidTypeException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.DataStructures.CustomMap;
import pt.isel.ls.Model.Entities.Course;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Entities.EntityCommand;
import pt.isel.ls.Model.Mappers.AcademicSemesters;
import pt.isel.ls.Model.Results.ClassManagementResults.PostCoursesAcrClassesResultError;
import pt.isel.ls.Model.Results.RedirectResult;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Validators.ErrorValidator;
import pt.isel.ls.Model.Validators.Validation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostCoursesAcrClasses implements Command {
    private  CustomMap<String, String> semesterTime = new CustomMap<>();
    private String acr, sem, num, courseName;
    private Integer year;
    private ErrorValidator errorValidator;
    private boolean insertSemCourFlag = false;
    private static final Logger _logger = LoggerFactory.getLogger(PostCoursesAcrClasses.class);

    public PostCoursesAcrClasses() {
    }

    public PostCoursesAcrClasses(String description, String template) {
        commands.add(new EntityCommand(template, description));
    }

    /**
     * Put the corresponding string of the SQL to the one given by the user.
     */
    private void fillSemesterTime(){
        semesterTime.put("v","summer");
        semesterTime.put("i", "winter");
    }

    /**
     * Create a new class on the course with acr acronym.
     * @param pathParameters CustomMap<String, String> that will have the parameters passed in the path.
     * @param parameters CustomMap<String, CustomList<String>> that contains the parameters.
     * @param conn Connection with DataBase.
     * @return The result associated to this command.
     * @throws SQLException
     * @throws InvalidTypeException
     */
    @Override
    public Result execute(CustomMap<String, String> pathParameters, CustomMap<String, CustomList<String>> parameters, Connection conn) throws SQLException, InvalidTypeException {
        _logger.info("Validate Information to execute the command.");

        errorValidator = new ErrorValidator();

        /* Initiate the semester map. */
        fillSemesterTime();

        /* Verify that everything is correct to proceed with the command execution. */
        validatePathParameters(pathParameters);
        validateParameters(parameters);
        validateExistingInformationInDb(conn);

        _logger.info("Beginning to execute the command GET /courses.");

        if(errorValidator.hasError())
            return new PostCoursesAcrClassesResultError(errorValidator.getErrors(), acr);


        /* Queries to insert the respective class. */
        String postSemCour = "INSERT INTO semCour(cName, aSemester, aYear)\n\t" +
                "VALUES (?, ?, ?)";

        String postClass = "INSERT INTO class(id, cName, aYear, aSemester)\n\t" +
                            "VALUES (?, ?, ?, ?)";

        /* Create the academic semester if needed. */
        resolveSemester(sem, year, conn);

       /* Build prepared statement for post semCour. */
        PreparedStatement ps;
        if(insertSemCourFlag) {
            ps = conn.prepareStatement(postSemCour);
            ps.setString(1, courseName);
            ps.setString(2, sem);
            ps.setInt(3, year);

            ps.execute();
        }

        /* Build prepared statement for post Class. */
        ps = conn.prepareStatement(postClass);
        ps.setString(1, num);
        ps.setString(2, courseName);
        ps.setInt(3, year);
        ps.setString(4, sem);

        ps.execute();

        _logger.info("End the execute of the command POST /courses/{}/classes sem={}&num={}.", acr, sem, num);

        /* Return the result associated to this command. */
        return new RedirectResult("/courses/" + acr + "/classes");
    }

    /**
     * Verify if the semester and the year given by the user exists in the dataBase
     * if not a new entry is created in the table academicSemester
     * @param semester String that represents the semester.
     * @param year Integer that represents the year
     * @param conn Connection with DataBase.
     * @throws SQLException
     */
    private void resolveSemester(String semester, int year, Connection conn) throws SQLException {
        String select = "SELECT * FROM academicSemester WHERE academicYear = ? AND semesterTime = ?";
        AcademicSemesters academicsemesters = new AcademicSemesters();
        PreparedStatement ps = conn.prepareStatement(select);

        ps.setInt(1,year);
        ps.setString(2,semester);
        CustomList<Entity> result = academicsemesters.getData(ps.executeQuery());

        if (result.size() != 0)
            return;

        String insert = "INSERT INTO academicSemester (academicYear, semesterTime)" +
                            "values (?, ?)";
        ps = conn.prepareStatement(insert);
        ps.setInt(1,year);
        ps.setString(2,semester);
        ps.execute();
    }


    /**
     * Validate in case of existing parameters if are the correct ones and if they are in their correct form.
     * @param parameters CustomMap<String, CustomList<String>> that contains the parameters.
     */
    private void validateParameters(CustomMap<String, CustomList<String>> parameters) {
        /* Verify if the parameters are correct. */
        if(parameters == null || parameters.size() > 2) {
            errorValidator.addError("num", "This parameter must exist.");
            errorValidator.addError("sem", "This parameter must exist.");
            return;
        }

        CustomList numAux = parameters.get("num"), semAux = parameters.get("sem");
        if(numAux == null)
            errorValidator.addError("num", "This parameter must exist.");

        if (semAux == null)
            errorValidator.addError("sem", "This parameter must exist.");

        /* If it has error stop the validation. */
        if(errorValidator.hasError())
            return;

        /* Verify sem and num parameters type. */
        String semester = parameters.get("sem").get(0), semesterChar = semester, textYear = semester;
        if( semester.length() > 1) {
            semesterChar = semester.substring(semester.length() - 1);
            textYear = semester.substring(0, semester.length() - 1);
        }

        year = !parameters.isNumeric(textYear) ? null : Integer.parseInt(textYear);
        if(year == null)
            errorValidator.addError("sem", "Must be a four character number indicating the year and 'v' or 'i' to indicate the semester.");

        sem = semesterTime.getString(semesterChar);
        if(sem == null)
            errorValidator.addError("sem", "Must be a four character number indicating the year and 'v' or 'i' to indicate the semester.");

        num = numAux.getVerifiedString(0, errorValidator, "num");

        if(num.length() != 2)
            errorValidator.addError("num", "Must be a 2 character value.");
    }

    /**
     * Validate if exist the path parameter and if it is in its correct form.
     * @param pathParameters CustomMap<String, String> that will have the parameters passed in the path.
     * @throws InvalidTypeException
     */
    private void validatePathParameters(CustomMap<String, String> pathParameters) throws InvalidTypeException {
        acr = pathParameters.getString("{acr}");
        if(acr == null)
            throw new InvalidTypeException("{acr} must be a text value.");
    }

    /**
     * Validate if it's possible to perform the query without happening a sql exception.
     * @param conn Connection with DataBase.
     * @throws SQLException
     */
    private void validateExistingInformationInDb(Connection conn) throws SQLException {
        /* Validate that exists the course with acronym acr. */
        Course result =  Validation.validateExistentCoursesAcr(conn, acr, errorValidator, "acr");
        if(errorValidator.hasError())
            return;

        courseName = (result).getName();
        /* Query that selects the classes of the course with courseName in the semester sem and year year. */
        String semCourQuery = "SELECT *\n" +
                              "FROM semCour\n" +
                              "WHERE cName = ? AND aYear = ? AND aSemester = ?";

        /* Build prepared statement. */
        PreparedStatement ps = conn.prepareStatement(semCourQuery);
        ps.setString(1, this.courseName);
        ps.setInt(2, year);
        ps.setString(3, sem);

        /* Execute query. */
        ResultSet rs = ps.executeQuery();

        /* If the class doesn't exist insert it. */
        if (!rs.next())
            insertSemCourFlag = true;

        /* Validate that doesn't exist already the class in the course.*/
        Validation.validateNotExistentCoursesAcrClassesSemNum(conn, num, acr, year, sem, errorValidator, "sem", "num", "acr");
    }
}