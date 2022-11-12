package Helper.UserInput;

import Controller.personActions;
import Controller.adminControl;
import Helper.File.fileHandler;
import Utils.SOUT_utils;
import Model.Buyer;
import Model.Item;
import Model.Admin;
import Helper.Banners;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ShopHelper {
    private static final Scanner uInput = new Scanner(System.in);
    private static final List<Buyer> listOfBuyers = Admin.buyers;
    private static final List<Item> inventoryItems = Admin.inventory;
    private static final File accountsFile = new File("src/CSV/accounts.csv");
    private static final File productsFile = new File("src/CSV/products.csv");
    private static final File moneyMovementsFile = new File("src/CSV/transactions.csv");
    private static final File productsOnCardFile = new File("src/CSV/customerCart.csv");

    public static void openShop() throws IOException {
        loadCSVs();

        for (int x = 0; x < 1; x++) continue;

        while (true) {
            // print SHOP Banneŕas it will look good
            Banners.printRandomShopBanner();
            System.out.println("1.) Log In");
            System.out.println("2.) Create Customer");
            System.out.println("3.) Load Files");
            System.out.println("4.) Save Loaded data to Files");
            System.out.println("5.) End Program");

            // get input
            String uInput = ShopHelper.uInput.nextLine();
            // check input correction ( numeric <1,5> )
            if (stdInCheck.containLetter(uInput)) continue;
            int choice = Integer.parseInt(uInput);

            switch (choice) {
                case 1 -> ShopHelper.logIn();
                case 2 -> ShopHelper.signUp();
                case 3 -> loadCSVs();
                case 4 -> updateCSVs();
                case 5 -> {
                    updateCSVs();
                    return;
                }
                default -> SOUT_utils.sleep(1, "Please choose from 1-3 only!");
            }
        }
    }

    private static void loadCSVs() {
        fileHandler.loadAccounts(accountsFile);
        fileHandler.loadProducts(productsFile);
        fileHandler.loadCustomerCart(productsOnCardFile);
        fileHandler.loadTransactions(moneyMovementsFile);
    }

    private static void updateCSVs() {
        if (accountsFile.exists()) accountsFile.delete();
        fileHandler.makeFile(accountsFile.toString(), "FirstName,LastName,Username,Password,Balance\n");

        if (productsFile.exists()) productsFile.delete();
        fileHandler.makeFile(productsFile.toString(), "ProductName,ProductPrice,ProductQuantity\n");

        for (Buyer buyer : listOfBuyers) {
            fileHandler.writeToFile(accountsFile, buyer + "\n");
        }

        for (Item item : inventoryItems) {
            if (item.getProductQuantity() != 0) {
                fileHandler.writeToFile(productsFile, item + "\n");
            }
        }
    }

    private static void logIn() {
        Banners.printRandomLoginBanner();
        System.out.print("\nEnter your username: ");
        String username = uInput.nextLine();

        System.out.print("Enter your password: ");
        String password = uInput.nextLine();

        if (stdInCheck.isInputInvalid(username, password)) return;

//        TODO: implement proper login solution

        if (username.equals("admin") && password.equals("pass")) {
            Output_STD_functions.postLogin();

            adminControl adminControl = new adminControl();
            adminControl.chooseFromDashboard();
            return;
        }

        for (Buyer buyer : ShopHelper.listOfBuyers) {
            if (buyer.getUsername().equals(username) && buyer.getPass().equals(password)) {
                Output_STD_functions.postLogin();

                personActions personActions = new personActions(buyer);
                personActions.chooseFromDashboard();
                return;
            }
        }

        Output_STD_functions.sleep(1, "No account found!");
    }

    private static void signUp() {
        Banners.printRandomRegisterBanner();

        System.out.print("\nEnter your first name: ");
        String firstName = uInput.nextLine();

        System.out.print("Enter your last name: ");
        String lastName = uInput.nextLine();

        System.out.print("Enter your username: ");
        String username = uInput.nextLine();

        System.out.print("Enter your password: ");
        String password = uInput.nextLine();

        if (stdInCheck.isInputInvalid(firstName, lastName, username, password)) return;

        SOUT_utils.sleep(1, "Registration success!");
        listOfBuyers.add(new Buyer(firstName, lastName, username, password));
    }
}
