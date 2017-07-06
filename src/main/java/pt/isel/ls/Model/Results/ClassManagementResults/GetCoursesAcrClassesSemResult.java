package pt.isel.ls.Model.Results.ClassManagementResults;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.ClassesManagementViews.GetCoursesAcrClassesSem.ViewHTMLGetCoursesAcrClassesSem;
import pt.isel.ls.View.CommandViews.ClassesManagementViews.GetCoursesAcrClassesSem.ViewJSONGetCoursesAcrClassesSem;
import pt.isel.ls.View.CommandViews.ClassesManagementViews.GetCoursesAcrClassesSem.ViewPlainTextGetCoursesAcrClassesSem;

public class GetCoursesAcrClassesSemResult extends Result {
    private CustomList<Entity> classes;
    private int skip, top, numberRows, year;
    private String courseAcronym, notFilteredsemester, semester;

    public GetCoursesAcrClassesSemResult(CustomList<Entity> classes, int skip, int top, int numberRows, String courseAcronym, String notFilteredsemester, String semester, int year) {
        super(new ViewHTMLGetCoursesAcrClassesSem(), new ViewPlainTextGetCoursesAcrClassesSem(), new ViewJSONGetCoursesAcrClassesSem());
        this.classes = classes;
        this.skip = skip;
        this.top = top;
        this.numberRows = numberRows;
        this.courseAcronym = courseAcronym;
        this.notFilteredsemester = notFilteredsemester;
        this.semester = semester;
        this.year = year;
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

    public String getSemester() {
        return semester;
    }

    public int getYear() {
        return year;
    }

    public String getNotFilteredsemester() {
        return notFilteredsemester;
    }
}