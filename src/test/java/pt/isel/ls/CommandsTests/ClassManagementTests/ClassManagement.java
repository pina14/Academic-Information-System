package pt.isel.ls.CommandsTests.ClassManagementTests;

import pt.isel.ls.CommandsTests.CommandsTest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClassManagement extends CommandsTest{
    /***************Auxiliar Method for Tests**************/
    protected static ResultSet selectClass(String[] param, Connection conn) throws SQLException {
        String select = "SELECT * FROM class";
        PreparedStatement ps;
        if(param != null){
            select += "\nWHERE id = ? AND cName = ? AND aYear = ? AND aSemester = ?";
            ps = conn.prepareStatement(select);
            ps.setString(1, param[0]);
            ps.setString(2, param[1]);
            ps.setInt(3, Integer.parseInt(param[2]));
            ps.setString(4, param[3]);
        } else ps = conn.prepareStatement(select);
        return ps.executeQuery();
    }
}