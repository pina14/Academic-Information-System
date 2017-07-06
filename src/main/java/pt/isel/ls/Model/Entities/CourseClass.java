package pt.isel.ls.Model.Entities;

public class CourseClass implements Entity{
    private String id;
    private String cName;
    private int aYear;
    private String aSemester;
    private String courseAcr;

    public CourseClass(String id, String cName, int aYear, String aSemester, String courseAcr) {
        this.id = id;
        this.cName = cName;
        this.aYear = aYear;
        this.aSemester = aSemester;
        this.courseAcr = courseAcr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public int getaYear() {
        return aYear;
    }

    public void setaYear(int aYear) {
        this.aYear = aYear;
    }

    public String getaSemester() {
        return aSemester;
    }

    public void setaSemester(String aSemester) {
        this.aSemester = aSemester;
    }

    public String getCourseAcr() {
        return courseAcr;
    }

    public void setCourseAcr(String courseAcr) {
        this.courseAcr = courseAcr;
    }
}