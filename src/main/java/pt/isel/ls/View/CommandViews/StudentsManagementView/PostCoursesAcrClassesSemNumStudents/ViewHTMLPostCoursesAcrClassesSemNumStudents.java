package pt.isel.ls.View.CommandViews.StudentsManagementView.PostCoursesAcrClassesSemNumStudents;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Results.StudentManagementResults.PostCoursesAcrClassesSemNumStudentsResultError;
import pt.isel.ls.Model.Utils;
import pt.isel.ls.View.CommandViews.ViewHTML;
import pt.isel.ls.View.ViewTypes.Writable;
import pt.isel.ls.View.ViewTypes.html.HtmlPage;

import java.io.IOException;

import static pt.isel.ls.View.CommandViews.StudentsManagementView.StudentsManagementAuxiliary.getCheckboxes;
import static pt.isel.ls.View.ViewTypes.html.Html.*;

public class ViewHTMLPostCoursesAcrClassesSemNumStudents extends ViewHTML {

    @Override
    protected HtmlPage build(Result rt) throws IOException {
        /* Get the result. */
        PostCoursesAcrClassesSemNumStudentsResultError resultError = (PostCoursesAcrClassesSemNumStudentsResultError) rt;

        /* Get the students not enrolled in the class. */
        CustomList<Entity> studentsNotEnrolled = resultError.getStudentsNotEnrolled();

        /* Initiate variables */
        String yearSem = resultError.getYear() + String.valueOf(Utils.semMap.get(resultError.getSem())),
               classId = resultError.getNum(),
               curClass = "Class " + classId + " from semester " + resultError.getYear() + " " + resultError.getSem() +" from Course " + resultError.getAcr(),
               curClassPath = "/courses/" + resultError.getAcr() + "/classes/" + yearSem + "/" + classId,
               title = "Students from "+ curClass;

        /* Get the checkBoxes, containing the not enrolled students, element to present in the HTML page. */
        Writable[] checkboxes = getCheckboxes(studentsNotEnrolled);

        /* Build HTML page. */
        return new HtmlPage(
                title,
                h3(text("Ups Something Went Wrong..."))).with(form("POST", curClassPath + "/students",
                ul(checkboxes),
                button("text", "submit", "Post Students"))
        );
    }


}
