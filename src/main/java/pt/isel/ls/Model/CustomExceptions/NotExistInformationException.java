package pt.isel.ls.Model.CustomExceptions;

public class NotExistInformationException extends Exception {
    public NotExistInformationException(){
        super("Doesn't exist Information.");
    }

    public NotExistInformationException(String s){
        super(s);
    }
}