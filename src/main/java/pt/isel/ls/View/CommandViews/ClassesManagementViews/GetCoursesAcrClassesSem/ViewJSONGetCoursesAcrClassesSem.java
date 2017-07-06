package pt.isel.ls.View.CommandViews.ClassesManagementViews.GetCoursesAcrClassesSem;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Results.ClassManagementResults.GetCoursesAcrClassesSemResult;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.View;
import pt.isel.ls.View.Formatters.JSON.Classes.JSONClassesFormatter;

import java.io.IOException;
import java.io.StringWriter;

public class ViewJSONGetCoursesAcrClassesSem implements View {

    @Override
    public void writeResult(Result rt, StringWriter writer) throws IOException {
        /* Get the JSON classes formatter. */
        JSONClassesFormatter jsonFormatter = new JSONClassesFormatter();
        /* Get the classes. */
        CustomList<Entity> classes = ((GetCoursesAcrClassesSemResult) rt).getClasses();
        /* Prints each class to the writer. */
        jsonFormatter.formatEntities(classes).writeTo(writer);
    }
}