package pt.isel.ls.Model.Validators;

import pt.isel.ls.Model.Commands.QueryOperations.QueryOperations;
import pt.isel.ls.Model.CustomExceptions.NotExistInformationException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Class;
import pt.isel.ls.Model.Entities.*;

import java.sql.Connection;
import java.sql.SQLException;

public class Validation {

    /**
     *  Validate that the course with acronym acr exists.
     * @param conn Connection with DataBase.
     * @param acr Course acronym.
     * @param errorValidator Object that will contain this validation errors.
     * @param errorID Error ID to be added to the container.
     * @return The course with acronym acr.
     * @throws SQLException
     */
    public static Course validateExistentCoursesAcr(Connection conn, String acr, ErrorValidator errorValidator, String errorID) throws SQLException {
        /* Get the course. */
        Course result = QueryOperations.queryCoursesAcr(conn, acr);

        /* If the course doesn't exist add an error to the error validator. */
        if (result == null)
            errorValidator.addError(errorID, "The course with acronym " + acr + " doesn't exist.");

        return result;
    }

    /**
     * Validate that the course with acronym acr exists.
     * @param conn Connection with DataBase.
     * @param acr Course acronym.
     * @return The course with acronym acr.
     * @throws SQLException
     * @throws NotExistInformationException
     */
    public static Course validateExistentCoursesAcr(Connection conn, String acr) throws SQLException, NotExistInformationException {
        /* Get the course. */
        Course result = QueryOperations.queryCoursesAcr(conn, acr);

        /* If the course doesn't exist throw exception. */
        if (result == null)
           throw new NotExistInformationException("The course with acronym " + acr + " doesn't exist.");
        return result;
    }

    /**
     * Validate that the course with acronym acr doesn't exist.
     * @param conn Connection with DataBase.
     * @param acr Course acronym.
     * @param errorValidator Object that will contain this validation errors.
     * @param errorID Error ID to be added to the container.
     * @throws SQLException
     */
    public static void validateNotExistentCoursesAcr(Connection conn, String acr, ErrorValidator errorValidator, String errorID) throws SQLException {
        /* Get the course. */
        Course result = QueryOperations.queryCoursesAcr(conn, acr);

        /* If the course exists add an error to the error validator. */
        if (result != null)
            errorValidator.addError(errorID, "The acronym " + acr + " its already taken by another course.");
    }

    /**
     * Validate that exist classes in the semester and with ID received in the parameters.
     * @param conn Connection with DataBase.
     * @param num Class ID.
     * @param year Year that this class belongs to.
     * @param sem Semester that this class belongs to.
     * @return The classes in the semester and with ID received in the parameters.
     * @throws SQLException
     * @throws NotExistInformationException
     */
    public static CustomList<Entity> validateExistentClassesSemNum(Connection conn, String num, int year, String sem) throws SQLException, NotExistInformationException {
        /* Get the classes. */
        CustomList<Entity> result = QueryOperations.queryClassesSemNum(conn, num, year, sem);

        /* If the classes doesn't exist throw exception. */
        if (result.size() == 0)
            throw new NotExistInformationException("The Class " + num + " in year " + year + " in semester " + sem + " doesn't exist.");
        return result;

    }

    /**
     * Validate that exist classes in the semester and with ID received in the parameters.
     * @param conn Connection with DataBase.
     * @param num Class ID.
     * @param year Year that this class belongs to.
     * @param sem Semester that this class belongs to.
     * @param errorValidator Object that will contain this validation errors.
     * @param errorID1 Error ID to be added to the container.
     * @param errorID2 Error ID to be added to the container.
     * @return The classes in the semester and with ID received in the parameters.
     * @throws SQLException
     */
    public static CustomList<Entity> validateExistentClassesSemNum(Connection conn, String num, Integer year, String sem, ErrorValidator errorValidator, String errorID1, String errorID2) throws SQLException {
        /* Get the classes. */
        CustomList<Entity> result = QueryOperations.queryClassesSemNum(conn, num, year, sem);

        /* If the classes doesn't exists add errors to the error validator. */
        if (result.size() == 0) {
            errorValidator.addError(errorID1, "The Class " + num + " in year " + year + " in semester " + sem + " doesn't exist.");
            errorValidator.addError(errorID2, "The Class " + num + " in year " + year + " in semester " + sem + " doesn't exist.");
        }
        return result;
    }

    /**
     * Validate that the class of course acr in the year year and semester sem doesn't exist.
     * @param conn Connection with DataBase.
     * @param num Class ID.
     * @param acr Course acronym.
     * @param year Year that this class belongs to.
     * @param sem Semester that this class belongs to.
     * @param errorValidator Object that will contain this validation errors.
     * @param errorID1 Error ID to be added to the container.
     * @param errorID2 Error ID to be added to the container.
     * @param errorID3 Error ID to be added to the container.
     * @throws SQLException
     */
    public static void validateNotExistentCoursesAcrClassesSemNum(Connection conn, String num, String acr, int year, String sem, ErrorValidator errorValidator, String errorID1, String errorID2, String errorID3) throws SQLException {
        /* Get the class. */
        Class result = QueryOperations.queryCoursesAcrClassesSemNum(conn, num, acr, year, sem);

        /* If the class exists add errors to the error validator. */
        if(result != null){
            errorValidator.addError(errorID1, "The class represented by " + num + " in the year " + year + " on semester " + sem + " of course with acronym " + acr + " already exists.");
            errorValidator.addError(errorID2, "The class represented by " + num + " in the year " + year + " on semester " + sem + " of course with acronym " + acr + " already exists.");
            errorValidator.addError(errorID3, "The class represented by " + num + " in the year " + year + " on semester " + sem + " of course with acronym " + acr + " already exists.");
        }
    }

    /**
     * Validate that the class of course acr in the year year and semester sem exists.
     * @param conn Connection with DataBase.
     * @param num Class ID.
     * @param acr Course acronym.
     * @param year Year that this class belongs to.
     * @param sem Semester that this class belongs to.
     * @param errorValidator Object that will contain this validation errors.
     * @param errorID1 Error ID to be added to the container.
     * @param errorID2 Error ID to be added to the container.
     * @param errorID3 Error ID to be added to the container.
     * @return The class of course acr in the year year and semester sem
     * @throws SQLException
     */
    public static Class validateExistentCoursesAcrClassesSemNum(Connection conn, String num, String acr, Integer year, String sem, ErrorValidator errorValidator, String errorID1, String errorID2, String errorID3) throws SQLException {
        /* Get the class. */
        Class result = QueryOperations.queryCoursesAcrClassesSemNum(conn, num, acr, year, sem);

        /* If the class doesn't exist add errors to the error validator. */
        if(result == null){
            errorValidator.addError(errorID1, "The class represented by " + num + " in the year " + year + " on semester " + sem + " of course with acronym " + acr + " doesn't exist.");
            errorValidator.addError(errorID2, "The class represented by " + num + " in the year " + year + " on semester " + sem + " of course with acronym " + acr + " doesn't exist.");
            errorValidator.addError(errorID3, "The class represented by " + num + " in the year " + year + " on semester " + sem + " of course with acronym " + acr + " doesn't exist.");
        }
        return result;
    }

    /**
     * Validate that the class of course acr in the year year and semester sem exists.
     * @param conn Connection with DataBase.
     * @param num Class ID.
     * @param acr Course acronym.
     * @param year Year that this class belongs to.
     * @param sem Semester that this class belongs to.
     * @return The class of course acr in the year year and semester sem
     * @throws SQLException
     * @throws NotExistInformationException
     */
    public static Class validateExistentCoursesAcrClassesSemNum(Connection conn, String num, String acr, int year, String sem) throws SQLException, NotExistInformationException {
        /* Get the class. */
        Class result = QueryOperations.queryCoursesAcrClassesSemNum(conn, num, acr, year, sem);

        /* If the class doesn't exist throw exception. */
        if(result == null)
            throw new NotExistInformationException("The class represented by " + num + " in the year " + year + " on semester " + sem + " of course with acronym " + acr + " doesn't exist.");
        return result;
    }

    /**
     * Validate that the programme with the ID pid exist.
     * @param conn Connection with DataBase.
     * @param pid Programme ID.
     * @return The programme with the ID pid.
     * @throws SQLException
     * @throws NotExistInformationException
     */
    public static Programme validateExistentProgrammesPid(Connection conn, String pid) throws SQLException, NotExistInformationException {
        /* Get the programme with ID pid. */
        Programme programme = QueryOperations.queryProgrammesPid(conn, pid);

        /* If the programme doesn't exist throw exception. */
        if(programme == null)
            throw new NotExistInformationException("The programme with acronym " + pid + " doesn't exist.");
        return programme;
    }

    /**
     * Validate that the programme with the ID pid exist.
     * @param conn Connection with DataBase.
     * @param pid Programme ID.
     * @param errorValidator Object that will contain this validation errors.
     * @param errorID Error ID to be added to the container.
     * @return The programme with the ID pid.
     * @throws SQLException
     */
    public static Programme validateExistentProgrammesPid(Connection conn, String pid, ErrorValidator errorValidator, String errorID) throws SQLException {
        /* Get the programme with ID pid. */
        Programme programme = QueryOperations.queryProgrammesPid(conn, pid);

        /* If the programme doesn't exist add an error to the error validator. */
        if(programme == null)
            errorValidator.addError(errorID, "The programme with ID " + pid + " doesn't exists.");
        return programme;
    }

    /**
     * Validate that the programme with the ID pid doesn't exist.
     * @param conn Connection with DataBase.
     * @param pid Programme ID.
     * @param errorValidator Object that will contain this validation errors.
     * @param errorID Error ID to be added to the container.
     * @throws SQLException
     * @throws NotExistInformationException
     */
    public static void validateNotExistentProgrammesPid(Connection conn, String pid, ErrorValidator errorValidator, String errorID) throws SQLException, NotExistInformationException {
        /* Get the programme with ID pid. */
        Programme programme = QueryOperations.queryProgrammesPid(conn, pid);

        /* If the programme exist add an error to the error validator. */
        if(programme != null)
            errorValidator.addError(errorID, "The programme with ID " + pid + " already exists.");
    }

    /**
     *  Validate that the teacher with the number num exist.
     * @param conn Connection with DataBase.
     * @param num Teacher's number.
     * @return The teacher with the number num exist.
     * @throws SQLException
     * @throws NotExistInformationException
     */
    public static Teacher validateExistentTeachersNum(Connection conn, int num) throws SQLException, NotExistInformationException {
        /* Get the teacher with number num. */
        Teacher result = QueryOperations.queryTeacherNum(conn, num);

        /* If the teacher doesn't exist throw exception. */
        if(result == null)
            throw new NotExistInformationException("The teacher with number " + num + " doesn't exist.");
        return result;
    }

    /**
     * Validate that the teacher with the number num exist.
     * @param conn Connection with DataBase.
     * @param num Teacher's number.
     * @param errorValidator Object that will contain this validation errors.
     * @param errorID Error ID to be added to the container.
     * @return The teacher with the number num exist.
     * @throws SQLException
     */
    public static Teacher validateExistentTeachersNum(Connection conn, Integer num, ErrorValidator errorValidator, String errorID) throws SQLException {
       /* Get the teacher with number num. */
        Teacher result = QueryOperations.queryTeacherNum(conn, num);

        /* If the teacher doesn't exist add an error to the error validator. */
        if(result == null)
            errorValidator.addError(errorID,"The teacher with number " + num + " doesn't exist.");
        return result;
    }

    /**
     * Validate that the teacher with the number num doesn't exist.
     * @param conn Connection with DataBase.
     * @param num Teacher's number.
     * @param errorValidator  Object that will contain this validation errors.
     * @param errorID Error ID to be added to the container.
     * @throws SQLException
     */
    public static void validateNotExistentTeachersNum(Connection conn, Integer num, ErrorValidator errorValidator, String errorID) throws SQLException {
        /* Get the teacher with number num. */
        Teacher result = QueryOperations.queryTeacherNum(conn, num);

        /* If the teacher exist add an error to the error validator. */
        if(result != null)
            errorValidator.addError(errorID,"The teacher with number " + num + " already exist.");
    }

    /**
     * Validate that the student with the number num exists.
     * @param conn Connection with DataBase.
     * @param num Student's number.
     * @return The student with the number num.
     * @throws SQLException
     * @throws NotExistInformationException
     */
    public static Student validateExistentStudentNum(Connection conn, int num) throws SQLException, NotExistInformationException {
        /* Get the student with number num. */
        Student result = QueryOperations.queryStudentNum(conn, num);

        /* If the student doesn't exist throw exception. */
        if(result == null)
            throw new NotExistInformationException("The student with number " + num + " doesn't exist.");
        return result;
    }

    /**
     * Validate if the student with the number num doesn't exist otherwise adds it as an error to the error validator.
     * @param conn Connection with DataBase.
     * @param num Student's number.
     * @param errorValidator Object that will contain this validation errors.
     * @param errorID Error ID to be added to the container.
     * @throws SQLException
     * @throws NotExistInformationException
     */
    public static void validateNotExistentStudentNum(Connection conn, int num, ErrorValidator errorValidator, String errorID) throws SQLException, NotExistInformationException {
        /* Get the student with number num. */
        Student result = QueryOperations.queryStudentNum(conn, num);

        /* If the student exist add an error to the error validator. */
        if(result != null)
           errorValidator.addError(errorID, "The student with number " + num + " already exists.");
    }

    /**
     * Validate if the student that is in the class represented by the parameters exist, if not throws exception.
     * @param conn Connection with DataBase.
     * @param num Class ID.
     * @param courseName Course name.
     * @param year Year that this class belongs to.
     * @param sem Semester that this class belongs to.
     * @param studentNumber Student number.
     * @return The student that is in the class with ID num in the year year and semester sem of course courseName with number studentNumber.
     * @throws SQLException
     * @throws NotExistInformationException
     */
    public static Student validateExistentCoursesClassesSemNumStudentNum(Connection conn, String num, String courseName, int year, String sem, int studentNumber) throws SQLException, NotExistInformationException {
        /* Get the student with number studentNumber that belongs to the class. */
        Student student = QueryOperations.queryCoursesAcrClassesSemNumStudentNum(conn, num, courseName, year, sem, studentNumber);

        /* If the student doesn't exist in that class throw exception. */
        if(student == null)
            throw new NotExistInformationException("The student with number " + studentNumber + "isn't assigned to the class");
        return student;
    }


}