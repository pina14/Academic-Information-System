package pt.isel.ls.Model.Commands.UserManagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.CustomExceptions.InvalidTypeException;
import pt.isel.ls.Model.CustomExceptions.NotExistInformationException;
import pt.isel.ls.Model.CustomExceptions.ParametersException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.DataStructures.CustomMap;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Entities.EntityCommand;
import pt.isel.ls.Model.Mappers.Users;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Results.UserManagementResults.GetUsersResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetUsers implements Command {
    private Integer skip, top;
    private static final Logger _logger = LoggerFactory.getLogger(GetUsers.class);

    public GetUsers() {
    }

    public GetUsers(String description, String template) {
        commands.add(new EntityCommand(template, description));
    }

    /**
     * shows all users.
     * @param pathParameters
     * @param parameters
     * @param conn
     * @return
     * @throws SQLException
     * @throws NotExistInformationException
     */
    public Result execute(CustomMap<String, String> pathParameters, CustomMap<String, CustomList<String>> parameters, Connection conn) throws ParametersException, InvalidTypeException, SQLException {
        _logger.info("Validate Information to execute the command.");

        validateParameters(parameters);

        _logger.info("Beginning to execute the command GET /users");

        String get = "SELECT r2.number, r2.email, r2.name, r2.Type\n" +
                "FROM (SELECT r1.number, r1.email, r1.name, r1.Type, ROW_NUMBER() OVER(ORDER BY r1.number) AS Row\n" +
                "FROM (SELECT number, email, name, 'students' AS 'Type'\n" +
                "FROM student\n" +
                "UNION\n" +
                "SELECT number, email, name, 'teachers' AS 'Type'\n" +
                "FROM teacher)\n" +
                "AS r1)\n" +
                "AS r2\n" +
                "WHERE Row > ? AND Row <= ?\n" +
                "ORDER BY r2.number";

        PreparedStatement ps = conn.prepareStatement(get);
        ps.setInt(1, skip);
        ps.setInt(2, skip + top);

        /* Return the query result. */
        Users usersMapper = new Users();
        CustomList<Entity> users = usersMapper.getData(ps.executeQuery());

        _logger.info("End the execute of the command GET /users");

        return new GetUsersResult(users, skip, top, getNumberRows(conn));
    }

    private int getNumberRows(Connection conn) throws SQLException {
        String getNumberStudentRows = "SELECT count(*)\n" +
                                      "FROM student";
        PreparedStatement ps = conn.prepareStatement(getNumberStudentRows);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int numberStudents = rs.getInt(1);

        String getNumberTeacherRows = "SELECT count(*)\n" +
                                      "FROM teacher";
        ps = conn.prepareStatement(getNumberTeacherRows);
        rs = ps.executeQuery();
        rs.next();
        int numberTeachers = rs.getInt(1);

        return numberStudents + numberTeachers;
    }

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