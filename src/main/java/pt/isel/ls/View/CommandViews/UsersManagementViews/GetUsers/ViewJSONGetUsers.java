package pt.isel.ls.View.CommandViews.UsersManagementViews.GetUsers;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Results.UserManagementResults.GetUsersResult;
import pt.isel.ls.View.CommandViews.View;
import pt.isel.ls.View.Formatters.JSON.Users.JSONUsersFormatter;

import java.io.IOException;
import java.io.StringWriter;

public class ViewJSONGetUsers implements View {
    @Override
    public void writeResult(Result rt, StringWriter writer) throws IOException {
        /* Get the JSON users formatter. */
        JSONUsersFormatter jsonFormatter = new JSONUsersFormatter();
        /* Get the users. */
        CustomList<Entity> users = ((GetUsersResult)rt).getUsers();
        /* Prints each user to the writer. */
        jsonFormatter.formatEntities(users).writeTo(writer);
    }
}