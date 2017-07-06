package pt.isel.ls.View.CommandViews.UsersManagementViews.GetStudents;

import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Results.UserManagementResults.GetStudentsResult;
import pt.isel.ls.View.CommandViews.ViewHTML;
import pt.isel.ls.View.Formatters.HTMLFormatters.Students.HTMLStudentsFormatter;
import pt.isel.ls.View.ViewTypes.html.HtmlPage;

import static pt.isel.ls.View.ViewTypes.html.Html.*;

public class ViewHTMLGetStudents extends ViewHTML {
    @Override
    protected HtmlPage build(Result rt) {
        /* Get the result. */
        GetStudentsResult result = (GetStudentsResult) rt;

        /* Initiate variables. */
        String title = "All the Students";
        HTMLStudentsFormatter htmlStudentsFormatter = new HTMLStudentsFormatter();

        /* Build the HTML page. */
        HtmlPage page =  new HtmlPage(
                title,
                h1(text(title)),
                p(a("/", "Menu")),
                h3(text("Students Description:")),
                htmlStudentsFormatter.formatEntities(result.getStudents()),
                h3(text("Add Student:")),
                form("POST", "/students",
                        ul(
                           //the textInput name is the name of the parameters
                           li(label("num", "Student number:"), textInput("num")),
                           li(label("name", "Student name:"), textInput("name")),
                           li(label("email", "Student email: "), textInput("email")),
                           li(label("pid", "Programme pid: "), textInput("pid"))
                        ),
                        button("text", "submit", "Post Student")
                )
        );

        /* Add if possible the option of paging to the page.
         * Check if its possible to enable the "Previous" link. */
        int skip = result.getSkip(), top = result.getTop();
        if(skip > 0)
            page.with(p(a("/students?skip=" + (skip - top) + "&top=" + top, "Previous")));
        /* Check if its possible to enable "Next" link. */
        if(result.getNumberRows() > skip + top)
            page.with(p(a("/students?skip=" + (skip + top) +"&top=" + top, "Next")));

        return page;
    }
}