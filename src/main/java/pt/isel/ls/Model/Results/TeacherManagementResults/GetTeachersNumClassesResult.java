package pt.isel.ls.Model.Results.TeacherManagementResults;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.TeachersManagementViews.GetTeachersNumClasses.ViewHTMLGetTeachersNumClasses;
import pt.isel.ls.View.CommandViews.TeachersManagementViews.GetTeachersNumClasses.ViewJSONGetTeachersNumClasses;
import pt.isel.ls.View.CommandViews.TeachersManagementViews.GetTeachersNumClasses.ViewPlainTextGetTeachersNumClasses;

public class GetTeachersNumClassesResult extends Result {
    private CustomList<Entity> coursesClasses;
    private int skip, top, numberRows, teacherNum;

    public GetTeachersNumClassesResult(CustomList<Entity> coursesClasses, int skip, int top, int numberRows, int teacherNum) {
        super(new ViewHTMLGetTeachersNumClasses(), new ViewPlainTextGetTeachersNumClasses(), new ViewJSONGetTeachersNumClasses());
        this.coursesClasses = coursesClasses;
        this.skip = skip;
        this.top = top;
        this.numberRows = numberRows;
        this.teacherNum = teacherNum;
    }

    public CustomList<Entity> getCoursesClasses() {
        return coursesClasses;
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

    public int getTeacherNum() {
        return teacherNum;
    }
}