package pt.isel.ls.View.CommandViews.ClassesManagementViews.GetCoursesAcrClassesSem;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Results.ClassManagementResults.GetCoursesAcrClassesSemResult;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.View;
import pt.isel.ls.View.Formatters.PlainText.Classes.PlainTextClassesFormatter;

import java.io.IOException;
import java.io.StringWriter;

public class ViewPlainTextGetCoursesAcrClassesSem implements View {
    @Override
    public void writeResult(Result rt, StringWriter writer) throws IOException {
        /* Get the plain text classes formatter. */
        PlainTextClassesFormatter textFormatter = new PlainTextClassesFormatter();
        /* Get the classes. */
        CustomList<Entity> classes = ((GetCoursesAcrClassesSemResult) rt).getClasses();
        /* Prints each class to the writer. */
        textFormatter.formatEntities(classes).writeTo(writer);
    }
}