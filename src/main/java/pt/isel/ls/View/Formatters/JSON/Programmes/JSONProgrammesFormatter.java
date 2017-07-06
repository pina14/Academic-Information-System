package pt.isel.ls.View.Formatters.JSON.Programmes;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Entities.Programme;
import pt.isel.ls.View.Formatters.Formatter;
import pt.isel.ls.View.ViewTypes.JSON.*;
import pt.isel.ls.View.ViewTypes.Writable;

public class JSONProgrammesFormatter implements Formatter {

    /**
     * Get the JSON representation of a programme.
     * @param entity The programme to be represented in JSON form.
     * @return JSON form of the programme.
     */
    @Override
    public Writable formatEntity(Entity entity) {
        /* Get the programme. */
        Programme programme = (Programme) entity;

        /* Create the JSONObject with the programme fields and its values. */
        JSONObject json = new JSONObject();
        json.withField(new JSONString("name", programme.getName()))
            .withField(new JSONString("acronym", programme.getAcronym()))
            .withField(new JSONNumber("numSemester", programme.getNumSemester()));
        return json;
    }

    /**
     * Get the JSON representation of programmes.
     * @param entities The programmes to be represented in JSON form.
     * @return JSON form of the programmes.
     */
    @Override
    public Writable formatEntities(CustomList<Entity> entities) {
        /* Create the JSONArray adding the JSONObjects that represents each programme,
         * the JSONObject must the contain the programme fields and its values. */
        JSONArray json = new JSONArray();
        for (int i = 0; i < entities.size(); i++) {
            json.addField((JSONValue)formatEntity(entities.get(i)));
        }
        return json;
    }
}