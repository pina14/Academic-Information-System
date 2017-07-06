package pt.isel.ls.Model.Validators;


import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Error.CustomError;

public class ErrorValidator {

    private CustomList<CustomError> errors = new CustomList<>();

    public void addError(String ID, String description){
        errors.add(new CustomError(ID, description));
    }

    public boolean hasError() {
        return errors.size() > 0;
    }

    public CustomList<CustomError> getErrors() {
        return errors;
    }
}
