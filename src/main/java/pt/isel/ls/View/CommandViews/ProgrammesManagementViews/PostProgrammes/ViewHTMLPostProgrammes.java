package pt.isel.ls.View.CommandViews.ProgrammesManagementViews.PostProgrammes;

import pt.isel.ls.Model.Results.ProgrammeManagementResults.PostProgrammesResultError;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.ViewHTML;
import pt.isel.ls.View.ViewTypes.html.HtmlPage;

import java.io.IOException;

import static pt.isel.ls.View.ViewTypes.html.Html.*;

public class ViewHTMLPostProgrammes extends ViewHTML {
    private static final String PID = "pid";
    private static final String NAME = "name";
    private static final String LENGTH = "length";

    @Override
    protected HtmlPage build(Result rt) throws IOException {
        /* Get the result. */
        PostProgrammesResultError resultError = (PostProgrammesResultError) rt;

        /* Build the HTML page. */
        return new HtmlPage(
                "Programmes",
                h1(text("Ups Something Went Wrong...")),
                form("POST", "/programmes",
                        ul(li(label(PID, "Programme Acronym:").withAttr("Style", resultError.getColor(PID)),
                           textInput("pid"),
                           text(resultError.getErrorDescription(PID))),
                           li(label(NAME, "Programme Name:").withAttr("Style", resultError.getColor(NAME)),
                           textInput("name"),
                           text(resultError.getErrorDescription(NAME))),
                           li(label(LENGTH, "Number of Semesters:").withAttr("Style", resultError.getColor(LENGTH)),
                           textInput("length"),
                           text(resultError.getErrorDescription(LENGTH)))
                        ),
                        button("text", "submit", "Post Programme")
                )
        );
    }
}