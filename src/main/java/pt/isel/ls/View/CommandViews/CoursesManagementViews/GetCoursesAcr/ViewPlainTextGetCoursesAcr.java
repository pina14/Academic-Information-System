package pt.isel.ls.View.CommandViews.CoursesManagementViews.GetCoursesAcr;

import pt.isel.ls.Model.Entities.Course;
import pt.isel.ls.Model.Results.CourseManagementResults.GetCoursesAcrResult;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.View;
import pt.isel.ls.View.Formatters.PlainText.Courses.PlainTextCoursesFormatter;

import java.io.IOException;
import java.io.StringWriter;

public class ViewPlainTextGetCoursesAcr  implements View{
    @Override
    public void writeResult(Result rt, StringWriter writer) throws IOException {
        /* Get the plain text courses formatter. */
        PlainTextCoursesFormatter textFormatter = new PlainTextCoursesFormatter();
        /* Get the course. */
        Course course  = ((GetCoursesAcrResult)rt).getCourse();
        /* Prints the course to the writer. */
        textFormatter.formatEntity(course).writeTo(writer);
    }
}