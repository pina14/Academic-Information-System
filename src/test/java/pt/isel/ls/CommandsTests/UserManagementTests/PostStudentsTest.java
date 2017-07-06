package pt.isel.ls.CommandsTests.UserManagementTests;

import org.junit.Test;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.Commands.UserManagement.PostStudents;
import pt.isel.ls.Model.CustomExceptions.InvalidTypeException;
import pt.isel.ls.Model.CustomExceptions.NotExistInformationException;
import pt.isel.ls.Model.CustomExceptions.ParametersException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Student;
import pt.isel.ls.Model.Error.CustomError;
import pt.isel.ls.Model.Mappers.Students;
import pt.isel.ls.Model.Results.UserManagementResults.PostStudentsResultError;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class PostStudentsTest extends UserManagement {
    @Test
    public void normalConditionTest() {
        /**
         * Initiate tests data.
         */
        CustomList<String> dataTest1 = new CustomList<>();
        dataTest1.add("user1");

        CustomList<String> dataTest2 = new CustomList<>();
        dataTest2.add("user1@gmail.com");

        CustomList<String> dataTest3 = new CustomList<>();
        dataTest3.add("564454");

        CustomList<String> dataTest4 = new CustomList<>();
        dataTest4.add("LEIC");

        Students students = new Students();
        try {
            /**
             * Get the command to be tested.
             */
            Command c = new PostStudents();
            parameters.put("name", dataTest1);
            parameters.put("email", dataTest2);
            parameters.put("num", dataTest3);
            parameters.put("pid", dataTest4);

            c.execute(null, parameters, conn);
            tableAuxContent = students.getData(selectStudents("564454", conn));

            /**
             * Assert equal if the information returned by teh select are the same than the one inserted by the command.
             */
            assertEquals(1, tableAuxContent.size());
            Student aux = (Student) tableAuxContent.get(0);
            assertEquals("user1", aux.getName());
            assertEquals("user1@gmail.com", aux.getEmail());
            assertEquals(564454, aux.getNumber());

        } catch (SQLException | ParametersException
                | InvalidTypeException | NotExistInformationException e) {
            System.out.println(e.getMessage());
            assertFalse(true);
        }
    }

    @Test
    public void badTypeTest() {
        CustomList<String> dataTest1 = new CustomList<>();
        dataTest1.add("user1");

        CustomList<String> dataTest2 = new CustomList<>();
        dataTest2.add("user1@gmail.com");

        CustomList<String> dataTest3 = new CustomList<>();
        dataTest3.add("um");

        CustomList<String> dataTest4 = new CustomList<>();
        dataTest4.add("LEIC");
        try {
            Command c = new PostStudents();
            parameters.put("name", dataTest1);
            parameters.put("email", dataTest2);
            parameters.put("num", dataTest3);
            parameters.put("pid", dataTest4);

            PostStudentsResultError error = (PostStudentsResultError)c.execute(null, parameters, conn);
            assertTrue(error.getErrors().get(0).getId().equals("num"));
        } catch (SQLException | ParametersException
                | NotExistInformationException | InvalidTypeException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }

    @Test
    public void badParametersTest() {
        CustomList<String> dataTest1 = new CustomList<>();
        dataTest1.add("user1");

        CustomList<String> dataTest2 = new CustomList<>();
        dataTest2.add("user1@gmail.com");

        CustomList<String> dataTest3 = new CustomList<>();
        dataTest3.add("564454");
        try {

            Command c = new PostStudents();
            parameters.put("name", dataTest1);
            parameters.put("email", dataTest2);
            parameters.put("num", dataTest3);

            PostStudentsResultError error = (PostStudentsResultError) c.execute(null, parameters, conn);
            assertTrue(error.getErrors().get(0).getId().equals("pid"));
        } catch (SQLException | InvalidTypeException
                | NotExistInformationException | ParametersException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }

    @Test
    public void InformationAlreadyExistTest()  {
        CustomList<String> dataTest1 = new CustomList<>();
        dataTest1.add("Pedro");

        CustomList<String> dataTest2 = new CustomList<>();
        dataTest2.add("pedro@gmail.com");

        CustomList<String> dataTest3 = new CustomList<>();
        dataTest3.add("4111");

        CustomList<String> dataTest4 = new CustomList<>();
        dataTest4.add("LEIC");
        try {
            Command c = new PostStudents();
            parameters.put("name", dataTest1);
            parameters.put("email", dataTest2);
            parameters.put("num", dataTest3);
            parameters.put("pid", dataTest4);
            PostStudentsResultError error = (PostStudentsResultError)c.execute(null, parameters, conn);

            CustomList<CustomError> errors = error.getErrors();
            CustomError customError;
            for (int i = 0; i < errors.size(); i++) {
                customError = errors.get(i);
                if (customError.getId().equals("pid") || customError.getId().equals("num")) {
                    assertTrue(true);
                    return;
                }
            }
            assertTrue(false);
        } catch (SQLException | ParametersException
                 | NotExistInformationException | InvalidTypeException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }
}