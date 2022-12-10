package Model;

import java.util.ArrayList;

public abstract class Person  {
    private String fName, lName, uname, pass;
    public static final ArrayList<Item> inventory = new ArrayList<>();

    public Person(String f, String l, String u, String p) {
        this.fName = f;
        this.lName = l;
        this.uname = u;
        this.pass = p;
    }
    public Person(String f, String l) {
        this.fName = f;
        this.lName = l;
        this.uname = f;
        this.pass = "pass";
    }

    public void printInfo() {
        System.out.printf("""
                ---------------------------------
                | Name: %s %s |
                | Username: %s |
                ----------------------------------
                """, this.fName, this.lName, this.uname);
    }

    public String getFirstName() {
        return fName;
    }
    public String getLastName() {
        return lName;
    }
    public String getUsername() {
        return uname;
    }
    public String getPass() {
        return pass;
    }
    public void setFirstName(String fName) {
        this.fName = fName;
    }
    public void setLastName(String lName) {
        this.lName = lName;
    }
    public void setUsername(String uname) {
        this.uname = uname;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }
}
