package pt.isel.ls.View.Formatters.PlainText.Classes;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Class;
import pt.isel.ls.Model.Entities.CourseClass;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.View.Formatters.Formatter;
import pt.isel.ls.View.ViewTypes.PlainText.TextElement;
import pt.isel.ls.View.ViewTypes.PlainText.TextTable;
import pt.isel.ls.View.ViewTypes.Writable;

public class PlainTextCoursesClassesFormatter implements Formatter{
    /**
     * Get the plain text representation of a class.
     * @param entity The class to be represented in plain text form.
     * @return plain text form of the class.
     */
    @Override
    public Writable formatEntity(Entity entity) {
        /* Get the class. */
        CourseClass cs = (CourseClass) entity;
        Class _class = new Class(cs.getId(), cs.getcName(), cs.getaYear(), cs.getaSemester());

        /* Get the class formatter. */
        PlainTextClassesFormatter classesFormatter = new PlainTextClassesFormatter();
        return classesFormatter.formatEntity(_class);
    }

    /**
     * Get the plain text representation of classes.
     * @param entities The classes to be represented in plain text form.
     * @return plain text form of the classes.
     */
    @Override
    public Writable formatEntities(CustomList<Entity> entities) {
        /* Create a table and add the text elements resultant of single class format. */
        TextTable textElements = new TextTable();
        for (int i = 0; i < entities.size(); i++) {
            textElements.addElement((TextElement)formatEntity(entities.get(i)));
        }
        return textElements;
    }
}