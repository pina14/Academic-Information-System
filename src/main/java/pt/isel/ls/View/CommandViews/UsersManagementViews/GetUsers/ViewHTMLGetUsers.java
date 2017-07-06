package pt.isel.ls.View.CommandViews.UsersManagementViews.GetUsers;

import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Results.UserManagementResults.GetUsersResult;
import pt.isel.ls.View.CommandViews.ViewHTML;
import pt.isel.ls.View.Formatters.HTMLFormatters.Users.HTMLUsersFormatter;
import pt.isel.ls.View.ViewTypes.html.HtmlPage;

import static pt.isel.ls.View.ViewTypes.html.Html.*;

public class ViewHTMLGetUsers extends ViewHTML{
    @Override
    protected HtmlPage build(Result rt) {
        /* Get the result. */
        GetUsersResult result = (GetUsersResult) rt;

        /* Initiate variables. */
        HTMLUsersFormatter usersFormatter = new HTMLUsersFormatter();
        String title = "All the Users";

        /* Build the HTML page. */
        HtmlPage page =  new HtmlPage(
                title,
                h1(text(title)),
                p(a("/", "Menu")),
                h3(text("Users Description:")),
                usersFormatter.formatEntities(result.getUsers())
        );

        /* Add if possible the option of paging to the page.
         * Check if its possible to enable the "Previous" link. */
        int skip = result.getSkip(), top = result.getTop();
        if(skip > 0)
            page.with(p(a("/users?skip=" + (skip - top) + "&top=" + top, "Previous")));
        /* Check if its possible to enable "Next" link. */
        if(result.getNumberRows() > skip + top)
            page.with(p(a("/users?skip=" + (skip + top) +"&top=" + top, "Next")));

        return page;
    }
}