package pt.isel.ls.CommandsTests.UserManagementTests;

import org.junit.Test;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.Commands.UserManagement.GetUsers;
import pt.isel.ls.Model.CustomExceptions.InvalidTypeException;
import pt.isel.ls.Model.CustomExceptions.NotExistInformationException;
import pt.isel.ls.Model.CustomExceptions.ParametersException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Entities.User;
import pt.isel.ls.Model.Results.UserManagementResults.GetUsersResult;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GetUsersTest extends UserManagement {
    @Test
    public void normalConditionTest() {
        /**
         * Initiate tests data.
         */
        tableAuxContent = new CustomList<>();
        User data1 =  new User(3111, "david@gmail.com", "David", "teachers"),
                data2 = new User(3222, "marco@gmail.com", "Marco", "teachers"),
                data3 = new User(4111, "pedro@gmail.com", "Pedro", "students"),
                data4 = new User(4222, "manel@gmail.com", "Manel", "students"),
                data5 = new User(9999, "Leo@gmail.com", "Leonardo", "teachers");
        tableAuxContent.add(data1);
        tableAuxContent.add(data2);
        tableAuxContent.add(data3);
        tableAuxContent.add(data4);
        tableAuxContent.add(data5);
        try {
            /**
             * Get the command to be tested.
             */
            Command c = new GetUsers();
            GetUsersResult result = (GetUsersResult) c.execute(null, null, conn);
            CustomList<Entity> users = result.getUsers();

            /**
             * Assert equal if the information returned by the command are the same than test data.
             */
            assertEquals(users.size(), tableAuxContent.size());

            User actual, expected;
            for (int i = 0; i < users.size(); i++) {
                actual = (User)users.get(i);
                expected = (User)tableAuxContent.get(i);

                assertEquals(expected.getNumber(), actual.getNumber());
                assertEquals(expected.getEmail(), actual.getEmail());
                assertEquals(expected.getName(), actual.getName());
            }
        } catch (SQLException | ParametersException
                | NotExistInformationException | InvalidTypeException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }

    @Test
    /**
     * Tests if the execute of the class GetUsers with paging behaves as expected.
     */
    public void normalConditionSupportingPagingTest() {
        /**
         * Initiate tests data.
         */
        tableAuxContent = new CustomList<>();
        User data1 =  new User(3111, "david@gmail.com", "David", "teachers"),
             data2 = new User(3222, "marco@gmail.com", "Marco", "teachers");
        tableAuxContent.add(data1);
        tableAuxContent.add(data2);
        CustomList<String> data3 = new CustomList<>();
        data3.add("2");
        try {
            /**
             * Get the command to be tested.
             */
            Command c = new GetUsers();
            parameters.put("top", data3);
            GetUsersResult result = (GetUsersResult)c.execute(null, parameters, conn);
            CustomList<Entity> users = result.getUsers();

            /**
             * Assert equal if the information returned by the command are the same than test data.
             */
            assertEquals(users.size(), tableAuxContent.size());

            User actual, expected;
            for (int i = 0; i < users.size(); i++) {
                actual = (User)users.get(i);
                expected = (User)tableAuxContent.get(i);

                assertEquals(expected.getNumber(), actual.getNumber());
                assertEquals(expected.getEmail(), actual.getEmail());
                assertEquals(expected.getName(), actual.getName());
            }

        } catch (SQLException | ParametersException
                | NotExistInformationException | InvalidTypeException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }

    @Test
    public void badParametersTest() {
        CustomList<String> data = new CustomList<>();
        data.add("0");
        try {
            Command c = new GetUsers();
            parameters.put("num", data);
            c.execute(null, parameters, conn);

            assertTrue(false);

        } catch (SQLException | InvalidTypeException
                | NotExistInformationException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        } catch (ParametersException e) {
            System.out.println(e.getMessage());
            assertTrue(true);
        }
    }
}