package pt.isel.ls.CommandsTests.ProgrammeManagementTests;

import org.junit.Test;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.Commands.ProgrammeManagement.GetProgrammesPid;
import pt.isel.ls.Model.CustomExceptions.InvalidTypeException;
import pt.isel.ls.Model.CustomExceptions.NotExistInformationException;
import pt.isel.ls.Model.CustomExceptions.ParametersException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Programme;
import pt.isel.ls.Model.Results.ProgrammeManagementResults.GetProgrammesPidResult;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class GetProgrammesPidTest extends ProgrammeManagement {
    @Test
    public void normalConditionTest() {
        /**
         * Initiate tests data.
         */
        tableAuxContent = new CustomList<>();
        Programme dataTest1 = new Programme("LEIC", "Licenciatura em Engenharia Informática e Computadores", 6);
        tableAuxContent.add(dataTest1);

        try {
            /**
             * Get the command to be tested.
             */
            Command c = new GetProgrammesPid();
            pathParameters.put("{pid}", "LEIC");
            GetProgrammesPidResult rt = (GetProgrammesPidResult) c.execute(pathParameters,null,conn);
            Programme programme = rt.getProgramme();

            /**
             * Assert equal if the information returned by the command are the same than test data.
             */
            assertEquals(dataTest1.getAcronym(), programme.getAcronym());
            assertEquals(dataTest1.getName(), programme.getName());
            assertEquals(dataTest1.getNumSemester(), programme.getNumSemester());

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
        Programme dataTest1 = new Programme("LEIC","Licenciatura em Engenharia Informática e Computadores", 6);
        tableAuxContent.add(dataTest1);
        CustomList<String> dataTest2 = new CustomList<>();
        dataTest2.add("0");
        CustomList<String> dataTest3 = new CustomList<>();
        dataTest3.add("1");

        try {
            /**
             * Get the command to be tested.
             */
            Command c = new GetProgrammesPid();
            pathParameters.put("{pid}", "LEIC");
            GetProgrammesPidResult rt = (GetProgrammesPidResult) c.execute(pathParameters,null,conn);
            Programme programme = rt.getProgramme();

            /**
             * Assert equal if the information returned by the command are the same than test data.
             */
            assertEquals(dataTest1.getAcronym(), programme.getAcronym());
            assertEquals(dataTest1.getName(), programme.getName());
            assertEquals(dataTest1.getNumSemester(), programme.getNumSemester());


        } catch (SQLException | ParametersException
                | InvalidTypeException | NotExistInformationException e) {
            assertFalse(true);
            System.out.println(e.getMessage());

        }
    }

    @Test
    public void badParametersTest(){
        try {
            CustomList<String> dataTest = new CustomList<>();
            dataTest.add("4");

            Command c = new GetProgrammesPid();
            pathParameters.put("{pid}", "LEIC");
            parameters.put("pid", dataTest);

            c.execute(pathParameters,parameters,conn);

            assertFalse(true);
        } catch (SQLException | InvalidTypeException | NotExistInformationException e) {
            System.out.println(e.getMessage());
            assertFalse(true);
        } catch (ParametersException e) {
            System.out.println(e.getMessage());
            assertFalse(false);
        }
    }

    @Test
    public void notExistingInfoTest() throws SQLException {
        try {
            finish();
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);

            Command c = new GetProgrammesPid();
            pathParameters.put("{pid}", "LEIC");

            c.execute(pathParameters,null,conn);

            assertFalse(true);
        } catch (SQLException | InvalidTypeException | ParametersException  e) {
            System.out.println(e.getMessage());
            assertFalse(true);
        } catch (NotExistInformationException e) {
            System.out.println(e.getMessage());
            assertFalse(false);
        }
    }
}