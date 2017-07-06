package pt.isel.ls.CommandsTests.UserManagementTests;

import org.junit.Test;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.Commands.UserManagement.PostTeachers;
import pt.isel.ls.Model.CustomExceptions.InvalidTypeException;
import pt.isel.ls.Model.CustomExceptions.NotExistInformationException;
import pt.isel.ls.Model.CustomExceptions.ParametersException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Teacher;
import pt.isel.ls.Model.Mappers.Teachers;
import pt.isel.ls.Model.Results.UserManagementResults.PostTeachersResultError;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PostTeachersTest extends UserManagement {
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

        Teachers teachers = new Teachers();
        try {
            /**
             * Get the command to be tested.
             */
            Command c = new PostTeachers();
            parameters.put("name", dataTest1);
            parameters.put("email", dataTest2);
            parameters.put("num", dataTest3);

            c.execute(null, parameters, conn);
            tableAuxContent = teachers.getData(selectTeachers("564454", conn));

            /**
             * Assert equal if the information returned by teh select are the same than the one inserted by the command.
             */
            assertEquals(1, tableAuxContent.size() );
            Teacher aux = (Teacher)tableAuxContent.get(0);
            assertEquals("user1", aux.getName());
            assertEquals("user1@gmail.com", aux.getEmail());
            assertEquals(564454, aux.getNumber());
        } catch (SQLException | ParametersException
                | InvalidTypeException | NotExistInformationException e) {
            assertTrue(false);
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void badTypeTest() {
        CustomList<String> dataTest1 = new CustomList<>();
        dataTest1.add("user1");
        CustomList<String> dataTest2 = new CustomList<>();
        dataTest2.add("user1@gmail.com");
        CustomList<String> dataTest3 = new CustomList<>();
        dataTest3.add("one");
        try {
            Command c = new PostTeachers();
            parameters.put("name", dataTest1);
            parameters.put("email", dataTest2);
            parameters.put("num", dataTest3);

            PostTeachersResultError error = (PostTeachersResultError)c.execute(null, parameters, conn);

            assertTrue(error.getErrors().get(0).getId().equals("num"));
        } catch (SQLException| ParametersException
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
        try {
            Command c = new PostTeachers();
            parameters.put("name", dataTest1);
            parameters.put("email", dataTest2);
            PostTeachersResultError error = (PostTeachersResultError) c.execute(null, parameters, conn);

            assertTrue(error.getErrors().get(0).getId().equals("num"));
        } catch (SQLException | InvalidTypeException
                | ParametersException | NotExistInformationException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }

    @Test
    public void InformationAlreadyExistsTest() {
        CustomList<String> dataTest1 = new CustomList<>();
        dataTest1.add("David");

        CustomList<String> dataTest2 = new CustomList<>();
        dataTest2.add("david@gmail.com");

        CustomList<String> dataTest3 = new CustomList<>();
        dataTest3.add("3111");
        try {
            Command c = new PostTeachers();
            parameters.put("name", dataTest1);
            parameters.put("email", dataTest2);
            parameters.put("num", dataTest3);
            PostTeachersResultError error = (PostTeachersResultError) c.execute(null, parameters, conn);

            assertTrue(error.getErrors().get(0).getId().equals("num"));

        } catch (SQLException | NotExistInformationException
                | InvalidTypeException | ParametersException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }
}