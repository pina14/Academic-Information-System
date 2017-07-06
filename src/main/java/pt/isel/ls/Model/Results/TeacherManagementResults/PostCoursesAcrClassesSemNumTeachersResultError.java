package pt.isel.ls.Model.Results.TeacherManagementResults;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Error.CustomError;
import pt.isel.ls.Model.Results.ResultError;
import pt.isel.ls.View.CommandViews.TeachersManagementViews.PostCoursesAcrClassesSemNumTeachers.ViewHTMLPostCoursesAcrClassesSemNumTeachers;

public class PostCoursesAcrClassesSemNumTeachersResultError extends ResultError {
    private String acr, sem, num;
    private Integer year;

    public PostCoursesAcrClassesSemNumTeachersResultError(CustomList<CustomError> errors, String acr, String sem, String num, Integer year) {
        super(new ViewHTMLPostCoursesAcrClassesSemNumTeachers(), errors);
        this.acr = acr;
        this.sem = sem;
        this.num = num;
        this.year = year;
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

    public Integer getYear() {
        return year;
    }
}