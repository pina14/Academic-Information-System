package pt.isel.ls.Model.Results.TeacherManagementResults;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Class;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.TeachersManagementViews.GetCoursesAcrClassesSemNumTeachers.ViewHTMLGetCoursesAcrClassesSemNumTeachers;
import pt.isel.ls.View.CommandViews.TeachersManagementViews.GetCoursesAcrClassesSemNumTeachers.ViewJSONGetCoursesAcrClassesSemNumTeachers;
import pt.isel.ls.View.CommandViews.TeachersManagementViews.GetCoursesAcrClassesSemNumTeachers.ViewPlainTextGetCoursesAcrClassesSemNumTeachers;

public class GetCoursesAcrClassesSemNumTeachersResult extends Result {
    private CustomList<Entity> teachers;
    private Class _class;
    private int skip, top, numberRows;
    private String courseAcronym;

    public GetCoursesAcrClassesSemNumTeachersResult(CustomList<Entity> teachers, Class _class, int skip, int top, int numberRows, String courseAcronym) {
        super(new ViewHTMLGetCoursesAcrClassesSemNumTeachers(), new ViewPlainTextGetCoursesAcrClassesSemNumTeachers(), new ViewJSONGetCoursesAcrClassesSemNumTeachers());
        this.teachers = teachers;
        this._class = _class;
        this.skip = skip;
        this.top = top;
        this.numberRows = numberRows;
        this.courseAcronym = courseAcronym;
    }

    public CustomList<Entity> getTeachers() {
        return teachers;
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