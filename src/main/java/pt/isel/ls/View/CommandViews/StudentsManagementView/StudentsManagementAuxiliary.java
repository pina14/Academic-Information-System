package pt.isel.ls.View.CommandViews.StudentsManagementView;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Entities.Student;
import pt.isel.ls.View.ViewTypes.Writable;

import static pt.isel.ls.View.ViewTypes.html.Html.checkBox;
import static pt.isel.ls.View.ViewTypes.html.Html.li;

public class StudentsManagementAuxiliary {
    /**
     * Get the HTML checkBox form containing the students received in parameters.
     * @param studentsNotEnrolledList CustomList<Entity> that represents the students.
     * @return Writable object that will contain its form of representation in HTML.
     */
    public static Writable[] getCheckboxes(CustomList<Entity> studentsNotEnrolledList) {
        /* Initiate variables.*/
        Writable[] checkboxes = new Writable[studentsNotEnrolledList.size()];
        Student student;
        int numStu;

        /* For each student not enrolled, create a checkbox and store it. */
        for (int i = 0; i < studentsNotEnrolledList.size(); i++) {
            student = (Student) studentsNotEnrolledList.get(i);
            numStu = student.getNumber();
            checkboxes[i] = li(checkBox(String.valueOf(numStu), "checkbox", "Student " + numStu, "numStu"));
        }

        return checkboxes;
    }
}