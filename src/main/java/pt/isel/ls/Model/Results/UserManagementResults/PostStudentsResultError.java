package pt.isel.ls.Model.Results.UserManagementResults;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Error.CustomError;
import pt.isel.ls.Model.Results.ResultError;
import pt.isel.ls.View.CommandViews.UsersManagementViews.PostStudents.ViewHTMLPostStudents;

public class PostStudentsResultError extends ResultError {
    public PostStudentsResultError(CustomList<CustomError> errors) {
        super(new ViewHTMLPostStudents(), errors);
    }
}