package Utils.castin;

import java.util.ArrayList;

public class Casting {
    public static void main(String args[]) {

        // Castovanie smerom UP - bezpecne
        User instanceOfUser1 = (User) new RegularUser("ivo", "bdfg", 15);
        instanceOfUser1.displayInformation();

        // Castovanie smerom DOWN

        User user = new RegularUser("Deno", "abcd", 15);
        user.name = "Denis";

        // Implicitne Downcastovanie
        // runtime ERROR
//        RegularUser dwnCasted = new User();

        // Explicitne Downcastovanie
        RegularUser dwnCasted = (RegularUser) user;

        dwnCasted.name = "Ivan";
        System.out.println(dwnCasted.name);
        dwnCasted.displayInformation();

        ArrayList<User> users = new ArrayList<User>();
        users.add(new User("user1","user1", 25));
        users.add(new RegularUser("regularUser1","regularUser1", 30));
        users.add(new Admin("regularUser1","regularUser1", 30));

        for (User uInLisr: users) {
            uInLisr.name = "Tomas";
            System.out.println(uInLisr.name);
            uInLisr.displayInformation();
        }

    }
}
