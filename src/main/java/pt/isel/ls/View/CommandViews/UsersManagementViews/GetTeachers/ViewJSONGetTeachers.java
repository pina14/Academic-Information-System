package pt.isel.ls.View.CommandViews.UsersManagementViews.GetTeachers;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Results.UserManagementResults.GetTeachersResult;
import pt.isel.ls.View.CommandViews.View;
import pt.isel.ls.View.Formatters.JSON.Teachers.JSONTeachersFormatter;

import java.io.IOException;
import java.io.StringWriter;

public class ViewJSONGetTeachers implements View {
    @Override
    public void writeResult(Result rt, StringWriter writer) throws IOException {
        /* Get the JSON teachers formatter. */
        JSONTeachersFormatter jsonFormatter = new JSONTeachersFormatter();
        /* Get the teachers. */
        CustomList<Entity> teachers = ((GetTeachersResult)rt).getTeachers();
        /* Prints each teacher to the writer. */
        jsonFormatter.formatEntities(teachers).writeTo(writer);
    }
}