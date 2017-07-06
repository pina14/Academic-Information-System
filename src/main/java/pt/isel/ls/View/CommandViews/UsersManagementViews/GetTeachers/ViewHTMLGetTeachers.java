package pt.isel.ls.View.CommandViews.UsersManagementViews.GetTeachers;

import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Results.UserManagementResults.GetTeachersResult;
import pt.isel.ls.View.CommandViews.ViewHTML;
import pt.isel.ls.View.Formatters.HTMLFormatters.Teachers.HTMLTeachersFormatter;
import pt.isel.ls.View.ViewTypes.html.HtmlPage;

import static pt.isel.ls.View.ViewTypes.html.Html.*;

public class ViewHTMLGetTeachers extends ViewHTML {
    @Override
    protected HtmlPage build(Result rt) {
        /* Get the result. */
        GetTeachersResult result = (GetTeachersResult) rt;

        /* Initiate variables. */
        HTMLTeachersFormatter htmlTeachersFormatter = new HTMLTeachersFormatter();
        String title = "All the Teachers";

        /* Build the HTML page. */
        HtmlPage page = new HtmlPage(
                title,
                h1(text(title)),
                p(a("/", "Menu")),
                h3(text("Teachers Description:")),
                htmlTeachersFormatter.formatEntities(result.getTeachers()),
                h3(text("Add Teacher:")),
                form("POST", "/teachers",
                        ul(
                           //the textInput name is the name of the parameters
                           li(label("num", "Teachers number:"), textInput("num")),
                           li(label("name", "Teachers name:"), textInput("name")),
                           li(label("email", "Teachers email: "), textInput("email"))
                        ),
                        button("text", "submit", "Post Teachers")
                )
        );

        /* Add if possible the option of paging to the page.
         * Check if its possible to enable the "Previous" link. */
        int skip = result.getSkip(), top = result.getTop();
        if(skip > 0)
            page.with(p(a("/teachers?skip=" + (skip - top) + "&top=" + top, "Previous")));
        /* Check if its possible to enable "Next" link. */
        if(result.getNumberRows() > skip + top)
            page.with(p(a("/teachers?skip=" + (skip + top) +"&top=" + top, "Next")));

        return page;
    }
}