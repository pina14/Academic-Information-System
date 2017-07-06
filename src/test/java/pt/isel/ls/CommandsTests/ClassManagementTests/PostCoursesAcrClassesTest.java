package pt.isel.ls.CommandsTests.ClassManagementTests;

import org.junit.Test;
import pt.isel.ls.Model.Commands.ClassManagement.PostCoursesAcrClasses;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.CustomExceptions.InvalidTypeException;
import pt.isel.ls.Model.CustomExceptions.NotExistInformationException;
import pt.isel.ls.Model.CustomExceptions.ParametersException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.DataStructures.CustomMap;
import pt.isel.ls.Model.Entities.Class;
import pt.isel.ls.Model.Error.CustomError;
import pt.isel.ls.Model.Mappers.Classes;
import pt.isel.ls.Model.Results.ClassManagementResults.PostCoursesAcrClassesResultError;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class PostCoursesAcrClassesTest extends ClassManagement {
    @Test
    /**
     * Tests if the execute of the class PostCoursesAcrClasses behaves as expected.
     */
    public void normalConditionTest() {
        /**
         * Initiate tests data and the mapper.
         */
        tableAuxContent = new CustomList<>();
        CustomList<String> dataTest1 = new CustomList<>();
        dataTest1.add("1617i");
        CustomList<String> dataTest2 = new CustomList<>();
        dataTest2.add("N1");
        Classes classes = new Classes();

        try {
            /**
             * Get the command to be tested.
             */
            Command c = new PostCoursesAcrClasses();
            parameters.put("sem", dataTest1);
            parameters.put("num", dataTest2);
            pathParameters.put("{acr}", "LS");

            /**
             * Get the result of the select
             */
            c.execute(pathParameters, parameters, conn);
            tableAuxContent = classes.getData(selectClass(new String[]{"N1", "Laboratório de Software", "1617", "winter"}, conn));

            /**
             * Assert equal if the information returned by teh select are the same than the one inserted by the command.
             */
            assertEquals(1, tableAuxContent.size() );

            Class aux = (Class)tableAuxContent.get(0);

            assertEquals("Laboratório de Software", aux.getcName());
            assertEquals("N1", aux.getId());
            assertEquals(1617, aux.getaYear());
            assertEquals("winter", aux.getaSemester());

        } catch (SQLException | ParametersException
                | InvalidTypeException  | NotExistInformationException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }

    @Test
    /**
     * tests if the execute of the class PostCoursesAcrClasses behaves as expected
     */
    public void ContainingWrongParametersTest() throws NotExistInformationException {
        /* Initiate tests data. */
        parameters.put("test", null);
        try {
            /* Get the command to be tested. */
            Command c = new PostCoursesAcrClasses();
            pathParameters.put("{acr}","M1");
            PostCoursesAcrClassesResultError resultError = (PostCoursesAcrClassesResultError)c.execute(pathParameters, parameters, conn);

            CustomList<CustomError> errors = resultError.getErrors();
            CustomError customError;
            for (int i = 0; i < errors.size(); i++) {
                customError = errors.get(i);
                if (customError.getId().equals("sem") || customError.getId().equals("num")) {
                    assertTrue(true);
                    return;
                }
            }
            assertFalse(true);
        } catch (SQLException | InvalidTypeException | ParametersException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }

    @Test
    /**
     * tests if the execute of the class PostCoursesAcrClasses behaves as expected
     */
    public void wrongTypeTest() throws NotExistInformationException {
        /* Initiate tests data and the mapper. */
        parameters = new CustomMap<>();
        CustomList<String> dataTest1 = new CustomList<>();
        dataTest1.add("1617i");
        CustomList<String> dataTest2 = new CustomList<>();
        dataTest2.add("N1");
        parameters.put("sem", dataTest1);
        parameters.put("num", dataTest2);
        try {
            /* Get the command to be tested. */
            Command c = new PostCoursesAcrClasses();
            pathParameters.put("{acr}","15");
            c.execute(pathParameters, parameters, conn);
            assertTrue(false);
        } catch (SQLException | ParametersException  e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        } catch (InvalidTypeException e) {
            assertTrue(true);
        }
    }

    @Test
    /**
     * tests if the execute of the class PostCoursesAcrClasses behaves as expected
     */
    public void alreadyExistInformationInDB() throws NotExistInformationException {
        /* Initiate tests data and the mapper. */
        parameters = new CustomMap<>();
        CustomList<String> dataTest1 = new CustomList<>();
        dataTest1.add("1314i");
        CustomList<String> dataTest2 = new CustomList<>();
        dataTest2.add("D1");
        parameters.put("sem", dataTest1);
        parameters.put("num", dataTest2);
        /* Get the command to be tested. */
        Command c = new PostCoursesAcrClasses();
        pathParameters.put("{acr}", "M1");
        try {
            PostCoursesAcrClassesResultError resultError = (PostCoursesAcrClassesResultError)c.execute(pathParameters, parameters, conn);

            CustomList<CustomError> errors = resultError.getErrors();
            CustomError customError;
            for (int i = 0; i < errors.size(); i++) {
                customError = errors.get(i);
                if (customError.getId().equals("sem") || customError.getId().equals("num"))
                    assertTrue(true);
                return;
            }
            assertFalse(true);
        } catch (ParametersException | SQLException | InvalidTypeException e) {
            assertTrue(false);
        }

    }
}