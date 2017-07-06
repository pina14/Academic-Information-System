package pt.isel.ls.CommandsTests.ClassManagementTests;

import org.junit.Test;
import pt.isel.ls.Model.Commands.ClassManagement.GetCoursesAcrClasses;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.CustomExceptions.InvalidTypeException;
import pt.isel.ls.Model.CustomExceptions.NotExistInformationException;
import pt.isel.ls.Model.CustomExceptions.ParametersException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Class;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Results.ClassManagementResults.GetCoursesAcrClassesResult;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GetCoursesAcrClassesTest extends ClassManagement {
    @Test
    /**
     * Tests if the execute of the class GetCoursesAcrClasses behaves as expected.
     */
    public void normalConditionTest() {
        /**
         * Initiate tests data.
         */
        tableAuxContent = new CustomList<>();
        Class dataTest1 = new Class("D1", "Laboratório de Software", 1617, "summer");
        Class dataTest2 = new Class("D2", "Laboratório de Software", 1617, "summer");
        tableAuxContent.add(dataTest1);
        tableAuxContent.add(dataTest2);

        try {
            /**
             * Get the command to be tested.
             */
            Command c = new GetCoursesAcrClasses();
            pathParameters.put("{acr}", "LS");

            /* Get the result of the command.  */
            GetCoursesAcrClassesResult result = ((GetCoursesAcrClassesResult)c.execute(pathParameters, null,conn));
            CustomList<Entity> classes = result.getClasses();
            /**
             * Assert equal if the information returned by the command are the same than test data.
             */
            assertEquals(classes.size(), tableAuxContent.size());
            for (int i = 0; i < classes.size(); i++) {
                Class actual = (Class)classes.get(i), expected = (Class)tableAuxContent.get(i);
                assertEquals( expected.getId(), actual.getId());
                assertEquals(expected.getaSemester(), actual.getaSemester());
                assertEquals(expected.getaYear(), actual.getaYear());
                assertEquals(expected.getcName(), actual.getcName());
            }

        } catch (SQLException | ParametersException
                | InvalidTypeException | NotExistInformationException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }

    @Test
    /**
     * Tests if the execute of the class GetCoursesAcrClasses with paging behaves as expected.
     */
    public void normalConditionSupportingPagingTest() {
        /**
         * Initiate tests data.
         */
        tableAuxContent = new CustomList<>();
        Class dataTest1 = new Class("N1", "Mecanismos 1", 1415, "winter");
        tableAuxContent.add(dataTest1);
        CustomList<String> dataTest2 = new CustomList<>();
        dataTest2.add("1");
        CustomList<String> dataTest3 = new CustomList<>();
        dataTest3.add("1");

        try {
            /**
             * Get the command to be tested.
             */
            Command c = new GetCoursesAcrClasses();
            pathParameters.put("{acr}", "M1");
            parameters.put("skip", dataTest2);
            parameters.put("top", dataTest3);

            /**
             *  Get the result of the command.
             */
            GetCoursesAcrClassesResult result = ((GetCoursesAcrClassesResult)c.execute(pathParameters, parameters,conn));
            CustomList<Entity> classes = result.getClasses();

            /**
             * Assert equal if the information returned by the command are the same than test data.
             */
            assertEquals(tableAuxContent.size(), classes.size());

            for (int i = 0; i < classes.size(); i++) {
                Class actual = (Class)classes.get(i), expected = (Class)tableAuxContent.get(i);
                assertEquals( expected.getId(), actual.getId());
                assertEquals(expected.getaSemester(), actual.getaSemester());
                assertEquals(expected.getaYear(), actual.getaYear());
                assertEquals(expected.getcName(), actual.getcName());
            }

        } catch (SQLException  | ParametersException
                | InvalidTypeException | NotExistInformationException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }

    @Test
    /**
     * tests if the execute of the class GetCoursesAcrClasses behaves as expected
     */
    public void ContainingParametersTest() throws NotExistInformationException {
        /* Initiate tests data. */
        parameters.put("test", null);
        try {
            /* Get the command to be tested. */
            Command c = new GetCoursesAcrClasses();
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
     * tests if the execute of the class GetCoursesAcrClasses behaves as expected
     */
    public void wrongTypeTest() throws NotExistInformationException {
        try {
            /* Get the command to be tested. */
            Command c = new GetCoursesAcrClasses();
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
     * tests if the execute of the class GetCoursesAcrClasses behaves as expected
     */
    public void notExistingInformationInDBTest() throws NotExistInformationException {
        try {
            /* Get the command to be tested. */
           Command c = new GetCoursesAcrClasses();
            pathParameters.put("{acr}","GH1");
            c.execute(pathParameters, null,conn);

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