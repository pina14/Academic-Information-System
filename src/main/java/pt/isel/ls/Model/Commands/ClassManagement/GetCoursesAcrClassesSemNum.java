package pt.isel.ls.Model.Commands.ClassManagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.CustomExceptions.InvalidTypeException;
import pt.isel.ls.Model.CustomExceptions.NotExistInformationException;
import pt.isel.ls.Model.CustomExceptions.ParametersException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.DataStructures.CustomMap;
import pt.isel.ls.Model.Entities.Class;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Entities.EntityCommand;
import pt.isel.ls.Model.Mappers.Students;
import pt.isel.ls.Model.Mappers.Teachers;
import pt.isel.ls.Model.Results.ClassManagementResults.GetCoursesAcrClassesSemNumResult;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Validators.Validation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GetCoursesAcrClassesSemNum implements Command {
    private  CustomMap<String, String> semesterTime = new CustomMap<>();
    private String acr, sem, num;
    private Integer year;
    private Class _class;
    private static final Logger _logger = LoggerFactory.getLogger(GetCoursesAcrClassesSemNum.class);

    public GetCoursesAcrClassesSemNum(){ }

    public GetCoursesAcrClassesSemNum(String description, String template) {
        commands.add(new EntityCommand(template, description));
    }

    /**
     * Put the corresponding string of the SQL to the one given by the user
     */
    private void fillSemesterTime(){
        semesterTime.put("v","summer");
        semesterTime.put("i", "winter");
    }

    /**
     * Get the class of the acr course on the sem semester and with num ID.
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

        /* Initiate the semester map. */
        fillSemesterTime();

        /* Verify that everything is correct to proceed with the command execution. */
        validateParameters(parameters);
        validatePathParameters(pathParameters);
        validateExistingInformationInDb(conn);

        _logger.info("Beginning to execute the command GET /courses/{}/classes/{}/{}.", acr, sem, num);

        /* Query that selects the students from a class of the Acr course, in the Sem semester and with the num ID. */
        String getStudents = "SELECT S.number,  S.pid, S.email, S.name\n" +
                             "FROM classStudent AS CS\n" +
                             "JOIN student AS S ON CS.sNumber = S.number\n" +
                             "JOIN course AS Cour ON Cour.name = CS.cName\n" +
                             "WHERE Cour.acronym = ? AND CS.aYear = ? AND CS.aSemester = ? AND CS.cId = ?";

        /* Build prepared statement.*/
        PreparedStatement ps = conn.prepareStatement(getStudents);
        ps.setString(1, acr);
        ps.setInt(2, year);
        ps.setString(3, sem);
        ps.setString(4, num);

        /* Get the students give by the query. */
        Students students = new Students();
        CustomList<Entity> studentsTable = students.getData(ps.executeQuery());

        /* Query that selects the teachers from a class of the Acr course, in the Sem semester and with the num ID. */
        String getTeachers = "SELECT T.number, T.email, T.name\n" +
                             "FROM classTeacher as CT\n" +
                             "JOIN teacher AS T ON CT.tNumber = T.number \n" +
                             "JOIN class AS Cl ON CT.cId = Cl.id AND CT.cName = Cl.cName AND CT.aYear = Cl.aYear AND CT.aSemester = Cl.aSemester\n" +
                             "JOIN course AS Cour ON Cour.name = Cl.cName\n" +
                             "WHERE Cour.acronym = ? AND Cl.aYear = ? AND Cl.aSemester = ? AND Cl.id = ?";

        /* Build prepared statement.*/
        ps = conn.prepareStatement(getTeachers);
        ps.setString(1, acr);
        ps.setInt(2, year);
        ps.setString(3, sem);
        ps.setString(4, num);

        /* Get the teachers give by the query. */
        Teachers teachers = new Teachers();
        CustomList<Entity> teachersTable =   teachers.getData(ps.executeQuery());

        _logger.info("End the execute of the command GET /courses/{}/classes/{}/{}.", acr, sem, num);
        /* Return the associated result to this command. */
        return new GetCoursesAcrClassesSemNumResult(_class, studentsTable, teachersTable, acr);
    }

    /**
     * Validate that doesn't exist parameters.
     * @param parameters CustomMap<String, CustomList<String>> that contains the parameters.
     * @throws ParametersException
     */
    private void validateParameters(CustomMap<String, CustomList<String>> parameters) throws ParametersException {
        if(parameters != null)
            throw new ParametersException("This command can't support parameters.");
    }

    /**
     * Validate if exist the path parameters and if they are in their correct form.
     * @param pathParameters CustomMap<String, String> that will have the parameters passed in the path.
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
            throw new InvalidTypeException("{acr} must be a text.");

        num = pathParameters.getString("{num}");
        if(num == null || num.length() != 2)
            throw new ParametersException("{num} must be a 2 characters value.");
    }

    /**
     * Validate if it's possible to perform the query without happening a sql exception.
     * @param conn Connection with DataBase.
     * @throws SQLException
     * @throws NotExistInformationException
     */
    private void validateExistingInformationInDb(Connection conn) throws SQLException, NotExistInformationException {
        /* Validate that exists the course with acronym acr. */
        Validation.validateExistentCoursesAcr(conn, acr);
        /* Validate that exist classes in the semester sem with ID num. */
        Validation.validateExistentClassesSemNum(conn, num, year, sem);
        /* Validate and get the class of the acr course on the sem semester and with num ID. */
        _class = Validation.validateExistentCoursesAcrClassesSemNum(conn, num, acr ,year, sem);
    }
}