package pt.isel.ls.Model.Results.CourseManagementResults;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.CoursesManagementViews.GetCourses.ViewHTMLGetCourses;
import pt.isel.ls.View.CommandViews.CoursesManagementViews.GetCourses.ViewJSONGetCourses;
import pt.isel.ls.View.CommandViews.CoursesManagementViews.GetCourses.ViewPlainTextGetCourses;

public class GetCoursesResult extends Result {
    private CustomList<Entity> courses;
    private int skip, top, numberRows;

    public GetCoursesResult(CustomList<Entity> courses, int skip, int top, int numberRows) {
        super(new ViewHTMLGetCourses(), new ViewPlainTextGetCourses(), new ViewJSONGetCourses());
        this.courses = courses;
        this.skip = skip;
        this.top = top;
        this.numberRows = numberRows;
    }

    public CustomList<Entity> getCourses() {
        return courses;
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
}