package pt.isel.ls.CommandsTests.StudentManagementTests;

import org.junit.Test;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.Commands.StudentManagement.PostCoursesAcrClassesSemNumStudents;
import pt.isel.ls.Model.CustomExceptions.InvalidTypeException;
import pt.isel.ls.Model.CustomExceptions.NotExistInformationException;
import pt.isel.ls.Model.CustomExceptions.ParametersException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.DataStructures.CustomPair;
import pt.isel.ls.Model.Entities.Class;
import pt.isel.ls.Model.Entities.Student;
import pt.isel.ls.Model.Results.ResultError;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class PostCoursesAcrClassesSemNumStudentsTest extends StudentManagement {
    @Test
    public void normalConditionTest() {
        CustomList<String> dataTest1 = new CustomList<>();
        dataTest1.add("4111");
        dataTest1.add("4222");

        try {
            /**
             * Get the command to be tested.
             */
           Command c = new PostCoursesAcrClassesSemNumStudents();
            parameters.put("numStu", dataTest1);
            pathParameters.put("{acr}", "LS");
            pathParameters.put("{num}", "D1");
            pathParameters.put("{sem}", "1617v");
            c.execute(pathParameters, parameters, conn);

            CustomPair<Class, Student> auxPair1 = selectClassStudents(new String[]{"D1", "Laboratório de Software", "1617", "summer", "4111"}, conn);

            Class _Class = auxPair1.getKey();

            /**
             * Verify the inserted data is in the table.
             */
            assertEquals("D1", _Class.getId());
            assertEquals("Laboratório de Software", _Class.getcName());
            assertEquals(1617, _Class.getaYear());
            assertEquals("summer", _Class.getaSemester());

            Student student = auxPair1.getValue();
            assertEquals(4111, student.getNumber());

            CustomPair<Class, Student> auxPair2 = selectClassStudents(new String[]{"D1", "Laboratório de Software", "1617", "summer", "4222"}, conn);

            _Class = auxPair2.getKey();

            /**
             * Verify the inserted data is in the table.
             */assertEquals("D1", _Class.getId());
            assertEquals("Laboratório de Software", _Class.getcName());
            assertEquals(1617, _Class.getaYear());
            assertEquals("summer", _Class.getaSemester());

            student = auxPair2.getValue();
            assertEquals(4222, student.getNumber());
        } catch (SQLException | ParametersException
                | InvalidTypeException | NotExistInformationException e) {
            assertFalse(true);
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void containingWrongParametersTest() {
        /* Initiate tests data. */
        CustomList<String> dataTest1 = new CustomList<>();
        dataTest1.add("4111");
        dataTest1.add("4222");

        try {
            /* Get the command to be tested. */
            Command c = new PostCoursesAcrClassesSemNumStudents();
            parameters.put("numStu", dataTest1);
            parameters.put("teste", null);
            pathParameters.put("{acr}", "LS");
            pathParameters.put("{num}", "D1");
            pathParameters.put("{sem}", "1617v");

            ResultError rt = (ResultError) c.execute(pathParameters, parameters, conn);

            assertEquals(rt.getErrors().get(0).getId(), "numStu");
        } catch (SQLException | NotExistInformationException | InvalidTypeException e) {
            System.out.println(e.getMessage());
            assertFalse(true);
        } catch (ParametersException e) {
            System.out.println(e.getMessage());
            assertTrue(true);
        }
    }

    @Test
    public void wrongTypeTest() {
        /* Initiate tests data. */
        CustomList<String> dataTest1 = new CustomList<>();
        dataTest1.add("4111");
        dataTest1.add("4222");

        try {
            /* Get the command to be tested. */
            Command c = new PostCoursesAcrClassesSemNumStudents();
            parameters.put("numStu", dataTest1);
            pathParameters.put("{acr}", "true");
            pathParameters.put("{num}", "D1");
            pathParameters.put("{sem}", "1617v");
            c.execute(pathParameters, parameters, conn);
            assertTrue(false);
        } catch (SQLException | ParametersException | NotExistInformationException e) {
            System.out.println(e.getMessage());
            assertFalse(true);
        } catch (InvalidTypeException e) {
            System.out.println(e.getMessage());
            assertTrue(true);
        }
    }

    @Test
    public void alreadyExistInformationInDB() {
        /* Initiate tests data. */
        CustomList<String> dataTest1 = new CustomList<>();
        dataTest1.add("4222");
        try {
            /* Get the command to be tested. */
            Command c = new PostCoursesAcrClassesSemNumStudents();
            parameters.put("numStu", dataTest1);
            pathParameters.put("{acr}", "M1");
            pathParameters.put("{num}", "N1");
            pathParameters.put("{sem}", "1415i");

            ResultError rt = (ResultError) c.execute(pathParameters, parameters, conn);

            assertEquals(rt.getErrors().get(0).getId(), "numStu");
        } catch (SQLException | NotExistInformationException
                | InvalidTypeException | ParametersException e) {
            System.out.println(e.getMessage());
            assertFalse(true);
        }
    }
}