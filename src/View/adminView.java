package View;

import Helper.Output_STD_functions;
import Helper.UserInput.InputChecker;
import Model.Buyer;
import Model.Item;
import Model.BuyProcess;

import java.util.List;

public class adminView extends BasicView{
    public void showDashboard() {
        System.out.println("""
                \n
                What do you want to do?
                1.) Create new item
                2.) Show Money Movements
                3.) Show info about Buyers
                4.) Show List of Products
                5.) Remove buyer
                6.) Logout
                """);
        System.out.print("-> ");
    }

    public void showMoneyMovement(List<BuyProcess> m) {
        if (m.size() == 0) {
            Output_STD_functions.sleep(1, "No transactions for now!");
            return;
        }

        m.forEach(transaction -> {
            System.out.println("\n+++++++++++++++++++++++++++++");
            System.out.println(transaction.getBuyer().getFirstName() + " " + transaction.getBuyer().getLastName() + " bought " + transaction.showInfo());
            System.out.println("+++++++++++++++++++++++++++++\n");
        });
    }

    public void showCustomerInfo(List<Buyer> c) {
        if (!InputChecker.hasCustomers(c)) return;

        c.forEach(customer -> {
            System.out.println("\n+++++++++++++++++++++++++++++");
            System.out.printf("Firstname: %s\nLastname: %s", customer.getFirstName(), customer.getLastName());
        });
            System.out.println("\n+++++++++++++++++++++++++++++");
    }

    public void showProducts(List<Item> p) {
        if (p.size() == 0) {
            Output_STD_functions.sleep(1, "You haven't add items yet!");
            return;
        }

        p.forEach(item -> {
            System.out.println("\n+++++++++++++++++++++++++++++");
            System.out.printf("Product name: %s\nProduct price: %.1f\nProduct quantity: %d", item.getProductName(), item.getProductPrice(), item.getProductQuantity());
        });
            System.out.println("+++++++++++++++++++++++++++++");
    }
}
