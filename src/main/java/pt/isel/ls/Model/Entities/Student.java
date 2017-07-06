package pt.isel.ls.Model.Entities;

public class Student implements Entity {
    private int number;
    private String email;
    private String name;
    private String pid;

    public Student(int number, String pid, String email, String name) {
        this.number = number;
        this.pid = pid;
        this.email = email;
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}