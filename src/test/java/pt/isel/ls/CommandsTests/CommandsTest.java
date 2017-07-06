package pt.isel.ls.CommandsTests;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.DataStructures.CustomMap;
import pt.isel.ls.Model.Entities.Entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class CommandsTest {
    protected static SQLServerDataSource dataSource = new SQLServerDataSource();
    protected Connection conn = null;
    protected CustomMap<String, String> pathParameters = new CustomMap<>();
    protected CustomMap<String, CustomList<String>> parameters = new CustomMap<>();
    protected CustomList<Entity> tableAuxContent, tableContent;

    private static final String insertStatement = "use LS_phase1_tests\n" +
            "\n" +
            "insert into programme (acronym, name, numSemester)\n" +
            "\tvalues\t('LEIC', 'Licenciatura em Engenharia Informática e Computadores', 6),\n" +
            "\t\t\t('LM', 'Licenciatura em Mecânica', 6)\n" +
            "\n" +
            "split\n" +
            "\n" +
            "insert into academicSemester (academicYear, semesterTime)\n" +
            "\tvalues\t(1415, 'winter'),\n" +
            "\t\t\t(1314, 'winter'),\n" +
            "\t\t\t(1617, 'summer')\n" +
            "\n" +
            "split\n" +
            "\n" +
            "insert into curricularSemester (curricularSemester)\n" +
            "\tvalues\t(1),\n" +
            "\t\t\t(2)\n" +
            "\n" +
            "split\n" +
            "\n" +
            "insert into teacher (number, email, name)\n" +
            "\tvalues\t(3111, 'david@gmail.com', 'David'),\n" +
            "\t\t\t(3222, 'marco@gmail.com', 'Marco'),\n" +
            "\t\t\t(9999, 'Leo@gmail.com', 'Leonardo')\n" +
            "\n" +
            "split\n" +
            "\n" +
            "insert into student (number, pid, email, name)\n" +
            "\tvalues\t(4111, 'LEIC', 'pedro@gmail.com', 'Pedro'),\n" +
            "\t\t\t(4222, 'LM', 'manel@gmail.com', 'Manel')\n" +
            "\n" +
            "split\n" +
            "\n" +
            "insert into course (name, acronym, tNumber)\n" +
            "\tvalues\t('Laboratório de Software', 'LS', 3111),\n" +
            "\t\t\t('Mecanismos 1', 'M1', 3222),\n" +
            "\t\t\t('Redes', 'RCp', 9999),\n" +
            "\t\t\t('Programação', 'PG', 3111)" +
            "\n" +
            "split\n" +
            "\n" +
            "insert into courProgrCurr (pid, cName, curricularSemester, mandatory)\n" +
            "\tvalues\t('LEIC', 'Laboratório de Software', 2, 1),\n" +
            "\t\t\t('LEIC', 'Programação', 1, 1),\n" +
            "\t\t\t('LM', 'Mecanismos 1', 1, 0)\n" +
            "\n" +
            "split\n" +
            "\n" +
            "insert into semCour (cName, aYear, aSemester)\n" +
            "\tvalues\t('Laboratório de Software', 1617, 'summer'),\n" +
            "\t\t\t('Mecanismos 1', 1314, 'winter'),\n"+
            "\t\t\t('Mecanismos 1', 1415, 'winter')\n" +
            "\n" +
            "split\n" +
            "\n" +
            "insert into class (id, cName, aYear, aSemester)\n" +
            "\tvalues\t('D1', 'Laboratório de Software', 1617, 'summer'),\n" +
            "\t\t\t('D1', 'Mecanismos 1', 1314, 'winter'),\n" +
            "\t\t\t('D2', 'Laboratório de Software', 1617, 'summer'),\n" +
            "\t\t\t('N1', 'Mecanismos 1', 1415, 'winter')\n" +
            "\n" +
            "split\n" +
            "\n" +
            "insert into classTeacher (cId, cName, aYear, aSemester, tNumber)\n" +
            "\tvalues\t('D1', 'Laboratório de Software', 1617, 'summer', 3111),\n" +
            "\t\t\t('D1', 'Laboratório de Software', 1617, 'summer', 3222),\n" +
            "\t\t\t('N1', 'Mecanismos 1', 1415, 'winter', 3222)\n" +
            "\n" +
            "split\n" +
            "\n" +
            "insert into classStudent (cId, cName, aYear, aSemester, sNumber)\n" +
            "\tvalues   ('N1', 'Mecanismos 1', 1415, 'winter', 4222),\n"+
            "\t\t\t('N1', 'Mecanismos 1', 1415, 'winter', 4111)";

    @BeforeClass
    public static void configDataSource(){
        dataSource.setUser("G2");
        dataSource.setPassword("pass");
        dataSource.setServerName("localhost");
        dataSource.setDatabaseName("LS_phase1_tests");
    }

    /**
     * Create and execute a prepared statement to insert the data received
     * in arguments, returns the number of rows affected.
     * @param conn
     * @throws SQLException
     */
     public static void insert(Connection conn) throws SQLException {
        String[] inserts = insertStatement.split("split");
        PreparedStatement ps;
        for (int i = 0; i < inserts.length; i++) {
            ps = conn.prepareStatement(inserts[i]);
            ps.executeUpdate();
        }
    }

    @Before
    public void init() throws SQLException {
        conn = dataSource.getConnection();
        conn.setAutoCommit(false);
        insert(conn);
    }

    @After
    public void finish() {
        try {
            if (conn != null) {
                conn.rollback();
                conn.setAutoCommit(true);
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}