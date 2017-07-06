package pt.isel.ls.View.Formatters.JSON.Courses;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.CourseSemCurr;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.View.Formatters.Formatter;
import pt.isel.ls.View.ViewTypes.JSON.*;
import pt.isel.ls.View.ViewTypes.Writable;

public class JSONCoursesSemCurrFormatter implements Formatter {

    /**
     * Get the JSON representation of a course with current semester.
     * @param entity The course with current semester to be represented in JSON form.
     * @return JSON form of the course with current semester.
     */
    @Override
    public Writable formatEntity(Entity entity) {
        /* Get the course with current semester. */
        CourseSemCurr courseWithSemester = (CourseSemCurr) entity;

        /* Create the JSONObject with the course with current semester fields and its values. */
        JSONObject json = new JSONObject();
        json.withField(new JSONString("name", courseWithSemester.getName()))
            .withField(new JSONString("acronym", courseWithSemester.getAcronym()))
            .withField(new JSONNumber("tNumber", courseWithSemester.gettNumber()))
            .withField(new JSONNumber("semester", courseWithSemester.getCurrSem()));
        return json;
    }

    /**
     * Get the JSON representation of courses with current semester.
     * @param entities The courses with current semester to be represented in JSON form.
     * @return JSON form of the courses with current semester.
     */
    @Override
    public Writable formatEntities(CustomList<Entity> entities) {
        /* Create the JSONArray adding the JSONObjects that represents each course with current semester,
         * the JSONObject must the contain the course fields and its values. */
        JSONArray json = new JSONArray();
        for (int i = 0; i < entities.size(); i++) {
            json.addField((JSONValue)formatEntity(entities.get(i)));
        }
        return json;
    }
}