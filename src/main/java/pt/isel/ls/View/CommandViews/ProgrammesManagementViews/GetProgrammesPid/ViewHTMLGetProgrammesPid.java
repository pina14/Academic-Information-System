package pt.isel.ls.View.CommandViews.ProgrammesManagementViews.GetProgrammesPid;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Entities.Programme;
import pt.isel.ls.Model.Results.ProgrammeManagementResults.GetProgrammesPidResult;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.ViewHTML;
import pt.isel.ls.View.Formatters.HTMLFormatters.Courses.HTMLCoursesCurrSemFormatter;
import pt.isel.ls.View.Formatters.HTMLFormatters.Programmes.HTMLProgrammesFormatter;
import pt.isel.ls.View.ViewTypes.html.HtmlPage;

import static pt.isel.ls.View.ViewTypes.html.Html.*;

public class ViewHTMLGetProgrammesPid extends ViewHTML {

    @Override
    public HtmlPage build(Result rt ) {
        /* Get the result. */
        GetProgrammesPidResult result = (GetProgrammesPidResult) rt;

        /* Initiate variables. */
        Programme programme = result.getProgramme();
        CustomList<Entity> programmeCourses = result.getProgrammeCourses();
        HTMLProgrammesFormatter htmlProgrammesFormatter = new HTMLProgrammesFormatter();
        HTMLCoursesCurrSemFormatter htmlCoursesCurrSemFormatter = new HTMLCoursesCurrSemFormatter();

        /* Build the HTML page. */
        return new HtmlPage(
                "Programme " + programme.getAcronym(),
                h1(text("Programme " + programme.getAcronym())),
                p(a("/", "Menu")),
                p(a("/programmes", "Programmes")),
                h3(text("Programme Description:")),
                htmlProgrammesFormatter.formatEntity(programme),
                h3(a("/programmes/"+programme.getAcronym()+"/courses", "Programme courses")),
                htmlCoursesCurrSemFormatter.formatEntities(programmeCourses)
        );
    }
}