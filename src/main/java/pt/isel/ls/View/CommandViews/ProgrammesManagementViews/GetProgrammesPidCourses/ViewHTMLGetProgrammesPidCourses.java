package pt.isel.ls.View.CommandViews.ProgrammesManagementViews.GetProgrammesPidCourses;

import pt.isel.ls.Model.Results.ProgrammeManagementResults.GetProgrammesPidCoursesResult;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.ViewHTML;
import pt.isel.ls.View.Formatters.HTMLFormatters.Courses.HTMLCoursesCurrSemFormatter;
import pt.isel.ls.View.ViewTypes.html.HtmlPage;

import static pt.isel.ls.View.ViewTypes.html.Html.*;

public class ViewHTMLGetProgrammesPidCourses extends ViewHTML{

    @Override
    public HtmlPage build(Result rt) {
        /* Get the result. */
        GetProgrammesPidCoursesResult result = (GetProgrammesPidCoursesResult) rt;

        /* Initiate variable. */
        String pid = result.getPid();
        HTMLCoursesCurrSemFormatter htmlCoursesCurrSemFormatter = new HTMLCoursesCurrSemFormatter();

        /* Build the HTML page. */
        HtmlPage page = new HtmlPage(
                pid + " courses",
                h1(text( pid + " courses")),
                p(a("/", "Menu")),
                p(a("/programmes", "Programmes")),
                h3(text("Courses")),
                htmlCoursesCurrSemFormatter.formatEntities(result.getCoursesWithCurricularSemester()),
                h3(text("Add Course to this Programme:")),
                form("POST", "/programmes/"+pid+"/courses",
                        ul(
                            li(label("acr", "Course acronym:"), textInput("acr")),
                            li(label("mandatory", "Mandatory:"), textInput("mandatory")),
                            li(label("semesters", "Curricular Semesters list: "), textInput("semesters"))
                        ),
                        button("text", "submit", "Post Course")
                )
        );

        /* Add if possible the option of paging to the page.
         * Check if its possible to enable the "Previous" link. */
        int skip = result.getSkip(), top = result.getTop();
        if(skip > 0)
            page.with(p(a("/programmes/" + pid + "/courses?skip=" + (skip - top) + "&top=" + top, "Previous")));

        /* Check if its possible to enable "Next" link. */
        if(result.getNumberRows() > skip + top)
            page.with(p(a("/programmes/" + pid + "/courses?skip=" + (skip + top) +"&top=" + top, "Next")));

        return page;
    }
}