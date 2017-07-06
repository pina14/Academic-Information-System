package pt.isel.ls.Model.Commands.Additional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.CustomExceptions.ParametersException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.DataStructures.CustomMap;
import pt.isel.ls.Model.Entities.EntityCommand;
import pt.isel.ls.Model.Results.AdditionalCommandsResults.OptionsResult;
import pt.isel.ls.Model.Results.Result;

import java.sql.Connection;

public class Option implements Command {
    private static final Logger _logger = LoggerFactory.getLogger(Option.class);

    public Option(String description, String template) {
        commands.add(new EntityCommand(template, description));
    }

    /**
     * Present a list of the available commands and their characteristics.
     * @param pathParameters CustomMap<String, String> that will have the parameters passed in the path.
     * @param parameters CustomMap<String, CustomList<String>> that contains the parameters.
     * @param conn Connection with DataBase.
     * @return The result associated to this command.
     * @throws ParametersException received from validate
     */
    public Result execute(CustomMap<String, String> pathParameters, CustomMap<String, CustomList<String>> parameters, Connection conn) throws ParametersException {
        _logger.info("Beginning the execution of the command Option.");

        validate(parameters);

        _logger.info("End the execution of the command Option.");

        return new OptionsResult(commands);
    }

    /**
     * Check if all the values needed for the execute are valid.
     * @param parameters CustomMap<String, CustomList<String>> that contains the parameters.
     * @throws ParametersException in case there is a parameter
     */
    public void validate(CustomMap<String, CustomList<String>> parameters) throws ParametersException {
        if (parameters != null)
            throw new ParametersException("This command doesn't support parameters");
    }
}