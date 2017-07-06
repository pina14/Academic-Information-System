package pt.isel.ls.Model.Results.UserManagementResults;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.UsersManagementViews.GetStudents.ViewHTMLGetStudents;
import pt.isel.ls.View.CommandViews.UsersManagementViews.GetStudents.ViewJSONGetStudents;
import pt.isel.ls.View.CommandViews.UsersManagementViews.GetStudents.ViewPlainTextGetStudents;

public class GetStudentsResult extends Result {
    private CustomList<Entity> students;
    private int skip, top, numberRows;

    public GetStudentsResult(CustomList<Entity> students, int skip, int top, int numberRows) {
        super(new ViewHTMLGetStudents(), new ViewPlainTextGetStudents(), new ViewJSONGetStudents());
        this.students = students;
        this.skip = skip;
        this.top = top;
        this.numberRows = numberRows;
    }

    public CustomList<Entity> getStudents() {
        return students;
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