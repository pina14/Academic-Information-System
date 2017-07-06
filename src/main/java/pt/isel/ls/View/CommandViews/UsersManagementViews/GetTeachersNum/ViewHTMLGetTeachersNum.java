package pt.isel.ls.View.CommandViews.UsersManagementViews.GetTeachersNum;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Entities.Teacher;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Results.UserManagementResults.GetTeachersNumResult;
import pt.isel.ls.View.CommandViews.ViewHTML;
import pt.isel.ls.View.Formatters.HTMLFormatters.Classes.HTMLCoursesClassesFormatter;
import pt.isel.ls.View.Formatters.HTMLFormatters.Courses.HTMLCoursesFormatter;
import pt.isel.ls.View.Formatters.HTMLFormatters.Teachers.HTMLTeachersFormatter;
import pt.isel.ls.View.ViewTypes.html.HtmlPage;

import static pt.isel.ls.View.ViewTypes.html.Html.*;

public class ViewHTMLGetTeachersNum extends ViewHTML{
    @Override
    protected HtmlPage build(Result rt) {
        /* Get the result. */
        GetTeachersNumResult result = (GetTeachersNumResult) rt;

        /* Initiate variables. */
        HTMLTeachersFormatter teachersFormatter = new HTMLTeachersFormatter();
        HTMLCoursesClassesFormatter coursesClassesFormatter = new HTMLCoursesClassesFormatter();
        HTMLCoursesFormatter coursesFormatter = new HTMLCoursesFormatter();
        Teacher teacher =  result.getTeacher();
        CustomList<Entity> courses = result.getCourses(), coursesClasses = result.getCoursesClasses();
        String title = "Teacher with the number " + teacher.getNumber();

        /* Build the HTML page. */
        return new HtmlPage(
                title,
                h1(text(title)),
                p(a("/", "Menu")),
                p(a("/teachers", "Teachers")),
                p(a("/users", "Users")),
                h3(text("Teacher Description:")),
                teachersFormatter.formatEntity(teacher),
                h3(a("/teachers/"+teacher.getNumber()+"/classes", "Teacher Classes")),
                coursesClassesFormatter.formatEntities(coursesClasses),
                h3(text("Teacher Courses")),
                coursesFormatter.formatEntities(courses)
        );
    }
}