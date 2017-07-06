package pt.isel.ls.CommandsTests.StudentManagementTests;

import org.junit.Test;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.Commands.StudentManagement.GetCoursesAcrClassesSemNumStudentsSorted;
import pt.isel.ls.Model.CustomExceptions.InvalidTypeException;
import pt.isel.ls.Model.CustomExceptions.NotExistInformationException;
import pt.isel.ls.Model.CustomExceptions.ParametersException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Entities.Student;
import pt.isel.ls.Model.Results.StudentManagementResults.GetCoursesAcrClassesSemNumStudentsResult;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GetCoursesAcrClassesSemNumStudentsSortedTest extends StudentManagement {
    @Test
    public void normalConditionTest() {
        tableAuxContent = new CustomList<>();
        Student dataTest1 = new Student(4111, "LEIC", "pedro@gmail.com", "Pedro");
        Student dataTest2 = new Student(4222, "LM", "manel@gmail.com", "Manel");
        tableAuxContent.add(dataTest1);
        tableAuxContent.add(dataTest2);

        try {
            /**
             * Get the command to be tested.
             */
            Command c = new GetCoursesAcrClassesSemNumStudentsSorted();
            pathParameters.put("{acr}","M1");
            pathParameters.put("{sem}","1415i");
            pathParameters.put("{num}","N1");


            GetCoursesAcrClassesSemNumStudentsResult rt = (GetCoursesAcrClassesSemNumStudentsResult) c.execute(pathParameters, null, conn);
            CustomList<Entity> students = rt.getStudents();

            /**
             * Assert equal if the information returned by the command are the same than test data.
             */
            int lastNumber = 0;
            assertEquals(students.size(), tableAuxContent.size());
            for (int i = 0; i < students.size(); i++) {
                Student actual = (Student) students.get(i), expected = (Student) tableAuxContent.get(i);

                assertEquals( expected.getEmail(), actual.getEmail());
                assertEquals( expected.getPid(), actual.getPid());
                assertEquals(expected.getName(), actual.getName());
                assertEquals(expected.getNumber(), actual.getNumber());
                assertTrue(actual.getNumber() > lastNumber);
                lastNumber = actual.getNumber();
            }
        } catch (SQLException | ParametersException
                | InvalidTypeException | NotExistInformationException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }

    @Test
    public void containingParametersTest() {
        /**
         * Initiate tests data.
         */
        tableAuxContent = new CustomList<>();
        Student dataTest1 = new Student(4111, "LEIC", "pedro@gmail.com", "Pedro");
        Student dataTest2 = new Student(4222, "LM", "manel@gmail.com", "Manel");
        tableAuxContent.add(dataTest1);
        tableAuxContent.add(dataTest2);
        parameters.put("teste", null);
        try {
            /**
             * Get the command to be tested.
             */
            Command c = new GetCoursesAcrClassesSemNumStudentsSorted();
            pathParameters.put("{acr}","M1");
            pathParameters.put("{sem}","1415i");
            pathParameters.put("{num}","N1");
            c.execute(pathParameters, parameters, conn);

            assertTrue(false);
        } catch (SQLException | InvalidTypeException | NotExistInformationException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        } catch (ParametersException e) {
            System.out.println(e.getMessage());
            assertTrue(true);
        }
    }

    @Test
    public void wrongTypeTest() {
        /**
         * Initiate tests data.
         */
        tableAuxContent = new CustomList<>();
        Student dataTest1 = new Student(4111, "LEIC", "pedro@gmail.com", "Pedro");
        Student dataTest2 = new Student(4222, "LM", "manel@gmail.com", "Manel");
        tableAuxContent.add(dataTest1);
        tableAuxContent.add(dataTest2);
        try {
            /**
             * Get the command to be tested.
             */
            Command c = new GetCoursesAcrClassesSemNumStudentsSorted();
            pathParameters.put("{acr}","11");
            pathParameters.put("{sem}","1415i");
            pathParameters.put("{num}","1");
            c.execute(pathParameters,null, conn);

            assertTrue(false);
        } catch (SQLException | ParametersException | NotExistInformationException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        } catch (InvalidTypeException e) {
            System.out.println(e.getMessage());
            assertTrue(true);
        }
    }

    @Test
    public void notExistingInformationInDBTest() {
        /**
         * Initiate tests data.
         */
        tableAuxContent = new CustomList<>();
        Student dataTest1 = new Student(4111, "LEIC", "pedro@gmail.com", "Pedro");
        Student dataTest2 = new Student(4222, "LM", "manel@gmail.com", "Manel");
        tableAuxContent.add(dataTest1);
        tableAuxContent.add(dataTest2);
        try {
            /**
             * Get the command to be tested.
             */
            Command c = new GetCoursesAcrClassesSemNumStudentsSorted();
            pathParameters.put("{acr}","M1");
            pathParameters.put("{sem}","1213v");
            pathParameters.put("{num}","N1");
            c.execute(pathParameters,null, conn);

            assertTrue(false);
        } catch (SQLException | ParametersException | InvalidTypeException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        } catch (NotExistInformationException e) {
            System.out.println(e.getMessage());
            assertTrue(true);
        }
    }
}