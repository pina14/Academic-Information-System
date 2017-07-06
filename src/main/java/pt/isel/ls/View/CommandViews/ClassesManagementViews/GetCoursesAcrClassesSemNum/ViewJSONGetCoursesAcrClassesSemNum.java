package pt.isel.ls.View.CommandViews.ClassesManagementViews.GetCoursesAcrClassesSemNum;

import pt.isel.ls.Model.Entities.Class;
import pt.isel.ls.Model.Results.ClassManagementResults.GetCoursesAcrClassesSemNumResult;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.View;
import pt.isel.ls.View.Formatters.JSON.Classes.JSONClassesFormatter;

import java.io.IOException;
import java.io.StringWriter;

public class ViewJSONGetCoursesAcrClassesSemNum implements View{
    @Override
    public void writeResult(Result rt, StringWriter writer) throws IOException {
        /* Get the JSON classes formatter. */
        JSONClassesFormatter jsonFormatter = new JSONClassesFormatter();
        /* Get the class. */
        Class _class  = ((GetCoursesAcrClassesSemNumResult)rt).get_class();
        /* Prints the class to the writer. */
        jsonFormatter.formatEntity(_class).writeTo(writer);
    }
}