package pt.isel.ls.View.Formatters.HTMLFormatters.Courses;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Course;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.View.Formatters.Formatter;
import pt.isel.ls.View.ViewTypes.Writable;
import pt.isel.ls.View.ViewTypes.html.HtmlElem;

import static pt.isel.ls.View.ViewTypes.html.Html.*;

public class HTMLCoursesFormatter implements Formatter {

    /**
     * Get the HTML representation of a course.
     * @param entity The course to be represented in HTML form.
     * @return HTML form of the course.
     */
    @Override
    public Writable formatEntity(Entity entity) {
        /* Create HTML list. */
        HtmlElem list = new HtmlElem("ul");
        /* Get the course and the course's teacher number. */
        Course course = (Course) entity;
        int teacherNumber = course.gettNumber();
        /* Fill the list that will represent the course with its values. */
        list.with(li(text("Acronym : " + course.getAcronym())))
            .with(li(text("Name : "  + course.getName())))
            .with(aWithText("/teachers/" + teacherNumber, teacherNumber+"", "Teacher Number : " ));
        return list;
    }


    /**
     * Get the HTML representation of courses.
     * @param entities The courses to be represented in HTML form.
     * @return HTML form of the courses.
     */
    @Override
    public Writable formatEntities(CustomList<Entity> entities) {
        /* Create the HTML table. */
        HtmlElem table =  new HtmlElem("table");
        table.withAttr("Border","1");

        /* Define columns name and insert it to the courses table. */
        HtmlElem trHeaders = new HtmlElem("tr"), trData;
        trHeaders.with(th(text("Acronym")))
                 .with(th(text("Name")));
        table.with(trHeaders);

        Course course;
        /* For each course in the courses list insert it to the table. */
        for (int i = 0; i < entities.size(); i++) {
            /* Get current course. */
            course = (Course) entities.get(i);

            /* Create the table row that will contain the representation of the course. */
            trData = new HtmlElem("tr");
            trData.with(td(a("/courses/" + course.getAcronym(), course.getAcronym())))
                  .with(td(text(course.getName())));

            /* Add the current row to the table. */
            table.with(trData);
        }
        return table;
    }
}