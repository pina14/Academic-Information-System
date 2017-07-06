package pt.isel.ls.View.CommandViews.StudentsManagementView.GetCoursesAcrClassesSemNumStudents;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Results.StudentManagementResults.GetCoursesAcrClassesSemNumStudentsResult;
import pt.isel.ls.View.CommandViews.View;
import pt.isel.ls.View.Formatters.JSON.Students.JSONStudentsFormatter;

import java.io.IOException;
import java.io.StringWriter;

public class ViewJSONGetCoursesAcrClassesSemNumStudents implements View {
    @Override
    public void writeResult(Result rt, StringWriter writer) throws IOException {
        /* Get the JSON students formatter. */
        JSONStudentsFormatter jsonFormatter = new JSONStudentsFormatter();
        /* Get the students. */
        CustomList<Entity> students  = ((GetCoursesAcrClassesSemNumStudentsResult)rt).getStudents();
        /* Prints each student to the writer. */
        jsonFormatter.formatEntities(students).writeTo(writer);
    }
}