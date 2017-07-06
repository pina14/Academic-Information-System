package pt.isel.ls.CommandsTests.StudentManagementTests;

import org.junit.Test;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.Commands.StudentManagement.DeleteCoursesAcrClassesSemNumStudentsNumStu;
import pt.isel.ls.Model.CustomExceptions.InvalidTypeException;
import pt.isel.ls.Model.CustomExceptions.NotExistInformationException;
import pt.isel.ls.Model.CustomExceptions.ParametersException;
import pt.isel.ls.Model.DataStructures.CustomPair;
import pt.isel.ls.Model.Entities.Class;
import pt.isel.ls.Model.Entities.Student;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class DeleteCoursesAcrClassesSemNumStudentsNumStuTest extends StudentManagement {
    @Test
    public void normalConditionTest() {
        pathParameters.put("{numStu}", "4111");
        pathParameters.put("{acr}", "M1");
        pathParameters.put("{sem}", "1415i");
        pathParameters.put("{num}", "N1");
        Command c = new DeleteCoursesAcrClassesSemNumStudentsNumStu();

        try {
            CustomPair<Class, Student> present = selectClassStudents(new String[]{"N1", "Mecanismos 1", "1415", "winter", "4222"}, conn);

            c.execute(pathParameters, null, conn);
            CustomPair<Class, Student> notPresent = selectClassStudents(new String[]{"N1", "Mecanismos 1", "1415", "winter", "4111"}, conn);

            assertNull(notPresent);
            assertNotNull(present);
        } catch (SQLException | ParametersException
                | InvalidTypeException | NotExistInformationException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }

    @Test
    public void containingParametersTest() throws NotExistInformationException {
        pathParameters.put("{numStu}", "4111");
        pathParameters.put("{acr}", "M1");
        pathParameters.put("{sem}", "1415i");
        pathParameters.put("{num}", "N1");
        parameters.put("teste", null);

        Command c = new DeleteCoursesAcrClassesSemNumStudentsNumStu();

        try {
            c.execute(pathParameters, parameters, conn);
            assertTrue(false);
        } catch (SQLException | InvalidTypeException | NotExistInformationException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        } catch (ParametersException e) {
            System.out.println(e.getMessage());
            assertTrue(true);
        }
    }

    @Test
    public void wrongTypeTest() {
        pathParameters.put("{numStu}", "xpto");
        pathParameters.put("{acr}", "11");
        pathParameters.put("{sem}", "1415i");
        pathParameters.put("{num}", "N1");
        Command c = new DeleteCoursesAcrClassesSemNumStudentsNumStu();
        try {
            c.execute(pathParameters, null, conn);
            assertTrue(false);

        } catch (SQLException | ParametersException| NotExistInformationException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        } catch (InvalidTypeException e) {
            System.out.println(e.getMessage());
            assertTrue(true);
        }
    }

    @Test
    public void notExistingInformationInDBTest(){
        pathParameters.put("{numStu}", "1111");
        pathParameters.put("{acr}", "xpto");
        pathParameters.put("{sem}", "1415i");
        pathParameters.put("{num}", "N1");
        Command c = new DeleteCoursesAcrClassesSemNumStudentsNumStu();

        try {
            c.execute(pathParameters, null, conn);
            assertTrue(false);
        } catch (SQLException | ParametersException | InvalidTypeException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        } catch (NotExistInformationException e) {
            System.out.println(e.getMessage());
            assertTrue(true);
        }
    }
}