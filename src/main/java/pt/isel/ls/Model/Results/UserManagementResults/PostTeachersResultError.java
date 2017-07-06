package pt.isel.ls.Model.Results.UserManagementResults;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Error.CustomError;
import pt.isel.ls.Model.Results.ResultError;
import pt.isel.ls.View.CommandViews.UsersManagementViews.PostTeachers.ViewHTMLPostTeachers;

public class PostTeachersResultError extends ResultError {
    public PostTeachersResultError(CustomList<CustomError> errors) {
        super(new ViewHTMLPostTeachers(), errors);
    }
}