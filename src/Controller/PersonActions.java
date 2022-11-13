package Controller;

import Utils.Banners;
import Helper.File.CSV_fileUpdater;
import Helper.File.CSV_fileWriter;
import Utils.SOUT_utils;
import Utils.ShopUtils;
import Model.Buyer;
import Model.Item;
import Model.Admin;
import Model.BuyProcess;
import View.PersonView;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class PersonActions {
    private final Buyer buyer;
    private static final ArrayList<Item> shopItemList = Admin.inventory;
    private static final ArrayList<BuyProcess> moneyMovements = Admin.movements;
    private final ArrayList<Item> itemsOnCard;
    private final ArrayList<Item> buyerItemList;
    private final PersonView personView = new PersonView();
    private final Scanner uInput = new Scanner(System.in);

    public PersonActions(Buyer buyer) {
        this.buyer = buyer;
        itemsOnCard = buyer.getMyCart();
        buyerItemList = buyer.getOwnedItems();
    }

    public void chooseFromDashboard() {
        while (true) {
            Banners.printRandomPersonBanner(false);
            personView.showMENU();
            String input = uInput.nextLine();

            if (ShopUtils.containLetter(input)) continue;

            int choice = Integer.parseInt(input);

            switch (choice) {
                case 0 -> buyer.printInfo();
                case 1 -> addMoney();
                case 2 -> goWildin();
                case 3 -> personView.showUserInfo(buyer);
                case 4 -> personView.showUserMoney(buyer);
                case 5 -> personView.viewMyCart(itemsOnCard);
                case 6 -> clearCart();
                case 7 -> checkOut();
                case 8 -> personView.viewOwnedItems(buyerItemList);
                case 9 -> {
                    return;
                }
            }

        }
    }

    private void addMoney() {
        try {
            int upLimit = 1000;
            int lowLimit = 500;

            System.out.println("How much money you want to add <500,1000>\n: ");
            double inputMoney = Double.parseDouble(uInput.nextLine());

            if (inputMoney < lowLimit || inputMoney > upLimit) {
                SOUT_utils.delayMessage(1, "Out of range!");
                addMoney();
            }
            else {
                SOUT_utils.delayMessage(1, String.format("Success! you cashed in P%.1f", inputMoney));
                buyer.setBalance(buyer.getMoney() + inputMoney);
            }
            // Code would reach here if the user input a numeric char.
        } catch (NumberFormatException exc) {
            ShopUtils.numFormatException();
            addMoney();
        }
    }

    private void goWildin() {
        Item chosenItem;

        //?  This is for java to immediately exit the method if there are no products.
        if (shopItemList.size() == 0) {
            SOUT_utils.delayMessage(1, "We Have Nothing!");
            return;
        }
        Banners.printRandomShopBanner(true);
        int q = 0;
        for (Item item : shopItemList) {
            if (item.getItemAmount() == 0) continue;

            System.out.println("─────────────────────────────");
            System.out.printf("""
                    Item N*: %d
                    Name: %s
                    Price: %.2f
                    Amount: %d
                    """, q, item.getItemName(), item.getItemPrice(), item.getItemAmount());
            System.out.println("─────────────────────────────");
            q++;
        }
        try {
            System.out.print("\nWhich one u want to buy [number]: ");
            int pNum = Integer.parseInt(uInput.nextLine());
            chosenItem = shopItemList.get(pNum);

            System.out.println("""
                    \nAdd to cart or Buy now?
                    1.) Cart
                    2.) Buy
                    """);

            int uChoice = Integer.parseInt(uInput.nextLine());

            if (uChoice == 1) 
                addToCart(chosenItem);
            else if (uChoice == 2) 
                buyNow(chosenItem);
            else {
                ShopUtils.outOfRangeException();
                goWildin();
            }
        } 
        catch (NumberFormatException exc) {
            ShopUtils.numFormatException();
            goWildin();
        } 
        catch (IndexOutOfBoundsException exc) {
            ShopUtils.outOfRangeException();
            goWildin();
        }
    }

    private void addToCart(Item desiredItem) {
        try {
            System.out.print("Amount to add to Card: ");
            int qty = Integer.parseInt(uInput.nextLine());
            desiredItem.kolkoKupit(qty);

            if (qty > desiredItem.getItemAmount()) {
                SOUT_utils.delayMessage(1, "Sorry, you are requesting a bit too much!");
                return;
            }

            desiredItem.setItemAmount(desiredItem.getItemAmount() - qty);

            CSV_fileWriter.createCSV("data/cart.csv", "BuyerName,ItemName,ItemPrice,ItemsOwnedAmount\n");

            CSV_fileWriter.writeToCSV(
                    new File("data/cart.csv"),
                    String.format("%s,%s,%.2f,%d\n",
                            buyer.getFirstName(),
                            desiredItem.getItemName(),
                            desiredItem.getItemPrice(),
                            desiredItem.getAmount_toBuy())
            );

            itemsOnCard.add(desiredItem);

        }
        catch (NumberFormatException exc)
        {
            ShopUtils.numFormatException();
            addToCart(desiredItem);
        }
    }

    private void clearCart() {
        if (ShopUtils.hasNoItems(itemsOnCard)) return;

        SOUT_utils.delayMessage(1, "Card is null!");
        itemsOnCard.clear();
    }

    private void buyNow(Item chosenItem) {
        System.out.print("\nAmoount: ");
        int qty = Integer.parseInt(uInput.nextLine());
        chosenItem.kolkoKupit(qty);

        if (qty == 0) qty = 1;
        if (qty > chosenItem.getItemAmount()) {
            SOUT_utils.delayMessage(1, "We don't have enough amount for u!");
            return;
        }

        BuyProcess buyProcess = new BuyProcess(buyer, chosenItem);
        MoneyMovementActions moneyMovementActions = new MoneyMovementActions(buyProcess);

        //  if transaction failed
        if (!moneyMovementActions.startTransaction()) 
            return;

        moneyMovements.add(buyProcess);
        buyerItemList.add(chosenItem);

        ItemActions itemActions = new ItemActions(chosenItem);
        itemActions.updateItemAmount(qty);
        itemActions.updateItem();

        // ? This is for making/writing to transactionsCSV to load later when the program runs again
        CSV_fileWriter.createCSV("data/movements.csv", "BuyerName,ItemName,ItemPrice,ItemAmount\n");
        CSV_fileWriter.moneyMovementsWriter(buyProcess + "\n");
    }

    private void checkOut() {
        int calculatedPricee=0;

        if (itemsOnCard.size()== 0) {
            SOUT_utils.delayMessage(1, "Your card is empty!"); return;
        }

        for (Item item : itemsOnCard) {
            if (item.getItemAmount() <= 0) {
                SOUT_utils.delayMessage(1, "Sorry, amount is 0 of item: " + item.getItemName());
                return;
            }
            calculatedPricee += item.getItemPrice() * item.getAmount_toBuy();
        }

        if (buyer.getMoney() < calculatedPricee) {
            SOUT_utils.delayMessage(1, "You don't have enough money!");
            return;
        }

          for (Item item : itemsOnCard) {
            BuyProcess buyProcess = new BuyProcess(buyer, item);
            MoneyMovementActions moneyMovementActions = new MoneyMovementActions(buyProcess);

            SOUT_utils.delayMessage(1, "Processing....");
            moneyMovementActions.startTransaction();

            moneyMovements.add(buyProcess);
            buyerItemList.add(item);

            ItemActions itemActions = new ItemActions(item);
            itemActions.updateItem();

            CSV_fileWriter.createCSV("data/movements.csv", "BuyerName,ItemName,ItemPrice,ItemAmount\n");
            CSV_fileWriter.moneyMovementsWriter(buyProcess + "\n");
        }

        SOUT_utils.delayMessage(1, "Checkout done!");

        // ? Updates the file , rem buyer who registered this time.
        CSV_fileUpdater.update_BuyerCardItems(buyer.getFirstName());
        itemsOnCard.clear();
    }
}
