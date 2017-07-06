package pt.isel.ls.CommandsTests.StudentManagementTests;

import org.junit.Test;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.Commands.StudentManagement.PutStudentsNum;
import pt.isel.ls.Model.CustomExceptions.InvalidTypeException;
import pt.isel.ls.Model.CustomExceptions.NotExistInformationException;
import pt.isel.ls.Model.CustomExceptions.ParametersException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Student;
import pt.isel.ls.Model.Mappers.Students;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class PutStudentsNumTest extends StudentManagement {
    @Test
    public void normalConditionTest() {
        tableAuxContent = new CustomList<>();
        Student data1 = new Student( 4111,"test@","test", "LEIC");
        tableAuxContent.add(data1);

        CustomList<String> dataTest1 = new CustomList<>();
        dataTest1.add("test@");

        CustomList<String> dataTest2 = new CustomList<>();
        dataTest2.add("LEIC");

        CustomList<String> dataTest3 = new CustomList<>();
        dataTest3.add("test");

        Students students = new Students();

        try {
            /**
             * Get the command to be tested.
             */
            Command c = new PutStudentsNum();
            parameters.put("email", dataTest1);
            parameters.put("pid", dataTest2 );
            parameters.put("name", dataTest3);
            pathParameters.put("{num}", "4111");
            c.execute(pathParameters,parameters, conn);
            tableContent = students.getData(selectStudents("4111", conn));

            /**
             * Verify if the content is equal to both tables.
             */
            Student aux = (Student)tableContent.get(0);

            assertEquals(tableAuxContent.size(), tableContent.size());
            assertEquals("test",aux.getName());
            assertEquals("test@",aux.getEmail());
            assertEquals("LEIC",aux.getPid());
            assertEquals(4111,aux.getNumber());
        } catch (SQLException | NotExistInformationException
                | ParametersException | InvalidTypeException e) {
            System.out.println(e.getMessage());
            assertFalse(true);
        }
    }

    @Test
    public void containingBadParametersTest() {
         /* Initiate tests data. */
        CustomList<String> dataTest1 = new CustomList<>();
        dataTest1.add("1111");
        CustomList<String> dataTest2 = new CustomList<>();
        dataTest2.add("LEIC");
        CustomList<String> dataTest3 = new CustomList<>();
        dataTest3.add("test");

        try {
             /* Get the command to be tested. */
            Command c = new PutStudentsNum();
            parameters.put("xpto", dataTest1);
            parameters.put("pid", dataTest2 );
            parameters.put("name", dataTest3);
            pathParameters.put("{num}", "aaaa");
            c.execute(pathParameters,parameters, conn);

            assertFalse(true);
        } catch (SQLException | NotExistInformationException | InvalidTypeException e) {
            System.out.println(e.getMessage());
            assertFalse(true);
        } catch ( ParametersException e) {
            assertTrue(true);
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void wrongTypeTest() {
         /* Initiate tests data. */
        CustomList<String> dataTest1 = new CustomList<>();
        dataTest1.add("1111");
        CustomList<String> dataTest2 = new CustomList<>();
        dataTest2.add("LEIC");
        CustomList<String> dataTest3 = new CustomList<>();
        dataTest3.add("test");

        try {
             /* Get the command to be tested. */
            Command c = new PutStudentsNum();
            parameters.put("email", dataTest1);
            parameters.put("pid", dataTest2 );
            parameters.put("name", dataTest3);
            pathParameters.put("{num}", "aaaa");
            c.execute(pathParameters,parameters, conn);
            assertFalse(true);
        } catch (SQLException | ParametersException | NotExistInformationException e) {
            System.out.println(e.getMessage());
            assertFalse(true);
        } catch ( InvalidTypeException e) {
            assertTrue(true);
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void notExistingInformationInDBTest() {
         /* Initiate tests data. */
        CustomList<String> dataTest1 = new CustomList<>();
        dataTest1.add("test@");
        CustomList<String> dataTest2 = new CustomList<>();
        dataTest2.add("LEIC");
        CustomList<String> dataTest3 = new CustomList<>();
        dataTest3.add("test");

        try {
             /* Get the command to be tested. */
            Command c = new PutStudentsNum();
            parameters.put("email", dataTest1);
            parameters.put("pid", dataTest2 );
            parameters.put("name", dataTest3);
            pathParameters.put("{num}", "1111");
            c.execute(pathParameters,parameters, conn);
            assertFalse(true);
        } catch (SQLException | ParametersException | InvalidTypeException e) {
            System.out.println(e.getMessage());
            assertFalse(true);
        } catch ( NotExistInformationException e) {
            assertTrue(true);
            System.out.println(e.getMessage());
        }
    }
}
