package pt.isel.ls.Model.Commands.QueryOperations;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Class;
import pt.isel.ls.Model.Entities.*;
import pt.isel.ls.Model.Mappers.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QueryOperations {
    private static PreparedStatement ps;
    private static final Courses COURSES = new Courses();
    private static final Classes CLASSES = new Classes();
    private static final Students STUDENTS = new Students();

    /**
     * Query that selects the course with acronym acr.
     * @param conn Connection with DataBase.
     * @param acr Course acronym.
     * @return The course with acronym acr.
     * @throws SQLException
     */
    public static Course queryCoursesAcr(Connection conn, String acr) throws SQLException {
        /* Query to select the course with acronym acr. */
        String elementInformation = "SELECT * FROM course\n" +
                                    "WHERE acronym = ?\n"+
                                    "ORDER BY name";

        /* Build prepared statement. */
        ps = conn.prepareStatement(elementInformation);
        ps.setString(1, acr);

        /* Map the information resultant of the query into courses. */
        CustomList<Entity> courses = COURSES.getData(ps.executeQuery());

        /* As this query only selects one element if it exists returns it otherwise returns null. */
        return courses.size() == 0 ? null : (Course) courses.get(0);
    }

    /**
     * Query that selects the programme with the ID pid.
     * @param conn Connection with DataBase.
     * @param pid Programme ID.
     * @return The programme with the ID pid.
     * @throws SQLException
     */
    public static Programme queryProgrammesPid(Connection conn, String pid) throws SQLException {
        /* Query to select the information about the programme with the pid acr. */
        String programmeInformation = "SELECT * FROM programme\n" +
                                      "WHERE acronym = ?\n" +
                                      "ORDER BY acronym";

        /* Build prepared statement. */
        PreparedStatement ps = conn.prepareStatement(programmeInformation);
        ps.setString(1, pid);

        /* Map the information resultant of the query into programmes. */
        CustomList<Entity> programmes = new Programmes().getData(ps.executeQuery());

        /* As this query only selects one element if it exists returns it otherwise returns null. */
        return programmes.size() == 0 ? null : (Programme) programmes.get(0);
    }

    /**
     * Query that selects all the students belonging to the class of course acr in the year year and semester sem.
     * @param conn Connection with DataBase.
     * @param acr Course acronym.
     * @param year Year that this class belongs to.
     * @param sem Semester that this class belongs to.
     * @param num Class ID.
     * @param skip Number of rows to skip.
     * @param top Number of rows to limit.
     * @return All the students belonging to the class of course acr in the year year and semester sem.
     * @throws SQLException
     */
    public static CustomList<Entity> queryCoursesAcrClassesSemNumStudents(Connection conn, String acr, int year, String sem, String num, int skip, int top) throws SQLException {
        /* Query that selects the students belonging to the class of the acr course, in the sem semester and with the num ID. */
        String getStudent = "SELECT r.number,  r.pid, r.email, r.name\n" +
                            "FROM (SELECT S.number,  S.pid, S.email, S.name, ROW_NUMBER() OVER(ORDER BY S.number) AS Row\n" +
                                  "FROM classStudent AS CS\n" +
                                  "JOIN student AS S ON CS.sNumber = S.number\n" +
                                  "JOIN course AS Cour ON Cour.name = CS.cName\n" +
                                  "WHERE Cour.acronym = ? AND CS.aYear = ? AND CS.aSemester = ? AND CS.cId = ?) AS r\n" +
                            "WHERE r.Row > ? AND r.Row <= ?";

        /* Build prepared statement. */
        ps = conn.prepareStatement(getStudent);
        ps.setString(1, acr);
        ps.setInt(2, year);
        ps.setString(3, sem);
        ps.setString(4, num);
        ps.setInt(5, skip);
        ps.setInt(6, skip + top);

        /* Map the information resultant of the query into students. */
        return STUDENTS.getData(ps.executeQuery());
    }

    /**
     * Query that selects all the students not belonging to the class of course acr in the year year and semester sem.
     * @param conn Connection with DataBase.
     * @param acr Course acronym.
     * @param year Year that this class belongs to.
     * @param sem Semester that this class belongs to.
     * @param num Class ID.
     * @param skip Number of rows to skip.
     * @param top Number of rows to limit.
     * @return All the students not belonging to the class of course acr in the year year and semester sem.
     * @throws SQLException
     */
    public static CustomList<Entity> queryCoursesAcrClassesSemNumStudentsNotEnrolled(Connection conn, String acr, int year, String sem, String num, int skip, int top) throws SQLException {
        /* Query that selects the students not belonging to the class of the acr course, in the sem semester and with the num ID. */
        String getStudent = "SELECT * from student as t\n" +
                            "EXCEPT\n" +
                            "SELECT DISTINCT S.number,  S.pid, S.email, S.name FROM (classStudent as CS JOIN student as S ON (CS.sNumber = S.number)\n" +
                            "JOIN course as Cour ON (Cour.name = CS.cName))\n" +
                            "WHERE Cour.acronym = ? AND CS.aYear = ? AND CS.aSemester = ? AND CS.cId = ?\n" +
                            "ORDER BY t.number\n";

        /* Build prepared statement. */
        ps = conn.prepareStatement(getStudent);
        ps.setString(1, acr);
        ps.setInt(2, year);
        ps.setString(3, sem);
        ps.setString(4, num);

        /* Map the information resultant of the query into students. */
        return STUDENTS.getData(ps.executeQuery());
    }

    /**
     * Selects the class of course acr in the year year and semester sem.
     * @param conn Connection with DataBase.
     * @param num Class ID.
     * @param acr Course acronym.
     * @param year Year that this class belongs to.
     * @param sem Semester that this class belongs to.
     * @return The class of course acr in the year year and semester sem.
     * @throws SQLException
     */
    public static Class queryCoursesAcrClassesSemNum(Connection conn, String num, String acr, int year, String sem) throws SQLException {
       /* Query that selects the information about the class of course acr in the semester sem and year year with id num. */
        String classInformation = "SELECT Cl.id, Cl.cName, Cl.aYear, Cl.aSemester FROM class AS Cl\n" +
                                  "JOIN course AS Cour ON (Cour.name = Cl.cName)\n" +
                                  "WHERE Cour.acronym = ? AND Cl.aYear = ? AND Cl.aSemester = ? AND Cl.id = ?\n" +
                                  "ORDER BY Cl.id";

        /* Build prepared statement. */
        PreparedStatement ps = conn.prepareStatement(classInformation);
        ps.setString(1, acr);
        ps.setInt(2, year);
        ps.setString(3, sem);
        ps.setString(4, num);

        /* Map the information resultant of the query into classes. */
        CustomList<Entity> classes = CLASSES.getData(ps.executeQuery());

        /* As this query only selects one element if it exists returns it otherwise returns null. */
        return classes.size() == 0 ? null :  (Class) classes.get(0);
    }

    /**
     * Query that selects the information about the student that is in the class represented by the parameters.
     * @param conn  Connection with DataBase.
     * @param num Class ID.
     * @param courseName Course name.
     * @param year Year that this class belongs to.
     * @param sem Semester that this class belongs to.
     * @param studentNumber Student number.
     * @return The student that is in the class with ID num in the year year and semester sem of course courseName with number studentNumber.
     * @throws SQLException
     */
    public static Student queryCoursesAcrClassesSemNumStudentNum(Connection conn, String num, String courseName, int year, String sem, int studentNumber) throws SQLException {
        /* Query that selects the information about the student with number num, that is in the class of course acr in the semester sem with id num. */
        String studentInClass = "SELECT S.number, S.pid, S.email, S.name\n" +
                                "FROM classStudent AS CS\n" +
                                "JOIN Student S  ON (CS.sNumber = S.number)\n" +
                                "WHERE CS.cId = ? AND CS.cName = ? AND CS.aYear = ? AND CS.aSemester = ? AND S.Number = ?";

        /* Build prepared statement. */
        PreparedStatement ps = conn.prepareStatement(studentInClass);
        ps.setString(1, num);
        ps.setString(2, courseName);
        ps.setInt(3, year);
        ps.setString(4, sem);
        ps.setInt(5, studentNumber);

        /* Map the information resultant of the query into students. */
        CustomList<Entity> students = STUDENTS.getData(ps.executeQuery());

        /* As this query only selects one element if it exists returns it otherwise returns null. */
        return students.size() == 0 ? null :  (Student) students.get(0);
    }

    /**
     * Query that selects all classes that are in the semester and with ID received in the parameters.
     * @param conn Connection with DataBase.
     * @param num Class ID.
     * @param year Year that this class belongs to.
     * @param sem Semester that this class belongs to.
     * @return All the classes that are in the semester sem and with ID num.
     * @throws SQLException
     */
    public static CustomList<Entity> queryClassesSemNum(Connection conn, String num, int year, String sem) throws SQLException {
        /* Query that selects the information about the class in the semester sem with ID num. */
        String classInformation = "SELECT Cl.id, Cl.cName, Cl.aYear, Cl.aSemester\n" +
                                  "FROM class AS Cl\n" +
                                  "JOIN course AS Cour ON (Cour.name = Cl.cName)\n" +
                                  "WHERE  Cl.aYear = ? AND Cl.aSemester = ? AND Cl.id = ?\n" +
                                  "ORDER BY Cl.id";

        /* Build prepared statement. */
        PreparedStatement ps = conn.prepareStatement(classInformation);
        ps.setInt(1, year);
        ps.setString(2, sem);
        ps.setString(3, num);

        /* Map the information resultant of the query into classes. */
        return CLASSES.getData(ps.executeQuery());
    }

    /**
     * Query that selects the student with the number num.
     * @param conn Connection with DataBase.
     * @param num Student's number.
     * @return The student with the number num.
     * @throws SQLException
     */
    public static Student queryStudentNum(Connection conn, int num) throws SQLException {
        /* Query that selects all the information from the student. */
        String getStudent = "SELECT * FROM student\n" +
                            "WHERE number = ?\n" +
                            "ORDER BY number\n";

        /* Build prepared statement. */
        PreparedStatement ps = conn.prepareStatement(getStudent);
        ps.setInt(1, num);

        /* Map the information resultant of the query into students. */
        CustomList<Entity> students = STUDENTS.getData(ps.executeQuery());

        /* As this query only selects one element if it exists returns it otherwise returns null. */
        return students.size() == 0 ? null :  (Student) students.get(0);
    }


    /**
     * Query that selects the teacher with the number num.
     * @param conn Connection with DataBase.
     * @param num Teacher's number.
     * @return The teacher with the number num.
     * @throws SQLException
     */
    public static Teacher queryTeacherNum(Connection conn, int num) throws SQLException {
        /* Query that selects all information from the teacher. */
        String teacher = "SELECT * FROM teacher WHERE number = ?";

        /* Build prepared statement. */
        PreparedStatement ps = conn.prepareStatement(teacher);
        ps.setInt(1, num);

        /* Map the information resultant of the query into teachers. */
        CustomList<Entity> teachers =  new Teachers().getData(ps.executeQuery());

        /* As this query only selects one element if it exists returns it otherwise returns null. */
        return teachers.size() == 0 ? null :  (Teacher) teachers.get(0);
    }
}