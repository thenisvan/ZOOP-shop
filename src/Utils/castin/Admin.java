package Utils.castin;

public class Admin extends User {

    public Admin(String name, String uname, int age) {
        super(name, uname, age);
    }

    void displayInformation() {
        System.out.println("This is [User].[Admin] class instance");
    }
}
