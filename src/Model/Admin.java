package Model;

import java.util.ArrayList;
import java.util.List;

public class Admin extends Person {
    //    public static final List<Product> inventory = new ArrayList<>();
    public static final List<BuyProcess> movements = new ArrayList<>();
    public static final List<Buyer> buyers = new ArrayList<>();


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
        System.out.println("| Customers:\n");
        for (Buyer b : buyers) {
            System.out.printf("\t\t %s\n",b.toString());
        }

    }
    public Admin() {
        super("Deno", "Ivan", "Admin", "pass");
    }
}