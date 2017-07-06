package pt.isel.ls.View.Formatters.PlainText.Programmes;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Entities.Programme;
import pt.isel.ls.View.Formatters.Formatter;
import pt.isel.ls.View.ViewTypes.PlainText.TextElement;
import pt.isel.ls.View.ViewTypes.PlainText.TextElementCreator;
import pt.isel.ls.View.ViewTypes.PlainText.TextTable;
import pt.isel.ls.View.ViewTypes.Writable;

public class PlainTextProgrammesFormatter implements Formatter {

    /**
     * Get the plain text representation of a programme.
     * @param entity The programme to be represented in plain text form.
     * @return plain text form of the programme.
     */
    @Override
    public Writable formatEntity(Entity entity) {
        /* Create a text element representing the programme. */
        return new TextElementCreator<Programme>()
                .withCol("Name", programme -> programme.getName())
                .withCol("Acronym", programme -> programme.getAcronym())
                .withCol("Semester Number", programme -> String.valueOf(programme.getNumSemester()))
                .build(entity);
    }

    /**
     * Get the plain text representation of programmes.
     * @param entities The programmes to be represented in plain text form.
     * @return plain text form of the programmes.
     */
    @Override
    public Writable formatEntities(CustomList<Entity> entities) {
        /* Create a table and add the text elements resultant of single programme format. */
        TextTable textElements = new TextTable();
        for (int i = 0; i < entities.size(); i++) {
            textElements.addElement((TextElement)formatEntity(entities.get(i)));
        }
        return textElements;
    }
}