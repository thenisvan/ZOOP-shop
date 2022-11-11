package Model;

import java.util.ArrayList;
import java.util.List;

public class shopOwner extends Person {
//    public static final List<Product> inventory = new ArrayList<>();
    public static final List<BuyProcess> movements = new ArrayList<>();
    public static final List<Buyer> buyers = new ArrayList<>();

    public shopOwner() {
        super("Deno", "Ivan", "Admin", "pass");
    }
}
