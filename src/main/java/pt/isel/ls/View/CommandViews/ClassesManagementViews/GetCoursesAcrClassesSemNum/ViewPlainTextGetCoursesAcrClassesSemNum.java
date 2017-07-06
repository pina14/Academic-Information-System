package pt.isel.ls.View.CommandViews.ClassesManagementViews.GetCoursesAcrClassesSemNum;

import pt.isel.ls.Model.Entities.Class;
import pt.isel.ls.Model.Results.ClassManagementResults.GetCoursesAcrClassesSemNumResult;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.View;
import pt.isel.ls.View.Formatters.PlainText.Classes.PlainTextClassesFormatter;

import java.io.IOException;
import java.io.StringWriter;

public class ViewPlainTextGetCoursesAcrClassesSemNum implements View {
    @Override
    public void writeResult(Result rt, StringWriter writer) throws IOException {
        /* Get the plain text classes formatter. */
        PlainTextClassesFormatter textFormatter = new PlainTextClassesFormatter();
        /* Get the class. */
        Class _class  = ((GetCoursesAcrClassesSemNumResult)rt).get_class();
        /* Prints the class to the writer. */
        textFormatter.formatEntity(_class).writeTo(writer);
    }
}