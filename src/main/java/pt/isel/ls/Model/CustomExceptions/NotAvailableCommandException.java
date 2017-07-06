package pt.isel.ls.Model.CustomExceptions;

public class NotAvailableCommandException extends Exception {
    public NotAvailableCommandException(){
        super("Invalid Command. Use \"OPTION /\" to see the commands available.");
    }
}