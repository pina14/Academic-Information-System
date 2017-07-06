package pt.isel.ls.View.Formatters.HTMLFormatters.Classes;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Class;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Utils;
import pt.isel.ls.View.Formatters.Formatter;
import pt.isel.ls.View.ViewTypes.Writable;
import pt.isel.ls.View.ViewTypes.html.HtmlElem;

import static pt.isel.ls.View.ViewTypes.html.Html.*;

public class HTMLClassesFormatter implements Formatter{
    private String acr;

    /**
     * Sets the classes acronym with a the acr value.
     * @param acr String that represented the acronym.
     */
    public void setAcr(String acr) {
        this.acr = acr;
    }

    /**
     * Get the HTML representation of a class.
     * @param entity The class to be represented in HTML form.
     * @return HTML form of the class.
     */
    @Override
    public Writable formatEntity(Entity entity) {
        /* Create HTML list. */
        HtmlElem list = new HtmlElem("ul");
        /* Get the class. */
        Class _Class = (Class) entity;
        /* Fill the list that will represent the class with its values. */
        list.with(li(text("Class ID: " + _Class.getId())))
            .with(li(text("Course Name: " + _Class.getcName())))
            .with(li(text("Academic Year: " + _Class.getaYear())))
            .with(li(text("Academic Semester: " + _Class.getaSemester())));
        return list;
    }

    /**
     * Get the HTML representation of classes.
     * @param entities The classes to be represented in HTML form.
     * @return HTML form of the classes.
     */
    @Override
    public Writable formatEntities(CustomList<Entity> entities) {
        /* Create the HTML table. */
        HtmlElem table = new HtmlElem("table");
        table.withAttr("Border","1");

        /* Define columns name and insert it to the classes. */
        HtmlElem trHeaders = new HtmlElem("tr");
        trHeaders.with(th(text("Class ID")))
                 .with(th(text("Course Name")))
                 .with(th(text("Academic Year")))
                 .with(th(text("Academic Semester")));
        table.with(trHeaders);

        /* For each class in the classes list insert it to the table. */
        HtmlElem trData;
        for (int i = 0; i < entities.size(); i++) {
            /* Get current class. */
            Class _class = (Class) entities.get(i);

            /* Create the table row that will contain the representation of the class. */
            trData = new HtmlElem("tr");
            String semYear = String.valueOf(_class.getaYear()) + Utils.semMap.get(_class.getaSemester());
            trData.with(td(a("/courses/" + acr + "/classes/" + semYear + "/" + _class.getId(), _class.getId())))
                    .with(td(text(_class.getcName())))
                    .with(td(a("/courses/" + acr + "/classes/" + semYear, String.valueOf(_class.getaYear()))))
                    .with(td(a("/courses/" + acr + "/classes/" + semYear, _class.getaSemester())));

            /* Add the current row to the table. */
            table.with(trData);
        }
        return table;
    }
}