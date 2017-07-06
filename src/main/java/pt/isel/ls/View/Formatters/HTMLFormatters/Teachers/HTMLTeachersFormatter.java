package pt.isel.ls.View.Formatters.HTMLFormatters.Teachers;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Entities.Teacher;
import pt.isel.ls.View.Formatters.Formatter;
import pt.isel.ls.View.ViewTypes.Writable;
import pt.isel.ls.View.ViewTypes.html.HtmlElem;

import static pt.isel.ls.View.ViewTypes.html.Html.*;

public class HTMLTeachersFormatter implements Formatter {

    /**
     * Get the HTML representation of a teacher.
     * @param entity The teacher to be represented in HTML form.
     * @return HTML form of the teacher.
     */
    @Override
    public Writable formatEntity(Entity entity) {
        /* Create HTML list. */
        HtmlElem list = new HtmlElem("ul");

        /* Get the teacher. */
        Teacher teacher = (Teacher) entity;

        /* Fill the list that will represent the teacher with its values. */
        list.with(li(text("Teacher Number : " + teacher.getNumber())))
            .with(li(text("Teacher Name : "  + teacher.getName())))
            .with(li(text("Teacher Email : " + teacher.getEmail())));
        return list;
    }


    /**
     * Get the HTML representation of teachers.
     * @param entities The teachers to be represented in HTML form.
     * @return HTML form of the teachers.
     */
    @Override
    public Writable formatEntities(CustomList<Entity> entities) {
        /* Create the HTML table. */
        HtmlElem table = new HtmlElem("table");
        table.withAttr("Border","1");

        /* Define columns name and insert it to the teachers. */
        HtmlElem trHeaders = new HtmlElem("tr"), trData;
        trHeaders.with(th(text("Teacher Number")))
                 .with(th(text("Teacher Name")));
        table.with(trHeaders);

        int number;
        Teacher teacher;

        /* For each teacher in the teachers list insert it to the table. */
        for (int i = 0; i < entities.size(); i++) {
            /* Get current teacher and its number. */
            teacher = (Teacher) entities.get(i);
            number = teacher.getNumber();

            /* Create the table row that will contain the representation of the teacher. */
            trData = new HtmlElem("tr");
            trData.with(td(a("/teachers/"+ number, String.valueOf(number))))
                  .with(td(text(teacher.getName())));

            /* Add the current row to the table. */
            table.with(trData);
        }
        return table;
    }
}