package pt.isel.ls.View.Formatters.HTMLFormatters.Classes;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.CourseClass;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Utils;
import pt.isel.ls.View.Formatters.Formatter;
import pt.isel.ls.View.ViewTypes.Writable;
import pt.isel.ls.View.ViewTypes.html.HtmlElem;

import static pt.isel.ls.View.ViewTypes.html.Html.*;

public class HTMLCoursesClassesFormatter implements Formatter{


    /**
     * Get the HTML representation of a class.
     * @param entity The class to be represented in HTML form.
     * @return HTML form of the class.
     */
    @Override
    public Writable formatEntity(Entity entity) {
        /* Create the HTML list. */
        HtmlElem list = new HtmlElem("ul");

        /* Get the class. */
        CourseClass courseClass = (CourseClass) entity;

        /* Fill the list that will represent the class with its values. */
        list.with(li(text("Class ID: " + courseClass.getId())))
            .with(li(text("Course Name: " + courseClass.getcName())))
            .with(li(text("Academic Year: " + courseClass.getaYear())))
            .with(li(text("Academic Semester: " + courseClass.getaSemester())));
        return list;
    }


    /**
     * Get the HTML representation of classes from different courses.
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
        HtmlElem trData;

        /* Create classes element. */
        CourseClass courseClass;
        String id, sem, acr;
        int year;

        /* For each class in the classes list insert it to the table. */
        for (int i = 0; i < entities.size(); i++) {
            /* Get current class. */
            courseClass = (CourseClass) entities.get(i);
            trData = new HtmlElem("tr");

            /* Get class information. */
            id = courseClass.getId();
            sem = courseClass.getaSemester();
            year = courseClass.getaYear();
            acr = courseClass.getCourseAcr();
            String semYear = String.valueOf(year) + Utils.semMap.get(sem);

            /* Create the table row that will contain the representation of the class. */
            trData.with(td(a("/courses/" + acr + "/classes/"+semYear+"/"+id, id)))
                    .with(td(text(courseClass.getcName())))
                    .with(td(a("/courses/"+acr+"/classes/"+semYear, String.valueOf(year))))
                    .with(td(a("/courses/"+acr+"/classes/"+semYear,sem)));

            /* Add the current row to the table. */
            table.with(trData);
        }
        return table;
    }
}