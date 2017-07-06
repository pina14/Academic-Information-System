package pt.isel.ls.Model.Commands.Additional;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Controller.Servlet;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.CustomExceptions.ParametersException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.DataStructures.CustomMap;
import pt.isel.ls.Model.Entities.EntityCommand;
import pt.isel.ls.Model.Results.NullResult;
import pt.isel.ls.Model.Results.Result;

import java.io.Closeable;
import java.sql.Connection;

public class Listen implements Command {
    private static final Logger _logger = LoggerFactory.getLogger(Listen.class);
    private Integer portNumber;
    private Server server;

    public Listen(){
    }

    public Listen(String description, String template, CustomList<Closeable> closeables) {
        commands.add(new EntityCommand(template, description));

        /* Add closeable */
        if(closeables != null) {
            closeables.add(() -> {
                try {
                    if (server != null)
                        server.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * Start the HTTP server.
     * @param pathParameters CustomMap<String, String> that will have the parameters passed in the path.
     * @param parameters CustomMap<String, CustomList<String>> that contains the parameters.
     * @param conn Connection with DataBase.
     * @return The result associated to this command.
     * @throws ParametersException received from validateParameters
     */
    @Override
    public Result execute(CustomMap<String, String> pathParameters, CustomMap<String, CustomList<String>> parameters, Connection conn) throws ParametersException {
        _logger.info("Beginning the execution of the command Listen");

        /* Validate if everything is correct to continue the command. */
        validateParameters(parameters);

        _logger.info("Beginning to execute the command LISTEN .");

        /* Start the server with the port number received as parameter. */
        server = new Server(portNumber);

        /* Create the servlet handler and set the handler to the server. */
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);
        handler.addServletWithMapping(new ServletHolder(new Servlet()), "/*");

        /* Start the server. */
        try {
            server.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        _logger.info("End the execution of the command Listen");

        /* Return query result. */
        return new NullResult();
    }

    /**
     * Validate if there is only one parameter being the number that indicates which port the server shall listen to and validate its type.
     * @param parameters CustomMap<String, CustomList<String>> that contains the parameters.
     * @throws ParametersException in case any of the parameters isn't valid
     */
    private void validateParameters(CustomMap<String, CustomList<String>> parameters) throws ParametersException {
        /* Verify if exist parameter and if it is acceptable. */
        if(parameters == null || parameters.size() != 1)
            throw new ParametersException("This command must receive only the port number as parameter.");

        /* Get the port entry. */
        CustomList<String> auxPort = parameters.get("port");

        /* Verify if the entry that indicates the port number exists */
        if(auxPort == null)
            throw new ParametersException("This command must receive only the port number as parameter.");

        /* Validate its type, must be an integer, and affects the portNumber variable since it is everything correct so far. */
        portNumber = auxPort.getInt(0);
        if(portNumber == null)
            throw new ParametersException("port wasn't properly inserted");
    }
}