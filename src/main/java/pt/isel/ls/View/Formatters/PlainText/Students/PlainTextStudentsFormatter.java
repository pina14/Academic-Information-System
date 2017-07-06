package pt.isel.ls.View.Formatters.PlainText.Students;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Entities.Student;
import pt.isel.ls.View.Formatters.Formatter;
import pt.isel.ls.View.ViewTypes.PlainText.TextElement;
import pt.isel.ls.View.ViewTypes.PlainText.TextElementCreator;
import pt.isel.ls.View.ViewTypes.PlainText.TextTable;
import pt.isel.ls.View.ViewTypes.Writable;

public class PlainTextStudentsFormatter implements Formatter {

    /**
     * Get the plain text representation of a student.
     * @param entity The student to be represented in plain text form.
     * @return plain text form of the student.
     */
    @Override
    public Writable formatEntity(Entity entity) {
        /* Create a text element representing the student. */
        return new TextElementCreator<Student>()
                .withCol("Name", student -> student.getName())
                .withCol("Number", student -> String.valueOf(student.getNumber()))
                .withCol("Email", student -> student.getEmail())
                .withCol("Enrolled in Programme", student -> student.getPid())
                .build(entity);
    }

    /**
     * Get the plain text representation of students.
     * @param entities The students to be represented in plain text form.
     * @return plain text form of the students.
     */
    @Override
    public Writable formatEntities(CustomList<Entity> entities) {
        /* Create a table and add the text elements resultant of single student format. */
        TextTable textElements = new TextTable();
        for (int i = 0; i < entities.size(); i++) {
            textElements.addElement((TextElement) formatEntity(entities.get(i)));
        }
        return textElements;
    }
}