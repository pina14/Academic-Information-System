package pt.isel.ls.View.Formatters.PlainText.Courses;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Course;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.View.Formatters.Formatter;
import pt.isel.ls.View.ViewTypes.PlainText.TextElement;
import pt.isel.ls.View.ViewTypes.PlainText.TextElementCreator;
import pt.isel.ls.View.ViewTypes.PlainText.TextTable;
import pt.isel.ls.View.ViewTypes.Writable;

public class PlainTextCoursesFormatter implements Formatter {
    /**
     * Get the plain text representation of a course.
     * @param entity The course to be represented in plain text form.
     * @return plain text form of the course.
     */
    @Override
    public Writable formatEntity(Entity entity) {
        /* Create a text element representing the course. */
        return new TextElementCreator<Course>()
                .withCol("Name", course -> course.getName())
                .withCol("Acronym", course -> course.getAcronym())
                .withCol("Teacher Number", course -> String.valueOf(course.gettNumber()))
                .build(entity);
    }

    /**
     * Get the plain text representation of courses.
     * @param entities The courses to be represented in plain text form.
     * @return plain text form of the courses.
     */
    @Override
    public Writable formatEntities(CustomList<Entity> entities) {
        /* Create a table and add the text elements resultant of single course format. */
        TextTable textElements = new TextTable();
        for (int i = 0; i < entities.size(); i++) {
            textElements.addElement((TextElement)formatEntity(entities.get(i)));
        }
        return textElements;
    }
}