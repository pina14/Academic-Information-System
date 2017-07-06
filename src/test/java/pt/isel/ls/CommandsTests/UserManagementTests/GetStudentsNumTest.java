package pt.isel.ls.CommandsTests.UserManagementTests;

import org.junit.Test;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.Commands.UserManagement.GetStudentsNum;
import pt.isel.ls.Model.CustomExceptions.InvalidTypeException;
import pt.isel.ls.Model.CustomExceptions.NotExistInformationException;
import pt.isel.ls.Model.CustomExceptions.ParametersException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Student;
import pt.isel.ls.Model.Results.UserManagementResults.GetStudentsNumResult;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class GetStudentsNumTest extends UserManagement {
    @Test
    public void normalConditionTest() {
        /**
         * Initiate tests data.
         */
        tableAuxContent = new CustomList<>();
        Student data1 = new Student(4111, "LEIC", "pedro@gmail.com", "Pedro");
        tableAuxContent.add(data1);
        try {
            /**
             * Get the command to be tested.
             */
            Command c = new GetStudentsNum();
            pathParameters.put("{num}", "4111");
            GetStudentsNumResult result = (GetStudentsNumResult) c.execute(pathParameters, null, conn);
            Student student = result.getStudent();

            /**
             * Assert equal if the information returned by the command are the same than test data.
             */
            Student expected = data1;
            assertEquals( expected.getNumber(), student.getNumber());
            assertEquals(expected.getName(), student.getName());
            assertEquals(expected.getEmail(), student.getEmail());
            assertEquals(expected.getPid(), student.getPid());
        } catch (SQLException | ParametersException
                | InvalidTypeException | NotExistInformationException e) {
            assertFalse(true);
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void badParametersTest() {
        CustomList<String> dataTest = new CustomList<>();
        dataTest.add("4");
        try {
            Command c = new GetStudentsNum();
            pathParameters.put("{num}", "4111");
            parameters.put("num", dataTest);
            c.execute(pathParameters, parameters, conn);
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

    @Test
    public void NotExistingInformationTest() {
        try {
            finish();
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);

            Command c = new GetStudentsNum();
            pathParameters.put("{num}", "0");
            c.execute(pathParameters,null, conn);
            assertTrue(false);
        } catch (SQLException | InvalidTypeException
                | ParametersException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        } catch (NotExistInformationException e) {
            System.out.println(e.getMessage());
            assertTrue(true);
        }
    }
}