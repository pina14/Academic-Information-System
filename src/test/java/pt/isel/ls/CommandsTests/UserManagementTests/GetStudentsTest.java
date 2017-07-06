package pt.isel.ls.CommandsTests.UserManagementTests;

import org.junit.Test;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.Commands.UserManagement.GetStudents;
import pt.isel.ls.Model.CustomExceptions.InvalidTypeException;
import pt.isel.ls.Model.CustomExceptions.NotExistInformationException;
import pt.isel.ls.Model.CustomExceptions.ParametersException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Entities.Student;
import pt.isel.ls.Model.Results.UserManagementResults.GetStudentsResult;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class GetStudentsTest extends UserManagement {
    @Test
    public void normnalConditionTest() {
        /**
         * Initiate tests data.
         */
        tableAuxContent = new CustomList<>();
        Student data1 = new Student(4111, "LEIC", "pedro@gmail.com", "Pedro"),
                data2 =  new Student(4222, "LM", "manel@gmail.com", "Manel");
        tableAuxContent.add(data1);
        tableAuxContent.add(data2);
        try {
            /**
             * Get the command to be tested.
             */
            Command c = new GetStudents();
            GetStudentsResult result = (GetStudentsResult) c.execute(null, null, conn);
            CustomList<Entity> students = result.getStudents();

            /**
             * Assert equal if the information returned by the command are the same than test data.
             */
            assertEquals(students.size(), tableAuxContent.size());
            Student actual, expected;
            for (int i = 0; i < students.size(); i++) {
                actual = (Student)students.get(i);
                expected= (Student)tableAuxContent.get(i);

                assertEquals(expected.getNumber(), actual.getNumber());
                assertEquals(expected.getEmail(), actual.getEmail());
                assertEquals(expected.getName(), actual.getName());
                assertEquals(expected.getPid(), actual.getPid());
            }
        } catch (SQLException | ParametersException
                | InvalidTypeException| NotExistInformationException e) {
            assertFalse(true);
            System.out.println(e.getMessage());
        }
    }

    @Test
    /**
     * Tests if the execute of the class GetStudents with paging behaves as expected.
     */
    public void normalConditionSupportingPagingTest() {
        /**
         * Initiate tests data.
         */
        tableAuxContent = new CustomList<>();
        Student data1 = new Student(4111, "LEIC", "pedro@gmail.com", "Pedro"),
                data2 =  new Student(4222, "LM", "manel@gmail.com", "Manel");
        tableAuxContent.add(data1);
        tableAuxContent.add(data2);
        CustomList<String> data3 = new CustomList<>();
        data3.add("0");
        CustomList<String> data4 = new CustomList<>();
        data4.add("2");
        try {
            /**
             * Get the command to be tested.
             */
            Command c = new GetStudents();
            parameters.put("skip", data3);
            parameters.put("top", data4);
            GetStudentsResult result = (GetStudentsResult)c.execute(null, parameters, conn);
            CustomList<Entity> students = result.getStudents();

            /**
             * Assert equal if the information returned by the command are the same than test data.
             */
            assertEquals(students.size(), tableAuxContent.size());

            Student actual, expected;
            for (int i = 0; i < students.size(); i++) {
                actual = (Student)students.get(i);
                expected = (Student)tableAuxContent.get(i);

                assertEquals(expected.getNumber(), actual.getNumber());
                assertEquals(expected.getEmail(), actual.getEmail());
                assertEquals(expected.getName(), actual.getName());
                assertEquals(expected.getPid(), actual.getPid());
            }
        } catch (SQLException | ParametersException
                | InvalidTypeException | NotExistInformationException e) {
            assertFalse(true);
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void BadParametersTest() {
        CustomList<String> data = new CustomList<>();
        data.add("0");
        try {
            Command c = new GetStudents();
            parameters.put("num", data);
            c.execute(null, parameters, conn);
            assertTrue(false);
        } catch (SQLException | InvalidTypeException
                | NotExistInformationException  e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        } catch (ParametersException e) {
            System.out.println(e.getMessage());
            assertTrue(true);
        }
    }
}