package pt.isel.ls.View.Formatters.JSON.Users;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Entities.User;
import pt.isel.ls.View.Formatters.Formatter;
import pt.isel.ls.View.ViewTypes.JSON.*;
import pt.isel.ls.View.ViewTypes.Writable;

public class JSONUsersFormatter implements Formatter {

    /**
     * Get the JSON representation of a user.
     * @param entity The user to be represented in JSON form.
     * @return JSON form of the user.
     */
    @Override
    public Writable formatEntity(Entity entity) {
        /* Get the user. */
        User curr = (User)entity;

        /* Create the JSONObject with the user fields and its values. */
        JSONObject json = new JSONObject();
        json.withField(new JSONString("name", curr.getName()))
            .withField(new JSONString("email", curr.getEmail()))
            .withField(new JSONNumber("number", curr.getNumber()));
        return json;
    }


    /**
     * Get the JSON representation of users.
     * @param entities The users to be represented in JSON form.
     * @return JSON form of the users.
     */
    @Override
    public Writable formatEntities(CustomList<Entity> entities) {
        /* Create the JSONArray adding the JSONObjects that represents each teacher,
         * the JSONObject must the contain the user fields and its values. */
        JSONArray json = new JSONArray();
        for (int i = 0; i < entities.size(); i++) {
            json.addField((JSONValue)formatEntity(entities.get(i)));
        }
        return json;
    }
}