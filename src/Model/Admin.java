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
                \n
                """, this.getFirstName(), this.getLastName(), this.getUsername());

        System.out.println("| Movements:\n");
        for (BuyProcess bp : movements) {
            System.out.printf("\t\t %s\n",bp.toString());
        }
        System.out.println("| Buyers:\n");
        for (Buyer b : buyers) {
            System.out.printf("\t\t %s\n",b.toString());
        }

    }

    @Override
    public int getPoints() {
        return 0;
    }

    public Admin() {
        super("Deno", "Ivan", "admin", "pass");
    }
}