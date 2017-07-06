package pt.isel.ls.Model.Results.ClassManagementResults;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Class;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.ClassesManagementViews.GetCoursesAcrClassesSemNum.ViewHTMLGetCoursesAcrClassesSemNum;
import pt.isel.ls.View.CommandViews.ClassesManagementViews.GetCoursesAcrClassesSemNum.ViewJSONGetCoursesAcrClassesSemNum;
import pt.isel.ls.View.CommandViews.ClassesManagementViews.GetCoursesAcrClassesSemNum.ViewPlainTextGetCoursesAcrClassesSemNum;

public class GetCoursesAcrClassesSemNumResult extends Result {
    private Class _class;
    private CustomList<Entity> _classStudents;
    private CustomList<Entity> _classTeachers;
    private String courseAcronym;

    public GetCoursesAcrClassesSemNumResult(Class _class, CustomList<Entity> _classStudents, CustomList<Entity> _classTeachers, String courseAcronym) {
        super(new ViewHTMLGetCoursesAcrClassesSemNum(), new ViewPlainTextGetCoursesAcrClassesSemNum(), new ViewJSONGetCoursesAcrClassesSemNum());
        this._class = _class;
        this._classStudents = _classStudents;
        this._classTeachers = _classTeachers;
        this.courseAcronym = courseAcronym;
    }

    public Class get_class() {
        return _class;
    }

    public CustomList<Entity> get_classStudents() {
        return _classStudents;
    }

    public CustomList<Entity> get_classTeachers() {
        return _classTeachers;
    }

    public String getCourseAcronym() {
        return courseAcronym;
    }
}