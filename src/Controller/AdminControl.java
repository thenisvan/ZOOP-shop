package Controller;

import Model.*;
import Utils.Banners;
import Utils.SOUT_utils;
import Utils.ShopUtils;
import View.AdminView;

import java.util.ArrayList;
import java.util.Scanner;

public class AdminControl {
    private final AdminView adminView = new AdminView();
    private final Scanner uInput = new Scanner(System.in);
    //    lists
    private static final ArrayList<Buyer> listOfBuyers = Admin.buyers;
    private final ArrayList<Item> shopInventory = Admin.inventory;
    private final ArrayList<BuyProcess> moneyMovementsList = Admin.movements;

    public void selectFromMenu() {

        while (true) {
            Banners.printRandomAdminBanner(false);
            adminView.showMENU();
            String uIn = uInput.nextLine().trim(); // Remove whitespace from both sides of a string
            if (ShopUtils.containLetter(uIn)) continue;
            int choice = Integer.parseInt(uIn);

            switch (choice) {
                case 1 -> addItem();
                case 2 -> adminView.showMoneyMovement(moneyMovementsList);
                case 3 -> adminView.showBuyerInfo(listOfBuyers);
                case 4 -> adminView.showItems(shopInventory);
                case 5 -> delBuyer();
                case 6 -> assignPoints();
                case 7 -> {
                    return;
                }

                default -> SOUT_utils.delayMessage(1, "Enter only nums in range!");
            }
        }
    }

    public void addItem() {
        try {
            System.out.print("\nName: ");
            String pName = uInput.nextLine();

            System.out.print("Price: ");
            Double pPrice = Double.parseDouble(uInput.nextLine());

            System.out.print("Amount: ");
            Integer pQuantity = Integer.parseInt(uInput.nextLine());

            shopInventory.add(new Item(pName, pPrice, pQuantity));

            SOUT_utils.delayMessage(1, String.format("%d %ss was added!", pQuantity, pName));
        } catch (NumberFormatException e) {
            ShopUtils.numFormatException();
            addItem();
        }
    }

    public void delBuyer() {
        if (!ShopUtils.hasBuyers(listOfBuyers))
            return;

        adminView.showBuyerInfo(listOfBuyers);

        System.out.print("First name of buyer do be deleted: ");
        String buyerName = uInput.nextLine();

        if (ShopUtils.isInputInvalid(buyerName))
            return;

        for (Buyer buyer : listOfBuyers) {
            if (buyer.getFirstName().equals(buyerName)) {
                SOUT_utils.delayMessage(1, String.format("Buyer %s kicked!", buyer.getFirstName()));
                listOfBuyers.remove(buyer);
                return;
            }
        }

        System.out.println("\nNo user found!\n");
    }

    public void assignPoints() {
        if (!ShopUtils.hasBuyers(listOfBuyers))
            return;

        adminView.showBuyerInfo(listOfBuyers);

        System.out.print("username of buyer: ");
        String buyerName = uInput.nextLine();
        System.out.print("Points to give: ");
        int givenPoints = uInput.nextInt();

        if (ShopUtils.isInputInvalid(buyerName)) return;


        for (Buyer buyer : listOfBuyers) {
            if (buyer.getFirstName().equals(buyerName)) {
                SOUT_utils.delayMessage(1, String.format("Buyer %s kicked!", buyer.getFirstName()));
                buyer.setPoints(buyer.getPoints() + givenPoints); // current walue of points +
                return;
            }
        }

        System.out.println("\nNo user found!\n");
    }

    public void setInfoAsPerson(Buyer acc2) {
        // UPCASTING

        ((Person)acc2).setPreference("I like to buy electronic stuff..");

        // DOWNCASTING


// Admin acc5 = (Admin)acc3;
//        if (accB instanceof Admin) {
//            Admin acc5 = (Admin) accB;
//            acc5.setPass("factory-default");
//            System.out.println("Password was successfully resetted !!");
//        }
//
//        if (accB instanceof Buyer) {
//            Buyer acc5 = (Buyer) accB;
//            acc5.setPoints(acc5.getPoints() + 100);
//            System.out.printf("Update, user with name %s got extra 50 points!\n", acc5.getFirstName());
//            System.out.printf("Currently has %d points!\n", acc5.getPoints());
//        }

        System.out.println("\nNo user found!\n");
}

}
