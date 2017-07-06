package pt.isel.ls.View.CommandViews.StudentsManagementView.GetCoursesAcrClassesSemNumStudents;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Class;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Results.StudentManagementResults.GetCoursesAcrClassesSemNumStudentsResult;
import pt.isel.ls.Model.Utils;
import pt.isel.ls.View.CommandViews.StudentsManagementView.StudentsManagementAuxiliary;
import pt.isel.ls.View.CommandViews.ViewHTML;
import pt.isel.ls.View.Formatters.HTMLFormatters.Students.HTMLStudentsFormatter;
import pt.isel.ls.View.ViewTypes.Writable;
import pt.isel.ls.View.ViewTypes.html.HtmlPage;

import java.io.IOException;

import static pt.isel.ls.View.ViewTypes.html.Html.*;

public class ViewHTMLGetCoursesAcrClassesSemNumStudents extends ViewHTML {

    @Override
    protected HtmlPage build(Result rt) throws IOException {
        /* Get the result. */
        GetCoursesAcrClassesSemNumStudentsResult result = (GetCoursesAcrClassesSemNumStudentsResult)rt;

        /* Get the class. */
        Class _class = result.get_class();

        /* Get the students. */
        CustomList<Entity> studentsEnrolled = result.getStudents();
        CustomList<Entity> studentsNotEnrolled = result.getStudentsNotEnrolled();

        /* Get the students formatter. */
        HTMLStudentsFormatter htmlStudentsFormatter = new HTMLStudentsFormatter();

        /* Initiate variables */
        String yearSem = _class.getaYear()+ String.valueOf(Utils.semMap.get(_class.getaSemester())), classId = _class.getId(),
               curClass = "Class " + classId + " from semester "+ _class.getaYear()+" "+ _class.getaSemester() +" from Course " + _class.getcName(),
               curClassPath = "/courses/"+ result.getCourseAcronym() +"/classes/"+yearSem+"/"+classId,
               title = "Students from "+ curClass;

        /* Get the checkBoxes, containing the not enrolled students, element to present in the HTML page. */
        Writable[] checkboxes = StudentsManagementAuxiliary.getCheckboxes(studentsNotEnrolled);

        /* Build HTML page */
        HtmlPage page = new HtmlPage(
                title,
                h1(text(title)),
                p(a("/", "Menu")),
                p(a(curClassPath, curClass)),
                h3(text("Students:")),
                htmlStudentsFormatter.formatEntities(studentsEnrolled)
        );

        /* Add form if there's students not enrolled, if not show message with that information */
        if(checkboxes.length > 0) {
            page.with(h3(text("Add Students to this Class:"))).with(form("POST", curClassPath + "/students",
                    ul(
                        checkboxes
                    ),
                    button("text", "submit", "Post Students")
            ));
        }
        else
            page.with(h3(text("There's no students left to add to this class")));

        /* Add if possible the option of paging to the page.
         * Check if its possible to enable the "Previous" link. */
        int skip = result.getSkip(), top = result.getTop();
        if(skip > 0)
            page.with(p(a(curClassPath + "/students?skip=" + (skip - top) + "&top=" + top, "Previous")));

        /* Check if its possible to enable "Next" link. */
        if(result.getNumberRows() > skip + top)
            page.with(p(a(curClassPath + "/students?skip=" + (skip + top) +"&top=" + top, "Next")));

        return page;
    }
}