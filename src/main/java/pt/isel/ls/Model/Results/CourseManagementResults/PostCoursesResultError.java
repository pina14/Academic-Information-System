package pt.isel.ls.Model.Results.CourseManagementResults;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Error.CustomError;
import pt.isel.ls.Model.Results.ResultError;
import pt.isel.ls.View.CommandViews.CoursesManagementViews.PostCourses.ViewHTMLPostCourses;

public class PostCoursesResultError extends ResultError {
    public PostCoursesResultError(CustomList<CustomError> errors) {
        super(new ViewHTMLPostCourses(), errors);
    }
}