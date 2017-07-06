package pt.isel.ls.Model.Entities;

public class Course implements Entity {
    private String name;
    private String acronym;
    private int tNumber;

    public Course(String name, String acronym, int tNumber) {
        this.name = name;
        this.acronym = acronym;
        this.tNumber = tNumber;
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
}