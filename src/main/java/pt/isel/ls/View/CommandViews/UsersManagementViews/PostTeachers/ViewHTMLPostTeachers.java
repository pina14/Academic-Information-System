package pt.isel.ls.View.CommandViews.UsersManagementViews.PostTeachers;

import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Results.UserManagementResults.PostTeachersResultError;
import pt.isel.ls.View.CommandViews.ViewHTML;
import pt.isel.ls.View.ViewTypes.html.HtmlPage;

import java.io.IOException;

import static pt.isel.ls.View.ViewTypes.html.Html.*;

public class ViewHTMLPostTeachers extends ViewHTML{
    private static final String NUM = "num";
    private static final String NAME = "name";
    private static final String EMAIL = "email";

    @Override
    protected HtmlPage build(Result rt) throws IOException {
        /* Get the result. */
        PostTeachersResultError resultError = (PostTeachersResultError) rt;

        /* Build the HTML page. */
        return new HtmlPage(
                "Post Teachers",
                h1(text("Ups Something Went Wrong...")),
                form("POST", "/teachers",
                        ul(li(label(NUM, "Teachers number:").withAttr("Style", resultError.getColor(NUM)),
                           textInput("num"),
                           text(resultError.getErrorDescription(NUM))),
                           li(label(NAME, "Teachers name:").withAttr("Style", resultError.getColor(NAME)),
                           textInput("name"),
                           text(resultError.getErrorDescription(NAME))),
                           li(label(EMAIL, "Teachers email: ").withAttr("Style", resultError.getColor(EMAIL)),
                           textInput("email"),
                           text(resultError.getErrorDescription(EMAIL)))
                        ),
                        button("text", "submit", "Post Teachers")
                )
        );
    }
}