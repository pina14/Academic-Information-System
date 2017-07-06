package pt.isel.ls.View.CommandViews.CoursesManagementViews.GetCourses;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Results.CourseManagementResults.GetCoursesResult;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.View;
import pt.isel.ls.View.Formatters.JSON.Courses.JSONCoursesFormatter;

import java.io.IOException;
import java.io.StringWriter;

public class ViewJSONGetCourses  implements View{
    @Override
    public void writeResult(Result rt, StringWriter writer) throws IOException {
        /* Get the JSON courses formatter. */
        JSONCoursesFormatter jsonFormatter = new JSONCoursesFormatter();
        /* Get the courses. */
        CustomList<Entity> courses = ((GetCoursesResult) rt).getCourses();
        /* Prints each course to the writer. */
        jsonFormatter.formatEntities(courses).writeTo(writer);
    }
}