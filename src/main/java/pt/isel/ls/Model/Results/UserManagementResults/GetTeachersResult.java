package pt.isel.ls.Model.Results.UserManagementResults;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.UsersManagementViews.GetTeachers.ViewHTMLGetTeachers;
import pt.isel.ls.View.CommandViews.UsersManagementViews.GetTeachers.ViewJSONGetTeachers;
import pt.isel.ls.View.CommandViews.UsersManagementViews.GetTeachers.ViewPlainTextGetTeachers;

public class GetTeachersResult extends Result {
    private CustomList<Entity> teachers;
    private int skip, top, numberRows;

    public GetTeachersResult(CustomList<Entity> teachers, int skip, int top, int numberRows) {
        super(new ViewHTMLGetTeachers(), new ViewPlainTextGetTeachers(), new ViewJSONGetTeachers());
        this.teachers = teachers;
        this.skip = skip;
        this.top = top;
        this.numberRows = numberRows;
    }

    public CustomList<Entity> getTeachers() {
        return teachers;
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