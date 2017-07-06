package pt.isel.ls.View.CommandViews.UsersManagementViews.GetStudentsNum;

import pt.isel.ls.Model.Entities.Student;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Results.UserManagementResults.GetStudentsNumResult;
import pt.isel.ls.View.CommandViews.View;
import pt.isel.ls.View.Formatters.PlainText.Students.PlainTextStudentsFormatter;

import java.io.IOException;
import java.io.StringWriter;

public class ViewPlainTextGetStudentsNum implements View {
    @Override
    public void writeResult(Result rt, StringWriter writer) throws IOException {
        /* Get the plain text students formatter. */
        PlainTextStudentsFormatter textFormatter = new PlainTextStudentsFormatter();
        /* Get the student. */
        Student student = ((GetStudentsNumResult)rt).getStudent();
        /* Prints the student to the writer. */
        textFormatter.formatEntity(student).writeTo(writer);
    }
}