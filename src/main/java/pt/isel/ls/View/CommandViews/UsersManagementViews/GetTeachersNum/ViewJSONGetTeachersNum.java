package pt.isel.ls.View.CommandViews.UsersManagementViews.GetTeachersNum;

import pt.isel.ls.Model.Entities.Teacher;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Results.UserManagementResults.GetTeachersNumResult;
import pt.isel.ls.View.CommandViews.View;
import pt.isel.ls.View.Formatters.JSON.Teachers.JSONTeachersFormatter;

import java.io.IOException;
import java.io.StringWriter;

public class ViewJSONGetTeachersNum implements View {
    @Override
    public void writeResult(Result rt, StringWriter writer) throws IOException {
        /* Get the plain text teachers formatter. */
        JSONTeachersFormatter jsonFormatter = new JSONTeachersFormatter();
        /* Get the teacher. */
        Teacher teacher = ((GetTeachersNumResult)rt).getTeacher();
        /* Prints the teacher to the writer. */
        jsonFormatter.formatEntity(teacher).writeTo(writer);
    }
}