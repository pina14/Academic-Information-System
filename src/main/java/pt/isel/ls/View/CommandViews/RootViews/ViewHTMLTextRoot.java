package pt.isel.ls.View.CommandViews.RootViews;

import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.ViewHTML;
import pt.isel.ls.View.ViewTypes.Writable;
import pt.isel.ls.View.ViewTypes.html.HtmlElem;
import pt.isel.ls.View.ViewTypes.html.HtmlPage;

import static pt.isel.ls.View.ViewTypes.html.Html.*;

public class ViewHTMLTextRoot extends ViewHTML {

    @Override
    protected HtmlPage build(Result rt) {
        /* Build HTML page. */
        return new HtmlPage(
                "Menu",
                h1(text("Menu")),
                menuContent()
        );
    }

    /**
     * Get the HTML form of command GET/.
     * @return Writable object that will contain its form of representation in HTML.
     */
    private Writable menuContent() {
        /* Create a list that will represent the menu. */
        HtmlElem ul = new HtmlElem("ul")
             .with(li(a("/courses","Courses")))
             .with(li(a("/programmes","Programmes")))
             .with(li(a("/users","Users")))
             .with(li(a("/teachers","Teachers")))
             .with(li(a("/students","Students"))
        );
        return ul;
    }
}