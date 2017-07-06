package pt.isel.ls.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Model.Commands.Additional.Listen;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.CustomExceptions.*;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.DataStructures.CustomMap;
import pt.isel.ls.Model.Results.RedirectResult;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Utils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

public class Servlet extends HttpServlet implements Controller {
    private static final Logger _logger = LoggerFactory.getLogger(Servlet.class);
    private static final String PORT = "8080";
    private Connection conn;

    public Servlet(){
    }

    public static void main(String[] args) {
        _logger.info("Beginning the application through servlet");
        Servlet servlet = new Servlet();
        servlet.run();
    }

    private void initDataSource(){
        String user = System.getenv("USER");
        String serverName = System.getenv("SERVER_NAME");
        String dataBaseName = System.getenv("DATABASE_NAME");

        DATA_SOURCE.setUser(user);
        DATA_SOURCE.setPassword(System.getenv("PASSWORD"));
        DATA_SOURCE.setServerName(serverName);
        DATA_SOURCE.setDatabaseName(dataBaseName);

        _logger.info("Initializing data source with: user={}, server={}, dataBase={}", user, serverName, dataBaseName);
    }

    /**
     * To a given a command validate, performs it and show the result.
     */
    public void run() {
        initDataSource();
        ROUTER.createCommandTree(this, null);
        String serverPort = System.getenv("PORT");
        String port = serverPort == null ? PORT : serverPort;
        _logger.info("Application is communicating through port {}", port);

        try {
            /* Start the web application. */
            Command cmd = new Listen();
            cmd.execute(new CustomMap<>(), Utils.organizeParameters("port=" + port), conn);
        } catch ( InvalidTypeException  | SQLException
                | ParametersException | NotExistInformationException e) {
            _logger.warn("Exception whe trying to execute a command...", e);
        }
    }

    /**
     * Executes the GET commands received via web.
     * @param req Object that contain all the client's request information.
     * @param resp Object for HttpServlets to return information to the client.
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        _logger.info("Beginning to execute a get command through servlet.");

        /* Initiate variables. */
        String typeMethod = req.getMethod(),
               path = req.getRequestURI(),
               parameters = req.getQueryString();

        /* To the given a command validate, performs it and show the result.  */
        runCommand(req, resp, typeMethod, path, Utils.organizeParameters(parameters));
        _logger.info("Finishing the execution of the get command.");
    }

    /**
     * Executes the POST commands received via web.
     * @param req Object that contain all the client's request information.
     * @param resp Object for HttpServlets to return information to the client.
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        _logger.info("Beginning the preparation of the parameters of  a post command through servlet.");
        /* Initiate variables */
        String typeMethod = req.getMethod(), path = req.getRequestURI();
        CustomMap<String, CustomList<String>> parameters = new CustomMap<>();

        /* Transform the map that request contained with the parameters to a CustomMap. */
        Map<String, String[]> map = req.getParameterMap();
        Set<Map.Entry<String, String[]>> entries =  map.entrySet();

        for (Map.Entry<String, String[]> entry: entries){
            CustomList<String> list = new CustomList<>();
            for (int i = 0; i < entry.getValue().length; i++) {
                list.add(entry.getValue()[i]);
            }
            parameters.put(entry.getKey(), list);
        }

         /* To the given a command validate, performs it and show the result.  */
        runCommand(req, resp, typeMethod, path, parameters);
        _logger.info("Finishing the execution of the post command.");
    }

    /**
     * To the given a command validate, performs it and show the result.
     * @param req Object that contain all the client's request information.
     * @param resp Object for HttpServlets to return information to the client.
     * @param typeMethod Define the type of action to perform.
     * @param path Define the resource on which the command is executed.
     * @param parameters Sequence of "name=value" pairs, separated by "&".
     */
    private void runCommand(HttpServletRequest req, HttpServletResponse resp, String typeMethod, String path, CustomMap<String, CustomList<String>> parameters) {
        _logger.info("Beginning to run the command of the type {}", typeMethod);
       /* Initiate variables. */
       Charset utf8 = Charset.forName("utf-8");
       byte[] respBodyBytes = null;
       String errorMessage = "";
       CustomMap<String, String> pathParameters = new CustomMap<>();
       /* Get the format to format the result. */
       String[] headers = req.getHeaders("Accept").nextElement().split(",");
       String viewType = getHeader(headers);
       try {
           /* Get a connection with the database. */
           conn = DATA_SOURCE.getConnection();

           /* Get the command and the view according to the user input. */
           Command command = ROUTER.getCommand(typeMethod, path, pathParameters);

           /* Get the command result and sets the view. */
           Result rt = command.execute(pathParameters, parameters, conn);
           rt.setView(viewType);

           /* Get the bytes representing view of the command. */
           StringWriter writer = new StringWriter();
           rt.write(writer);
           resp.setContentType(String.format(viewType + "; charset=%s", utf8.name()));
           respBodyBytes  = writer.toString().getBytes(utf8);

           /* If its a GET command sets status to show its view otherwise
            * Check if its a POST successful result to set status code to redirect or
            * In case of being an unsuccessful POST result set status code to show its view. */
           if(typeMethod.equals("GET"))
               resp.setStatus(HttpServletResponse.SC_OK);
           else{
               if(rt instanceof RedirectResult){
                   /* Go to the page with the url stored in the header location */
                   resp.setHeader("Location",((RedirectResult)rt).getUri());
                   resp.setStatus(HttpServletResponse.SC_SEE_OTHER);
               }else
                   resp.setStatus(HttpServletResponse.SC_OK);
           }

       } catch (InvalidTypeException | ParametersException | HeadersMismatchException e) {
           _logger.warn("The command was not executed with success ", e);
           resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
       } catch (SQLException | IOException e) {
           _logger.error("Error in the server ", e);
           resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
       } catch (NotAvailableCommandException e) {
           _logger.warn("The command doesn't exist");
           resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
       } catch (NotExistInformationException e) {
           _logger.warn("Not available information. ", e);
           resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
       } finally {
           if(conn != null){
               try {
                   if(respBodyBytes != null){
                       OutputStream outputStream = resp.getOutputStream();
                       resp.setContentLength(respBodyBytes.length);
                       outputStream.write(respBodyBytes);
                       outputStream.flush();
                       outputStream.close();
                   } else
                       resp.sendError(resp.getStatus(), errorMessage);
                   conn.close();
               } catch (SQLException | IOException e) {
                   _logger.error("Unable to end the execution of the command.",e);
               }
           }
       }
    }

    /**
     * Seek in the String[] containing the formats which shall format to.
     * @param headers Composed by a sequence of "name-value" pairs, where each pair is separated by the '|' character. The name is separated from the value by the ':' character.
     * @return String that contains the first supported header found ("text/html", "application/json", "text/plain")..
     */
    private String getHeader(String[] headers) {
        /* Initiate variable containing the acceptable formats to this application. */
        String[] accept = {"text/html", "application/json", "text/plain"};
        /* Loop over the different formats offered by the input and checks if any is equal to the acceptable ones,
         * if so returns it. */
        for (int i = 0; i < accept.length; i++) {
            for (int j = 0; j < headers.length; j++) {
                if (headers[j].equals(accept[i]))
                    return headers[j];
            }
        }
        return null;
    }

    @Override
    public void requestExit() {}
}