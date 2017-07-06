package pt.isel.ls.CommandsTests.TeacherManagementTests;

import org.junit.Test;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.Commands.TeacherManagement.PostCoursesAcrClassesSemNumTeachers;
import pt.isel.ls.Model.CustomExceptions.InvalidTypeException;
import pt.isel.ls.Model.CustomExceptions.NotExistInformationException;
import pt.isel.ls.Model.CustomExceptions.ParametersException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.DataStructures.CustomPair;
import pt.isel.ls.Model.Entities.Class;
import pt.isel.ls.Model.Entities.Teacher;
import pt.isel.ls.Model.Results.TeacherManagementResults.PostCoursesAcrClassesSemNumTeachersResultError;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class PostCoursesAcrClassesSemNumTeachersTest extends TeacherManagement {
    @Test
    public void normalConditionTest(){
        /**
         * Initiate tests data.
         */
        CustomList<String> dataTest1 = new CustomList<>();
        dataTest1.add("9999");
        try {
            /**
             * Get the command to be tested.
             */
            Command c = new PostCoursesAcrClassesSemNumTeachers();
            pathParameters.put("{acr}", "LS");
            pathParameters.put("{sem}", "1617v");
            pathParameters.put("{num}", "D1");
            parameters.put("numDoc",dataTest1);
            c.execute(pathParameters,parameters,conn);

            CustomPair<Class, Teacher> auxPair1 = selectClassTeacher(new String[]{"D1", "Laboratório de Software", "1617", "summer", "9999"},conn);

            /**
             * Verify the inserted data is in the table.
             */
            Class _Class = auxPair1.getKey();
            assertEquals("D1",_Class.getId());
            assertEquals("Laboratório de Software", _Class.getcName());
            assertEquals(1617,_Class.getaYear());
            assertEquals("summer", _Class.getaSemester());

            Teacher teacher = auxPair1.getValue();
            assertEquals(9999, teacher.getNumber());
        } catch (SQLException | ParametersException
                | InvalidTypeException | NotExistInformationException e) {
            System.out.println(e.getMessage());
            assertFalse(true);
        }
    }

    @Test
    public void containingWrongParametersTest() {
        try {
            /* Get the command to be tested. */
            Command c = new PostCoursesAcrClassesSemNumTeachers();
            pathParameters.put("{acr}", "LS");
            pathParameters.put("{sem}", "1617v");
            pathParameters.put("{num}", "D1");
            c.execute(pathParameters,null,conn);
            assertTrue(false);
        } catch (SQLException | InvalidTypeException
                | NotExistInformationException e) {
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
        dataTest1.add("true");
        try {
            /* Get the command to be tested. */
            Command c = new PostCoursesAcrClassesSemNumTeachers();
            pathParameters.put("{acr}", "11");
            pathParameters.put("{sem}", "truei");
            pathParameters.put("{num}", "false");
            parameters.put("numDoc",dataTest1);
            c.execute(pathParameters,parameters,conn);
            assertTrue(false);
        } catch (SQLException | ParametersException
                | NotExistInformationException e) {
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
        dataTest1.add("3111");

        try {
            /* Get the command to be tested. */
            Command c = new PostCoursesAcrClassesSemNumTeachers();
            pathParameters.put("{acr}", "LS");
            pathParameters.put("{sem}", "1617v");
            pathParameters.put("{num}", "D1");
            parameters.put("numDoc",dataTest1);
            PostCoursesAcrClassesSemNumTeachersResultError error = (PostCoursesAcrClassesSemNumTeachersResultError) c.execute(pathParameters,parameters,conn);

            assertTrue(error.getErrors().size() > 0);

        } catch (ParametersException | NotExistInformationException
                | InvalidTypeException | SQLException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }
}