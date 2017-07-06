package pt.isel.ls.CommandsTests.ProgrammeManagementTests;

import org.junit.Test;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.Commands.ProgrammeManagement.PostProgrammesPidCourses;
import pt.isel.ls.Model.CustomExceptions.InvalidTypeException;
import pt.isel.ls.Model.CustomExceptions.NotExistInformationException;
import pt.isel.ls.Model.CustomExceptions.ParametersException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.DataStructures.CustomPair;
import pt.isel.ls.Model.Entities.Course;
import pt.isel.ls.Model.Entities.Programme;
import pt.isel.ls.Model.Results.ResultError;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class PostProgrammesPidCoursesTest extends ProgrammeManagement {
    @Test
    public void normalConditionTest() {
        /**
         * Initiate tests data and the mapper.
         */
        CustomList<String> dataTest1 = new CustomList<>();
        dataTest1.add("RCp");
        CustomList<String> dataTest2 = new CustomList<>();
        dataTest2.add("True");
        CustomList<String> dataTest3 = new CustomList<>();
        dataTest3.add("2");

        try {
            /**
             * Get the command to be tested.
             */
            Command c = new PostProgrammesPidCourses();
            parameters.put("acr", dataTest1);
            parameters.put("mandatory", dataTest2);
            parameters.put("semesters", dataTest3);
            pathParameters.put("{pid}", "LEIC");
            c.execute(pathParameters, parameters, conn);

            CustomPair<Programme, Course> auxPair1 = selectCourProgrcurr(new String[]{"LEIC", "Redes", "2"}, conn);
            /**
             * Verify the inserted data is in the table.
             */
            Programme pg = auxPair1.getKey();
            assertEquals("LEIC", pg.getAcronym());
            Course cs = auxPair1.getValue();
            assertEquals("Redes", cs.getName());

        } catch (SQLException | ParametersException
                | InvalidTypeException | NotExistInformationException e) {
            assertFalse(true);
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void badParametersTypeTest() {
        CustomList<String> dataTest1 = new CustomList<>();
        dataTest1.add("RCp");
        CustomList<String> dataTest2 = new CustomList<>();
        dataTest2.add("True");
        CustomList<String> dataTest3 = new CustomList<>();
        dataTest3.add("dois");

        try {
            Command c = new PostProgrammesPidCourses();

            parameters.put("acr", dataTest1);
            parameters.put("mandatory", dataTest2);
            parameters.put("semesters", dataTest3);
            pathParameters.put("{pid}", "LEIC");

            ResultError rt = (ResultError) c.execute(pathParameters, parameters, conn);

            assertEquals(rt.getErrors().get(0).getId(), "semesters");
        } catch (SQLException | ParametersException
                | NotExistInformationException | InvalidTypeException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }

    @Test
    public void badParametersTest() {
        CustomList<String> dataTest1 = new CustomList<>();
        dataTest1.add("RCp");
        CustomList<String> dataTest2 = new CustomList<>();
        dataTest2.add("True");

        try {
            Command c = new PostProgrammesPidCourses();

            parameters.put("acr", dataTest1);
            parameters.put("mandatory", dataTest2);
            pathParameters.put("{pid}", "LEIC");

            ResultError rt = (ResultError) c.execute(pathParameters, parameters, conn);

            assertEquals(rt.getErrors().get(0).getId(), "semesters");
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
    public void badInformationTest() {
        CustomList<String> dataTest1 = new CustomList<>();
        dataTest1.add("RCp");
        CustomList<String> dataTest2 = new CustomList<>();
        dataTest2.add("False");
        CustomList<String> dataTest3 = new CustomList<>();
        dataTest3.add("2,p");

        try {
            Command c = new PostProgrammesPidCourses();

            parameters.put("acr", dataTest1);
            parameters.put("mandatory", dataTest2);
            parameters.put("semesters", dataTest3);
            pathParameters.put("{pid}", "LEIC");

            ResultError rt = (ResultError) c.execute(pathParameters, parameters, conn);

            assertEquals(rt.getErrors().get(0).getId(), "semesters");
        } catch (SQLException | NotExistInformationException | ParametersException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        } catch (InvalidTypeException e) {
            System.out.println(e.getMessage());
            assertTrue(true);
        }
    }
}