package pt.isel.ls.View.CommandViews.UsersManagementViews.GetStudentsNum;

import pt.isel.ls.Model.Entities.Student;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Results.UserManagementResults.GetStudentsNumResult;
import pt.isel.ls.View.CommandViews.View;
import pt.isel.ls.View.Formatters.JSON.Students.JSONStudentsFormatter;

import java.io.IOException;
import java.io.StringWriter;

public class ViewJSONGetStudentsNum implements View{
    @Override
    public void writeResult(Result rt, StringWriter writer) throws IOException {
        /* Get the JSON students formatter. */
        JSONStudentsFormatter jsonFormatter = new JSONStudentsFormatter();
        /* Get the student. */
        Student student = ((GetStudentsNumResult)rt).getStudent();
        /* Prints the student to the writer. */
        jsonFormatter.formatEntity(student).writeTo(writer);
    }
}