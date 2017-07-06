package pt.isel.ls.CommandsTests.UserManagementTests;

import pt.isel.ls.CommandsTests.CommandsTest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserManagement extends CommandsTest {
    /***************Auxiliar Methods for Tests**************/
    protected static ResultSet selectStudents(String param, Connection conn) throws SQLException {
        String select = "SELECT * FROM student";
        PreparedStatement ps;
        if(param != null){
            select += "\nWHERE number = ?";
            ps = conn.prepareStatement(select);
            ps.setInt(1, Integer.parseInt(param));
        } else
            ps = conn.prepareStatement(select);
        return ps.executeQuery();
    }

    protected static ResultSet selectTeachers(String param, Connection conn) throws SQLException {
        String select = "SELECT * FROM teacher";
        PreparedStatement ps;
        if(param != null){
            select += "\nWHERE number = ?";
            ps = conn.prepareStatement(select);
            ps.setInt(1, Integer.parseInt(param));
        } else
            ps = conn.prepareStatement(select);
        return ps.executeQuery();
    }
}