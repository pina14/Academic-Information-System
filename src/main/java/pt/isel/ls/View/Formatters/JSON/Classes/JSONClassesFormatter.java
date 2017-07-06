package pt.isel.ls.View.Formatters.JSON.Classes;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Class;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.View.Formatters.Formatter;
import pt.isel.ls.View.ViewTypes.JSON.*;
import pt.isel.ls.View.ViewTypes.Writable;

public class JSONClassesFormatter implements Formatter {

    /**
     * Get the JSON representation of a class.
     * @param entity The class to be represented in JSON form.
     * @return JSON form of the class.
     */
    @Override
    public Writable formatEntity(Entity entity) {
        /* Get the class. */
        Class _Class = (Class) entity;

        /* Create the JSONObject with the class fields and its values. */
        JSONObject json = new JSONObject();
        json.withField(new JSONString("cName", _Class.getcName()))
            .withField(new JSONString("id", _Class.getId()))
            .withField(new JSONString("aSemester", _Class.getaSemester()))
            .withField(new JSONNumber("tNumber", _Class.getaYear()));
        return json;
    }


    /**
     * Get the JSON representation of classes.
     * @param entities The classes to be represented in JSON form.
     * @return JSON form of the classes.
     */
    @Override
    public Writable formatEntities(CustomList<Entity> entities) {
        /* Create the JSONArray adding the JSONObjects that represents each class,
         * the JSONObject must the contain the class fields and its values. */
        JSONArray json = new JSONArray();
        for (int i = 0; i < entities.size(); i++) {
            json.addField((JSONValue) formatEntity(entities.get(i)));
        }
        return json;
    }
}