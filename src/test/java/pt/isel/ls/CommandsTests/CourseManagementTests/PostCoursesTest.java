package pt.isel.ls.CommandsTests.CourseManagementTests;

import org.junit.Test;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.Commands.CourseManagement.PostCourses;
import pt.isel.ls.Model.CustomExceptions.InvalidTypeException;
import pt.isel.ls.Model.CustomExceptions.NotExistInformationException;
import pt.isel.ls.Model.CustomExceptions.ParametersException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.DataStructures.CustomMap;
import pt.isel.ls.Model.Entities.Course;
import pt.isel.ls.Model.Error.CustomError;
import pt.isel.ls.Model.Mappers.Courses;
import pt.isel.ls.Model.Results.CourseManagementResults.PostCoursesResultError;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class PostCoursesTest extends CourseManagement {
    @Test
    public void normalConditionTest() {
        /* Initiate tests data and the mapper. */
        CustomList<String> dataTest1 = new CustomList<>();
        dataTest1.add("Nv");

        CustomList<String> dataTest2 = new CustomList<>();
        dataTest2.add("Nova");

        CustomList<String> dataTest3 = new CustomList<>();
        dataTest3.add("9999");

        Courses courses = new Courses();

        try {
            /* Get the command to be tested. */
            Command c = new PostCourses();
            parameters.put("acr", dataTest1);
            parameters.put("name", dataTest2 );
            parameters.put("teacher", dataTest3);
            c.execute(null, parameters, conn);

            /*  Get the result of the select */
            tableAuxContent = courses.getData(select("Nova", conn));

            /* Assert equal if the information returned by teh select are the same than the one inserted by the command. */
            assertEquals(1, tableAuxContent.size());

            Course aux = (Course)tableAuxContent.get(0);

            assertEquals("Nova", aux.getName());
            assertEquals("Nv", aux.getAcronym());
            assertEquals(9999, aux.gettNumber());

        } catch (SQLException | ParametersException
                | InvalidTypeException | NotExistInformationException e) {
            assertFalse(true);
            System.out.println(e.getMessage());
        }
    }


    @Test
    /**
     * tests if the execute of the class PostCourses behaves as expected
     */
    public void ContainingParametersTest() throws NotExistInformationException {
        /* Initiate tests data. */
        parameters.put("test", null);
        try {
            /* Get the command to be tested. */
            Command c = new PostCourses();
            PostCoursesResultError resultError = (PostCoursesResultError) c.execute(pathParameters,parameters, conn);

            CustomList<CustomError> errors = resultError.getErrors();
            CustomError customError;
            for (int i = 0; i < errors.size(); i++) {
                customError = errors.get(i);
                if (customError.getId().equals("name") || customError.getId().equals("acr") || customError.getId().equals("teacher"))
                    assertTrue(true);
                return;
            }
            assertFalse(true);
        } catch (SQLException | InvalidTypeException |ParametersException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }

    @Test
    /**
     * tests if the execute of the class PostCourses behaves as expected
     */
    public void wrongTypeTest() {
        /* Initiate tests data and the mapper. */
        parameters = new CustomMap<>();

        CustomList<String> dataTest1 = new CustomList<>();
        dataTest1.add("123");

        CustomList<String> dataTest2 = new CustomList<>();
        dataTest2.add("tn");

        CustomList<String> dataTest3 = new CustomList<>();
        dataTest3.add("123");

        parameters.put("name", dataTest1);
        parameters.put("acr", dataTest2);
        parameters.put("teacher", dataTest3);

        try {
            /* Get the command to be tested. */
            Command c = new PostCourses();
            PostCoursesResultError resultError = (PostCoursesResultError) c.execute(pathParameters,parameters, conn);

            CustomList<CustomError> errors = resultError.getErrors();
            CustomError customError;
            for (int i = 0; i < errors.size(); i++) {
                customError = errors.get(i);
                if (customError.getId().equals("name"))
                    assertTrue(true);
                return;
            }
            assertFalse(true);
        } catch (SQLException | NotExistInformationException | ParametersException | InvalidTypeException e) {
            assertTrue(false);
            System.out.println(e.getMessage());
        }
    }

    @Test
    /**
     * tests if the execute of the class PostCourses behaves as expected
     */
    public void alreadyExistInformationInDB() {
        /* Initiate tests data and the mapper. */
        parameters = new CustomMap<>();

        CustomList<String> dataTest1 = new CustomList<>();
        dataTest1.add("Mecanismos 1");

        CustomList<String> dataTest2 = new CustomList<>();
        dataTest2.add("M1");

        CustomList<String> dataTest3 = new CustomList<>();
        dataTest3.add("3222");

        parameters.put("name", dataTest1);
        parameters.put("acr", dataTest2);
        parameters.put("teacher", dataTest3);

        try {
            /* Get the command to be tested. */
            Command c = new PostCourses();
            PostCoursesResultError resultError = (PostCoursesResultError) c.execute(pathParameters,parameters, conn);

            CustomList<CustomError> errors = resultError.getErrors();
            CustomError customError;
            for (int i = 0; i < errors.size(); i++) {
                customError = errors.get(i);
                if (customError.getId().equals("acr"))
                    assertTrue(true);
                return;
            }
            assertFalse(true);
        } catch (SQLException | ParametersException
                | InvalidTypeException | NotExistInformationException  e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }
}