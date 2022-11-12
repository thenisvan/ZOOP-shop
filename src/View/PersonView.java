package View;

import Utils.SOUT_utils;
import Helper.UserInput.shopChecker;
import Model.Item;

import java.util.List;

public class PersonView extends BasicView {
    public void showDashboard() {
        System.out.println("""
                What do you want to do?
                0.) Print All My Information
                1.) Add Money to account
                2.) Go shoppin'
                3.) View basic Info
                4.) View My Balance
                5.) View Items On Card
                6.) Delete Card Items
                7.) Buy items on Card
                8.) View my inventory
                9.) Logout
                """);
        System.out.print("#: ");
    }

//    public void showUserInfo(Customer customer) {
//        System.out.println("\n#############################");
//        System.out.printf("Firstname : %s\nLastname : %s", customer.getFirstName(), customer.getLastName());
//        System.out.println("#############################");
//    }

//    public void showUserBalance(Customer customer) {
//        UIHelper.sleep(1, "Balance: P" + customer.getBalance());
//    }

    public void viewMyCart(List<Item> customerCart) {
        if (shopChecker.isCartEmpty(customerCart)) return;

        System.out.println("\n** YOUR CART **");
        customerCart.forEach(cart -> {
            System.out.println("\n+++++++++++++++++++++++++++++");
            System.out.printf("""
                    Product name: %s
                    Product price: %.1f
                    Quantity: %d
                    """, cart.getProductName(), cart.getProductPrice(), cart.getAmount_toBuy());
            System.out.println("\n+++++++++++++++++++++++++++++");
        });
    }

    public void viewMyBoughtProducts(List<Item> boughtItems) {
        if (boughtItems.size() == 0) {
            SOUT_utils.delayMessage(1, "You haven't bought any items!");
            return;
        }

        System.out.println("\n** Your Bought Products **");
        boughtItems.forEach(boughtProduct -> {
            System.out.println("\n+++++++++++++++++++++++++++++");
            System.out.printf("""
                    Product name: %s
                    Product price: %.1f
                    Bought Quantity: %d
                    """, boughtProduct.getProductName(), boughtProduct.getProductPrice(), boughtProduct.getAmount_toBuy());
            System.out.println("\n+++++++++++++++++++++++++++++");
        });
    }
}
