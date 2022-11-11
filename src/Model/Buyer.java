package Model;

import java.util.ArrayList;
import java.util.List;

public class Buyer extends Person {
    private final List<Item> shoppingCard = new ArrayList<>();
    private final List<Item> inventory = new ArrayList<>();
    private Double balance = 0.0;

    public Buyer(String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username, password);
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%.1f", this.getFirstName(), this.getLastName(), this.getUsername(), this.getPass(), this.getBalance());
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

    public List<Item> getMyCart() {
        return shoppingCard;
    }

    public List<Item> getBoughtProducts() {
        return inventory;
    }
}
