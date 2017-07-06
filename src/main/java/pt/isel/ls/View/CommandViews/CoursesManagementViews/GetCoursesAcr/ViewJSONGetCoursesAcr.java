package pt.isel.ls.View.CommandViews.CoursesManagementViews.GetCoursesAcr;

import pt.isel.ls.Model.Entities.Course;
import pt.isel.ls.Model.Results.CourseManagementResults.GetCoursesAcrResult;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.View;
import pt.isel.ls.View.Formatters.JSON.Courses.JSONCoursesFormatter;

import java.io.IOException;
import java.io.StringWriter;

public class ViewJSONGetCoursesAcr implements View{
    @Override
    public void writeResult(Result rt, StringWriter writer) throws IOException {
        /* Get the JSON courses formatter. */
        JSONCoursesFormatter jsonFormatter = new JSONCoursesFormatter();
        /* Get the course. */
        Course course  = ((GetCoursesAcrResult)rt).getCourse();
        /* Prints the course to the writer. */
        jsonFormatter.formatEntity(course).writeTo(writer);
    }
}