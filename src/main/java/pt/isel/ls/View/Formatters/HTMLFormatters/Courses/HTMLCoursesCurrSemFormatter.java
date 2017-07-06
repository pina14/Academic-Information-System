package pt.isel.ls.View.Formatters.HTMLFormatters.Courses;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.CourseSemCurr;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.View.Formatters.Formatter;
import pt.isel.ls.View.ViewTypes.Writable;
import pt.isel.ls.View.ViewTypes.html.HtmlElem;

import static pt.isel.ls.View.ViewTypes.html.Html.*;

public class HTMLCoursesCurrSemFormatter implements Formatter{


    /**
     * Get the HTML representation of a course with the current semester.
     * @param entity The course with the current semester to be represented in HTML form.
     * @return HTML form of the course with the current semester.
     */
    @Override
    public Writable formatEntity(Entity entity) {
        /* Create HTML list. */
        HtmlElem list = new HtmlElem("ul");
        /* Get the course and the course's teacher number. */
        CourseSemCurr courseSemCurr =  (CourseSemCurr) entity;
        int teacherNumber = courseSemCurr.gettNumber();
        /* Fill the list that will represent the course with its values. */
        list.with(li(text("Acronym : " + courseSemCurr.getAcronym())))
            .with(li(text("Name : "  + courseSemCurr.getName())))
            .with(aWithText("/teachers/" + teacherNumber, teacherNumber + "", "Teacher Number : " ))
            .with(td(intText(courseSemCurr.getCurrSem())));
        return list;
    }


    /**
     * Get the HTML representation of courses with the current semester.
     * @param entities The courses with the current semester to be represented in HTML form.
     * @return HTML form of the courses with the current semester.
     */
    @Override
    public Writable formatEntities(CustomList<Entity> entities) {
        /* Create the HTML table. */
        HtmlElem table = new HtmlElem("table");
        table.withAttr("Border","1");

        /* Define columns name and insert it to the courses table. */
        HtmlElem trHeaders = new HtmlElem("tr"), trData;
        trHeaders.with(th(text("Acronym")))
                 .with(th(text("Name")))
                 .with(th(text("Curricular Semester")));
        table.with(trHeaders);

        CourseSemCurr courseSemCurr;
        /* For each course in the courses list insert it to the table. */
        for (int i = 0; i < entities.size(); i++) {
            /* Get current course. */
            courseSemCurr = (CourseSemCurr) entities.get(i);

            /* Create the table row that will contain the representation of the course. */
            trData = new HtmlElem("tr");
            trData.with(td(a("/courses/" + courseSemCurr.getAcronym(), courseSemCurr.getAcronym())))
                  .with(td(text(courseSemCurr.getName())))
                  .with(td(intText(courseSemCurr.getCurrSem())));
            /* Add the current row to the table. */
            table.with(trData);
        }
        return table;
    }
}