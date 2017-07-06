package pt.isel.ls.Model.Results.ClassManagementResults;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.ClassesManagementViews.GetCoursesAcrClasses.ViewHTMLGetCoursesAcrClasses;
import pt.isel.ls.View.CommandViews.ClassesManagementViews.GetCoursesAcrClasses.ViewJSONGetCoursesAcrClasses;
import pt.isel.ls.View.CommandViews.ClassesManagementViews.GetCoursesAcrClasses.ViewPlainTextGetCoursesAcrClasses;

public class GetCoursesAcrClassesResult extends Result {
    private CustomList<Entity> classes;
    private int skip, top, numberRows;
    private String courseAcronym;

    public GetCoursesAcrClassesResult(CustomList<Entity> classes, int skip, int top, int numberRows, String courseAcronym) {
        super(new ViewHTMLGetCoursesAcrClasses(), new ViewPlainTextGetCoursesAcrClasses(), new ViewJSONGetCoursesAcrClasses());
        this.classes = classes;
        this.skip = skip;
        this.top = top;
        this.numberRows = numberRows;
        this.courseAcronym = courseAcronym;
    }

    public CustomList<Entity> getClasses() {
        return classes;
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