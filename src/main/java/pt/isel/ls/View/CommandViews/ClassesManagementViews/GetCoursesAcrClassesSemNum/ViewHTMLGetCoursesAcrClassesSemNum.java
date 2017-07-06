package pt.isel.ls.View.CommandViews.ClassesManagementViews.GetCoursesAcrClassesSemNum;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Class;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Results.ClassManagementResults.GetCoursesAcrClassesSemNumResult;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Utils;
import pt.isel.ls.View.CommandViews.ViewHTML;
import pt.isel.ls.View.Formatters.HTMLFormatters.Classes.HTMLClassesFormatter;
import pt.isel.ls.View.Formatters.HTMLFormatters.Students.HTMLStudentsFormatter;
import pt.isel.ls.View.Formatters.HTMLFormatters.Teachers.HTMLTeachersFormatter;
import pt.isel.ls.View.ViewTypes.html.HtmlPage;

import static pt.isel.ls.View.ViewTypes.html.Html.*;

public class ViewHTMLGetCoursesAcrClassesSemNum extends ViewHTML {

    @Override
    public HtmlPage build(Result rt) {
        /* Get the result. */
        GetCoursesAcrClassesSemNumResult result = (GetCoursesAcrClassesSemNumResult) rt;

        /* Initiate variables */
        Class _class = result.get_class();
        CustomList<Entity> students = result.get_classStudents();
        CustomList<Entity> teachers = result.get_classTeachers();
        String acr = result.getCourseAcronym();
        String courseName = _class.getcName();
        String title = "Class " + _class.getId() + " from semester " + _class.getaYear()+" " + _class.getaSemester() + " from Course " + courseName;
        String viewPath = "/courses/" + acr + "/classes/" + _class.getaYear() + Utils.semMap.get(_class.getaSemester()) + "/" + _class.getId();

        HTMLClassesFormatter classFormatter = new HTMLClassesFormatter();
        HTMLStudentsFormatter studentsFormatter = new HTMLStudentsFormatter();
        HTMLTeachersFormatter teachersFormatter = new HTMLTeachersFormatter();

        /* Build the HTML page. */
        return new HtmlPage(
                title,
                h1(text(title)),
                p(a("/", "Menu")),
                p(a("/courses/" + acr + "/classes", "Classes from course " + courseName)),
                h3(text("Class Description:")),
                classFormatter.formatEntity(_class),
                h3(a(viewPath + "/students", "Class Students:")),
                studentsFormatter.formatEntities(students),
                h3(a(viewPath + "/teachers", "Class Teachers:")),
                teachersFormatter.formatEntities(teachers)
        );
    }
}