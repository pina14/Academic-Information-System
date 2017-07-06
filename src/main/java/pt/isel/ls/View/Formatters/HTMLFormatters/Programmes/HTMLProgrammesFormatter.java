package pt.isel.ls.View.Formatters.HTMLFormatters.Programmes;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Entities.Programme;
import pt.isel.ls.View.Formatters.Formatter;
import pt.isel.ls.View.ViewTypes.Writable;
import pt.isel.ls.View.ViewTypes.html.HtmlElem;

import static pt.isel.ls.View.ViewTypes.html.Html.*;

public class HTMLProgrammesFormatter implements Formatter {


    /**
     * Get the HTML representation of a programme.
     * @param entity The programme to be represented in HTML form.
     * @return HTML form of the programme.
     */
    @Override
    public Writable formatEntity(Entity entity) {
        /* Create HTML list. */
        HtmlElem list = new HtmlElem("ul");
        /* Get the programme. */
        Programme programme = (Programme)entity;
        /* Fill the list that will represent the programme with its values. */
        list.with(li(text("Acronym: " + programme.getAcronym())))
            .with(li(text("Name: " + programme.getName())))
            .with(li(text("Number of Semesters: " + programme.getNumSemester())));

        return list;
    }


    /**
     * Get the HTML representation of programmes.
     * @param entities The programmes to be represented in HTML form.
     * @return HTML form of the programmes.
     */
    @Override
    public Writable formatEntities(CustomList<Entity> entities) {
        /* Create the HTML table. */
        HtmlElem table = new HtmlElem("table");
        table.withAttr("Border","1");

        /* Define columns name and insert it to the programmes. */
        HtmlElem trHeaders = new HtmlElem("tr"), trData;
        trHeaders.with(th(text("Acronym")))
                .with(th(text("Name")));
        table.with(trHeaders);

        /* For each programme in the programmes list insert it to the table. */
        for (int i = 0; i < entities.size(); i++) {
            /* Get current programme and its acronym. */
            Programme programme = (Programme) entities.get(i);
            String acr = programme.getAcronym();

            /* Create the table row that will contain the representation of the programme. */
            trData = new HtmlElem("tr");
            trData.with(td(a("/programmes/" + acr, acr)))
                  .with(td(text(programme.getName())));
            /* Add the current row to the table. */
            table.with(trData);
        }
        return table;
    }
}