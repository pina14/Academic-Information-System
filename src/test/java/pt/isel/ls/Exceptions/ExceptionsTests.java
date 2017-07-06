package pt.isel.ls.Exceptions;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.junit.BeforeClass;
import org.junit.Test;
import pt.isel.ls.Controller.App;
import pt.isel.ls.Model.Commands.CourseManagement.GetCoursesAcr;
import pt.isel.ls.Model.CustomExceptions.*;
import pt.isel.ls.Model.DataStructures.CustomMap;
import pt.isel.ls.Model.Router;
import pt.isel.ls.Model.Utils;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertTrue;

public class ExceptionsTests {
    private static Router router = new Router();
    private static App app = new App();
    private static SQLServerDataSource dataSource = new SQLServerDataSource();
    protected Connection conn = null;

    @BeforeClass
    public static void prepareTest(){
        router.createCommandTree(app, null);

        dataSource.setUser("G2");
        dataSource.setPassword("pass");
        dataSource.setServerName("localhost");
        dataSource.setDatabaseName("LS_phase1_tests");
    }

    @Test
    /**
     * Test if the exception NotAvailableCommand is working properly
     */
    public void NotAvailableCommandExceptionTest() {
        String typeMethod = "UPDATE";
        String path = "/courses/LS";
        CustomMap<String, String> pathParameters = new CustomMap<>();
        try {
            router.getCommand(typeMethod,path,pathParameters);
            assertTrue(false);
        } catch (NotAvailableCommandException e) {
            assertTrue(true);
        } catch (HeadersMismatchException e) {
            assertTrue(false);
        }
    }

    @Test
    /**
     * Test if the exception NotExistInformation is working properly
     */
    public void NotExistInformationExceptionTest() {
        String typeMethod = "GET";
        String path = "/courses/AED";
        CustomMap<String, String> pathParameters = new CustomMap<>();

        try {
            conn = dataSource.getConnection();
            GetCoursesAcr command = (GetCoursesAcr) router.getCommand(typeMethod,path, pathParameters);

            command.execute(pathParameters, Utils.organizeParameters(""), conn);

            assertTrue(false);
        } catch (NotExistInformationException e) {
            assertTrue(true);
        } catch (ParametersException | InvalidTypeException
                | HeadersMismatchException | SQLException
                | NotAvailableCommandException e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    @Test
    /**
     * Test if the exception FormatMismatch is working properly
     */
    public void FormatMismatchExceptionTest() {
        try {
            Utils.filterSpaces(new String[]{"GET"});
            assertTrue(false);
        } catch (FormatMismatchException e) {
            assertTrue(true);
        }
    }

    @Test
    /**
     * Test if the exception HeaderMismatch is working properly
     */
    public void HeaderMismatchExceptionTest() {
        try {
            Utils.organizeHeaders("accept:text/plain:text/html");
            assertTrue(false);
        } catch (HeadersMismatchException e) {
            assertTrue(true);
        }
    }
}