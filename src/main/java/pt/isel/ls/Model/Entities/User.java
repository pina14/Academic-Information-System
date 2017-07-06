package pt.isel.ls.Model.Entities;

public class User implements Entity {
    private int number;
    private String email;
    private String name;
    private String userType;

    public User(int number, String email, String name, String userType) {
        this.number = number;
        this.email = email;
        this.name = name;
        this.userType = userType;
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

    public String getUserType(){return userType;}

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserType(String userType){this.userType = userType;}
}