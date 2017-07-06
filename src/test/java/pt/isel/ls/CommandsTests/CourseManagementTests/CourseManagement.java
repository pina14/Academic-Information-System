package pt.isel.ls.CommandsTests.CourseManagementTests;

import pt.isel.ls.CommandsTests.CommandsTest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseManagement extends CommandsTest{
    /***************Auxiliar Method for Tests**************/
    protected ResultSet select(String params, Connection conn) throws SQLException {
        String select = "SELECT * FROM course";
        PreparedStatement ps;
        if(params != null){
            select += "\nWHERE name = ?\n" +
                    "ORDER BY name";
            ps = conn.prepareStatement(select);
            ps.setString(1, params);
        } else ps = conn.prepareStatement(select);
        return ps.executeQuery();
    }
}