package pt.isel.ls.Model.Commands.Additional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.DataStructures.CustomMap;
import pt.isel.ls.Model.Entities.EntityCommand;
import pt.isel.ls.Model.Results.AdditionalCommandsResults.RootResult;
import pt.isel.ls.Model.Results.Result;

import java.sql.Connection;

public class Root implements Command{
    private static final Logger _logger = LoggerFactory.getLogger(Root.class);

    public Root(String description, String template) {
        commands.add(new EntityCommand(template, description));
    }

    /**
     * Does nothing since the the action is done by its view
     * @param pathParameters CustomMap<String, String> that will have the parameters passed in the path.
     * @param parameters CustomMap<String, CustomList<String>> that contains the parameters.
     * @param conn Connection with DataBase.
     * @return The result associated to this command.
     */
    @Override
    public Result execute(CustomMap<String, String> pathParameters, CustomMap<String, CustomList<String>> parameters, Connection conn) {
        _logger.info("Execute the command Root");
        return new RootResult();
    }
}