package pt.isel.ls.View.CommandViews.ClassesManagementViews.GetCoursesAcrClassesSem;

import pt.isel.ls.Model.Results.ClassManagementResults.GetCoursesAcrClassesSemResult;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.ViewHTML;
import pt.isel.ls.View.Formatters.HTMLFormatters.Classes.HTMLClassesFormatter;
import pt.isel.ls.View.ViewTypes.html.HtmlPage;

import static pt.isel.ls.View.ViewTypes.html.Html.*;

public class ViewHTMLGetCoursesAcrClassesSem extends ViewHTML {

    @Override
    public HtmlPage build(Result rt){
        /* Get the result. */
        GetCoursesAcrClassesSemResult result = (GetCoursesAcrClassesSemResult) rt;

        /* Initiate variables. */
        String acr = result.getCourseAcronym(),
               sem = result.getNotFilteredsemester(),
               title = "Classes in " + result.getYear() + result.getSemester() + " from Course " + acr;

        /* Get the HTML classes formatter. */
        HTMLClassesFormatter classesFormatter = new HTMLClassesFormatter();
        classesFormatter.setAcr(acr);

        /* Build the HTML page. */
        HtmlPage page = new HtmlPage(
                title,
                h1(text(title)),
                p(a("/", "Menu")),
                p(a("/courses/" + acr +"/classes", "Classes from course " + acr)),
                h3(text("Classes Description:")),
                classesFormatter.formatEntities(result.getClasses())
        );

        /* Add if possible the option of paging to the page.
         * Check if its possible to enable the "Previous" link. */
        int skip = result.getSkip(), top = result.getTop();
        if(skip > 0)
            page.with(p(a("/courses/" + acr + "/classes/" + sem + "?skip=" + (skip - top) + "&top=" + top, "Previous")));

        /* Check if its possible to enable "Next" link. */
        if(result.getNumberRows() > skip + top)
            page.with(p(a("/courses/" + acr + "/classes/" + sem + "?skip=" + (skip + top) +"&top=" + top, "Next")));

        return page;
    }
}