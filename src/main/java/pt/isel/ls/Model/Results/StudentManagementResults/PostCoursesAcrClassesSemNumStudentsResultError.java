package pt.isel.ls.Model.Results.StudentManagementResults;


import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Error.CustomError;
import pt.isel.ls.Model.Results.ResultError;
import pt.isel.ls.View.CommandViews.StudentsManagementView.PostCoursesAcrClassesSemNumStudents.ViewHTMLPostCoursesAcrClassesSemNumStudents;

public class PostCoursesAcrClassesSemNumStudentsResultError extends ResultError {
    private String acr, sem, num;
    private int year;
    private CustomList<Entity> studentsNotEnrolled;

    public PostCoursesAcrClassesSemNumStudentsResultError(CustomList<CustomError> errors, String acr, String sem, String num, Integer year, CustomList<Entity> studentsNotEnrolled) {
        super(new ViewHTMLPostCoursesAcrClassesSemNumStudents(),errors);
        this.acr = acr;
        this.sem = sem;
        this.num = num;
        this.year = year;
        this.studentsNotEnrolled = studentsNotEnrolled;
    }

    public String getAcr() {
        return acr;
    }

    public String getSem() {
        return sem;
    }

    public String getNum() {
        return num;
    }

    public int getYear() {
        return year;
    }

    public CustomList<Entity> getStudentsNotEnrolled() {
        return studentsNotEnrolled;
    }
}
