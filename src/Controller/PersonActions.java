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
import java.util.List;
import java.util.Scanner;

public class personActions {
    private final Buyer buyer;
    private static final List<Item> shopItemList = Admin.inventory;
    private static final List<BuyProcess> moneyMovements = Admin.movements;
    private final List<Item> productsOnCard;
    private final List<Item> customerInventory;
    private final PersonView personView = new PersonView();
    private final Scanner uInput = new Scanner(System.in);

    public personActions(Buyer buyer) {
        this.buyer = buyer;
        productsOnCard = buyer.getMyCart();
        customerInventory = buyer.getOwnedItems();
    }

    public void chooseFromDashboard() {
        while (true) {
            Banners.printRandomPersonBanner(true);
            personView.showDashboard();
            String input = uInput.nextLine();

            if (ShopUtils.containLetter(input)) continue;

            int choice = Integer.parseInt(input);

            switch (choice) {
                case 0 -> buyer.printInfo();
                case 1 -> addMoney();
                case 2 -> goWildin();
                case 3 -> personView.showUserInfo(buyer);
                case 4 -> personView.showUserBalance(buyer);
                case 5 -> personView.viewMyCart(productsOnCard);
                case 6 -> clearCart();
                case 7 -> checkOut();
                case 8 -> personView.viewMyBoughtProducts(customerInventory);
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
                buyer.setBalance(buyer.getBalance() + inputMoney);
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

            System.out.println("----------------------------");
            System.out.printf("""
                    item number: %d
                    item name: %s
                    price: %.1f
                    quantity: %d
                    """, q, item.getItemName(), item.getProductPrice(), item.getItemAmount());
            System.out.println("----------------------------");
            q++;
        }
        try {
            System.out.print("\nEnter the item number: ");
            int pNum = Integer.parseInt(uInput.nextLine());
            chosenItem = shopItemList.get(pNum);

            System.out.println("""
                    \nDo you want to add to cart or buy now?
                    1 -> Add to cart
                    2 -> Buy now
                    """);

            int choice = Integer.parseInt(uInput.nextLine());

            if (choice == 1) addToCart(chosenItem);
            else if (choice == 2) buyNow(chosenItem);
            else {
                ShopUtils.outOfRangeException();
                goWildin();
            }
        } catch (NumberFormatException exc) {
            ShopUtils.numFormatException();
            goWildin();
        } catch (IndexOutOfBoundsException exc) {
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

            // ? sets the new quantity of that item
            desiredItem.setItemAmount(desiredItem.getItemAmount() - qty);

            // ? make and write the file to where that item will be stored.
            CSV_fileWriter.createCSV("data/card.csv", "CustomerName,ProductName,ProductPrice,ProductBoughtQuantity\n");

            CSV_fileWriter.writeToCSV(
                    new File("data/customerCart.csv"),
                    String.format("%s,%s,%.1f,%d\n",
                            buyer.getFirstName(),
                            desiredItem.getItemName(),
                            desiredItem.getProductPrice(),
                            desiredItem.getAmount_toBuy())
            );

            productsOnCard.add(desiredItem);

        } catch (NumberFormatException e) {
            ShopUtils.numFormatException();
            addToCart(desiredItem);
        }
    }

    private void clearCart() {
        if (ShopUtils.isCartEmpty(productsOnCard)) return;

        SOUT_utils.delayMessage(1, "Cart successfully cleared!");
        productsOnCard.clear();
    }

    private void buyNow(Item chosenItem) {
        System.out.print("\nEnter quantity: ");
        int qty = Integer.parseInt(uInput.nextLine());
        chosenItem.kolkoKupit(qty);

        //? This is for when the user input 0 , we set it to 1 to avoid multiplying to 0
        if (qty == 0) qty = 1;
        if (qty > chosenItem.getItemAmount()) {
            SOUT_utils.delayMessage(1, "We don't have enough stock for that quantity!");
            return;
        }

        BuyProcess buyProcess = new BuyProcess(buyer, chosenItem);
        MoneyMovementActions moneyMovementActions = new MoneyMovementActions(buyProcess);

        //  if transaction failed
        if (!moneyMovementActions.startTransaction()) return;

        moneyMovements.add(buyProcess);
        customerInventory.add(chosenItem);

        ProductActions productActions = new ProductActions(chosenItem);
        productActions.updateItemAmount(qty);
        productActions.updateItem();

        // ? This is for making/writing to transactionsCSV to load later when the program runs again
        CSV_fileWriter.createCSV("data/movements.csv", "CustomerName,ProductName,ProductPrice,ProductQuantity\n");
        CSV_fileWriter.moneyMovementsWriter(buyProcess + "\n");
    }

    private void checkOut() {
        int totalPrice = 0;

        if (productsOnCard.size() == 0) {
            SOUT_utils.delayMessage(1, "Your card is empty!");
            return;
        }

        for (Item item : productsOnCard) {
            if (item.getItemAmount() <= 0) {
                SOUT_utils.delayMessage(1, "No stocks for " + item.getItemName());
                return;
            }
            totalPrice += item.getProductPrice() * item.getAmount_toBuy();
        }

        if (buyer.getBalance() < totalPrice) {
            SOUT_utils.delayMessage(1, "You don't have enough money to pay for that!");
            return;
        }

        // ? Loops throughout the customerCart and make transactions in each of the products
        for (Item item : productsOnCard) {
            BuyProcess buyProcess = new BuyProcess(buyer, item);
            MoneyMovementActions moneyMovementActions = new MoneyMovementActions(buyProcess);

            SOUT_utils.delayMessage(1, "Processing your request..");
            moneyMovementActions.startTransaction();

            moneyMovements.add(buyProcess);
            customerInventory.add(item);

            ProductActions productActions = new ProductActions(item);
            productActions.updateItem();

            CSV_fileWriter.createCSV("data/movements.csv", "CustomerName,ProductName,ProductPrice,ProductQuantity\n");
            CSV_fileWriter.moneyMovementsWriter(buyProcess + "\n");
        }

        SOUT_utils.delayMessage(1, "Checkout done!");

        // ? Updates the file (customerCart.csv) by removing the customer who just checked out.
        CSV_fileUpdater.update_BuyerCardItems(buyer.getFirstName());
        productsOnCard.clear();
    }
}
