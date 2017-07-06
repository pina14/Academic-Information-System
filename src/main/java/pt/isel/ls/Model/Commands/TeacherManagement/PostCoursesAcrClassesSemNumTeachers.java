package pt.isel.ls.Model.Commands.TeacherManagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.CustomExceptions.InvalidTypeException;
import pt.isel.ls.Model.CustomExceptions.NotExistInformationException;
import pt.isel.ls.Model.CustomExceptions.ParametersException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.DataStructures.CustomMap;
import pt.isel.ls.Model.Entities.Course;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Entities.EntityCommand;
import pt.isel.ls.Model.Results.RedirectResult;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Results.TeacherManagementResults.PostCoursesAcrClassesSemNumTeachersResultError;
import pt.isel.ls.Model.Validators.ErrorValidator;
import pt.isel.ls.Model.Validators.Validation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Add a new teacher represented by numDoc to a class
 * in the course represented by acr, of semester sem with ID num.
 */
public class PostCoursesAcrClassesSemNumTeachers implements Command {
    private  CustomMap<String, String> semesterTime = new CustomMap<>();
    private String acr, sem, num, courseName;
    private Integer year, number;
    private ErrorValidator errorValidator;
    private static final Logger _logger = LoggerFactory.getLogger(PostCoursesAcrClassesSemNumTeachers.class);

    public PostCoursesAcrClassesSemNumTeachers() {
    }

    public PostCoursesAcrClassesSemNumTeachers(String description, String template) {
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
     * Perform the insertion of a teacher in the given class if possible.
     */


    /**
     *
     * @param pathParameters
     * @param parameters
     * @param conn
     * @return
     * @throws ParametersException
     * @throws InvalidTypeException
     * @throws SQLException
     */
    @Override
    public Result execute(CustomMap<String, String> pathParameters, CustomMap<String, CustomList<String>> parameters, Connection conn) throws ParametersException, InvalidTypeException, SQLException {
        _logger.info("Validate Information to execute the command.");

        errorValidator = new ErrorValidator();

        /* Fill the semester time with corresponding letter to it's season. */
        fillSemesterTime();

        _logger.info("Validate information to execute the command.");

        /* Validate if it's everything fine to continue the command. */
        validatePathParameters(pathParameters);
        validateParameters(parameters);
        validateExistingInformationInDb(conn);

        _logger.info("Beginning to execute the command POST /courses/{}/classes/{}/{}/teachers", acr, sem, num);

        if(errorValidator.hasError())
            return new PostCoursesAcrClassesSemNumTeachersResultError(errorValidator.getErrors(), acr, sem, num, year);


        /* Variable initialization.
         * Query that inserts the teacher with number numDoc to the class of the course with acronym acr,
         * in the semester Sem and with ID Num.
         */
        String addTeacherToClass = "INSERT INTO classTeacher(cID, cName, aYear, aSemester, tNumber) \n\t" +
                                   "VALUES (?, ?, ?, ?, ?)";

        /* Set the prepared statement to perform the query. */
        PreparedStatement ps = conn.prepareStatement(addTeacherToClass);
        ps.setString(1,num);
        ps.setString(2,courseName);
        ps.setInt(3,year);
        ps.setString(4,sem);
        ps.setInt(5,number);
        ps.execute();

        _logger.info("End the execution of the command POST /courses/{}/classes/{}/{}/teachers", acr, sem, num);

        /* Return query result. */
        return new RedirectResult("/courses/"+acr+"/classes/"+pathParameters.get("{sem}")+"/"+num+"/teachers");
    }

    /**
     * Verify if it contain the supported parameters and also verifies it's types.
     * Supported parameters and its types:
     * -> numDoc - CustomList<String> (contains the number to be casted to Int).
     * @param parameters
     * @throws ParametersException
     * @throws InvalidTypeException
     * @throws NotExistInformationException
     */
    private void validateParameters(CustomMap<String, CustomList<String>> parameters) throws ParametersException {
        /* Verify if it has parameters. */
        if(parameters == null)
            throw new ParametersException("This command must have parameters.");

        CustomList<String> aux = parameters.get("numDoc");

        /* Verify if the parameter is only the teacher number. */
        if(parameters.size() != 1 || aux == null)
            throw new ParametersException("This command only accepts numDoc has parameter");

        number = aux.getVerifiedInt(0, errorValidator, "numDoc");
    }

    /**
     * Verify if there's the parameters needed to this command and validate's it's types
     * Supported path parameters and its types:
     * -> sem - String, it will divide in two variables(semesterChar and year), semesterChar must be of String type and year must be an Int.
     * -> acr - String
     * -> num - String
     * @param pathParameters
     * @throws InvalidTypeException
     * @throws SQLException
     * @throws NotExistInformationException
     */
    private void validatePathParameters(CustomMap<String, String> pathParameters) throws ParametersException, InvalidTypeException {
        String semester = pathParameters.get("{sem}");
        if(semester == null || semester.length() != 5)
            throw new ParametersException("{sem} must be a four character number indicating the year and 'v' or 'i' to indicate the semester.");

        String semesterChar = semester.substring(semester.length() - 1);
        if(!semesterChar.equals("i") && !semesterChar.equals("v"))
            throw new ParametersException(semester + " the last letter must be either i or v, to represent the semester.\n");
        sem = semesterTime.getString(semesterChar);

        String textYear = semester.substring(0, semester.length() - 1);
        if(!pathParameters.isNumeric(textYear) )
            throw new InvalidTypeException("The first 4 characters in {sem} must be a year.");
        year =  Integer.parseInt(textYear);

        acr = pathParameters.getString("{acr}");
        if(acr == null)
            throw new InvalidTypeException("{acr} must be a text value.");

        num = pathParameters.getString("{num}");
        if(num == null || num.length() != 2)
            throw new InvalidTypeException("{num} must be a 2 characters value.");
    }

    /**
     * Validate if it's possible to perform the query without happening a sql exception.
     * Must verify:
     * -> If the course with acronym acr exists.
     * -> If the class identified by Sem and Num exists.
     * -> If the teacher with number numDoc exists.
     * -> If the teacher with number numDoc doesn't exist in the class already.
     * @param conn
     * @throws SQLException
     * @throws NotExistInformationException
     */
    private void validateExistingInformationInDb(Connection conn) throws SQLException {
        Entity course =  Validation.validateExistentCoursesAcr(conn, acr, errorValidator, "acr");
        Validation.validateExistentClassesSemNum(conn, num, year, sem, errorValidator, "num", "sem");
        if(errorValidator.hasError())
            return;

        Validation.validateExistentCoursesAcrClassesSemNum(conn, num, acr, year, sem, errorValidator, "num", "acr", "sem");
        if(errorValidator.hasError())
            return;

        courseName = ((Course)course).getName();

        /* Validate if exists the teacher with number num. */
        Validation.validateExistentTeachersNum(conn, number, errorValidator, "numDoc");
        if(errorValidator.hasError())
            return;

        /* Verify if exists teacher with that number in the class already. */
        String classTeacher = "SELECT * FROM classTeacher\n" +
                "WHERE cId = ? AND cName = ? AND aYear = ? AND aSemester = ? AND tNumber = ?";

        PreparedStatement ps = conn.prepareStatement(classTeacher);
        ps.setString(1, num);
        ps.setString(2, courseName);
        ps.setInt(3, year);
        ps.setString(4, sem);
        ps.setInt(5, number);
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            errorValidator.addError("numDoc","There are already a teacher with number " + number + " assigned to the class represented by " + num + " in the year " + year + " on semester " + sem + " of course " + this.courseName);
    }
}