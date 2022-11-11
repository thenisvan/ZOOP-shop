package Model;

import java.util.ArrayList;
import java.util.List;

public abstract class Person {
    private String fName, lName, uname, pass;

    public static final List<Product> inventory = new ArrayList<>();

    public Person(String f, String l, String u, String p) {
        this.fName = f;
        this.lName = l;
        this.uname = u;
        this.pass = p;
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
