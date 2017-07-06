package pt.isel.ls.View.CommandViews.TeachersManagementViews.GetTeachersNumClasses;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Results.TeacherManagementResults.GetTeachersNumClassesResult;
import pt.isel.ls.View.CommandViews.View;
import pt.isel.ls.View.Formatters.PlainText.Classes.PlainTextCoursesClassesFormatter;

import java.io.IOException;
import java.io.StringWriter;

public class ViewPlainTextGetTeachersNumClasses implements View {
    @Override
    public void writeResult(Result rt, StringWriter writer) throws IOException {
        /* Get the plain text classes formatter. */
        PlainTextCoursesClassesFormatter textFormatter = new PlainTextCoursesClassesFormatter();
        /* Get the classes. */
        CustomList<Entity> coursesClasses  = ((GetTeachersNumClassesResult)rt).getCoursesClasses();
        /* Prints each class to the writer. */
        textFormatter.formatEntities(coursesClasses).writeTo(writer);
    }
}