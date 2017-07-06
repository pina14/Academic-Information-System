package pt.isel.ls.View.CommandViews.ClassesManagementViews.GetCoursesAcrClasses;

import pt.isel.ls.Model.Results.ClassManagementResults.GetCoursesAcrClassesResult;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.ViewHTML;
import pt.isel.ls.View.Formatters.HTMLFormatters.Classes.HTMLClassesFormatter;
import pt.isel.ls.View.ViewTypes.html.HtmlPage;

import static pt.isel.ls.View.ViewTypes.html.Html.*;

public class ViewHTMLGetCoursesAcrClasses extends ViewHTML {

    @Override
    public HtmlPage build(Result rt){
        /* Get the result. */
        GetCoursesAcrClassesResult result = (GetCoursesAcrClassesResult) rt;

        /* Initiate variables. */
        String acr = result.getCourseAcronym(),
               title = "Classes from Course " + acr;
        HTMLClassesFormatter classesFormatter = new HTMLClassesFormatter();
        classesFormatter.setAcr(acr);

        /* Build the HTML page. */
        HtmlPage page = new HtmlPage(
                title,
                h1(text(title)),
                p(a("/", "Menu")),
                p(a("/courses/" + acr, "Course " + acr)),
                h3(text("Classes Description:")),
                classesFormatter.formatEntities(result.getClasses()),
                h3(text("Add Class:")),
                form("POST", "/courses/" + acr + "/classes",
                        ul(
                                li(label("sem", "Year and Semester:"), textInput("sem")),
                                li(label("num", "Class ID:"), textInput("num"))
                        ),
                        button("text", "submit", "Post Class")
                )
        );

        /* Add if possible the option of paging to the page.
         * Check if its possible to enable the "Previous" link. */
        int skip = result.getSkip(), top = result.getTop();
        if(skip > 0)
            page.with(p(a("/courses/" + acr + "/classes?skip=" + (skip - top) + "&top=" + top, "Previous")));

        /* Check if its possible to enable "Next" link. */
        if(result.getNumberRows() > skip + top)
            page.with(p(a("/courses/" + acr + "/classes?skip=" + (skip + top) +"&top=" + top, "Next")));

        return page;
    }
}