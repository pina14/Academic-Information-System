package pt.isel.ls.CommandsTests.ProgrammeManagementTests;

import org.junit.Test;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.Commands.ProgrammeManagement.GetProgrammes;
import pt.isel.ls.Model.CustomExceptions.InvalidTypeException;
import pt.isel.ls.Model.CustomExceptions.NotExistInformationException;
import pt.isel.ls.Model.CustomExceptions.ParametersException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Entities.Programme;
import pt.isel.ls.Model.Results.ProgrammeManagementResults.GetProgrammesResult;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class GetProgrammesTest extends ProgrammeManagement {
    @Test
    public void normalConditionTest() {
        /**
         * Initiate tests data.
         */
        tableAuxContent = new CustomList<>();
        Programme dataTest1 = new Programme("LEIC", "Licenciatura em Engenharia Informática e Computadores", 6),
                dataTest2 = new Programme("LM", "Licenciatura em Mecânica", 6);

        tableAuxContent.add(dataTest1);
        tableAuxContent.add(dataTest2);

        try {
            /**
             * Get the command to be tested.
             */
            Command c = new GetProgrammes();
            GetProgrammesResult rt = (GetProgrammesResult) c.execute(null, null, conn);
            CustomList<Entity> programmes = rt.getProgrammes();

            /**
             * Assert equal if the information returned by the command are the same than test data.
             */
            assertEquals(programmes.size(), tableAuxContent.size());
            for (int i = 0; i < programmes.size(); i++) {
                Programme actual = (Programme)programmes.get(i), expected = (Programme)tableAuxContent.get(i);
                assertEquals( expected.getAcronym(), actual.getAcronym());
                assertEquals(expected.getName(), actual.getName());
                assertEquals(expected.getNumSemester(), actual.getNumSemester());
            }
        } catch (SQLException | ParametersException
                | InvalidTypeException | NotExistInformationException e) {
            assertFalse(true);
            System.out.println(e.getMessage());
        }
    }

    @Test
    /**
     * Tests if the execute of the class GetProgrammes with paging behaves as expected.
     */
    public void normalConditionSupportingPagingTest() {
        /**
         * Initiate tests data.
         */
        tableAuxContent = new CustomList<>();
        Programme dataTest1 = new Programme("LEIC", "Licenciatura em Engenharia Informática e Computadores", 6);
        tableAuxContent.add(dataTest1);

        CustomList<String> dataTest2 = new CustomList<>();
        dataTest2.add("0");

        CustomList<String> dataTest3 = new CustomList<>();
        dataTest3.add("1");
        try {
            /**
             * Get the command to be tested.
             */
            Command c = new GetProgrammes();
            parameters.put("skip", dataTest2);
            parameters.put("top", dataTest3);
            GetProgrammesResult rt = (GetProgrammesResult) c.execute(null, parameters, conn);
            CustomList<Entity> programmes = rt.getProgrammes();

            /**
             * Assert equal if the information returned by the command are the same than test data.
             */
            assertEquals(programmes.size(), tableAuxContent.size());
            for (int i = 0; i < programmes.size(); i++) {
                Programme actual = (Programme)programmes.get(i), expected = (Programme)tableAuxContent.get(i);
                assertEquals( expected.getAcronym(), actual.getAcronym());
                assertEquals(expected.getName(), actual.getName());
                assertEquals(expected.getNumSemester(), actual.getNumSemester());
            }
        } catch (SQLException | ParametersException
                | InvalidTypeException | NotExistInformationException e) {
            assertFalse(true);
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void badParametersTest(){
        CustomList<String> dataTest = new CustomList<>();
        dataTest.add("0");
        try {
            Command c = new GetProgrammes();
            parameters.put("pid", dataTest);
            c.execute(null, parameters, conn);
            assertTrue(false);
        } catch (SQLException | InvalidTypeException | NotExistInformationException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        } catch (ParametersException e) {
            System.out.println(e.getMessage());
            assertTrue(true);
        }
    }
}