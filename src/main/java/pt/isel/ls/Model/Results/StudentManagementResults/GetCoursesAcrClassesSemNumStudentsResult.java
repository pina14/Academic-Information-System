package pt.isel.ls.Model.Results.StudentManagementResults;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Class;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.StudentsManagementView.GetCoursesAcrClassesSemNumStudents.ViewHTMLGetCoursesAcrClassesSemNumStudents;
import pt.isel.ls.View.CommandViews.StudentsManagementView.GetCoursesAcrClassesSemNumStudents.ViewJSONGetCoursesAcrClassesSemNumStudents;
import pt.isel.ls.View.CommandViews.StudentsManagementView.GetCoursesAcrClassesSemNumStudents.ViewPlainTextGetCoursesAcrClassesSemNumStudents;

public class GetCoursesAcrClassesSemNumStudentsResult extends Result {
    private CustomList<Entity> students, studentsNotEnrolled;
    private Class _class;
    private int skip, top, numberRows;
    private String courseAcronym;

    public GetCoursesAcrClassesSemNumStudentsResult(CustomList<Entity> students, Class _class, CustomList<Entity> studentsNotEnrolled, int skip, int top, int numberRows, String courseAcronym) {
        super(new ViewHTMLGetCoursesAcrClassesSemNumStudents(), new ViewPlainTextGetCoursesAcrClassesSemNumStudents(), new ViewJSONGetCoursesAcrClassesSemNumStudents());
        this.students = students;
        this._class = _class;
        this.studentsNotEnrolled = studentsNotEnrolled;
        this.skip = skip;
        this.top = top;
        this.numberRows = numberRows;
        this.courseAcronym = courseAcronym;
    }

    public CustomList<Entity> getStudents() {
        return students;
    }

    public CustomList<Entity> getStudentsNotEnrolled() {
        return studentsNotEnrolled;
    }

    public Class get_class() {
        return _class;
    }

    public int getSkip() {
        return skip;
    }

    public int getTop() {
        return top;
    }

    public int getNumberRows() {
        return numberRows;
    }

    public String getCourseAcronym() {
        return courseAcronym;
    }
}