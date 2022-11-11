package Helper.File;

import Model.BuyProcess;
import Model.Buyer;
import Model.Item;
import Model.Admin;

import java.io.*;
import java.util.List;
import java.util.logging.FileHandler;

public class FileLoader extends FileHandler {
     private static final List<Buyer> buyers = Admin.buyers;
    private static final List<Item> ITEMS = Admin.inventory;
    private static final List<BuyProcess> moneyMovementsList = Admin.movements;

    public FileLoader() throws IOException, SecurityException {}

    public static void loadAccounts(File ACCOUNTS_CSV) {
        if (!ACCOUNTS_CSV.exists()) return;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(ACCOUNTS_CSV))) {
            String line;

            String header = bufferedReader.readLine(); // eats the header

            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");

                //? Makes a customer based on the data to imitate loading from database.
                Buyer buyer = new Buyer(data[0], data[1], data[2], data[3]);
                buyer.setBalance(Double.parseDouble(data[4]));

                buyers.add(buyer);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadProducts(File PRODUCTS_CSV) {
        if (!PRODUCTS_CSV.exists()) return;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(PRODUCTS_CSV))) {
            String line;

            String header = bufferedReader.readLine(); // eats the header
            if (header == null) return;

            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");

                //? Makes a product based on the data to imitate loading from database.
                Item item = new Item(data[0], Double.parseDouble(data[1]), Integer.parseInt(data[2]));
                ITEMS.add(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void loadTransactions(File TRANSACTIONS_CSV) {
        if (!TRANSACTIONS_CSV.exists()) return;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(TRANSACTIONS_CSV))) {
            String line;
            Buyer buyer = null;
            Item item = null;

            String header = bufferedReader.readLine(); // eats the header
            if (header == null) return;

            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");

                String customerName = data[0];
                String productName = data[1];

                for (Buyer c : buyers) {
                    if (c.getFirstName().equals(customerName)) buyer = c;
                }

                for (Item p : ITEMS) {
                    if (p.getProductName().equals(productName)) item = p;
                }

                if (buyer == null || item == null) return;

                //? Makes a transaction based on the data to imitate loading from database.
                moneyMovementsList.add(new BuyProcess(buyer, item));

                item.setAmount_toBuy(Integer.valueOf(data[3]));

                List<Item> CUSTOMER_BOUGHT_Items = buyer.getBoughtProducts();
                CUSTOMER_BOUGHT_Items.add(item);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadCustomerCart(File CUSTOMER_CART_CSV) {
        if (!CUSTOMER_CART_CSV.exists()) return;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(CUSTOMER_CART_CSV))) {
            String line;
            Buyer buyer = null;
            List<Item> customerCart = null;
            Item item = null;

            String header = bufferedReader.readLine(); // eats the header
            if (header == null) return;

            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");

                String customerName = data[0];
                String productName = data[1];

                for (Buyer c : buyers) {
                    if (c.getFirstName().equals(customerName)) {
                        buyer = c;
                        customerCart = buyer.getMyCart();
                    }
                }

                for (Item p : ITEMS) {
                    if (p.getProductName().equals(productName)) item = p;
                }

                if (buyer == null || item == null || customerCart == null) return;

                //? Makes a product and store to the customerCart based on the data to imitate loading from database.
                Item loadedItem = new Item(productName, Double.parseDouble(data[2]), item.getProductQuantity());

                loadedItem.setAmount_toBuy(Integer.valueOf(data[3]));
                customerCart.add(loadedItem);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // * @param username {the customer to be skipped/removed}
    public static void updateCustomerCartCSV(String username) {
        File tempFile = new File("src/CSV/tempCustomerCart.csv");
        File CUSTOMER_CART_CSV = new File("src/CSV/customerCart.csv");

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(CUSTOMER_CART_CSV))) {
            if (tempFile.createNewFile()) {
                FileWriter writer = new FileWriter(tempFile, true);

                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    String[] data = line.split(",");
                    String customerName = data[0];

                    //? Skips the customer who just checked out (that means his cart is empty).
                    //? To imitate removing of accounts.
                    if (customerName.equals(username)) continue;

                    //? Write other customer to the tempFile.
                    writer.write(line + "\n");
                }
                writer.close();

                //? Copies the content of the tempFile and write it to the new File to
                //? Simulate updating of file.
                replaceFile(tempFile.toString(), CUSTOMER_CART_CSV.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * @param pathOfOldFile {The file to be deleted.}
     * @param pathOfNewFile {The file to be replaced with the contents of the file that is going to be deleted.}
     */
    private static void replaceFile(String pathOfOldFile, String pathOfNewFile) {
        String sCurrentLine;

        try {
            BufferedReader br = new BufferedReader(new FileReader(pathOfOldFile));
            BufferedWriter bw = new BufferedWriter(new FileWriter(pathOfNewFile));

            while ((sCurrentLine = br.readLine()) != null) {
                bw.write(sCurrentLine);
                bw.newLine();
            }

            br.close();
            bw.close();

            // delete the old file
            File org = new File(pathOfOldFile);
            org.delete();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
