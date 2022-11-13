package Controller;

import Utils.Banners;
import Utils.SOUT_utils;
import Utils.ShopUtils;
import Model.Buyer;
import Model.Item;
import Model.Admin;
import Model.BuyProcess;
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
                case 6 -> { return; }

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
}
