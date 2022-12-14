package Model;

import View.PersonView;

import java.util.ArrayList;

public class Buyer extends Person implements Abilities{
    private final ArrayList<Item> shoppingCard = new ArrayList<>();
    private final ArrayList<Item> inventory = new ArrayList<>();
    private Double balance = 0.0;
    private int points = 0;

    public Buyer(String f, String l, String u, String p) {
        super(f, l, u, p);
    }
    private Buyer(String f, String l) {
        super(f, l, f, "default");
    }
    @Override
    public void printAbilities(Person p) {
        System.out.println("Abilities for Buyer:");
        if (p instanceof Buyer) {
            ((Buyer) p).printDetailedInfo((Buyer) p);
        }
    }
    public Buyer(String f, String l, String u, String p, Integer points) {
        super(f, l, u, p);
        this.points = points;
    }

    public Buyer() {

    }
    @Override
    public void printDetailedInfo(Person p) {
        p.printInfo();
        System.out.println(p.getPoints());
        System.out.println(p.getPreference());
        System.out.println(p.getClass());
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%.2f", this.getFirstName(), this.getLastName(), this.getUsername(), this.getPass(), this.getMoney());
    }

    @Override
    public void printInfo() {
        System.out.printf("""
                ---------------------------------
                |   Customer
                ---------------------------------
                | Name: %s %s
                | Username: %s
                | Points: %d
                """, this.getFirstName(), this.getLastName(), this.getUsername(), this.getPoints());


        System.out.println("| Card:\n");
        for (Item i : shoppingCard)
            System.out.printf("| \t\t %s\n", i.toString());


        System.out.println("| Owned Items:");


        for (Item i : inventory)
            System.out.printf("| \t\t %s\n", i.toString());

    }

    public void setPreference(String info) {
        this.preference = info;
        System.out.println(" - buyer preference changed ");
    }

    public String getPreference() {
        System.out.println("sorry, this information is only available for admins and users with higher priviledges");
        return "<redacted>";

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
