package pt.isel.ls.View.CommandViews.TeachersManagementViews.GetCoursesAcrClassesSemNumTeachers;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Class;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Results.TeacherManagementResults.GetCoursesAcrClassesSemNumTeachersResult;
import pt.isel.ls.Model.Utils;
import pt.isel.ls.View.CommandViews.ViewHTML;
import pt.isel.ls.View.Formatters.HTMLFormatters.Teachers.HTMLTeachersFormatter;
import pt.isel.ls.View.ViewTypes.html.HtmlPage;

import static pt.isel.ls.View.ViewTypes.html.Html.*;

public class ViewHTMLGetCoursesAcrClassesSemNumTeachers extends ViewHTML{
    @Override
    public HtmlPage build(Result rt) {
        /* Get the result. */
        GetCoursesAcrClassesSemNumTeachersResult result = ((GetCoursesAcrClassesSemNumTeachersResult)rt);

        /* Get the class and its teachers*/
        Class _class = result.get_class();
        CustomList<Entity> teachers = result.getTeachers() ;

        /* Initiate variables */
        HTMLTeachersFormatter htmlTeachersFormatter = new HTMLTeachersFormatter();
        String curClass = "Class "+_class.getId()+ " from semester "+ _class.getaYear()+" "+ _class.getaSemester() +" from Course " + _class.getcName();
        String title = "Teachers from "+curClass;
        String yearSem = _class.getaYear()+ String.valueOf(Utils.semMap.get(_class.getaSemester()));
        String acr = result.getCourseAcronym();

        /* Build HTML page. */
        HtmlPage page = new HtmlPage(
                title,
                h1(text(title)),
                p(a("/", "Menu")),
                p(a("/courses/"+ acr + "/classes/"+ yearSem + "/" + _class.getId(), curClass)),
                h3(text("Teachers:")),
                htmlTeachersFormatter.formatEntities(teachers),
                h3(text("Add teacher to this class:")),
                form("POST", "/courses/" + acr + "/classes/" + yearSem + "/" + _class.getId() + "/teachers",
                        ul(
                            //the textInput name is the name of the parameters
                            li(label("numDoc", "Teacher Number:"), textInput("numDoc"))
                        ),
                        button("text", "submit", "Post Teacher")
                )
        );

        /* Add if possible the option of paging to the page.
         * Check if its possible to enable the "Previous" link. */
        int skip = result.getSkip(), top = result.getTop();
        if(skip > 0)
            page.with(p(a("/courses/" + acr + "/classes/" + yearSem + "/" + _class.getId()+ "/teachers?skip=" + (skip - top) + "&top=" + top, "Previous")));

        /* Check if its possible to enable "Next" link. */
        if(result.getNumberRows() > skip + top)
            page.with(p(a("/courses/" + acr + "/classes/" + yearSem + "/" + _class.getId()+ "/teachers?skip=" + (skip + top) +"&top=" + top, "Next")));

        return page;
    }
}