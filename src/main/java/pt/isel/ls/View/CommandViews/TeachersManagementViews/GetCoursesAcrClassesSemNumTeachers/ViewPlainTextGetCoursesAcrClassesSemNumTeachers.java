package pt.isel.ls.View.CommandViews.TeachersManagementViews.GetCoursesAcrClassesSemNumTeachers;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Results.TeacherManagementResults.GetCoursesAcrClassesSemNumTeachersResult;
import pt.isel.ls.View.CommandViews.View;
import pt.isel.ls.View.Formatters.PlainText.Teachers.PlainTextTeachersFormatter;

import java.io.IOException;
import java.io.StringWriter;

public class ViewPlainTextGetCoursesAcrClassesSemNumTeachers  implements View {
    @Override
    public void writeResult(Result rt, StringWriter writer) throws IOException {
        /* Get the plain text teachers formatter. */
        PlainTextTeachersFormatter textFormatter = new PlainTextTeachersFormatter();
        /* Get the teachers. */
        CustomList<Entity> teachers  = ((GetCoursesAcrClassesSemNumTeachersResult)rt).getTeachers();
        /* Prints each teacher to the writer. */
        textFormatter.formatEntities(teachers).writeTo(writer);
    }
}