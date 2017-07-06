package pt.isel.ls.Model.Commands.Additional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.DataStructures.CustomMap;
import pt.isel.ls.Model.Entities.EntityCommand;
import pt.isel.ls.Model.Results.NullResult;
import pt.isel.ls.Model.Results.Result;

import java.sql.Connection;

public class GetFavicon implements Command {
    private static final Logger _logger = LoggerFactory.getLogger(GetFavicon.class);

    public GetFavicon(String description, String template) {
        commands.add(new EntityCommand(template, description));
    }

    /**
     * Does nothing, command to avoid exception caused by internet request of favicon.
     * @param pathParameters CustomMap<String, String> that will have the parameters passed in the path.
     * @param parameters CustomMap<String, CustomList<String>> that contains the parameters.
     * @param conn Connection with DataBase.
     * @return The result associated to this command.
     */
    @Override
    public Result execute(CustomMap<String, String> pathParameters, CustomMap<String, CustomList<String>> parameters, Connection conn) {
        _logger.info("Execute the command Get /favicon.ico");
        return new NullResult();
    }
}