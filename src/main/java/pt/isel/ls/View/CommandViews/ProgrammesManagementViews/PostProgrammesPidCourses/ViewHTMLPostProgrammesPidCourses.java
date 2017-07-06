package pt.isel.ls.View.CommandViews.ProgrammesManagementViews.PostProgrammesPidCourses;

import pt.isel.ls.Model.Results.ProgrammeManagementResults.PostProgrammesPidCoursesResultError;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.ViewHTML;
import pt.isel.ls.View.ViewTypes.html.HtmlPage;

import java.io.IOException;

import static pt.isel.ls.View.ViewTypes.html.Html.*;

public class ViewHTMLPostProgrammesPidCourses extends ViewHTML {
    private static final String ACR = "acr";
    private static final String MANDATORY = "mandatory";
    private static final String SEMESTERS = "semesters";

    @Override
    protected HtmlPage build(Result rt) throws IOException {
        /* Get the result. */
        PostProgrammesPidCoursesResultError resultError = (PostProgrammesPidCoursesResultError) rt;

        /* Build the HTML page. */
        return new HtmlPage(
                resultError.getPid() + " courses",
                h1(text("Ups Something Went Wrong...")),
                form("POST", "/programmes/" + resultError.getPid() + "/courses",
                        ul(li(label(ACR, "Course acronym:").withAttr("Style", resultError.getColor(ACR)),
                           textInput("acr"),
                           text(resultError.getErrorDescription(ACR))),
                           li(label(MANDATORY, "Mandatory:").withAttr("Style", resultError.getColor(MANDATORY)),
                           textInput("mandatory"),
                           text(resultError.getErrorDescription(MANDATORY))),
                           li(label(SEMESTERS, "Curricular Semesters list: ").withAttr("Style", resultError.getColor(SEMESTERS)),
                           textInput("semesters"),
                           text(resultError.getErrorDescription(SEMESTERS)))
                        ),
                        button("text", "submit", "Post Course")
                )
        );
    }
}