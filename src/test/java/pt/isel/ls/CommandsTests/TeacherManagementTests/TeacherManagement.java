package pt.isel.ls.CommandsTests.TeacherManagementTests;

import pt.isel.ls.CommandsTests.CommandsTest;
import pt.isel.ls.Model.DataStructures.CustomPair;
import pt.isel.ls.Model.Entities.Class;
import pt.isel.ls.Model.Entities.Teacher;
import pt.isel.ls.Model.Mappers.Classes;
import pt.isel.ls.Model.Mappers.Teachers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherManagement extends CommandsTest {
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

    protected static ResultSet selectTeachers(String param, Connection conn) throws SQLException {
        String select = "SELECT * FROM teacher";
        PreparedStatement ps;
        if(param != null){
            select += "\nWHERE number = ?";
            ps = conn.prepareStatement(select);
            ps.setInt(1, Integer.parseInt(param));
        } else ps = conn.prepareStatement(select);
        return ps.executeQuery();
    }

    protected CustomPair<Class, Teacher> selectClassTeacher(String[] params, Connection conn) throws SQLException {
        String select = "SELECT * FROM classTeacher\n" +
                        "WHERE cId = ? AND CName = ? AND aYear = ? AND aSemester = ?  AND tNumber = ?";
        String[] classPrimaryKey = { params[0],  params[1],  params[2],  params[3]};
        String TeacherPrimaryKey = params[4];
        PreparedStatement ps = conn.prepareStatement(select);
        ps.setString(1, params[0]);
        ps.setString(2, params[1]);
        ps.setInt(3,Integer.parseInt(params[2]));
        ps.setString(4, params[3]);
        ps.setInt(5, Integer.parseInt(TeacherPrimaryKey));
        if(!ps.executeQuery().next()) return null;

        Classes classes = new Classes();
        Class _class = (Class)classes.getData(selectClass(classPrimaryKey, conn)).get(0);

        Teachers teachers = new Teachers();
        Teacher teacher = (Teacher) teachers.getData(selectTeachers(TeacherPrimaryKey, conn)).get(0);

        return new CustomPair<>(_class, teacher);
    }
}