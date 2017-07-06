package pt.isel.ls.View.Formatters.HTMLFormatters.Users;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Entities.User;
import pt.isel.ls.View.Formatters.Formatter;
import pt.isel.ls.View.ViewTypes.Writable;
import pt.isel.ls.View.ViewTypes.html.HtmlElem;

import static pt.isel.ls.View.ViewTypes.html.Html.*;

public class HTMLUsersFormatter implements Formatter {

    /**
     * Get the HTML representation of a user.
     * @param entity The user to be represented in HTML form.
     * @return HTML form of the user.
     */
    @Override
    public Writable formatEntity(Entity entity) {
        /* Create HTML list. */
        HtmlElem list = new HtmlElem("ul");

        /* Get the user. */
        User user = (User) entity;

        /* Fill the list that will represent the user with its values. */
        list.with(li(text("User Number : " + user.getNumber())))
            .with(li(text("User Name : "  + user.getName())))
            .with(li(text("User Email : " + user.getEmail())));
        return list;
    }


    /**
     * Get the HTML representation of users.
     * @param entities The users to be represented in HTML form.
     * @return HTML form of the users.
     */
    @Override
    public Writable formatEntities(CustomList<Entity> entities) {
        /* Create the HTML table. */
        HtmlElem table = new HtmlElem("table");
        table.withAttr("Border","1");

        /* Define columns name and insert it to the users. */
        HtmlElem trHeaders = new HtmlElem("tr"), trData;
        trHeaders.with(th(text("User Number")))
                 .with(th(text("User Name")))
                 .with(th(text("User email")));
        table.with(trHeaders);

        int number;
        User user;

        /* For each user in the users list insert it to the table. */
        for (int i = 0; i < entities.size(); i++) {
            /* Get current user and its acronym. */
            user = (User)entities.get(i);
            number = user.getNumber();
            String type = user.getUserType();

            /* Create the table row that will contain the representation of the user. */
            trData = new HtmlElem("tr");
            trData.with(td(a("/" + type + "/" + number, Integer.toString(number))))
                  .with(td(text(user.getName())))
                  .with(td(text(user.getEmail())));

            /* Add the current row to the table. */
            table.with(trData);
        }
        return table;
    }
}