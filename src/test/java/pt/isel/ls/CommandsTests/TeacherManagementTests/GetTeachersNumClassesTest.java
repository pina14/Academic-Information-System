package pt.isel.ls.CommandsTests.TeacherManagementTests;

import org.junit.Test;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.Commands.TeacherManagement.GetTeachersNumClasses;
import pt.isel.ls.Model.CustomExceptions.InvalidTypeException;
import pt.isel.ls.Model.CustomExceptions.NotExistInformationException;
import pt.isel.ls.Model.CustomExceptions.ParametersException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.CourseClass;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Results.TeacherManagementResults.GetTeachersNumClassesResult;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class GetTeachersNumClassesTest extends TeacherManagement {
    @Test
    public void normalConditionTest() {
        /**
         * Initiate tests data.
         */
        tableAuxContent = new CustomList<>();
        CourseClass dataTest1 = new CourseClass("D1", "Laboratório de Software", 1617, "summer", "LEIC");
        CourseClass dataTest2 = new CourseClass("N1", "Mecanismos 1", 1415, "winter", "LM");
        tableAuxContent.add(dataTest1);
        tableAuxContent.add(dataTest2);

        try {
            /**
             * Get the command to be tested.
             */
            Command c = new GetTeachersNumClasses();
            pathParameters.put("{num}","3222");
            GetTeachersNumClassesResult result = (GetTeachersNumClassesResult) c.execute(pathParameters,null, conn);

            CustomList<Entity> coursesClasses = result.getCoursesClasses();

            /**
             * Assert equal if the information returned by the command are the same than test data.
             */
            assertEquals(coursesClasses.size(), tableAuxContent.size());

            for (int i = 0; i < coursesClasses.size(); i++) {
                CourseClass actual = (CourseClass)coursesClasses.get(i), expected = (CourseClass)tableAuxContent.get(i);
                assertEquals( expected.getId(), actual.getId());
                assertEquals(expected.getcName(), actual.getcName());
                assertEquals(expected.getaYear(), actual.getaYear());
                assertEquals(expected.getaSemester(), actual.getaSemester());
            }

        } catch (SQLException | ParametersException
                | InvalidTypeException | NotExistInformationException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }

    @Test
    public void  normalConditionSupportingTest() {
        /**
         * Initiate tests data.
         */
        tableAuxContent = new CustomList<>();
        CourseClass dataTest1 = new CourseClass("D1", "Laboratório de Software", 1617, "summer", "LEIC");
        tableAuxContent.add(dataTest1);
        CustomList<String> dataTest2 = new CustomList<>();
        dataTest2.add("0");
        CustomList<String> dataTest3 = new CustomList<>();
        dataTest3.add("1");

        try {
            /**
             * Get the command to be tested.
             */
            Command c = new GetTeachersNumClasses();
            pathParameters.put("{num}","3222");
            parameters.put("skip", dataTest2);
            parameters.put("top", dataTest3);
            GetTeachersNumClassesResult result = (GetTeachersNumClassesResult) c.execute(pathParameters,parameters, conn);

            CustomList<Entity> classes = result.getCoursesClasses();

            /**
             * Assert equal if the information returned by the command are the same than test data.
             */
            assertEquals(classes.size(), tableAuxContent.size());

            for (int i = 0; i < classes.size(); i++) {
                CourseClass actual = (CourseClass)classes.get(i), expected = (CourseClass)tableAuxContent.get(i);
                assertEquals( expected.getId(), actual.getId());
                assertEquals(expected.getcName(), actual.getcName());
                assertEquals(expected.getaYear(), actual.getaYear());
                assertEquals(expected.getaSemester(), actual.getaSemester());
            }
        } catch (SQLException | InvalidTypeException
                | ParametersException
                | NotExistInformationException e) {
            System.out.println(e.getMessage());
            assertFalse(true);
        }
    }

    @Test
    public void containingParametersTest() {
        try {
             /* Get the command to be tested. */
            Command c = new GetTeachersNumClasses();
            pathParameters.put("{num}","3222");
            parameters.put("teste", null);
            c.execute(pathParameters, parameters, conn);
            assertTrue(false);
        } catch (SQLException| InvalidTypeException
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
        try {
             /* Get the command to be tested. */
            Command c = new GetTeachersNumClasses();
            pathParameters.put("{num}","xpto");

            c.execute(pathParameters, null, conn);

            assertTrue(false);
        } catch (SQLException | ParametersException |
                NotExistInformationException e) {
            System.out.println(e.getMessage());
            assertFalse(true);
        } catch (InvalidTypeException e) {
            System.out.println(e.getMessage());
            assertTrue(true);
        }
    }

    @Test
    public void notExistingInformationInDBTest() {
        try {
             /* Get the command to be tested. */
            Command c = new GetTeachersNumClasses();
            pathParameters.put("{num}","1111");
            c.execute(pathParameters,null, conn);

            assertTrue(false);
        } catch (SQLException | ParametersException
                | InvalidTypeException e) {
            System.out.println(e.getMessage());
            assertFalse(true);
        } catch (NotExistInformationException e) {
            System.out.println(e.getMessage());
            assertTrue(true);
        }
    }
}