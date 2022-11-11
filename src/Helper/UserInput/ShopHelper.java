package Helper.UserInput;

import Controller.personActions;
import Controller.adminControl;
import Helper.File.fileHandler;
import Helper.Output_STD_functions;
import Model.Buyer;
import Model.Item;
import Model.Admin;
import Helper.Banners;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ShopHelper {
    private static final Scanner SCAN = new Scanner(System.in);
    private static final List<Buyer> CUSTOMERS_LIST = Admin.buyers;
    private static final List<Item> ITEM_LIST = Admin.inventory;
    private static final File ACCOUNTS_CSV = new File("src/CSV/accounts.csv");
    private static final File PRODUCTS_CSV = new File("src/CSV/products.csv");
    private static final File TRANSACTION_CSV = new File("src/CSV/transactions.csv");
    private static final File CUSTOMER_CART_CSV = new File("src/CSV/customerCart.csv");

    public static void openShop() throws IOException {
        // ? loads the files (if they're present) before opening the shop.
        loadFiles();

        while (true) {
            Banners.printRandomShopBanner();
            System.out.println("""
                                        
                    1 -> Login
                    2 -> Register
                    3 -> Exit
                    """);

            String input = SCAN.nextLine();

            if (InputChecker.containLetter(input)) continue;

            int choice = Integer.parseInt(input);

            switch (choice) {
                case 1 -> ShopHelper.login();
                case 2 -> ShopHelper.register();
                case 3 -> {
                    updateFilesOnExit();
                    return;
                }
                default -> Output_STD_functions.sleep(1, "Please choose from 1-3 only!");
            }
        }
    }

    private static void loadFiles() {
        fileHandler.loadAccounts(ACCOUNTS_CSV);
        fileHandler.loadProducts(PRODUCTS_CSV);
        fileHandler.loadCustomerCart(CUSTOMER_CART_CSV);
        fileHandler.loadTransactions(TRANSACTION_CSV);
    }

    private static void updateFilesOnExit() {
        if (ACCOUNTS_CSV.exists()) ACCOUNTS_CSV.delete();
        fileHandler.makeFile(ACCOUNTS_CSV.toString(), "FirstName,LastName,Username,Password,Balance\n");

        if (PRODUCTS_CSV.exists()) PRODUCTS_CSV.delete();
        fileHandler.makeFile(PRODUCTS_CSV.toString(), "ProductName,ProductPrice,ProductQuantity\n");

        for (Buyer buyer : CUSTOMERS_LIST) {
            fileHandler.writeToFile(ACCOUNTS_CSV, buyer + "\n");
        }

        for (Item item : ITEM_LIST) {
            if (item.getProductQuantity() != 0) {
                fileHandler.writeToFile(PRODUCTS_CSV, item + "\n");
            }
        }
    }

    private static void login() {
        Banners.printRandomLoginBanner();
        System.out.print("\nEnter your username: ");
        String username = SCAN.nextLine();

        System.out.print("Enter your password: ");
        String password = SCAN.nextLine();

        if (InputChecker.isInputInvalid(username, password)) return;

//        TODO: implement proper login solution

        if (username.equals("admin") && password.equals("pass")) {
            Output_STD_functions.postLogin();

            adminControl adminControl = new adminControl();
            adminControl.chooseFromDashboard();
            return;
        }

        for (Buyer buyer : ShopHelper.CUSTOMERS_LIST) {
            if (buyer.getUsername().equals(username) && buyer.getPass().equals(password)) {
                Output_STD_functions.postLogin();

                personActions personActions = new personActions(buyer);
                personActions.chooseFromDashboard();
                return;
            }
        }

        Output_STD_functions.sleep(1, "No account found!");
    }

    private static void register() {
        Banners.printRandomRegisterBanner();

        System.out.print("\nEnter your first name: ");
        String firstName = SCAN.nextLine();

        System.out.print("Enter your last name: ");
        String lastName = SCAN.nextLine();

        System.out.print("Enter your username: ");
        String username = SCAN.nextLine();

        System.out.print("Enter your password: ");
        String password = SCAN.nextLine();

        if (InputChecker.isInputInvalid(firstName, lastName, username, password)) return;

        Output_STD_functions.sleep(1, "Registration success!");
        CUSTOMERS_LIST.add(new Buyer(firstName, lastName, username, password));
    }
}
