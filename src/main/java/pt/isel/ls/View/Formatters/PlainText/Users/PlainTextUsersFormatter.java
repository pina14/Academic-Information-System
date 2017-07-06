package pt.isel.ls.View.Formatters.PlainText.Users;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Entities.User;
import pt.isel.ls.View.Formatters.Formatter;
import pt.isel.ls.View.ViewTypes.PlainText.TextElement;
import pt.isel.ls.View.ViewTypes.PlainText.TextElementCreator;
import pt.isel.ls.View.ViewTypes.PlainText.TextTable;
import pt.isel.ls.View.ViewTypes.Writable;

public class PlainTextUsersFormatter implements Formatter {

    /**
     * Get the plain text representation of a user.
     * @param entity The user to be represented in plain text form.
     * @return plain text form of the user.
     */
    @Override
    public Writable formatEntity(Entity entity) {
        /* Create a text element representing the user. */
        return new TextElementCreator<User>()
                .withCol("Name", user -> user.getName())
                .withCol("Number", user -> String.valueOf(user.getNumber()))
                .withCol("Email", user -> user.getEmail())
                .build(entity);
    }

    /**
     * Get the plain text representation of users.
     * @param entities The users to be represented in plain text form.
     * @return plain text form of the users.
     */
    @Override
    public Writable formatEntities(CustomList<Entity> entities) {
        /* Create a table and add the text elements resultant of single user format. */
        TextTable textElements = new TextTable();
        for (int i = 0; i < entities.size(); i++) {
            textElements.addElement((TextElement) formatEntity(entities.get(i)));
        }
        return textElements;
    }
}