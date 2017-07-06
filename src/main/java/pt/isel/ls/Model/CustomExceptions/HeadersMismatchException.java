package pt.isel.ls.Model.CustomExceptions;

public class HeadersMismatchException extends Exception {
    public HeadersMismatchException(){
        super("Invalid headers format.\n\t-Format: header:header_value|...");
    }

    public HeadersMismatchException(String message){
        super(message);
    }
}