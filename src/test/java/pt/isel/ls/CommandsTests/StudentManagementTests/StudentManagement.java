package pt.isel.ls.CommandsTests.StudentManagementTests;

import pt.isel.ls.CommandsTests.CommandsTest;
import pt.isel.ls.Model.DataStructures.CustomPair;
import pt.isel.ls.Model.Entities.Class;
import pt.isel.ls.Model.Entities.Student;
import pt.isel.ls.Model.Mappers.Classes;
import pt.isel.ls.Model.Mappers.Students;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentManagement extends CommandsTest {
    /***************Auxiliar Methods for Tests**************/
    protected static ResultSet selectStudents(String param, Connection conn) throws SQLException {
        String select = "SELECT * FROM student";
        PreparedStatement ps;
        if(param != null){
            select += "\nWHERE number = ?";
            ps = conn.prepareStatement(select);
            ps.setInt(1, Integer.parseInt(param));
        } else ps = conn.prepareStatement(select);
        return ps.executeQuery();
    }

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

    protected static CustomPair<Class,Student> selectClassStudents(String[] params, Connection conn) throws SQLException {
        String select = "SELECT * FROM classStudent\n" +
                "WHERE cId = ? AND cName = ? AND aYear = ? AND aSemester = ? AND sNumber = ?\n" +
                "ORDER BY sNumber";
        String[] classPrimaryKey = { params[0],  params[1],  params[2],  params[3]};
        String studentPrimaryKey = params[4];
        PreparedStatement ps = conn.prepareStatement(select);
        ps.setString(1, params[0]);
        ps.setString(2, params[1]);
        ps.setInt(3,Integer.parseInt(params[2]));
        ps.setString(4, params[3]);
        ps.setInt(5, Integer.parseInt(studentPrimaryKey));
        if(!ps.executeQuery().next()) return null;
        Classes classes = new Classes();
        Class _class = (Class)classes.getData(selectClass(classPrimaryKey, conn)).get(0);

        Students students = new Students();
        Student student = (Student)students.getData(selectStudents(studentPrimaryKey, conn)).get(0);

        return new CustomPair<>(_class, student);
    }
}