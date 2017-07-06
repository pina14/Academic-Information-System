package pt.isel.ls.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.CustomExceptions.*;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.DataStructures.CustomMap;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Utils;

import java.awt.*;
import java.io.*;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class App implements Controller{
    private static final Logger _logger = LoggerFactory.getLogger(App.class);
    private boolean exit = false;
    private Connection conn;
    private CustomList<Closeable> closeables = new CustomList<>();

    /**
     * Sets the information in data source needed to get a connection with the DB.
     */
    private void initDataSource(){
        String user = System.getenv("USER");
        String serverName = System.getenv("SERVER_NAME");
        String dataBaseName = System.getenv("DATABASE_NAME");

        DATA_SOURCE.setUser(user);
        DATA_SOURCE.setPassword("pass");
        DATA_SOURCE.setServerName(serverName);
        DATA_SOURCE.setDatabaseName(dataBaseName);

        _logger.info("Initializing data source with: user={}, server={}, dataBase={}", user, serverName, dataBaseName);
    }

    public static void main(String[] args) {
        App app = new App();
        app.run(args);
    }

    /**
     * To a given a command validate, performs it and show the result.
     * @param args String[] with the parameters {method} {path} {headers} {parameters} (headers is optional).
     */
    private void run(String[] args){
        /* Sets information needed to get a connection with DB. */
        initDataSource();

        /* Create a router and also create the tree that will contain the commands and views that are available in this program. */
        ROUTER.createCommandTree(this, closeables);

        /* Determines either it's in automatic or manual mode. */
        if(args.length == 0)
            iterativeMain();
        else
            nonIterativeMain(args);
    }

    /**
     * Automatic mode, it will call the manual mode until the command EXIT is requested.
     */
    private void iterativeMain() {
        _logger.info("Beginning execution of iterative mode of application");

        Scanner scanIn = new Scanner(System.in);
        String command;
        do{
            System.out.print("Input the command to execute: ");
            /* Receive the command from user. */
            command = scanIn.nextLine();
            nonIterativeMain(command.split(" "));
        }  while(!exit);

        _logger.info("Terminating execution of iterative mode of application");
    }

    /**
     * Manual mode. To the given a command validate, performs it and show the result.
     * @param args String[] with the parameters {method} {path} {headers} {parameters} (headers is optional).
     */
    private void nonIterativeMain(String[] args) {
        _logger.info("Beginning execution of non iterative mode of application");

        /* Initiate variables. */
        String typeMethod, path, headers = "", parameters = "";
        CustomList<String> commandRequest;
        try {
            /* Get a connection with the DB and sets the auto commit a false in case anything wrong happens it isn't saved. */
            conn = DATA_SOURCE.getConnection();
            conn.setAutoCommit(false);

            /* Correct the user input if it's inserted in a bad form. */
            commandRequest = Utils.filterSpaces(args);

            /* Identify the different input that user inserted. */
            typeMethod = commandRequest.get(0);
            path = commandRequest.get(1);

            if(commandRequest.size() >= 3) {
                if (commandRequest.size() == 4) {
                    headers = commandRequest.get(2);
                    parameters = commandRequest.get(3);
                } else if (commandRequest.get(2).contains("accept") || commandRequest.get(2).contains("file-name"))
                    headers = commandRequest.get(2);
                else
                    parameters = commandRequest.get(2);
            }

            /* Initiate variables. */
            CustomMap<String, String> pathParameters = new CustomMap<>(), heads = Utils.organizeHeaders(headers);

            /* In case the current command is a post command set to post default view otherwise sets the input view. */
            String viewType = typeMethod.equals("POST") ? "text/plain" : heads.get("accept");

            /* Get the command according to the user input. */
            Command command = ROUTER.getCommand(typeMethod, path, pathParameters);

            /* Get the command result and sets the view. */
            Result rt = command.execute(pathParameters, Utils.organizeParameters(parameters), conn);
            rt.setView(viewType);

            /* Save in database the effects made by the command. */
            conn.commit();

            /* Determine which writer shall use, either write in file or in standard output. */
            String filename = heads.get("file-name");
            OutputStream outputStream = filename == null ? new FilterOutputStream(System.out): new FileOutputStream(filename);

            /* Write the result to the string writer. */
            StringWriter writer = new StringWriter();
            rt.write(writer);

            /* Prints the result to output. */
            outputStream.write(writer.toString().getBytes(Charset.forName("utf-8")));
            outputStream.flush();

            if(filename != null) {
                outputStream.close();

                /* Opens directly the file. */
                Desktop.getDesktop().open(new File(filename));
            }
        } catch (FormatMismatchException  | NotExistInformationException | NotAvailableCommandException
                 | HeadersMismatchException | ParametersException
                 | InvalidTypeException e) {

            /* If something goes wrong rollback the changes made. */
            _logger.warn("An exception occurred while trying to run a command", e);
            try {
                conn.rollback();
                conn.setAutoCommit(true);
            } catch (SQLException e1) {
                _logger.error("Unable to rollback and set AutoCommit to false", e);
            }
        } catch (IOException | SQLException e) {
            _logger.error("An exception occurred while trying to run a command", e);
        } finally {
            if(conn != null){
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    _logger.error("Unable to close connection",2);
                }
            }
        }
        _logger.info("Terminating execution of iterative mode of application");
    }

    /**
     * Set the exit variable true, which means to close programme, and closes all the closeables.
     */
    @Override
    public void requestExit() {
        _logger.info("Terminating execution of application");
        exit = true;
        for (int i = 0; i < closeables.size(); i++) {
            try {
                closeables.get(i).close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}