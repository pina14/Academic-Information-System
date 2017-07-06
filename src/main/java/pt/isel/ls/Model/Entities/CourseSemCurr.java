package pt.isel.ls.Model.Entities;

public class CourseSemCurr implements Entity{
    private String name;
    private String acronym;
    private int tNumber;
    private int currSem;

    public CourseSemCurr(String name, String acronym, int tNumber, int currSem) {
        this.name = name;
        this.acronym = acronym;
        this.tNumber = tNumber;
        this.currSem = currSem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public int gettNumber() {
        return tNumber;
    }

    public void settNumber(int tNumber) {
        this.tNumber = tNumber;
    }

    public int getCurrSem() {
        return currSem;
    }

    public void setCurrSem(int currSem) {
        this.currSem = currSem;
    }
}