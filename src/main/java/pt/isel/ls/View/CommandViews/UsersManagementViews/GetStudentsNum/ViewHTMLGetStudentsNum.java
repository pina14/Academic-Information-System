package pt.isel.ls.View.CommandViews.UsersManagementViews.GetStudentsNum;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Entities.Student;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Results.UserManagementResults.GetStudentsNumResult;
import pt.isel.ls.View.CommandViews.ViewHTML;
import pt.isel.ls.View.Formatters.HTMLFormatters.Classes.HTMLCoursesClassesFormatter;
import pt.isel.ls.View.Formatters.HTMLFormatters.Students.HTMLStudentsFormatter;
import pt.isel.ls.View.ViewTypes.html.HtmlPage;

import static pt.isel.ls.View.ViewTypes.html.Html.*;

public class ViewHTMLGetStudentsNum extends ViewHTML {
    @Override
    protected HtmlPage build(Result rt) {
        /* Get the result. */
        GetStudentsNumResult result = (GetStudentsNumResult) rt;

        /* Initiate variables. */
        HTMLStudentsFormatter studentsFormatter = new HTMLStudentsFormatter();
        HTMLCoursesClassesFormatter coursesClassesFormatter = new HTMLCoursesClassesFormatter();
        Student student = result.getStudent();
        CustomList<Entity> coursesClasses = result.getCoursesClasses();
        String title = "Student with the number " + student.getNumber();

        /* Build the HTML page. */
        return new HtmlPage(
                title,
                h1(text(title)),
                p(a("/", "Menu")),
                p(a("/students", "Students")),
                p(a("/users", "Users")),
                h3(text("Student Description:")),
                studentsFormatter.formatEntity(student),
                h3(text("Classes")),
                coursesClassesFormatter.formatEntities(coursesClasses)
        );
    }
}