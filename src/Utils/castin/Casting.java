package Utils.castin;

import Model.Admin;
import Model.Buyer;
import Model.Person;

import java.util.ArrayList;


public class Casting {

    public static void gradeRolePrefs(Buyer buyer, String preference) {
        ((Person)buyer).setPreference(preference);
        System.out.printf("Granted role prefferences %s\n", (buyer).getPreference());
    }

    public static void AssignInstance(Person o, String preference) {
        if (preference.equals("buyer") && o instanceof Buyer) {
            Buyer b = (Buyer) o;
            (b).setBalance(0);
        }
        else
            System.out.println("Unsuported object or wrong assignment option!");
    }

        public static void unstanceHandle(String args[]) {
        Buyer buyer = new Buyer("denis", "denis", "denis", "denis");

        ((Person) buyer).setPreference("I like to buy electronic stuff..");

        Buyer acc1 = buyer;
        Person acc2 = new Buyer();
        Person acc3 = new Admin();
        gradeRolePrefs(acc1, "nothing");
        AssignInstance(acc3, "christmass stuff");
        AssignInstance(acc2, "Electronics Stuff");

        System.out.println(((Person) buyer).getPreference());

        // DOWNCASTING

        Person acc4 = acc2;
        ((Admin) acc4).setPass("default123");

//        Admin acc5 = (Admin) acc3;
        if (acc4 instanceof Buyer) {
            Person acc5 = (Person) buyer;
            acc5.setPass("factory-default");
            System.out.println("Password was successfully resetted !!");
        }

        if (buyer instanceof Person) {
            Buyer acc5 = (Buyer) buyer;
            acc5.setPoints(acc5.getPoints() + 100);
            System.out.printf("Update, user with name %s got extra 50 points!\n", acc5.getFirstName());
            System.out.printf("Currently has %d points!\n", acc5.getPoints());
        }


        // Castovanie smerom UP - bezpecne

//        Person acc = new Person();
        System.out.println("-------------------------------------------------------------");
        Admin bacc = new Admin();


        Buyer buyer1 = new Buyer();
        Admin admin1 = new Admin();
        Person person1 = new Person();

        buyer1.getPreference();
        ((Person) buyer1).setPreference("i am able to change it");
        admin1.setInfo("asd");
        System.out.println(admin1.getPreference());
        person1.setPreference("asd");
        System.out.println(person1.getPreference());
//
        ArrayList<Person> users = new ArrayList<Person>();
        users.add(buyer1);
        users.add(admin1);
        users.add(person1);

        for (Person uInLisr : users) {
            System.out.println(uInLisr.getPreference());
//            uInLisr.displayInformation();
        }

        for (Person uInLisr : users) {

            uInLisr.setPreference("Christmas presents");
            System.out.println(uInLisr.getPreference());
        }
    }
}

