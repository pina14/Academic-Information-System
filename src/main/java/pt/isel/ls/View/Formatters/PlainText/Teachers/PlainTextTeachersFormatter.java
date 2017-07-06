package pt.isel.ls.View.Formatters.PlainText.Teachers;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Entities.Teacher;
import pt.isel.ls.View.Formatters.Formatter;
import pt.isel.ls.View.ViewTypes.PlainText.TextElement;
import pt.isel.ls.View.ViewTypes.PlainText.TextElementCreator;
import pt.isel.ls.View.ViewTypes.PlainText.TextTable;
import pt.isel.ls.View.ViewTypes.Writable;

public class PlainTextTeachersFormatter implements Formatter {

    /**
     * Get the plain text representation of a teacher.
     * @param entity The teacher to be represented in plain text form.
     * @return plain text form of the teacher.
     */
    @Override
    public Writable formatEntity(Entity entity) {
        /* Create a text element representing the teacher. */
        return new TextElementCreator<Teacher>()
                .withCol("Name", teacher -> teacher.getName())
                .withCol("Number", teacher -> String.valueOf(teacher.getNumber()))
                .withCol("Email", teacher -> teacher.getEmail())
                .build(entity);
    }

    /**
     * Get the plain text representation of teachers.
     * @param entities The teachers to be represented in plain text form.
     * @return plain text form of the teachers.
     */
    @Override
    public Writable formatEntities(CustomList<Entity> entities) {
        /* Create a table and add the text elements resultant of single teacher format. */
        TextTable textElements = new TextTable();
        for (int i = 0; i < entities.size(); i++) {
            textElements.addElement((TextElement) formatEntity(entities.get(i)));
        }
        return textElements;
    }
}