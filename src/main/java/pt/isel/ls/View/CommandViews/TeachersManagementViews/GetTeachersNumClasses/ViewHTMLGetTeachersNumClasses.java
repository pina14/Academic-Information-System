package pt.isel.ls.View.CommandViews.TeachersManagementViews.GetTeachersNumClasses;

import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Results.TeacherManagementResults.GetTeachersNumClassesResult;
import pt.isel.ls.View.CommandViews.ViewHTML;
import pt.isel.ls.View.Formatters.HTMLFormatters.Classes.HTMLCoursesClassesFormatter;
import pt.isel.ls.View.ViewTypes.html.HtmlPage;

import static pt.isel.ls.View.ViewTypes.html.Html.*;

public class ViewHTMLGetTeachersNumClasses extends ViewHTML {
    public HtmlPage build(Result rt){
        /* Get the result. */
        GetTeachersNumClassesResult result = (GetTeachersNumClassesResult)rt;

        /* Initiate variables */
        int num = result.getTeacherNum();
        String title = "Classes teacher " + num + " lectures";
        HTMLCoursesClassesFormatter coursesClassesFormatter = new HTMLCoursesClassesFormatter();

        /* Build HTML page. */
        HtmlPage page = new HtmlPage(
                title,
                h1(text(title)),
                p(a("/", "Menu")),
                coursesClassesFormatter.formatEntities(result.getCoursesClasses())
        );

        /* Add if possible the option of paging to the page.
         * Check if its possible to enable the "Previous" link. */
        int skip = result.getSkip(), top = result.getTop();
        if(skip > 0)
            page.with(p(a("/teachers/" + num + "/classes?skip=" + (skip - top) + "&top=" + top, "Previous")));

        /* Check if its possible to enable "Next" link. */
        if(result.getNumberRows() > skip + top)
            page.with(p(a("/teachers/" + num + "/classes?skip=" + (skip + top) +"&top=" + top, "Next")));

        return page;
    }
}