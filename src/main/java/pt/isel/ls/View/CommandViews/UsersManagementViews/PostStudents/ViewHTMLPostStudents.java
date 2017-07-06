package pt.isel.ls.View.CommandViews.UsersManagementViews.PostStudents;

import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Results.UserManagementResults.PostStudentsResultError;
import pt.isel.ls.View.CommandViews.ViewHTML;
import pt.isel.ls.View.ViewTypes.html.HtmlPage;

import java.io.IOException;

import static pt.isel.ls.View.ViewTypes.html.Html.*;

public class ViewHTMLPostStudents extends ViewHTML {
    private static final String NUM = "num";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String PID = "pid";

    @Override
    protected HtmlPage build(Result rt) throws IOException {
        /* Get the result. */
        PostStudentsResultError resultError = (PostStudentsResultError) rt;

        /* Build the HTML page. */
        return new HtmlPage(
                "Post Students",
                h1(text("Ups Something Went Wrong...")),
                form("POST", "/students",
                        ul(li(label(NUM, "Student number:").withAttr("Style", resultError.getColor(NUM)),
                           textInput("num"),
                           text(resultError.getErrorDescription(NUM))),
                           li(label(NAME, "Student name:").withAttr("Style", resultError.getColor(NAME)),
                           textInput("name"),
                           text(resultError.getErrorDescription(NAME))),
                           li(label(EMAIL, "Student email: ").withAttr("Style", resultError.getColor(EMAIL)),
                           textInput("email"),
                           text(resultError.getErrorDescription(EMAIL))),
                           li(label(PID, "Programme pid: ").withAttr("Style", resultError.getColor(PID)),
                           textInput("pid"),
                           text(resultError.getErrorDescription(PID)))
                        ),
                        button("text", "submit", "Post Student")
                )
        );
    }
}