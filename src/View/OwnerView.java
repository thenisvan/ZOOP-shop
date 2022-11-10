package View;

import Helper.UIHelper;
import Helper.ValidationHelper;
import Model.Customer;
import Model.Product;
import Model.Transaction;

import java.util.List;

public class OwnerView extends BasicView{
    public void showDashboard() {
        System.out.println("""
                \n
                What do you want to do?
                1 -> Add a product
                2 -> View transactions
                3 -> View customers details
                4 -> View your products
                5 -> Remove a customer
                6 -> Logout
                """);
        System.out.print(": ");
    }

    public void showMoneyMovement(List<Transaction> movements) {
        if (movements.size() == 0) {
            UIHelper.sleep(1, "No transactions for now!");
            return;
        }

        movements.forEach(transaction -> {
            System.out.println("\n+++++++++++++++++++++++++++++");
            System.out.println(transaction.getCustomer().getFirstName() + " " + transaction.getCustomer().getLastName() + " bought " + transaction.getProductInfo());
            System.out.println("+++++++++++++++++++++++++++++\n");
        });
    }

    public void showCustomerInfo(List<Customer> c) {
        if (!ValidationHelper.hasCustomers(c)) return;

        c.forEach(customer -> {
            System.out.println("\n+++++++++++++++++++++++++++++");
            System.out.printf("Firstname: %s\nLastname: %s", customer.getFirstName(), customer.getLastName());
        });
            System.out.println("+++++++++++++++++++++++++++++");
    }

    public void showProducts(List<Product> p) {
        if (p.size() == 0) {
            UIHelper.sleep(1, "You haven't add products yet!");
            return;
        }

        p.forEach(product -> {
            System.out.println("\n+++++++++++++++++++++++++++++");
            System.out.printf("Product name: %s\nProduct price: %.1f\nProduct quantity: %d", product.getProductName(), product.getProductPrice(), product.getProductQuantity());
        });
            System.out.println("+++++++++++++++++++++++++++++");
    }
}
