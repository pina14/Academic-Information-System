package pt.isel.ls.Phase0;

import org.junit.Test;
import pt.isel.ls.CommandsTests.CommandsTest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class DBTests extends CommandsTest {

    @Test
    public void insert_test() throws SQLException {
        try {
            finish();
            //arrange
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            //act
            insert("42121, hugo@gmail.com, Hugo", conn);
            insert("42200, rui@gmail.com, Rui", conn);
            insert("42400, bruno@gmail.com, Bruno", conn);

            String select = "select * from teacher";
            PreparedStatement ps = conn.prepareStatement(select);
            ResultSet rs =  ps.executeQuery();

            String[] res = getStrings(rs);

            //assert
            assertEquals("42121 hugo@gmail.com Hugo", res[0]);
            assertEquals("42200 rui@gmail.com Rui", res[1]);
            assertEquals("42400 bruno@gmail.com Bruno", res[2]);

        } catch (SQLException e) {
            assertFalse(true);
            e.printStackTrace();
        }
    }

    @Test
    public void select_test() {
        try {
            finish();
            //arrange
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);

            //act
            insert("42121, hugo@gmail.com, Hugo", conn);
            insert("42200, rui@gmail.com, Rui", conn);
            insert("42400, bruno@gmail.com, Bruno", conn);
            String select = "select * from teacher ";
            String condition = "where number = 42121 and name = 'Hugo' ";
            PreparedStatement ps2 = conn.prepareStatement(select + condition);
            ResultSet rs = ps2.executeQuery();

            String[] res = getStrings(rs);

            //assert
            assertEquals("42121 hugo@gmail.com Hugo", res[0]);
            assertEquals(1,res.length);

        } catch (SQLException e) {
            assertFalse(true);
            e.printStackTrace();
        }
    }

    @Test
    public void update_test() {
        try {
            finish();
            //arrange
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);

            //act
            insert("42121, hugo@gmail.com, Hugo", conn);
            insert("42200, rui@gmail.com, Rui", conn);
            insert("42400, bruno@gmail.com, Bruno", conn);
            String select = "select * from teacher ";
            String condition = "where number = 42121 and name = 'Hugo' ";
            PreparedStatement ps = conn.prepareStatement(select + condition);
            ResultSet rs = ps.executeQuery();

            String[] res = getStrings(rs);

            //assert1
            assertEquals("42121 hugo@gmail.com Hugo", res[0]);
            assertEquals(1,res.length);

            //act
            String update = "update teacher set name = 'António' where number = 42121";
            PreparedStatement ps2 = conn.prepareStatement(update);
            ps2.executeUpdate();

            String newSelect = "select name from teacher where number = 42121";
            ps = conn.prepareStatement(newSelect);
            rs = ps.executeQuery();
            String[] nome = getStrings(rs);

            //assert
            assertEquals("António",nome[0]);
        } catch (SQLException e) {
            assertFalse(true);
            e.printStackTrace();
        }
    }

    @Test
    public void delete_test(){
        try {
            finish();
            //arrange
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);

            //act
            /* Insert into database the experimental data for this test. */
            insert("42121, hugo@gmail.com, Hugo", conn);
            insert("42200, rui@gmail.com, Rui", conn);
            insert("42400, bruno@gmail.com, Bruno", conn);

            /* Verify if the data is correctly inserted. */
            ResultSet rs = select("42121", conn);
            assertTrue(rs.next());
            rs = select("42200", conn);
            assertTrue(rs.next());
            rs = select("42400", conn);
            assertTrue(rs.next());

            /* Delete from database the experimental data. */
            delete("42121", conn);
            delete("42200", conn);
            delete("42400", conn);

            //assert
            /* Verify if the experimental data isn't in the database anymore. */
            rs = select("42121", conn);
            assertFalse(rs.next());
            rs = select("42200", conn);
            assertFalse(rs.next());
            rs = select("42400", conn);
            assertFalse(rs.next());

        } catch (SQLException e1) {
            assertFalse(true);
            e1.printStackTrace();
            try {
                if(conn != null) {
                    conn.rollback();
                }
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
    }

    /**
     * Create and execute a prepared statement to insert the data received in arguments,
     * returns the number of rows affected.
     * @param data
     * @param conn
     * @return
     * @throws SQLException
     */
    private static int insert(String data, Connection conn) throws SQLException {
        String insert = "INSERT INTO teacher VALUES (?,?,?)";
        PreparedStatement ps = conn.prepareStatement(insert);
        String[] aux = data.split(", ");
        ps.setInt(1, Integer.parseInt(aux[0]));
        ps.setString(2, aux[1]);
        ps.setString(3, aux[2]);
        return ps.executeUpdate();
    }

    /**
     * Create and execute a prepared statement to delete the row pointed by the data,
     * returns the number of rows affected.
     * @param data
     * @param conn
     * @return
     * @throws SQLException
     */
    private static int delete(String data, Connection conn) throws SQLException {
        String delete = "DELETE FROM teacher WHERE number = ?";
        PreparedStatement ps = conn.prepareStatement(delete);
        String[] aux = data.split(", ");
        ps.setInt(1, Integer.parseInt(aux[0]));
        return ps.executeUpdate();
    }

    /**
     * Create and execute a prepared statement to selectClassTeacher all the information about the data received in arguments,
     * returns the resultset.
     * @param data
     * @param conn
     * @return
     * @throws SQLException
     */
    private static ResultSet select(String data, Connection conn) throws SQLException {
        String select = "SELECT * FROM teacher WHERE number = ?";
        PreparedStatement ps = conn.prepareStatement(select);
        String[] aux = data.split(", ");
        ps.setInt(1, Integer.parseInt(aux[0]));
        return ps.executeQuery();
    }

    private String[] getStrings(ResultSet rs) throws SQLException {
        int colCount = rs.getMetaData().getColumnCount();
        StringBuilder str = new StringBuilder();

        //Go through the result set
        while(rs.next()){
            for (int i = 1; i <= colCount; i++)
                str.append(rs.getString(i)+" ");

            //delete the last space
            str = str.delete(str.length()-1, str.length());
            //add \n char to divide the rows
            str.append("\n");
        }

        //split the string by rows into multiple strings
        String[] res = str.toString().split("\n");
        return res;
    }
}