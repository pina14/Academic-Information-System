package pt.isel.ls.Model.Commands.ProgrammeManagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.CustomExceptions.InvalidTypeException;
import pt.isel.ls.Model.CustomExceptions.ParametersException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.DataStructures.CustomMap;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Entities.EntityCommand;
import pt.isel.ls.Model.Mappers.Programmes;
import pt.isel.ls.Model.Results.ProgrammeManagementResults.GetProgrammesResult;
import pt.isel.ls.Model.Results.Result;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetProgrammes implements Command {
    private Integer skip, top;
    private static final Logger _logger = LoggerFactory.getLogger(GetProgrammes.class);

    public GetProgrammes(){
    }

    public GetProgrammes(String description, String template) {
        commands.add(new EntityCommand(template, description));
    }

    /**
     * Get all programmes.
     * @param pathParameters CustomMap<String, String> that will have the parameters passed in the path.
     * @param parameters CustomMap<String, CustomList<String>> that contains the parameters.
     * @param conn Connection with DataBase.
     * @return The result associated to this command.
     * @throws SQLException
     * @throws ParametersException
     * @throws InvalidTypeException
     */
    public Result execute(CustomMap<String, String> pathParameters, CustomMap<String, CustomList<String>> parameters, Connection conn) throws SQLException, ParametersException, InvalidTypeException {
        _logger.info("Validate Information to execute the command.");

        /* Verify that everything is correct to proceed with the command execution. */
        validateParameters(parameters);

        _logger.info("Beginning to execute the command GET /programmes");

        /* Query to select all programmes. */
        String get = "SELECT r.acronym, r.name, r.numSemester\n" +
                     "FROM (SELECT acronym, name, numSemester, ROW_NUMBER() OVER(ORDER BY acronym) AS Row\n" +
                           "FROM programme) AS r\n" +
                     "WHERE r.Row > ? AND r.Row <= ?";

        /* Build prepared statement. */
        PreparedStatement ps = conn.prepareStatement(get);
        ps.setInt(1, skip);
        ps.setInt(2, skip + top);

        /* Map the information resultant of the query into programmes. */
        Programmes programmesMapper = new Programmes();
        CustomList<Entity> programmes = programmesMapper.getData(ps.executeQuery());

        _logger.info("End the execution of the command GET /programmes");

        /* Return the associated result to this command. */
        return new GetProgrammesResult(programmes, skip, top, getNumberRows(conn));
    }

    /**
     * Get the number of rows that the database has associated to this command.
     * @param conn Connection with DataBase.
     * @return Number of rows.
     * @throws SQLException
     */
    private int getNumberRows(Connection conn) throws SQLException {
        /* Query to select the number of rows of programmes. */
        String getNumberRows = "SELECT count(*)\n" +
                               "FROM programme";

        /* Build prepared statement. */
        PreparedStatement ps = conn.prepareStatement(getNumberRows);

        /* Get the number of rows. */
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    /**
     * Validate in case of existing parameters if are the correct ones and if they are in their correct form.
     * @param parameters CustomMap<String, CustomList<String>> that contains the parameters.
     * @throws ParametersException
     * @throws InvalidTypeException
     */
    private void validateParameters(CustomMap<String, CustomList<String>> parameters) throws InvalidTypeException, ParametersException {
        skip = 0;
        top = 20;
        if(parameters != null){
            CustomList<String> skipAux = parameters.get("skip"), topAux = parameters.get("top");

            if (parameters.size() > 2 || parameters.size() <= 2 && skipAux == null && topAux == null)
                throw new ParametersException("This command can't support other parameters than skip and top.");

            if (skipAux != null){
                skip = skipAux.getInt(0);
                if(skip == null)
                    throw new InvalidTypeException("Skip value wasn't properly inserted.");

            }
            if (topAux != null){
                top = topAux.getInt(0);
                if(top == null)
                    throw new InvalidTypeException("Top value wasn't properly inserted.");
            }
        }
    }
}