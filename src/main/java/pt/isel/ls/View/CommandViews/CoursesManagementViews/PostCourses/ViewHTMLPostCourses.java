package pt.isel.ls.View.CommandViews.CoursesManagementViews.PostCourses;

import pt.isel.ls.Model.Results.CourseManagementResults.PostCoursesResultError;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.ViewHTML;
import pt.isel.ls.View.ViewTypes.html.HtmlPage;

import java.io.IOException;

import static pt.isel.ls.View.ViewTypes.html.Html.*;

public class ViewHTMLPostCourses extends ViewHTML {
    private static final String NAME = "name";
    private static final String ACR = "acr";
    private static final String TEACHER = "teacher";

    /**
     * Get the HTML view of the command PostCourses.
     * @param rt Object that represents the result.
     * @return HTML view of the command PostCourses.
     */
    @Override
    protected HtmlPage build(Result rt) throws IOException {
        /* Get the result. */
        PostCoursesResultError resultError = (PostCoursesResultError) rt;

        /* Build the HTML page. */
        return new HtmlPage("Courses",
                h1(text("Ups Something Went Wrong...")),
                form("POST", "/courses",
                        ul(li(label(NAME, "Course Name:").withAttr("Style", resultError.getColor(NAME)),
                           textInput("name"),
                           text(resultError.getErrorDescription(NAME))),
                           li(label(ACR, "Course Acronym:").withAttr("Style", resultError.getColor(ACR)),
                           textInput("acr"),
                           text(resultError.getErrorDescription(ACR))),
                           li(label(TEACHER, "Teacher Number:").withAttr("Style", resultError.getColor(TEACHER)),
                           textInput("teacher"),
                           text(resultError.getErrorDescription(TEACHER)))
                        ),
                        button("text", "submit", "Post Course")
                )
        );
    }
}