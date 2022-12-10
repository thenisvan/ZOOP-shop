package Utils.castin;

public class User {
    String name;
    String uname;
    int age;
       void displayInformation(){
           System.out.println("This is [User] class instance");
       }

    public User(String name, String uname, int age) {
        this.name = name;
        this.uname = uname;
        this.age = age;
    }
}