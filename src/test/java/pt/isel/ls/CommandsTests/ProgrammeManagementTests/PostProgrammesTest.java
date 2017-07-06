package pt.isel.ls.CommandsTests.ProgrammeManagementTests;

import org.junit.Test;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.Commands.ProgrammeManagement.PostProgrammes;
import pt.isel.ls.Model.CustomExceptions.InvalidTypeException;
import pt.isel.ls.Model.CustomExceptions.NotExistInformationException;
import pt.isel.ls.Model.CustomExceptions.ParametersException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Programme;
import pt.isel.ls.Model.Mappers.Programmes;
import pt.isel.ls.Model.Results.ResultError;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class PostProgrammesTest extends ProgrammeManagement {
    @Test
    public void normalConditionTest() {
        /**
         * Initiate tests data and the mapper.
         */
        CustomList<String> dataTest1 = new CustomList<>();
        dataTest1.add("LEETC");
        CustomList<String> dataTest2 = new CustomList<>();
        dataTest2.add("Teste");
        CustomList<String> dataTest3 = new CustomList<>();
        dataTest3.add("1");
        Programmes programmes = new Programmes();

        try {
            /**
             * Get the command to be tested.
             */
            Command c = new PostProgrammes();
            parameters.put("pid", dataTest1);
            parameters.put("name", dataTest2);
            parameters.put("length", dataTest3);
            c.execute(null, parameters, conn);

            /**
             * Get the result of the select
             */
            tableAuxContent = programmes.getData(selectProgramme("LEETC", conn));

            /**
             * Assert equal if the information returned by teh select are the same than the one inserted by the command.
             */
            assertEquals(1, tableAuxContent.size() );
            Programme aux = (Programme)tableAuxContent.get(0);
            assertEquals("Teste", aux.getName());
            assertEquals("LEETC", aux.getAcronym());
            assertEquals(1, aux.getNumSemester());

        } catch (SQLException | ParametersException
                | InvalidTypeException | NotExistInformationException e) {
            assertFalse(true);
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void badParametersTypeTest() {
        CustomList<String> dataTest1 = new CustomList<>();
        dataTest1.add("LEETC");
        CustomList<String> dataTest2 = new CustomList<>();
        dataTest2.add("Teste");
        CustomList<String> dataTest3 = new CustomList<>();
        dataTest3.add("um");
        try {
            Command c = new PostProgrammes();
            parameters.put("pid", dataTest1);
            parameters.put("name", dataTest2);
            parameters.put("length", dataTest3);

            ResultError rt = (ResultError) c.execute(pathParameters, parameters, conn);

            assertEquals(rt.getErrors().get(0).getId(), "length");
        } catch (SQLException | ParametersException
                | NotExistInformationException | InvalidTypeException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }

    @Test
    public void badParametersTest() {
        CustomList<String> dataTest1 = new CustomList<>();
        dataTest1.add("LEETC");
        CustomList<String> dataTest2 = new CustomList<>();
        dataTest2.add("Teste");

        try {
            Command c = new PostProgrammes();
            parameters.put("pid", dataTest1);
            parameters.put("name", dataTest2);

            ResultError rt = (ResultError) c.execute(pathParameters, parameters, conn);

            assertEquals(rt.getErrors().get(0).getId(), "length");
        } catch (SQLException | InvalidTypeException
                | NotExistInformationException | ParametersException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }
}