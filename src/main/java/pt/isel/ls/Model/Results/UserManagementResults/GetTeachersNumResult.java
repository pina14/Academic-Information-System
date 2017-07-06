package pt.isel.ls.Model.Results.UserManagementResults;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Entities.Teacher;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.UsersManagementViews.GetTeachersNum.ViewHTMLGetTeachersNum;
import pt.isel.ls.View.CommandViews.UsersManagementViews.GetTeachersNum.ViewJSONGetTeachersNum;
import pt.isel.ls.View.CommandViews.UsersManagementViews.GetTeachersNum.ViewPlainTextGetTeachersNum;

public class GetTeachersNumResult extends Result {
    private Teacher teacher;
    private CustomList<Entity> coursesClasses, courses;

    public GetTeachersNumResult(Teacher teacher, CustomList<Entity> coursesClasses, CustomList<Entity> courses) {
        super(new ViewHTMLGetTeachersNum(), new ViewPlainTextGetTeachersNum(), new ViewJSONGetTeachersNum());
        this.teacher = teacher;
        this.coursesClasses = coursesClasses;
        this.courses = courses;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public CustomList<Entity> getCoursesClasses() {
        return coursesClasses;
    }

    public CustomList<Entity> getCourses() {
        return courses;
    }
}