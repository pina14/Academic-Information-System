package pt.isel.ls.Model.Entities;

public class AcademicSemester implements Entity{
    private int academicYear;
    private String semesterTime;

    public AcademicSemester(int academicYear, String semesterTime){
        this.academicYear = academicYear;
        this.semesterTime = semesterTime;
    }

    public int getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(int academicYear) {
        this.academicYear = academicYear;
    }

    public String getSemesterTime() {
        return semesterTime;
    }

    public void setSemesterTime(String semesterTime) {
        this.semesterTime = semesterTime;
    }
}