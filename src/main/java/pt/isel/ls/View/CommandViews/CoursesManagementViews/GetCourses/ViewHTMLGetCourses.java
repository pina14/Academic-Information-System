package pt.isel.ls.View.CommandViews.CoursesManagementViews.GetCourses;

import pt.isel.ls.Model.Results.CourseManagementResults.GetCoursesResult;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.ViewHTML;
import pt.isel.ls.View.Formatters.HTMLFormatters.Courses.HTMLCoursesFormatter;
import pt.isel.ls.View.ViewTypes.html.HtmlPage;

import static pt.isel.ls.View.ViewTypes.html.Html.*;

public class ViewHTMLGetCourses extends ViewHTML {

    @Override
    public HtmlPage build(Result rt) {
        /* Get the result. */
        GetCoursesResult result = (GetCoursesResult) rt;

        /* Initiate variable. */
        HTMLCoursesFormatter htmlCoursesFormatter = new HTMLCoursesFormatter();

        /* Build the HTML page. */
        HtmlPage page = new HtmlPage("Courses",
                h1(text("Courses")),
                p(a("/", "Menu")),
                h3(text("Courses Description:")),
                htmlCoursesFormatter.formatEntities(result.getCourses()),
                h3(text("Add Course")),
                form("POST", "/courses",
                        ul(
                           li(label("name", "Course Name:"), textInput("name")),
                           li(label("acr", "Course Acronym:"), textInput("acr")),
                           li(label("teacher", "Teacher Number:"), textInput("teacher"))
                        ),
                        button("text", "submit", "Post Course")
                )
        );

        /* Add if possible the option of paging to the page.
         * Check if its possible to enable the "Previous" link. */
        int skip = result.getSkip(), top = result.getTop();
        if(skip > 0)
            page.with(p(a("/courses?skip=" + (skip - top) + "&top=" + top, "Previous")));

        /* Check if its possible to enable "Next" link. */
        if(result.getNumberRows() > skip + top)
            page.with(p(a("/courses?skip=" + (skip + top) +"&top=" + top, "Next")));

        return page;
    }
}