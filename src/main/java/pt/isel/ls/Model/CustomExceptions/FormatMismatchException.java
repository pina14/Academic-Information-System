package pt.isel.ls.Model.CustomExceptions;

public class FormatMismatchException extends Exception{
    public FormatMismatchException(String component) {
        super(component + " it's not in the correct format.\n\t-Format: {method} {path} {headers(optional)} {parameters(mandatory in PUT and POST command)}");
    }
}