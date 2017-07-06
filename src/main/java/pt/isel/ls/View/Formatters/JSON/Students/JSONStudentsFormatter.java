package pt.isel.ls.View.Formatters.JSON.Students;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Entities.Student;
import pt.isel.ls.View.Formatters.Formatter;
import pt.isel.ls.View.ViewTypes.JSON.*;
import pt.isel.ls.View.ViewTypes.Writable;

public class JSONStudentsFormatter implements Formatter {

    /**
     * Get the JSON representation of a student.
     * @param entity The student to be represented in JSON form.
     * @return JSON form of the student.
     */
    @Override
    public Writable formatEntity(Entity entity) {
        /* Get the student. */
        Student student = (Student) entity;

        /* Create the JSONObject with the student fields and its values. */
        JSONObject json = new JSONObject();
        json.withField(new JSONString("name", student.getName()))
            .withField(new JSONString("email", student.getEmail()))
            .withField(new JSONNumber("number", student.getNumber()))
            .withField(new JSONString("pid", student.getPid()));
        return json;
    }

    /**
     * Get the JSON representation of students.
     * @param entities The students to be represented in JSON form.
     * @return JSON form of the students.
     */
    @Override
    public Writable formatEntities(CustomList<Entity> entities) {
        /* Create the JSONArray adding the JSONObjects that represents each student,
         * the JSONObject must the contain the student fields and its values. */
        JSONArray json = new JSONArray();
        for (int i = 0; i < entities.size(); i++) {
            json.addField((JSONValue)formatEntity(entities.get(i)));
        }
        return json;
    }
}