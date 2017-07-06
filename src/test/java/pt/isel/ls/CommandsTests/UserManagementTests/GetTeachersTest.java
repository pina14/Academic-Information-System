package pt.isel.ls.CommandsTests.UserManagementTests;

import org.junit.Test;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.Commands.UserManagement.GetTeachers;
import pt.isel.ls.Model.CustomExceptions.InvalidTypeException;
import pt.isel.ls.Model.CustomExceptions.NotExistInformationException;
import pt.isel.ls.Model.CustomExceptions.ParametersException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Entities.Teacher;
import pt.isel.ls.Model.Results.UserManagementResults.GetTeachersResult;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class GetTeachersTest extends UserManagement {
    @Test
    public void normalConditionTest() {
        /**
         * Initiate tests data.
         */
        tableAuxContent = new CustomList<>();
        Teacher data1 =  new Teacher(3111,"david@gmail.com",  "David"),
                data2 = new Teacher(3222,"marco@gmail.com",  "Marco"),
                data3 = new Teacher(9999,"Leo@gmail.com",  "Leonardo");
        tableAuxContent.add(data1);
        tableAuxContent.add(data2);
        tableAuxContent.add(data3);

        try {
            /**
             * Get the command to be tested.
             */
            Command c = new GetTeachers();
            GetTeachersResult result = (GetTeachersResult) c.execute(null, null, conn);
            CustomList<Entity> teachers = result.getTeachers();

            /**
             * Assert equal if the information returned by the command are the same than test data.
             */
            assertEquals(teachers.size(), tableAuxContent.size());

            Teacher actual, expected;
            for (int i = 0; i < teachers.size(); i++) {
                actual = (Teacher)teachers.get(i);
                expected = (Teacher)tableAuxContent.get(i);

                assertEquals(expected.getNumber(), actual.getNumber());
                assertEquals(expected.getEmail(), actual.getEmail());
                assertEquals(expected.getName(), actual.getName());
            }
        } catch (SQLException | ParametersException
                | InvalidTypeException| NotExistInformationException e) {
            assertFalse(true);
            System.out.println(e.getMessage());
        }
    }

    @Test
    /**
     * Tests if the execute of the class GetTeachers with paging behaves as expected.
     */
    public void  normalConditionSupportingPagingTest() {
        /**
         * Initiate tests data.
         */
        tableAuxContent = new CustomList<>();
        Teacher data1 = new Teacher(3222,"marco@gmail.com",  "Marco"),
                data2 = new Teacher(9999,"Leo@gmail.com",  "Leonardo");
        tableAuxContent.add(data1);
        tableAuxContent.add(data2);
        CustomList<String> data3 = new CustomList<>();
        data3.add("1");

        try {
            /**
             * Get the command to be tested.
             */
            Command c = new GetTeachers();
            parameters.put("skip", data3);
            GetTeachersResult result = (GetTeachersResult)c.execute(null, parameters, conn);

            CustomList<Entity> teachers = result.getTeachers();

            /**
             * Assert equal if the information returned by the command are the same than test data.
             */
            assertEquals(teachers.size(), tableAuxContent.size());

            Teacher actual, expected;
            for (int i = 0; i < teachers.size(); i++) {
                actual = (Teacher)teachers.get(i);
                expected = (Teacher)tableAuxContent.get(i);

                assertEquals(expected.getNumber(), actual.getNumber());
                assertEquals(expected.getEmail(), actual.getEmail());
                assertEquals(expected.getName(), actual.getName());
            }
        } catch (SQLException | ParametersException
                | InvalidTypeException| NotExistInformationException e) {
            assertFalse(true);
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void badParametersTest() {
        CustomList<String> data = new CustomList<>();
        data.add("0");
        try {
            Command c = new GetTeachers();
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