package pt.isel.ls.View.CommandViews.ProgrammesManagementViews.GetProgrammes;

import pt.isel.ls.Model.Results.ProgrammeManagementResults.GetProgrammesResult;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.ViewHTML;
import pt.isel.ls.View.Formatters.HTMLFormatters.Programmes.HTMLProgrammesFormatter;
import pt.isel.ls.View.ViewTypes.html.HtmlPage;

import static pt.isel.ls.View.ViewTypes.html.Html.*;

public class ViewHTMLGetProgrammes  extends ViewHTML {

    @Override
    public HtmlPage build(Result rt) {
        /* Get the result. */
        GetProgrammesResult result = (GetProgrammesResult) rt;

        /* Initiate variables. */
        HTMLProgrammesFormatter htmlProgrammesFormatter = new HTMLProgrammesFormatter();

        /* Build the HTML page. */
        HtmlPage page = new HtmlPage(
                        "Programmes",
                        h1(text("Programmes:")),
                        p(a("/", "Menu")),
                        h3(text("Programmes Description:")),
                        htmlProgrammesFormatter.formatEntities(result.getProgrammes()),
                        h3(text("Add Programme")),
                        form("POST", "/programmes",
                                ul(
                                   li(label("pid", "Programme Acronym:"), textInput("pid")),
                                   li(label("name", "Programme Name:"), textInput("name")),
                                   li(label("length", "Number of Semesters:"), textInput("length"))
                                ),
                                button("text", "submit", "Post Programme")
                        )
        );

        /* Add if possible the option of paging to the page.
         * Check if its possible to enable the "Previous" link. */
        int skip = result.getSkip(), top = result.getTop();
        if(skip > 0)
            page.with(p(a("/programmes?skip=" + (skip - top) + "&top=" + top, "Previous")));

        /* Check if its possible to enable "Next" link. */
        if(result.getNumberRows() > skip + top)
            page.with(p(a("/programmes?skip=" + (skip + top) +"&top=" + top, "Next")));

        return page;
    }
}