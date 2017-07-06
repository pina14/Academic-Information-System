package pt.isel.ls.Model.Error;

public class CustomError {
    private String id, description;

    public CustomError(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }


}
