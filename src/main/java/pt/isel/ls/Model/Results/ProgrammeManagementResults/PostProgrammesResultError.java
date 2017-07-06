package pt.isel.ls.Model.Results.ProgrammeManagementResults;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Error.CustomError;
import pt.isel.ls.Model.Results.ResultError;
import pt.isel.ls.View.CommandViews.ProgrammesManagementViews.PostProgrammes.ViewHTMLPostProgrammes;

public class PostProgrammesResultError extends ResultError {
    public PostProgrammesResultError(CustomList<CustomError> errors) {
        super(new ViewHTMLPostProgrammes(), errors);
    }
}