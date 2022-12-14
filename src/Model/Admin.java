package Model;

import java.util.ArrayList;

public class Admin extends Person implements Abilities {
    public static final ArrayList<BuyProcess> movements = new ArrayList<>();
    public static final ArrayList<Buyer> buyers = new ArrayList<>();


    @Override
    public void printInfo() {
        System.out.printf("""
                ---------------------------------
                | Name: %s %s
                | Username: %s
                | Preference: %s
                \n
                """, this.getFirstName(), this.getLastName(), this.getUsername(), this.preference);

        System.out.println("| Movements:\n");
        for (BuyProcess bp : movements) {
            System.out.printf("\t\t %s\n", bp.toString());
        }
        System.out.println("| Buyers:\n");
        for (Buyer b : buyers) {
            System.out.printf("\t\t %s\n", b.toString());
        }

    }

    @Override
    public int getPoints() {
        return 0;
    }

    @Override
    public void printAbilities(Person p) {
        System.out.println("Abilities for Admin: ");
        if (p instanceof Admin) {
            ((Admin) p).printDetailedInfo((Admin) p);
        }
    }

    @Override
    public void printDetailedInfo(Person p) {
        p.printInfo();
        System.out.println(p.getPreference());
        System.out.println(p.getClass());
    }

    public void setInfo(String info) {
        this.preference = info;
        System.out.println(" - admin preference changed ");
    }

    @Override
    public String getPreference() {
        return preference;
    }

    public Admin() {
        super("admin", "admin", "admin", "pass");
    }
}