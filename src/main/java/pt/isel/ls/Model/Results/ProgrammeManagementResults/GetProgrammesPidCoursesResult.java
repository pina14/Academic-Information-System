package pt.isel.ls.Model.Results.ProgrammeManagementResults;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.ProgrammesManagementViews.GetProgrammesPidCourses.ViewHTMLGetProgrammesPidCourses;
import pt.isel.ls.View.CommandViews.ProgrammesManagementViews.GetProgrammesPidCourses.ViewJSONGetProgrammesPidCourses;
import pt.isel.ls.View.CommandViews.ProgrammesManagementViews.GetProgrammesPidCourses.ViewPlainTextGetProgrammesPidCourses;

public class GetProgrammesPidCoursesResult extends Result {
    private CustomList<Entity> coursesWithCurricularSemester;
    private int skip, top, numberRows;
    private String pid;

    public GetProgrammesPidCoursesResult(CustomList<Entity> coursesWithCurricularSemester, int skip, int top, int numberRows, String pid) {
        super(new ViewHTMLGetProgrammesPidCourses(), new ViewPlainTextGetProgrammesPidCourses(), new ViewJSONGetProgrammesPidCourses());
        this.coursesWithCurricularSemester = coursesWithCurricularSemester;
        this.skip = skip;
        this.top = top;
        this.numberRows = numberRows;
        this.pid = pid;
    }

    public CustomList<Entity> getCoursesWithCurricularSemester() {
        return coursesWithCurricularSemester;
    }

    public int getSkip() {
        return skip;
    }

    public int getTop() {
        return top;
    }

    public int getNumberRows() {
        return numberRows;
    }

    public String getPid() {
        return pid;
    }
}