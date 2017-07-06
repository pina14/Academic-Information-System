package pt.isel.ls.View.Formatters.JSON.Teachers;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Entities.Teacher;
import pt.isel.ls.View.Formatters.Formatter;
import pt.isel.ls.View.ViewTypes.JSON.*;
import pt.isel.ls.View.ViewTypes.Writable;

public class JSONTeachersFormatter implements Formatter {

    /**
     * Get the JSON representation of a teacher.
     * @param entity The teacher to be represented in JSON form.
     * @return JSON form of the teacher.
     */
    @Override
    public Writable formatEntity(Entity entity) {
        /* Get the teacher. */
        Teacher teacher = (Teacher) entity;

        /* Create the JSONObject with the teacher fields and its values. */
        JSONObject json = new JSONObject();
        json.withField(new JSONString("name", teacher.getName()))
            .withField(new JSONString("email", teacher.getEmail()))
            .withField(new JSONNumber("number", teacher.getNumber()));
        return json;
    }


    /**
     * Get the JSON representation of teachers.
     * @param entities The teachers to be represented in JSON form.
     * @return JSON form of the teachers.
     */
    @Override
    public Writable formatEntities(CustomList<Entity> entities) {
        /* Create the JSONArray adding the JSONObjects that represents each teacher,
         * the JSONObject must the contain the teacher fields and its values. */
        JSONArray json = new JSONArray();
        for (int i = 0; i < entities.size(); i++) {
            json.addField((JSONValue)formatEntity(entities.get(i)));
        }
        return json;
    }
}