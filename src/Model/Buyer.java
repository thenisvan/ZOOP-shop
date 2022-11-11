package Model;

import java.util.ArrayList;
import java.util.List;

public class Buyer extends Person {
    private final List<Product> shoppingCard = new ArrayList<>();
    private final List<Product> inventory = new ArrayList<>();
    private Double balance = 0.0;

    public Buyer(String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username, password);
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%.1f", this.getFirstName(), this.getLastName(), this.getUsername(), this.getPass(), this.getBalance());
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(double newBalance) {
        balance = newBalance;
    }

    public List<Product> getMyCart() {
        return shoppingCard;
    }

    public List<Product> getBoughtProducts() {
        return inventory;
    }
}
