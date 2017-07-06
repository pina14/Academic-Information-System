package pt.isel.ls.Model.Results.CourseManagementResults;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Course;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.CoursesManagementViews.GetCoursesAcr.ViewHTMLGetCoursesAcr;
import pt.isel.ls.View.CommandViews.CoursesManagementViews.GetCoursesAcr.ViewJSONGetCoursesAcr;
import pt.isel.ls.View.CommandViews.CoursesManagementViews.GetCoursesAcr.ViewPlainTextGetCoursesAcr;

public class GetCoursesAcrResult extends Result {
    private Course course;
    private CustomList<Entity> courseProgrammes;
    private String courseAcronym;

    public GetCoursesAcrResult(Course course, CustomList<Entity> courseProgrammes, String courseAcronym) {
        super(new ViewHTMLGetCoursesAcr(), new ViewPlainTextGetCoursesAcr(), new ViewJSONGetCoursesAcr());
        this.course = course;
        this.courseProgrammes = courseProgrammes;
        this.courseAcronym = courseAcronym;
    }

    public Course getCourse() {
        return course;
    }

    public CustomList<Entity> getCourseProgrammes() {
        return courseProgrammes;
    }

    public String getCourseAcronym() {
        return courseAcronym;
    }
}