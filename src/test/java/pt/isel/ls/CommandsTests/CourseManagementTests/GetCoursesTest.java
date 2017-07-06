package pt.isel.ls.CommandsTests.CourseManagementTests;

import org.junit.Test;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.Commands.CourseManagement.GetCourses;
import pt.isel.ls.Model.CustomExceptions.InvalidTypeException;
import pt.isel.ls.Model.CustomExceptions.NotExistInformationException;
import pt.isel.ls.Model.CustomExceptions.ParametersException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Course;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Results.CourseManagementResults.GetCoursesResult;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GetCoursesTest extends CourseManagement {
    @Test
    public void normalConditionTest() {
        /* Initiate tests data.*/
        tableAuxContent = new CustomList<>();
        Course dataTest1 = new Course("Laboratório de Software", "LS", 3111),
                dataTest2 = new Course("Mecanismos 1", "M1", 3222),
                dataTest3 = new Course("Programação", "PG", 3111),
                dataTest4 = new Course("Redes", "RCp", 9999);
        tableAuxContent.add(dataTest1);
        tableAuxContent.add(dataTest2);
        tableAuxContent.add(dataTest3);
        tableAuxContent.add(dataTest4);

        try {
            /* Get the command to be tested. */
            Command c = new GetCourses();
            GetCoursesResult result = (GetCoursesResult) c.execute(null, null, conn);
            CustomList<Entity> courses = result.getCourses();

            /* Assert equal if the information returned by the command are the same than test data. */
            assertEquals(courses.size(), tableAuxContent.size());

            for (int i = 0; i < courses.size(); i++) {
                Course actual = (Course)courses.get(i), expected = (Course)tableAuxContent.get(i);

                assertEquals( expected.getAcronym(), actual.getAcronym());
                assertEquals(expected.getName(), actual.getName());
                assertEquals(expected.gettNumber(), actual.gettNumber());
            }

        } catch (SQLException | ParametersException
                 | InvalidTypeException | NotExistInformationException e) {
            assertTrue(false);
            System.out.println(e.getMessage());
        }
    }

    @Test
    /**
     * Tests if the execute of the class GetCourses with paging behaves as expected.
     */
   public void normalConditionSupportingPagingTest() {
        /**
         * Initiate tests data.
         */
        tableAuxContent = new CustomList<>();
        Course dataTest1 = new Course("Mecanismos 1", "M1", 3222);
        tableAuxContent.add(dataTest1);
        CustomList<String> dataTest2 = new CustomList<>();
        dataTest2.add("1");
        CustomList<String> dataTest3 = new CustomList<>();
        dataTest3.add("1");

        try {
            /* Get the command to be tested. */
            Command c = new GetCourses();
            parameters.put("skip", dataTest2);
            parameters.put("top", dataTest3);
            GetCoursesResult result = (GetCoursesResult) c.execute(null, parameters, conn);
            CustomList<Entity> courses = result.getCourses();

            /* Assert equal if the information returned by the command are the same than test data. */
            assertEquals(courses.size(), tableAuxContent.size());
            for (int i = 0; i < courses.size(); i++) {
                Course actual = (Course)courses.get(i), expected = (Course)tableAuxContent.get(i);
                assertEquals( expected.getAcronym(), actual.getAcronym());
                assertEquals(expected.getName(), actual.getName());
                assertEquals(expected.gettNumber(), actual.gettNumber());
            }
        } catch (SQLException | ParametersException
                | NotExistInformationException | InvalidTypeException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }

    @Test
    /**
     * tests if the execute of the class GetCourses behaves as expected
     */
    public void ContainingParametersTest() throws NotExistInformationException {
        /* Initiate tests data. */
        parameters.put("test", null);
        try {
            /* Get the command to be tested. */
            Command c = new GetCourses();
            c.execute(pathParameters, parameters, conn);

            assertTrue(false);
        } catch (SQLException | InvalidTypeException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        } catch (ParametersException e) {
            System.out.println(e.getMessage());
            assertTrue(true);
        }
    }
}