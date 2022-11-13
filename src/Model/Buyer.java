package Model;

import java.util.ArrayList;

public class Buyer extends Person {
    private final ArrayList<Item> shoppingCard = new ArrayList<>();
    private final ArrayList<Item> inventory = new ArrayList<>();
    private Double balance = 0.0;

    public Buyer(String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username, password);
    }


    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%.2f", this.getFirstName(), this.getLastName(), this.getUsername(), this.getPass(), this.getBalance());
    }

    @Override
    public void printInfo() {
        System.out.printf("""
                ---------------------------------
                | Name: %s %s
                | Username: %s
                \n
                """, this.getFirstName(), this.getLastName(), this.getUsername());

        System.out.println("| Card:\n");
        for (Item i : shoppingCard) {
            System.out.printf("\t\t %s\n", i.toString());
        }
        System.out.println("| Owned Items:\n");
        for (Item i : inventory) {
            System.out.printf("\t\t %s\n", i.toString());
        }
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(double newBalance) {
        balance = newBalance;
    }

    public ArrayList<Item> getMyCart() {
        return shoppingCard;
    }

    public ArrayList<Item> getOwnedItems() {
        return inventory;
    }
}
