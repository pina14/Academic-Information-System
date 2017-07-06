package pt.isel.ls.CommandsTests.ClassManagementTests;

import org.junit.Test;
import pt.isel.ls.Model.Commands.ClassManagement.GetCoursesAcrClassesSemNum;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.CustomExceptions.InvalidTypeException;
import pt.isel.ls.Model.CustomExceptions.NotExistInformationException;
import pt.isel.ls.Model.CustomExceptions.ParametersException;
import pt.isel.ls.Model.Entities.Class;
import pt.isel.ls.Model.Results.ClassManagementResults.GetCoursesAcrClassesSemNumResult;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GetCoursesAcrClassesSemNumTest extends ClassManagement {
    @Test
    /**
     * Tests if the execute of the class GetCoursesAcrClassesSemNum behaves as expected.
     */

    public void normalConditionTest() {
        /* Initiate tests data. */

        Class dataTest1 = new Class("N1", "Mecanismos 1", 1415, "winter");

        try {
            /* Get the command to be tested. */

            Command c = new GetCoursesAcrClassesSemNum();
            pathParameters.put("{acr}", "M1");
            pathParameters.put("{sem}", "1415i");
            pathParameters.put("{num}", "N1");

            /* Get the result of the command and the real result. */
            GetCoursesAcrClassesSemNumResult result = ((GetCoursesAcrClassesSemNumResult)c.execute(pathParameters,null,conn));

            /* Assert equal if the information returned by the command are the same than test data.*/
            Class actual = result.get_class(), expected = dataTest1;
            assertEquals( expected.getId(), actual.getId());
            assertEquals(expected.getaSemester(), actual.getaSemester());
            assertEquals(expected.getaYear(), actual.getaYear());
            assertEquals(expected.getcName(), actual.getcName());

        } catch (SQLException | ParametersException
                | InvalidTypeException | NotExistInformationException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }

    @Test
    /**
     * tests if the execute of the class GetCoursesAcrClassesSemNum behaves as expected
    */
    public void ContainingParametersTest() throws NotExistInformationException {
        /* Initiate tests data. */

        parameters.put("test", null);
        try {
            /* Get the command to be tested. */
            Command c = new GetCoursesAcrClassesSemNum();
            pathParameters.put("{acr}","M1");
            pathParameters.put("{sem}","1415i");
            pathParameters.put("{num}","N1");
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
     * tests if the execute of the class GetCoursesAcrClassesSemNum behaves as expected
    */

    public void wrongTypeTest() throws NotExistInformationException {
        try {

            /* Get the command to be tested. */
            Command c = new GetCoursesAcrClassesSemNum();
            pathParameters.put("{acr}","15");
            pathParameters.put("{sem}","1215i");
            pathParameters.put("{num}","4");
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
     * tests if the execute of the class GetCoursesAcrClassesSemNum behaves as expected
    */

    public void notExistingInformationInDBTest() throws NotExistInformationException {
        try {
            /* Get the command to be tested. */
            Command c = new GetCoursesAcrClassesSemNum();
            pathParameters.put("{acr}","M1");
            pathParameters.put("{sem}","1213v");
            pathParameters.put("{num}","N1");
            c.execute(pathParameters,null, conn);

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
