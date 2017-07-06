package pt.isel.ls.View.CommandViews.TeachersManagementViews.PostCoursesAcrClassesSemNumTeachers;

import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Results.TeacherManagementResults.PostCoursesAcrClassesSemNumTeachersResultError;
import pt.isel.ls.Model.Utils;
import pt.isel.ls.View.CommandViews.ViewHTML;
import pt.isel.ls.View.ViewTypes.html.HtmlPage;

import java.io.IOException;

import static pt.isel.ls.View.ViewTypes.html.Html.*;

public class ViewHTMLPostCoursesAcrClassesSemNumTeachers extends ViewHTML {
    @Override
    protected HtmlPage build(Result rt) throws IOException {
        /* Get the result. */
        PostCoursesAcrClassesSemNumTeachersResultError resultError = (PostCoursesAcrClassesSemNumTeachersResultError) rt;

        /* Initiate variables. */
        String curClass = "Class " + resultError.getNum() + " from semester " +  resultError.getYear() + " " +  resultError.getSem() + " from Course " + resultError.getAcr();
        String title = "Teachers from " + curClass;

        /* Build HTML page. */
        return new HtmlPage(
                title,
                h1(text("Ups Something Went Wrong...")),
                form("POST", "/courses/" + resultError.getAcr() + "/classes/" + resultError.getYear() +  Utils.semMap.get(resultError.getSem()) + "/" + resultError.getNum() + "/teachers",
                        ul(li(label("numDoc", "Teacher Number:").withAttr("Style", resultError.getColor("numDoc")),
                           textInput("numDoc"),
                           text(resultError.getErrorDescription("numDoc")))
                        ),
                        button("text", "submit", "Post Teacher")
                )
        );
    }
}
