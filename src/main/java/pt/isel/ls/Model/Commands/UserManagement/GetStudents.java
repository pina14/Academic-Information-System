package pt.isel.ls.Model.Commands.UserManagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.CustomExceptions.InvalidTypeException;
import pt.isel.ls.Model.CustomExceptions.ParametersException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.DataStructures.CustomMap;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Entities.EntityCommand;
import pt.isel.ls.Model.Mappers.Students;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Results.UserManagementResults.GetStudentsResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetStudents implements Command {
    private Integer skip, top;
    private static final Logger _logger = LoggerFactory.getLogger(GetStudents.class);

    public GetStudents() {
    }

    public GetStudents(String description, String template) {
        commands.add(new EntityCommand(template, description));
    }

    /**
     * shows all students.
     * @param pathParameters
     * @param parameters
     * @param conn
     * @return
     * @throws SQLException
     * @throws ParametersException
     * @throws InvalidTypeException
     */
    @Override
    public Result execute(CustomMap<String, String> pathParameters, CustomMap<String, CustomList<String>> parameters, Connection conn) throws ParametersException, InvalidTypeException, SQLException {
        _logger.info("Validate Information to execute the command.");

        /* Validate if it's everything fine to continue the command. */
        validateParameters(parameters);

        _logger.info("Beginning to execute the command GET /students");

        String get = "SELECT r.number, r.pid, r.email, r.name\n" +
                "FROM (SELECT number, pid, email, name , ROW_NUMBER() OVER(ORDER BY number) AS Row\n" +
                "FROM student) AS r\n" +
                "WHERE r.Row > ? AND r.Row <= ?";

        PreparedStatement ps = conn.prepareStatement(get);
        ps.setInt(1, skip);
        ps.setInt(2, skip + top);

        Students studentsMapper = new Students();
        CustomList<Entity> students = studentsMapper.getData(ps.executeQuery());

        _logger.info("End the execute of the command GET /students");

        return new GetStudentsResult(students, skip, top, getNumberRows(conn));
    }

    /**
     * Get the number of rows that the database has associated to this command.
     * @param conn
     * @return
     * @throws SQLException
     */
    private int getNumberRows(Connection conn) throws SQLException {
        String getNumberRows = "SELECT count(*)\n" +
                               "FROM student";
        PreparedStatement ps = conn.prepareStatement(getNumberRows);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    /**
     * Validate in case of existing parameters if they are the correct ones and if they are in their correct form.
     * @param parameters
     * @throws ParametersException
     * @throws InvalidTypeException
     */
    private void validateParameters(CustomMap<String, CustomList<String>> parameters) throws ParametersException, InvalidTypeException {
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