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

public class DeleteCoursesAcrClassesSemNumStudentsNumStu implements Command {
    private CustomMap<String, String> semesterTime = new CustomMap<>();
    private String acr, sem, num, courseName;
    private Integer year, numStu;
    private static final Logger _logger = LoggerFactory.getLogger(DeleteCoursesAcrClassesSemNumStudentsNumStu.class);

    public DeleteCoursesAcrClassesSemNumStudentsNumStu() {}

    public DeleteCoursesAcrClassesSemNumStudentsNumStu(String description, String template) {
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
     * Perform the removal the given student from the given class if possible.
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

        /* Fill the semester time with corresponding letter to it's season. */
        fillSemesterTime();
        _logger.info("Validate information to execute the command");

        /* Verify that everything is correct to proceed with the command execution. */
        validateParameters(parameters);
        validatePathParameters(pathParameters);
        validateExistingInformationInDb(conn);

        _logger.info("Beginning to execute the command DELETE /courses/{}/classes/{}/{}/students/{}", acr, sem, num, numStu);

        /* Query that erases a student from a class and uses class ID, course name,
         * academic year, academic semester and student number to identify which entry it shall delete. */
        String deleteStudent = "DELETE FROM classStudent\n" +
                               "WHERE cId = ? AND cName = ? AND aYear = ? AND aSemester = ? AND sNumber = ?";

        /* Build prepared statement. */
        PreparedStatement ps = conn.prepareStatement(deleteStudent);
        ps.setString(1, num);
        ps.setString(2,courseName);
        ps.setInt(3, year);
        ps.setString(4, sem);
        ps.setInt(5, numStu);
        ps.execute();

        _logger.info("End of the execution the command DELETE /courses/{}/classes/{}/{}/students/{}", acr, sem, num, numStu);
        /* Return the associated result to this command. */
        return new NullResult();
    }

    /**
     * Verify if there's no parameters.
     * @param parameters CustomMap<String, CustomList<String>> that contains the parameters.
     * @throws ParametersException
     */
    private void validateParameters(CustomMap<String, CustomList<String>> parameters) throws ParametersException {
        if(parameters != null)
            throw new ParametersException("This command can't support parameters.");
    }

    /**
     * Verify if there's the parameters needed to this command and validate's it's types
     * @param pathParameters CustomMap<String, String> that will have the parameters passed in the path.
     * @throws ParametersException
     * @throws InvalidTypeException
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
            throw new ParametersException("{num} must be a 2 characters value.");

        numStu = pathParameters.getInt("{numStu}");
        if(numStu == null)
            throw new InvalidTypeException("{numStu} must be an Integer.");
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
        Validation.validateExistentStudentNum(conn, numStu);
        Validation.validateExistentCoursesClassesSemNumStudentNum(conn, num, courseName, year, sem, numStu);
    }
}