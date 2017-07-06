package pt.isel.ls.View.Formatters.JSON.Courses;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Course;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.View.Formatters.Formatter;
import pt.isel.ls.View.ViewTypes.JSON.*;
import pt.isel.ls.View.ViewTypes.Writable;

public class JSONCoursesFormatter implements Formatter {

    /**
     * Get the JSON representation of a course.
     * @param entity The course to be represented in JSON form.
     * @return JSON form of the course.
     */
    @Override
    public Writable formatEntity(Entity entity) {
        /* Get the course. */
        Course course = (Course) entity;

        /* Create the JSONObject with the course fields and its values. */
        JSONObject json = new JSONObject();
        json.withField(new JSONString("name", course.getName()))
            .withField(new JSONString("acronym", course.getAcronym()))
            .withField(new JSONNumber("tNumber", course.gettNumber()));
        return json;
    }


    /**
     * Get the JSON representation of courses.
     * @param entities The courses to be represented in JSON form.
     * @return JSON form of the courses.
     */
    @Override
    public Writable formatEntities(CustomList<Entity> entities) {
        /* Create the JSONArray adding the JSONObjects that represents each course,
         * the JSONObject must the contain the course fields and its values. */
        JSONArray json = new JSONArray();
        for (int i = 0; i < entities.size(); i++) {
            json.addField((JSONValue)formatEntity(entities.get(i)));
        }
        return json;
    }
}