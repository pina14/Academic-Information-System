package pt.isel.ls.Model.Results;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Error.CustomError;
import pt.isel.ls.View.CommandViews.DefaultPostView;
import pt.isel.ls.View.CommandViews.View;

public class ResultError extends Result {
    protected CustomList<CustomError> errors;

    public ResultError(View htmlView, CustomList<CustomError> errors) {
        super(htmlView, new DefaultPostView(), null);
        this.errors = errors;
    }

    public CustomList<CustomError> getErrors() {
        return errors;
    }

    /**
     * To the given error ID get its description.
     * @param key Error ID.
     * @return The Error with ID key description.
     */
    public String getErrorDescription(String key){
        /* Iterate over all errors if it finds the error with ID key returns it description. */
        CustomError curr;
        for (int i = 0; i < errors.size(); i++) {
            curr = errors.get(i);
            if(curr.getId().equals(key))
                return curr.getDescription();
        }
        return "";
    }

    /**
     * To the given error ID get its color, if the error exist return RED color otherwise BLACK.
     * @param key  Error ID.
     * @return The color associated to that ID.
     */
    public String getColor(String key){
        /* Iterate over all errors if it finds the error with ID key returns RED otherwise returns BLACK. */
        String red = "color:red;", black = "color:black;";
        for (int i = 0; i < errors.size(); i++) {
            if(errors.get(i).getId().equals(key))
                return red;
        }
        return black;
    }

}