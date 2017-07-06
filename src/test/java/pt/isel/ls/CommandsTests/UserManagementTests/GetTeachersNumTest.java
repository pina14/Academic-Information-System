package pt.isel.ls.CommandsTests.UserManagementTests;

import org.junit.Test;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.Commands.UserManagement.GetTeachersNum;
import pt.isel.ls.Model.CustomExceptions.InvalidTypeException;
import pt.isel.ls.Model.CustomExceptions.NotExistInformationException;
import pt.isel.ls.Model.CustomExceptions.ParametersException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Teacher;
import pt.isel.ls.Model.Results.UserManagementResults.GetTeachersNumResult;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class GetTeachersNumTest extends UserManagement {
    @Test
    public void normalConditionTest() {
        /**
         * Initiate tests data.
         */
        tableAuxContent = new CustomList<>();
        Teacher data1 =  new Teacher(3111,"david@gmail.com",  "David");
        tableAuxContent.add(data1);

        try {
            /**
             * Get the command to be tested.
             */
            Command c = new GetTeachersNum();
            pathParameters.put("{num}", "3111");
            GetTeachersNumResult result = (GetTeachersNumResult) c.execute(pathParameters, null, conn);
            Teacher teacher = result.getTeacher();

            /**
             * Assert equal if the information returned by the command are the same than test data.
             */
            Teacher expected = data1;
            assertEquals( expected.getNumber(), teacher.getNumber());
            assertEquals(expected.getName(), teacher.getName());
            assertEquals(expected.getEmail(), teacher.getEmail());

        } catch (SQLException | ParametersException
                | InvalidTypeException | NotExistInformationException e) {
            assertFalse(true);
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void BadTypeTest() {
        try {
            Command c = new GetTeachersNum();
            pathParameters.put("{num}", "Erro");
            c.execute(pathParameters,null, conn);

            assertTrue(false);
        } catch (SQLException | ParametersException
                | NotExistInformationException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        } catch (InvalidTypeException e) {
            System.out.println(e.getMessage());
            assertTrue(true);
        }
    }

    @Test
    public void BadParametersTest()  {
        CustomList<String> data = new CustomList<>();
        data.add("0");
        try {
            Command c = new GetTeachersNum();
            pathParameters.put("{num}", "3111");
            parameters.put("Erro", data);
            c.execute(pathParameters,parameters, conn);

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

            Command c = new GetTeachersNum();
            pathParameters.put("{num}", "0");
            c.execute(pathParameters, null, conn);

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