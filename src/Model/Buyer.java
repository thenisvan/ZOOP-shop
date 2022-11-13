package Model;

import java.util.ArrayList;

public class Buyer extends Person {
    private final ArrayList<Item> shoppingCard = new ArrayList<>();
    private final ArrayList<Item> inventory = new ArrayList<>();
    private Double balance = 0.0;
    private int points = 0;

    public Buyer(String f, String l, String u, String p) {
        super(f, l, u, p);
    }

    public Buyer(String f, String l, String u, String p, Integer points) {
        super(f, l, u, p);
        this.points = points;
    }


    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%.2f", this.getFirstName(), this.getLastName(), this.getUsername(), this.getPass(), this.getMoney());
    }

    @Override
    public void printInfo() {
        System.out.printf("""
                ---------------------------------
                | Name: %s %s
                | Username: %s
                """, this.getFirstName(), this.getLastName(), this.getUsername());


        System.out.println("| Card:\n");
        for (Item i : shoppingCard)
            System.out.printf("| \t\t %s\n", i.toString());


        System.out.println("| Owned Items:");


        for (Item i : inventory)
            System.out.printf("| \t\t %s\n", i.toString());

    }

    public int getPoints() {
        return points;
    }


    public Double getMoney() {
        return balance;
    }


    public void setBalance(double newBalance) {
        balance = newBalance;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public ArrayList<Item> getMyCart() {
        return shoppingCard;
    }

    public ArrayList<Item> getOwnedItems() {
        return inventory;
    }
}
