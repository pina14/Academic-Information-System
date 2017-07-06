package pt.isel.ls.View.CommandViews.CoursesManagementViews.GetCoursesAcr;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Course;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Results.CourseManagementResults.GetCoursesAcrResult;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.ViewHTML;
import pt.isel.ls.View.Formatters.HTMLFormatters.Courses.HTMLCoursesFormatter;
import pt.isel.ls.View.Formatters.HTMLFormatters.Programmes.HTMLProgrammesFormatter;
import pt.isel.ls.View.ViewTypes.html.HtmlPage;

import static pt.isel.ls.View.ViewTypes.html.Html.*;

public class ViewHTMLGetCoursesAcr extends ViewHTML {

    @Override
    public HtmlPage build(Result rt) {
        /* Get the result. */
        GetCoursesAcrResult result = (GetCoursesAcrResult) rt;

        /* Initiate variables. */
        CustomList<Entity> programmes = result.getCourseProgrammes();
        Course course = result.getCourse();
        HTMLCoursesFormatter htmlCourse = new HTMLCoursesFormatter();
        HTMLProgrammesFormatter programmesFormatter = new HTMLProgrammesFormatter();

        /* Build the HTML page. */
        return new HtmlPage(
                course.getName() + " course",
                h1(text("Course "  + course.getName())),
                p(a("/", "Menu")),
                p(a("/courses", "Courses")),
                p(a("/courses/" + result.getCourseAcronym() + "/classes", "Classes")),
                h3(text("Course Description:")),
                htmlCourse.formatEntity(course),
                h3(text("Course Programmes:")),
                programmesFormatter.formatEntities(programmes)
        );
    }
}