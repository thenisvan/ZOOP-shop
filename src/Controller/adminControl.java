package Controller;

import Helper.Output_STD_functions;
import Helper.UserInput.InputChecker;
import Model.Buyer;
import Model.shopOwner;
import Model.Product;
import Model.BuyProcess;
import View.adminView;

import java.util.List;
import java.util.Scanner;

public class adminControl {
    private static final List<Buyer> OWNER_BUYER_LIST = shopOwner.buyers;
    private final List<Product> OWNER_PRODUCT_LIST = shopOwner.inventory;
    private final List<BuyProcess> OWNER_BuyProcess_LIST = shopOwner.movements;
    private final adminView OWNER_VIEW = new adminView();
    private final Scanner uInput = new Scanner(System.in);

    public void chooseFromDashboard() {
        while (true) {
            OWNER_VIEW.showDashboard();
            String input = uInput.nextLine().trim();

            if (InputChecker.containLetter(input)) continue;

            int choice = Integer.parseInt(input);

            switch (choice) {
                case 1 -> addProduct();
                case 2 -> OWNER_VIEW.showMoneyMovement(OWNER_BuyProcess_LIST);
                case 3 -> OWNER_VIEW.showCustomerInfo(OWNER_BUYER_LIST);
                case 4 -> OWNER_VIEW.showProducts(OWNER_PRODUCT_LIST);
                case 5 -> removeACustomer();
                case 6 -> {
                    return;
                }
                default -> Output_STD_functions.sleep(1, "Please enter from 1-6 only!");
            }
        }
    }

    public void addProduct() {
        try {
            System.out.print("\nEnter the product name: ");
            String productName = uInput.nextLine();

            System.out.print("Enter the product price: ");
            Double productPrice = Double.parseDouble(uInput.nextLine());

            System.out.print("Enter the product quantity: ");
            Integer productQuantity = Integer.parseInt(uInput.nextLine());

            OWNER_PRODUCT_LIST.add(new Product(productName, productPrice, productQuantity));

            Output_STD_functions.sleep(1, String.format("%d %ss was added!", productQuantity, productName));
        } catch (NumberFormatException e) {
            InputChecker.printNumberFormatExceptionMessage();
            addProduct();
        }
    }

    public void removeACustomer() {
        if (!InputChecker.hasCustomers(OWNER_BUYER_LIST)) return;

        OWNER_VIEW.showCustomerInfo(OWNER_BUYER_LIST);

        System.out.print("Enter the customer first name: ");
        String customerName = uInput.nextLine();

        if (InputChecker.isInputInvalid(customerName)) return;

        for (Buyer buyer : OWNER_BUYER_LIST) {
            if (buyer.getFirstName().equals(customerName)) {
                Output_STD_functions.sleep(1, String.format("%s was successfully removed!", buyer.getFirstName()));

                OWNER_BUYER_LIST.remove(buyer);
                return;
            }
        }

        System.out.println("\nNo user found!\n");
    }
}
