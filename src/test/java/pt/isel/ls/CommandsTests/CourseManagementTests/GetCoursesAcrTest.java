package pt.isel.ls.CommandsTests.CourseManagementTests;

import org.junit.Test;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.Commands.CourseManagement.GetCoursesAcr;
import pt.isel.ls.Model.CustomExceptions.InvalidTypeException;
import pt.isel.ls.Model.CustomExceptions.NotExistInformationException;
import pt.isel.ls.Model.CustomExceptions.ParametersException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Course;
import pt.isel.ls.Model.Results.CourseManagementResults.GetCoursesAcrResult;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class GetCoursesAcrTest extends CourseManagement {
    @Test
    public void normalConditionTest() {
        /*  Initiate tests data. */
        tableAuxContent = new CustomList<>();
        Course dataTest1 = new Course("Laboratório de Software", "LS", 3111);
        tableAuxContent.add(dataTest1);

        try {
            /*  Get the command to be tested. */
            Command c = new GetCoursesAcr();
            pathParameters.put("{acr}", "LS");
            GetCoursesAcrResult result = (GetCoursesAcrResult) c.execute(pathParameters,null,conn);

            /* Assert equal if the information returned by the command are the same than test data. */
            Course actual = result.getCourse(), expected = dataTest1;

            assertEquals( expected.getAcronym(), actual.getAcronym());
            assertEquals(expected.getName(), actual.getName());
            assertEquals(expected.gettNumber(), actual.gettNumber());

        } catch (SQLException | ParametersException
                | InvalidTypeException | NotExistInformationException e) {
            assertFalse(true);
            System.out.println(e.getMessage());
        }
    }

    @Test
    /**
     * tests if the execute of the class GetCoursesAcr behaves as expected
     */
    public void ContainingParametersTest() throws NotExistInformationException {
        /* Initiate tests data. */
        parameters.put("test", null);
        try {
            /* Get the command to be tested. */
            Command c = new GetCoursesAcr();
            pathParameters.put("{acr}","M1");
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

    @Test
    /**
     * tests if the execute of the class GetCoursesAcr behaves as expected
     */
    public void wrongTypeTest() throws NotExistInformationException {
        try {
            /* Get the command to be tested. */
            Command c = new GetCoursesAcr();
            pathParameters.put("{acr}","15");
            c.execute(pathParameters,null, conn);
            assertTrue(false);
        } catch (SQLException | ParametersException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        } catch (InvalidTypeException e) {
            System.out.println(e.getMessage());
            assertTrue(true);
        }
    }

    @Test
    /**
     * tests if the execute of the class GetCoursesAcr behaves as expected
     */
    public void notExistingInformationInDBTest() throws NotExistInformationException {
        try {
            /* Get the command to be tested. */
            Command c = new GetCoursesAcr();
            pathParameters.put("{acr}","GH1");
            c.execute(pathParameters,null, conn);
            assertTrue(false);
        } catch (SQLException | ParametersException
                | InvalidTypeException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        } catch (NotExistInformationException e) {
            System.out.println(e.getMessage());
            assertTrue(true);
        }
    }
}