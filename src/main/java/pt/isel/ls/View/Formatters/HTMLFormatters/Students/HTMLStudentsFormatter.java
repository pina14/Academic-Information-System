package pt.isel.ls.View.Formatters.HTMLFormatters.Students;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Entities.Student;
import pt.isel.ls.View.Formatters.Formatter;
import pt.isel.ls.View.ViewTypes.Writable;
import pt.isel.ls.View.ViewTypes.html.HtmlElem;

import static pt.isel.ls.View.ViewTypes.html.Html.*;

public class HTMLStudentsFormatter implements Formatter {

    /**
     * Get the HTML representation of a student.
     * @param entity The student to be represented in HTML form.
     * @return HTML form of the student.
     */
    @Override
    public Writable formatEntity(Entity entity) {
        /* Create HTML list. */
        HtmlElem list = new HtmlElem("ul");

        /* Get the student and its programme ID. */
        Student student = (Student) entity;
        String pid = student.getPid();

        /* Fill the list that will represent the student with its values. */
        list.with(li(text("Student Number : " + student.getNumber())))
            .with(li(text("Student Name : "  + student.getName())))
            .with(li(text("Student Email : " + student.getEmail())))
            .with(aWithText("/programmes/" + pid, pid, "Programme PID : "));
        return list;
    }

    /**
     * Get the HTML representation of students.
     * @param entities The students to be represented in HTML form.
     * @return HTML form of the students.
     */
    @Override
    public Writable formatEntities(CustomList<Entity> entities) {
        /* Create the HTML table. */
        HtmlElem table = new HtmlElem("table");
        table.withAttr("Border","1");

        /* Define columns name and insert it to the students. */
        HtmlElem trHeaders = new HtmlElem("tr"), trData;
        trHeaders.with(th(text("Student Number")))
                 .with(th(text("Student Name")));
        table.with(trHeaders);

        int number;
        Student student;

        /* For each student in the students list insert it to the table. */
        for (int i = 0; i < entities.size(); i++) {
            /* Get current student and its number. */
            student = (Student) entities.get(i);
            number = student.getNumber();

            /* Create the table row that will contain the representation of the student. */
            trData = new HtmlElem("tr");
            trData.with(td(a("/students/"+ number, Integer.toString(number))))
                  .with(td(text(student.getName())));

            /* Add the current row to the table. */
            table.with(trData);
        }
        return table;
    }
}