package pt.isel.ls.Model.Entities;

public class Class implements Entity {
    private String id;
    private String cName;
    private int aYear;
    private String aSemester;

    public Class(String id, String cName, int aYear, String aSemester) {
        this.id = id;
        this.cName = cName;
        this.aYear = aYear;
        this.aSemester = aSemester;
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
}