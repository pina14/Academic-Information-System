package pt.isel.ls.Model.Results.ProgrammeManagementResults;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Error.CustomError;
import pt.isel.ls.Model.Results.ResultError;
import pt.isel.ls.View.CommandViews.ProgrammesManagementViews.PostProgrammesPidCourses.ViewHTMLPostProgrammesPidCourses;

public class PostProgrammesPidCoursesResultError extends ResultError {
    private String pid;

    public PostProgrammesPidCoursesResultError(CustomList<CustomError> errors, String pid) {
        super(new ViewHTMLPostProgrammesPidCourses(), errors);
        this.pid = pid;
    }

    public String getPid() {
        return pid;
    }
}