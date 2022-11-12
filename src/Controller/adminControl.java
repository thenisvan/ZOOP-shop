package Controller;

import Helper.Banners;
import Utils.SOUT_utils;
import Helper.UserInput.stdInCheck;
import Model.Buyer;
import Model.Item;
import Model.Admin;
import Model.BuyProcess;
import View.adminView;

import java.util.List;
import java.util.Scanner;

public class adminControl {
    private static final List<Buyer> buyersList = Admin.buyers;
    private final List<Item> shopInventory = Admin.inventory;
    private final List<BuyProcess> moneyMovementsList = Admin.movements;
    private final adminView adminView = new adminView();
    private final Scanner uInput = new Scanner(System.in);

    public void chooseFromDashboard() {
        while (true) {
            Banners.printRandomAdminBanner();
            adminView.showDashboard();
            String uIn = uInput.nextLine().trim(); // Remove whitespace from both sides of a string

//            Check if our input contains Alpha symbol
//            We can check it by regex using static function from InputChecker
            if (stdInCheck.containLetter(uIn)) continue;

//            parse user answer
            int choice = Integer.parseInt(uIn);

            switch (choice) {
                case 1 -> addProduct();
                case 2 -> adminView.showMoneyMovement(moneyMovementsList);
                case 3 -> adminView.showCustomerInfo(buyersList);
                case 4 -> adminView.showProducts(shopInventory);
                case 5 -> removeACustomer();
                case 6 -> {
                    return;
                }
                default -> SOUT_utils.sleep(1, "Please enter from 1-6 only!");
            }
        }
    }

    public void addProduct() {
        try {
            System.out.print("\nWhat is the name of new Item: ");
            String pName = uInput.nextLine();

            System.out.print("What is the price of new Item: ");
            Double pPrice = Double.parseDouble(uInput.nextLine());

            System.out.print("Quantity: ");
            Integer pQuantity = Integer.parseInt(uInput.nextLine());

            shopInventory.add(new Item(pName, pPrice, pQuantity));

            SOUT_utils.sleep(1, String.format("%d %ss was added!", pQuantity, pName));
        } catch (NumberFormatException e) {
            stdInCheck.printNumberFormatExceptionMessage();
            addProduct();
        }
    }

    public void removeACustomer() {
        if (!stdInCheck.hasCustomers(buyersList)) return;

        adminView.showCustomerInfo(buyersList);

        System.out.print("Enter the customer first name: ");
        String customerName = uInput.nextLine();

        if (stdInCheck.isInputInvalid(customerName)) return;

        for (Buyer buyer : buyersList) {
            if (buyer.getFirstName().equals(customerName)) {
                SOUT_utils.sleep(1, String.format("%s was successfully removed!", buyer.getFirstName()));

                buyersList.remove(buyer);
                return;
            }
        }

        System.out.println("\nNo user found!\n");
    }
}
