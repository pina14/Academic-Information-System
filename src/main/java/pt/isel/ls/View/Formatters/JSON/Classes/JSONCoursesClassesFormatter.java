package pt.isel.ls.View.Formatters.JSON.Classes;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Class;
import pt.isel.ls.Model.Entities.CourseClass;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.View.Formatters.Formatter;
import pt.isel.ls.View.ViewTypes.JSON.JSONArray;
import pt.isel.ls.View.ViewTypes.JSON.JSONValue;
import pt.isel.ls.View.ViewTypes.Writable;

public class JSONCoursesClassesFormatter implements Formatter {

    /**
     * Get the JSON representation of a class.
     * @param entity The class to be represented in JSON form.
     * @return JSON form of the class.
     */
    @Override
    public Writable formatEntity(Entity entity) {
        /* Get the class. */
        CourseClass cs = (CourseClass) entity;
        Class _class = new Class(cs.getId(), cs.getcName(), cs.getaYear(), cs.getaSemester());

        /* Get the class formatter. */
        JSONClassesFormatter classesFormatter = new JSONClassesFormatter();
        return classesFormatter.formatEntity(_class);
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