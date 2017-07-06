package pt.isel.ls.Model.Results.UserManagementResults;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.UsersManagementViews.GetUsers.ViewHTMLGetUsers;
import pt.isel.ls.View.CommandViews.UsersManagementViews.GetUsers.ViewJSONGetUsers;
import pt.isel.ls.View.CommandViews.UsersManagementViews.GetUsers.ViewPlainTextGetUsers;

public class GetUsersResult extends Result {
    private CustomList<Entity> users;
    private int skip, top, numberRows;

    public GetUsersResult(CustomList<Entity> users, int skip, int top, int numberRows) {
        super(new ViewHTMLGetUsers(), new ViewPlainTextGetUsers(), new ViewJSONGetUsers());
        this.users = users;
        this.skip = skip;
        this.top = top;
        this.numberRows = numberRows;
    }

    public CustomList<Entity> getUsers() {
        return users;
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