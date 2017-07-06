package pt.isel.ls.View.CommandViews.CoursesManagementViews.GetCourses;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Results.CourseManagementResults.GetCoursesResult;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.View;
import pt.isel.ls.View.Formatters.PlainText.Courses.PlainTextCoursesFormatter;

import java.io.IOException;
import java.io.StringWriter;

public class ViewPlainTextGetCourses implements View{
    @Override
    public void writeResult(Result rt, StringWriter writer) throws IOException {
        /* Get the plain text courses formatter. */
        PlainTextCoursesFormatter textFormatter = new PlainTextCoursesFormatter();
        /* Get the courses. */
        CustomList<Entity> courses = ((GetCoursesResult) rt).getCourses();
        /* Prints each course to the writer. */
        textFormatter.formatEntities(courses).writeTo(writer);
    }
}