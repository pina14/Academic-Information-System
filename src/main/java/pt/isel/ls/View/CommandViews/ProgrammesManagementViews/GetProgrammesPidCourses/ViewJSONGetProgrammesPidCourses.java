package pt.isel.ls.View.CommandViews.ProgrammesManagementViews.GetProgrammesPidCourses;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Results.ProgrammeManagementResults.GetProgrammesPidCoursesResult;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.View;
import pt.isel.ls.View.Formatters.JSON.Courses.JSONCoursesSemCurrFormatter;

import java.io.IOException;
import java.io.StringWriter;

public class ViewJSONGetProgrammesPidCourses implements View {
    @Override
    public void writeResult(Result rt, StringWriter writer) throws IOException {
        /* Get the JSON courses with current semester formatter. */
        JSONCoursesSemCurrFormatter jsonFormatter = new JSONCoursesSemCurrFormatter();
        /* Get the courses with current semester. */
        CustomList<Entity> coursesWithCurricularSemester = ((GetProgrammesPidCoursesResult) rt).getCoursesWithCurricularSemester();
        /* Prints each courses with current semester to the writer. */
        jsonFormatter.formatEntities(coursesWithCurricularSemester).writeTo(writer);
    }
}