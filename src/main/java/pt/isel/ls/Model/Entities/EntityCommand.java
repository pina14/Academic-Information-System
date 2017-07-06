package pt.isel.ls.Model.Entities;

public class EntityCommand implements Entity {
    private final String TEMPLATE;
    private final String DESCRIPTION;

    public String getTEMPLATE() {
        return TEMPLATE;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public EntityCommand(String template, String description){
        TEMPLATE = template;
        DESCRIPTION = description;
    }
}