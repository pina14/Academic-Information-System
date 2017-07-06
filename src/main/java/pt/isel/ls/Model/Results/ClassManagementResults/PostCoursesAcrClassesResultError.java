package pt.isel.ls.Model.Results.ClassManagementResults;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Error.CustomError;
import pt.isel.ls.Model.Results.ResultError;
import pt.isel.ls.View.CommandViews.ClassesManagementViews.PostCoursesAcrClasses.ViewHTMLPostCoursesAcrClasses;

public class PostCoursesAcrClassesResultError extends ResultError{
    private String courseAcronym;

    public PostCoursesAcrClassesResultError(CustomList<CustomError> errors, String courseAcronym) {
        super(new ViewHTMLPostCoursesAcrClasses(), errors);
        this.courseAcronym = courseAcronym;
    }

    public String getCourseAcronym() {
        return courseAcronym;
    }
}