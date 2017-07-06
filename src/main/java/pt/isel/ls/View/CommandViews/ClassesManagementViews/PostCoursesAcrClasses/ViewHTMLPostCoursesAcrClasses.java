package pt.isel.ls.View.CommandViews.ClassesManagementViews.PostCoursesAcrClasses;

import pt.isel.ls.Model.Results.ClassManagementResults.PostCoursesAcrClassesResultError;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.ViewHTML;
import pt.isel.ls.View.ViewTypes.html.HtmlPage;

import java.io.IOException;

import static pt.isel.ls.View.ViewTypes.html.Html.*;

public class ViewHTMLPostCoursesAcrClasses extends ViewHTML {
    private static final String SEM = "sem";
    private static final String NUM = "num";

    @Override
    protected HtmlPage build(Result rt) throws IOException {
        /* Get the result. */
        PostCoursesAcrClassesResultError resultError = (PostCoursesAcrClassesResultError) rt;

        /* Build the HTML page. */
        return new HtmlPage("Classes",
                h1(text("Ups Something Went Wrong...")),
                form("POST", "/courses/" + resultError.getCourseAcronym() + "/classes",
                        ul(li(label(SEM, "Year and Semester:").withAttr("Style", resultError.getColor(SEM)),
                           textInput("sem"),
                           text(resultError.getErrorDescription(SEM))),
                           li(label(NUM, "Class ID:").withAttr("Style", resultError.getColor(NUM)),
                           textInput("num"),
                           text(resultError.getErrorDescription(NUM)))
                        ),
                        button("text", "submit", "Post Class")
                )
        );
    }
}