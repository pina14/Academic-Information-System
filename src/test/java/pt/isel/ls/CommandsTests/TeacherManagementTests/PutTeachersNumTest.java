package pt.isel.ls.CommandsTests.TeacherManagementTests;

import org.junit.Test;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.Commands.TeacherManagement.PutTeachersNum;
import pt.isel.ls.Model.CustomExceptions.InvalidTypeException;
import pt.isel.ls.Model.CustomExceptions.NotExistInformationException;
import pt.isel.ls.Model.CustomExceptions.ParametersException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Teacher;
import pt.isel.ls.Model.Mappers.Teachers;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class PutTeachersNumTest extends TeacherManagement {
    @Test
    public void normalConditionTest() {
        Teachers teachers = new Teachers();
        Teacher teacher = new Teacher(3111, "test@test", "test");
        tableAuxContent = new CustomList<>();
        tableAuxContent.add(teacher);

        CustomList<String> dataTest1 = new CustomList<>();
        dataTest1.add("test@test");
        CustomList<String> dataTest2 = new CustomList<>();
        dataTest2.add("test");

        try {
            /* Get the command to be tested. */
            Command c = new PutTeachersNum();
            pathParameters.put("{num}","3111");
            parameters.put("email", dataTest1);
            parameters.put("name", dataTest2);
            c.execute(pathParameters,parameters,conn);

            /* Verify if the content is equal to both tables. */
            tableContent = new CustomList<>();
            tableContent = teachers.getData(selectTeachers("3111", conn));
            Teacher aux = (Teacher)tableContent.get(0);

            assertEquals(tableAuxContent.size(), tableContent.size());
            assertEquals(aux.getEmail(), "test@test");
            assertEquals(aux.getName(), "test");

        } catch (SQLException | ParametersException
                | InvalidTypeException| NotExistInformationException e) {
            System.out.println(e.getMessage());
            assertFalse(true);
        }
    }

    @Test
    public void containingBadParametersTest() {
        /* Initiate variables. */
        CustomList<String> dataTest1 = new CustomList<>();
        dataTest1.add("test@test");
        CustomList<String> dataTest2 = new CustomList<>();
        dataTest2.add("11");

        try {
            /* Get the command to be tested. */
            Command c = new PutTeachersNum();
            pathParameters.put("{num}","3111");
            parameters.put("email", dataTest1);
            parameters.put("age", dataTest2);
            c.execute(pathParameters,parameters,conn);

            assertTrue(false);
        } catch (SQLException | InvalidTypeException
                | NotExistInformationException e) {
            System.out.println(e.getMessage());
            assertFalse(true);
        } catch (ParametersException e) {
            System.out.println(e.getMessage());
            assertTrue(true);
        }
    }

    @Test
    public void wrongTypeTest() {
        /* Initiate variables. */
        CustomList<String> dataTest1 = new CustomList<>();
        dataTest1.add("false");
        CustomList<String> dataTest2 = new CustomList<>();
        dataTest2.add("11");

        try {
            /* Get the command to be tested. */
            Command c = new PutTeachersNum();
            pathParameters.put("{num}","xpto");
            parameters.put("email", dataTest1);
            parameters.put("name", dataTest2);
            c.execute(pathParameters,parameters,conn);
            assertTrue(false);
        } catch (SQLException | ParametersException
                | NotExistInformationException e) {
            System.out.println(e.getMessage());
            assertFalse(true);
        } catch (InvalidTypeException e) {
            System.out.println(e.getMessage());
            assertTrue(true);
        }
    }

    @Test
    public void notExistingInformationInDBTest() {
        /* Initiate variables. */
        CustomList<String> dataTest1 = new CustomList<>();
        dataTest1.add("aaa@aaa");
        CustomList<String> dataTest2 = new CustomList<>();
        dataTest2.add("antonio");

        try {
            /* Get the command to be tested. */
            Command c = new PutTeachersNum();
            pathParameters.put("{num}","1111");
            parameters.put("email", dataTest1);
            parameters.put("name", dataTest2);
            c.execute(pathParameters,parameters,conn);
            assertTrue(false);
        } catch (SQLException | ParametersException
                | InvalidTypeException e) {
            System.out.println(e.getMessage());
            assertFalse(true);
        } catch (NotExistInformationException e) {
            System.out.println(e.getMessage());
            assertTrue(true);
        }
    }
}