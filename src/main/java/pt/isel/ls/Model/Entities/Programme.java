package pt.isel.ls.Model.Entities;

public class Programme implements Entity {
    private String acronym;
    private String name;
    private int numSemester;

    public Programme(String acronym, String name, int numSemester) {
        this.acronym = acronym;
        this.name = name;
        this.numSemester = numSemester;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumSemester() {
        return numSemester;
    }

    public void setNumSemester(int numSemester) {
        this.numSemester = numSemester;
    }
}