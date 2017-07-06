package pt.isel.ls.CommandsTests.TeacherManagementTests;

import org.junit.Test;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.Commands.TeacherManagement.GetCoursesAcrClassesSemNumTeachers;
import pt.isel.ls.Model.CustomExceptions.InvalidTypeException;
import pt.isel.ls.Model.CustomExceptions.NotExistInformationException;
import pt.isel.ls.Model.CustomExceptions.ParametersException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Entities.Teacher;
import pt.isel.ls.Model.Results.TeacherManagementResults.GetCoursesAcrClassesSemNumTeachersResult;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class GetCoursesAcrClassesSemNumTeachersTest extends TeacherManagement {
    @Test
    public void normalConditionTest() {
        /**
         * Initiate tests data.
         */
        tableAuxContent = new CustomList<>();
        Teacher dataTest1 = new Teacher(3111, "david@gmail.com", "David");
        Teacher dataTest2 = new Teacher(3222, "marco@gmail.com", "Marco");
        tableAuxContent.add(dataTest1);
        tableAuxContent.add(dataTest2);

        try {
            /**
             * Get the command to be tested.
             */
            Command c = new GetCoursesAcrClassesSemNumTeachers();
            pathParameters.put("{acr}","LS");
            pathParameters.put("{sem}","1617v");
            pathParameters.put("{num}","D1");
            GetCoursesAcrClassesSemNumTeachersResult result = (GetCoursesAcrClassesSemNumTeachersResult) c.execute(pathParameters,null, conn);

            /**
             * Assert equal if the information returned by the command are the same than test data.
             */

            CustomList<Entity> teachers = result.getTeachers();

            assertEquals(teachers.size(), tableAuxContent.size());

            for (int i = 0; i < teachers.size(); i++) {
                Teacher actual = (Teacher)teachers.get(i), expected = (Teacher)tableAuxContent.get(i);

                assertEquals( expected.getEmail(), actual.getEmail());
                assertEquals(expected.getName(), actual.getName());
                assertEquals(expected.getNumber(), actual.getNumber());
            }

        } catch (SQLException | ParametersException
                | InvalidTypeException | NotExistInformationException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }

    @Test
    public void  normalConditionSupportingPagingTest() {
        tableAuxContent = new CustomList<>();
        Teacher dataTest1 = new Teacher(3222, "marco@gmail.com", "Marco");
        tableAuxContent.add(dataTest1);
        CustomList<String> dataTest2 = new CustomList<>();
        dataTest2.add("1");
        CustomList<String> dataTest3 = new CustomList<>();
        dataTest3.add("1");

        try {
            /**
             * Get the command to be tested.
             */
            Command c = new GetCoursesAcrClassesSemNumTeachers();
            pathParameters.put("{acr}","LS");
            pathParameters.put("{sem}","1617v");
            pathParameters.put("{num}","D1");
            parameters.put("skip", dataTest2);
            parameters.put("top", dataTest3);
            GetCoursesAcrClassesSemNumTeachersResult result = (GetCoursesAcrClassesSemNumTeachersResult) c.execute(pathParameters, parameters, conn);

            CustomList<Entity> teachers = result.getTeachers();

            /**
             * Assert equal if the information returned by the command are the same than test data.
             */
            assertEquals(teachers.size(), tableAuxContent.size());

            for (int i = 0; i < teachers.size(); i++) {

                Teacher actual = (Teacher)teachers.get(i), expected = (Teacher)tableAuxContent.get(i);

                assertEquals( expected.getEmail(), actual.getEmail());
                assertEquals(expected.getName(), actual.getName());
                assertEquals(expected.getNumber(), actual.getNumber());
            }

        } catch (SQLException | ParametersException
                | NotExistInformationException | InvalidTypeException  e) {
            System.out.println(e.getMessage());
            assertFalse(true);
        }
    }

    @Test
    public void containingParametersTest() {
        try {
            /* Get the command to be tested. */
            Command c = new GetCoursesAcrClassesSemNumTeachers();
            parameters.put("teste", null);
            pathParameters.put("{acr}","LS");
            pathParameters.put("{sem}","1617v");
            pathParameters.put("{num}","D1");

            c.execute(pathParameters,parameters, conn);
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
        try {
            /* Get the command to be tested. */
            Command c = new GetCoursesAcrClassesSemNumTeachers();
            pathParameters.put("{acr}","false");
            pathParameters.put("{sem}","1617v");
            pathParameters.put("{num}","D1");

            c.execute(pathParameters,null, conn);
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
    public void notExistingInformationInDBTest(){
        try {
            /* Get the command to be tested. */
            Command c = new GetCoursesAcrClassesSemNumTeachers();
            pathParameters.put("{acr}","COM");
            pathParameters.put("{sem}","1617v");
            pathParameters.put("{num}","N2");
            c.execute(pathParameters,null, conn);

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