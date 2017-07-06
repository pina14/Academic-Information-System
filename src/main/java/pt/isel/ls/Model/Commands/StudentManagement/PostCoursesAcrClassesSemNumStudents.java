package pt.isel.ls.Model.Commands.StudentManagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.Commands.QueryOperations.QueryOperations;
import pt.isel.ls.Model.CustomExceptions.InvalidTypeException;
import pt.isel.ls.Model.CustomExceptions.NotExistInformationException;
import pt.isel.ls.Model.CustomExceptions.ParametersException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.DataStructures.CustomMap;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Entities.EntityCommand;
import pt.isel.ls.Model.Results.RedirectResult;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Results.StudentManagementResults.PostCoursesAcrClassesSemNumStudentsResultError;
import pt.isel.ls.Model.Validators.ErrorValidator;
import pt.isel.ls.Model.Validators.Validation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PostCoursesAcrClassesSemNumStudents implements Command {
    private CustomMap<String, String> semesterTime = new CustomMap<>();
    private String acr, sem, num, courseName;
    private Integer[] numStudents;
    private Integer year;
    private ErrorValidator errorValidator;
    private static final Logger _logger = LoggerFactory.getLogger(PostCoursesAcrClassesSemNumStudents.class);

    public PostCoursesAcrClassesSemNumStudents() {
    }

    public PostCoursesAcrClassesSemNumStudents(String description, String template) {
        commands.add(new EntityCommand(template, description));
    }

    /**
     * Put the corresponding string of the SQL to the one given by the user.
     */
    private void fillSemesterTime() {
        semesterTime.put("v", "summer");
        semesterTime.put("i", "winter");
    }

    /**
     * Perform the insertion of students in the given class if possible.
     * @param pathParameters CustomMap<String, String> that will have the parameters passed in the path.
     * @param parameters CustomMap<String, CustomList<String>> that contains the parameters.
     * @param conn Connection with DataBase.
     * @return The result associated to this command.
     * @throws SQLException
     * @throws ParametersException
     * @throws InvalidTypeException
     * @throws NotExistInformationException
     */
    @Override
    public Result execute(CustomMap<String, String> pathParameters, CustomMap<String, CustomList<String>> parameters, Connection conn) throws SQLException, ParametersException, InvalidTypeException, NotExistInformationException {
        _logger.info("Validate Information to execute the command.");

        errorValidator = new ErrorValidator();
        _logger.info("Validate information to execute the command");

        /* Initiate the semester map. */
        fillSemesterTime();

        /* Validate if it's everything fine to continue the command. */
        validatePathParameters(pathParameters);
        validateParameters(parameters);

        /* Get the students that doesn't belong to the class*/
        CustomList<Entity> studentsNotEnrolled = QueryOperations.queryCoursesAcrClassesSemNumStudentsNotEnrolled(conn, acr, year, sem, num, 0 , 20);

        if(errorValidator.hasError())
            return new PostCoursesAcrClassesSemNumStudentsResultError(errorValidator.getErrors(), acr, sem, num, year, studentsNotEnrolled);

        /* Validate if it's everything fine to continue the command. */
        validateExistingInformationInDb(conn);

        if(errorValidator.hasError())
            return new PostCoursesAcrClassesSemNumStudentsResultError(errorValidator.getErrors(), acr, sem, num, year, studentsNotEnrolled);

        String toLog = "";
        for (int i = 0; i < numStudents.length; i++) {
            toLog += "numStu = " + numStudents[i];
        }

        _logger.info("Beginning to execute the command POST /courses/{}/classes/{}/{}/students ", acr, sem, num, toLog);

        /* Variable initialization.
         * Query that inserts the students with number numStu to the class of the course with acronym acr,
         * in the semester Sem and with ID Num. */
        String insert = "INSERT INTO classStudent VAlUES(?, ?, ?, ?, ?)";

        /* Set the prepared statement to perform the query. */
        PreparedStatement ps = conn.prepareStatement(insert);
        ps.setString(1, num);
        ps.setString(2, courseName);
        ps.setInt(3, year);
        ps.setString(4, sem);

        /* Execute query to insert every student in the class. */
        for (int i = 0; i < numStudents.length; i++) {
            ps.setInt(5, numStudents[i]);
            ps.execute();
        }

        /* Return the associated result to this command. */
        _logger.info("End of the execution the command POST /courses/{}/classes/{}/{}/students ", acr, sem, num, toLog);

        return new RedirectResult("/courses/"+acr+"/classes/"+pathParameters.get("{sem}")+"/"+num+"/students");
    }

    /**
     * Verify if it contain the supported parameters and also verifies it's types.
     * @param parameters CustomMap<String, CustomList<String>> that contains the parameters.
     * @throws ParametersException
     * @throws InvalidTypeException
     */
    private void validateParameters(CustomMap<String, CustomList<String>> parameters) throws ParametersException, InvalidTypeException {
        /* Verify if the parameters are the students number. */
        if(parameters == null){
            errorValidator.addError("numStu", "This parameter must exist.");
            return;
        }

        CustomList numStuds = parameters.get("numStu");
        if(parameters.size() != 1 || numStuds == null){
            errorValidator.addError("numStu", "This parameter must exist.");
            return;
        }

        /* Verify student numbers types. */
        numStudents = new Integer[numStuds.size()];
        Integer curr;
        boolean hasError = false;
        String wrongNumbers = "";
        for (int i = 0; i < numStudents.length; i++) {
            curr = numStuds.getInt(i);
            if(curr == null){
                hasError = true;
                wrongNumbers += numStuds.get(i) + " ";
            }
            numStudents[i] = curr;
        }
        if(hasError)
            errorValidator.addError("numStu", "The values " + wrongNumbers + " weren't in their correct form.");

        if(num.length() != 2)
            errorValidator.addError("num", "Must be a 2 character value.");
    }

    /**
     * Verify if there's the parameters needed to this command and validate's it's types.
     * @param pathParameters CustomMap<String, String> that will have the parameters passed in the path.
     * @throws ParametersException
     * @throws InvalidTypeException
     */
    private void validatePathParameters(CustomMap<String, String> pathParameters) throws ParametersException, InvalidTypeException {
        String semester = pathParameters.get("{sem}");
        if(semester == null || semester.length() != 5)
            throw new ParametersException("{sem} wasn't properly inserted.");

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
        if(num == null)
            throw new ParametersException("{num} must be a 2 characters value.");
    }

    /**
     * Validate if it's possible to perform the query without happening a sql exception.
     * @param conn Connection with DataBase.
     * @throws SQLException
     * @throws NotExistInformationException
     */
    private void validateExistingInformationInDb(Connection conn) throws SQLException, NotExistInformationException {
        courseName = Validation.validateExistentCoursesAcr(conn, acr).getName();
        Validation.validateExistentClassesSemNum(conn, num, year, sem);
        Validation.validateExistentCoursesAcrClassesSemNum(conn, num, acr, year, sem);

        String errorString = "";
        boolean hasError = false;

        /* For each student number verify that the student exist and verify if its not already in the class. */
        for (int i = 0; i < numStudents.length; i++) {
            Validation.validateExistentStudentNum(conn, numStudents[i]);
            if(QueryOperations.queryCoursesAcrClassesSemNumStudentNum(conn, num, courseName, year, sem, numStudents[i]) != null){
                hasError = true;
                errorString += numStudents[i] + " ";
            }
        }
        if(hasError)
            errorValidator.addError("numStu", "The students with the numbers " + errorString + "already exists.");
    }
}