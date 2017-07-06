package pt.isel.ls.CommandsTests.ProgrammeManagementTests;

import org.junit.Test;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.Commands.ProgrammeManagement.GetProgrammesPidCourses;
import pt.isel.ls.Model.CustomExceptions.InvalidTypeException;
import pt.isel.ls.Model.CustomExceptions.NotExistInformationException;
import pt.isel.ls.Model.CustomExceptions.ParametersException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.CourseSemCurr;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Results.ProgrammeManagementResults.GetProgrammesPidCoursesResult;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class GetProgrammesPidCoursesTest extends ProgrammeManagement {
    @Test
    public void normalConditionTest() {
        /**
         * Initiate tests data.
         */
        tableAuxContent = new CustomList<>();
        CourseSemCurr dataTest1 = new CourseSemCurr("Laboratório de Software", "LS", 3111, 1);
        CourseSemCurr dataTest2 = new CourseSemCurr("Programação", "PG", 3111, 1);
        tableAuxContent.add(dataTest1);
        tableAuxContent.add(dataTest2);

        try {
            /**
             *  Get the command to be tested.
             */
            Command c = new GetProgrammesPidCourses();
            pathParameters.put("{pid}", "LEIC");
            GetProgrammesPidCoursesResult rt = (GetProgrammesPidCoursesResult) c.execute(pathParameters, null, conn);
            CustomList<Entity> coursesWithCurricularSemester = rt.getCoursesWithCurricularSemester();

            /**
             *  Assert equal if the information returned by the command are the same than test data.
             */

            assertEquals(tableAuxContent.size(), coursesWithCurricularSemester.size());

            for (int i = 0; i < coursesWithCurricularSemester.size(); i++) {
                CourseSemCurr actual = (CourseSemCurr) coursesWithCurricularSemester.get(i), expected = (CourseSemCurr)tableAuxContent.get(i);
                assertEquals( expected.getAcronym(), actual.getAcronym());
                assertEquals(expected.getName(), actual.getName());
                assertEquals(expected.gettNumber(), actual.gettNumber());
            }

        } catch (SQLException | ParametersException
                | InvalidTypeException | NotExistInformationException e) {
            System.out.println(e.getMessage());
            assertFalse(true);
        }
    }

    @Test
    /**
     * Tests if the execute of the class GetProgrammesPidCourses with paging behaves as expected.
     */
    public void normalConditionSupportingPagingTest(){
        /**
         * Initiate tests data.
         */
        tableAuxContent = new CustomList<>();
        CourseSemCurr dataTest1 = new CourseSemCurr("Laboratório de Software", "LS", 3111, 1);
        tableAuxContent.add(dataTest1);
        CustomList<String> dataTest2 = new CustomList<>();
        dataTest2.add("0");
        CustomList<String> dataTest3 = new CustomList<>();
        dataTest3.add("1");

        try {
            /**
             * Get the command to be tested.
             */
            Command c = new GetProgrammesPidCourses();
            pathParameters.put("{pid}", "LEIC");
            parameters.put("skip", dataTest2);
            parameters.put("top", dataTest3);

            GetProgrammesPidCoursesResult rt = (GetProgrammesPidCoursesResult) c.execute(pathParameters, parameters, conn);
            CustomList<Entity> coursesWithCurricularSemester = rt.getCoursesWithCurricularSemester();

            /**
             * Assert equal if the information returned by the command are the same than test data.
             */
            assertEquals(tableAuxContent.size(), coursesWithCurricularSemester.size());

            for (int i = 0; i < coursesWithCurricularSemester.size(); i++) {
                CourseSemCurr actual = (CourseSemCurr)coursesWithCurricularSemester.get(i), expected = (CourseSemCurr) tableAuxContent.get(i);

                assertEquals( expected.getAcronym(), actual.getAcronym());
                assertEquals(expected.getName(), actual.getName());
                assertEquals(expected.gettNumber(), actual.gettNumber());
            }

        } catch (SQLException | ParametersException
                | InvalidTypeException | NotExistInformationException e) {
            System.out.println(e.getMessage());
            assertFalse(true);
        }
    }

    @Test
    public void badParametersTest()  {
        try {
            CustomList<String> dataTest = new CustomList<>();
            dataTest.add("4");

            Command c = new GetProgrammesPidCourses();
            pathParameters.put("{pid}", "LEIC");
            parameters.put("pid", dataTest);
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
    public void NotExistingInfoTest() {
        try {
            finish();
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);

            Command c = new GetProgrammesPidCourses();
            pathParameters.put("{pid}", "LEIC");
            c.execute(pathParameters, null, conn);
            assertTrue(false);

        } catch (SQLException | InvalidTypeException | ParametersException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        } catch (NotExistInformationException e) {
            System.out.println(e.getMessage());
            assertTrue(true);
        }
    }
}