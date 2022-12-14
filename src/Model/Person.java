package Model;

import java.util.ArrayList;

public abstract class Person implements Abilities{
    protected String fName, lName, uname;
    private String pass;
    protected String preference;
    public static final ArrayList<Item> inventory = new ArrayList<>();

    public Person(String f, String l, String u, String p) {
        this.fName = f;
        this.lName = l;
        this.uname = u;
        this.pass = p;
    }

    public Person(String f, String l, String u, String p, String i) {
        this.fName = f;
        this.lName = l;
        this.uname = u;
        this.pass = p;
        this.preference = i;
    }

    public Person(String f, String l) {
        this.fName = f;
        this.lName = l;
        this.uname = f;
        this.pass = "pass";
    }

    public Person() {

    }

    public void printAbilities(Person p) {
        System.out.println("Person instance has no abilities inside shop actions");
    }

    public void printInfo() {
        System.out.printf("""
                ---------------------------------
                |   PERSON
                ---------------------------------
                | Name: %s %s |
                | Username: %s |
                ----------------------------------
                """, this.fName, this.lName, this.uname);
    }

    @Override
    public int getPoints() {
        return 0;
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

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    public abstract void printDetailedInfo(Person p);

    public String getPreference() {
        return " I have preference in buying  ";
    }
}
