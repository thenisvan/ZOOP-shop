package Utils.castin;

public class RegularUser extends User {
    int points;

    public RegularUser(String name, String uname, int age) {
        super(name, uname, age);
    }

    void displayInformation() {
        System.out.println("This is [User].[RegularUser] class instance");
    }
}
