package Helper.UserInput;

import Controller.PersonActions;
import Controller.AdminControl;
import Helper.File.CSV_fileLoader;
import Helper.File.CSV_fileWriter;
import Model.Person;
import Utils.SOUT_utils;
import Model.Buyer;
import Model.Item;
import Model.Admin;
import Utils.Banners;
import Utils.ShopUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ShopAction {
    private static final Scanner uInput = new Scanner(System.in);
    private static final List<Buyer> listOfBuyers = Admin.buyers;
    private static final List<Item> inventoryItems = Admin.inventory;
    private static final File accountsFile = new File("data/accounts.csv");
    private static final File itemsFile = new File("data/items.csv");
    private static final File moneyMovementsFile = new File("data/movements.csv");
    private static final File itemsOnCardFile = new File("data/cart.csv");

    public static void startShop() throws IOException {
        loadCSVs();




        for (int x = 0; x < 1; x++) ;

        while (true) {
            // print SHOP Banneŕas it will look good
            Banners.printRandomShopBanner(false);
            System.out.println("1.) Log In");
            System.out.println("2.) Create Buyer");
            System.out.println("3.) Load Files");
            System.out.println("4.) Save Loaded data to Files");
            System.out.println("5.) End Program");

            // get input
            String uInput = ShopAction.uInput.nextLine();
            // check input correction ( numeric <1,5> )
            if (ShopUtils.containLetter(uInput)) continue;
            int choice = Integer.parseInt(uInput);

            switch (choice) {
                case 1 -> ShopAction.logIn();
                case 2 -> ShopAction.signUp();
                case 3 -> loadCSVs();
                case 4 -> updateCSVs();
                case 5 -> {
                    updateCSVs();
                    return;
                }
                default -> SOUT_utils.delayMessage(1, "\n Number is not from given range!");
            }
        }
    }

    private static void loadCSVs() {
        CSV_fileLoader.buyers_load(accountsFile);
        CSV_fileLoader.items_load(itemsFile);
        CSV_fileLoader.buyerCart_load(itemsOnCardFile);
        CSV_fileLoader.movements_load(moneyMovementsFile);
    }

    private static void updateCSVs() {
        if (accountsFile.exists())
            accountsFile.delete();
        CSV_fileWriter.createCSV(accountsFile.toString(), "FirstName,LastName,Username,Password,Balance\n");

        if (itemsFile.exists()) itemsFile.delete();
        CSV_fileWriter.createCSV(itemsFile.toString(), "ItemName,ItemPrice,ItemAmount\n");

        for (Buyer buyer : listOfBuyers) {
            CSV_fileWriter.writeToCSV(accountsFile, buyer + "\n");
        }

        for (Item item : inventoryItems) {
            if (item.getItemAmount() != 0) {
                CSV_fileWriter.writeToCSV(itemsFile, item + "\n");
            }
        }
    }

    private static void logIn() {
//        Print nice and shiny banner
        Banners.printRandomLoginBanner(true);
//        ask for creds
        System.out.print("\nUserName: ");
        String uName = uInput.nextLine();
        System.out.print("Password: ");
        String pass = uInput.nextLine();


//        this is painfull check
//        TODO: this needs to be handled correctly
        if (ShopUtils.isInputInvalid(uName, pass)) return;

//        TODO: implement proper login solution, not only string comparison
        if (uName.equals("admin") && pass.equals("pass")) {
            SOUT_utils.afterLogIn();

            AdminControl adminControl = new AdminControl();
            adminControl.selectFromMenu();
            return;
        }
//      Check creds for other accounts
        for (Buyer buyer : ShopAction.listOfBuyers) {
            if (buyer.getUsername().equals(uName) && buyer.getPass().equals(pass)) {
                SOUT_utils.afterLogIn();
                PersonActions personActions = new PersonActions(buyer);
                personActions.chooseFromDashboard();
                return;
            }
        }
        SOUT_utils.delayMessage(1, "No account found!");
    }

    private static void signUp() {
        Banners.printRandomRegisterBanner(true);
        System.out.print("\nFirst name: ");
        String fName = uInput.nextLine();
        System.out.print("Last name: ");
        String lName = uInput.nextLine();
        System.out.print("Username: ");
        String uName = uInput.nextLine();
        System.out.print("Password: ");
        String pass = uInput.nextLine();

        if (ShopUtils.isInputInvalid(fName, lName, uName, pass)) return;
        SOUT_utils.delayMessage(1, String.format("User [%s] has been added !", uName));
        listOfBuyers.add(new Buyer(fName, lName, uName, pass));
    }
}
