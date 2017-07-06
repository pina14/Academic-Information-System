package pt.isel.ls.View.CommandViews.UsersManagementViews.GetUsers;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Results.UserManagementResults.GetUsersResult;
import pt.isel.ls.View.CommandViews.View;
import pt.isel.ls.View.Formatters.PlainText.Users.PlainTextUsersFormatter;

import java.io.IOException;
import java.io.StringWriter;

public class ViewPlainTextGetUsers implements View {
    @Override
    public void writeResult(Result rt, StringWriter writer) throws IOException {
        /* Get the plain text users formatter. */
        PlainTextUsersFormatter textFormatter = new PlainTextUsersFormatter();
        /* Get the users. */
        CustomList<Entity> users = ((GetUsersResult)rt).getUsers();
        /* Prints each user to the writer. */
        textFormatter.formatEntities(users).writeTo(writer);
    }
}