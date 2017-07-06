package pt.isel.ls.CommandsTests.ProgrammeManagementTests;

import pt.isel.ls.CommandsTests.CommandsTest;
import pt.isel.ls.Model.DataStructures.CustomPair;
import pt.isel.ls.Model.Entities.Course;
import pt.isel.ls.Model.Entities.Programme;
import pt.isel.ls.Model.Mappers.Courses;
import pt.isel.ls.Model.Mappers.Programmes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProgrammeManagement extends CommandsTest {
    /***************Auxiliar Methods for Tests**************/
    protected ResultSet selectProgramme(String param, Connection conn) throws SQLException {
        String select = "SELECT * FROM Programme";
        PreparedStatement ps;
        if(param != null){
            select += "\nWHERE acronym = ?\n" +
                    "ORDER BY acronym";
            ps = conn.prepareStatement(select);
            ps.setString(1, param);
        } else ps = conn.prepareStatement(select);
        return ps.executeQuery();
    }

    protected ResultSet selectCourse(String params, Connection conn) throws SQLException {
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

    protected CustomPair<Programme, Course> selectCourProgrcurr(String[] params, Connection conn) throws SQLException {
        String select = "SELECT * FROM CourProgrcurr\n" +
                "WHERE pid = ? AND cName = ? AND curricularSemester = ? \n" +
                "ORDER BY cName";
        PreparedStatement ps;
        String pid = params[0], cName = params[1];
        ps = conn.prepareStatement(select);
        ps.setString(1, pid);
        ps.setString(2, cName);
        ps.setInt(3,Integer.parseInt(params[2]));

        ResultSet resultSet = ps.executeQuery();
        if(!resultSet.next()) return null;

        Programmes programmes = new Programmes();
        Programme programme = (Programme)programmes.getData(selectProgramme(pid, conn)).get(0);

        Courses courses = new Courses();
        Course course = (Course)courses.getData(selectCourse(cName, conn)).get(0);

        return new CustomPair<>(programme, course);
    }
}