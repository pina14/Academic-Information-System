package pt.isel.ls.Model.Results.UserManagementResults;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Entities.Student;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.UsersManagementViews.GetStudentsNum.ViewHTMLGetStudentsNum;
import pt.isel.ls.View.CommandViews.UsersManagementViews.GetStudentsNum.ViewJSONGetStudentsNum;
import pt.isel.ls.View.CommandViews.UsersManagementViews.GetStudentsNum.ViewPlainTextGetStudentsNum;

public class GetStudentsNumResult extends Result {
    private Student student;
    private CustomList<Entity> coursesClasses;

    public GetStudentsNumResult(Student student, CustomList<Entity> coursesClasses) {
        super(new ViewHTMLGetStudentsNum(), new ViewPlainTextGetStudentsNum(), new ViewJSONGetStudentsNum());
        this.student = student;
        this.coursesClasses = coursesClasses;
    }

    public Student getStudent() {
        return student;
    }

    public CustomList<Entity> getCoursesClasses() {
        return coursesClasses;
    }
}