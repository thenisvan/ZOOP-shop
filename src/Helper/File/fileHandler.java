package Helper.File;

import Model.Buyer;
import Model.shopOwner;
import Model.Product;
import Model.BuyProcess;

import java.io.*;
import java.util.List;

public class fileHandler {
    private static final List<Buyer> listOfBuyers = shopOwner.buyers;
    private static final List<Product> listOfProducts = shopOwner.inventory;
    private static final List<BuyProcess> listOfMMovements = shopOwner.movements;

    /*
     * @param filePath {where you want your file to go.}
     * @param header {The content you want to initially add.}
     */
    public static void makeFile(String path, String h) {
        File file = new File(path);

        try {
            if (new File("src/CSV").mkdir()) {
                if (file.createNewFile()) writeToFile(file, h);
            } else {
                if (file.createNewFile()) writeToFile(file, h);
                System.out.print("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * @param file {Where you want to write.}
     * @param content {The content you want to write.}
     */
    public static void writeToFile(File file, String content) {
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.append(content);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

                listOfBuyers.add(buyer);
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
                Product product = new Product(data[0], Double.parseDouble(data[1]), Integer.parseInt(data[2]));
                listOfProducts.add(product);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeTransactions(String content) {
        File file = new File("src/CSV/transactions.csv");

        try (FileWriter writer = new FileWriter(file, true)) {
            writer.append(content);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadTransactions(File TRANSACTIONS_CSV) {
        if (!TRANSACTIONS_CSV.exists()) return;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(TRANSACTIONS_CSV))) {
            String line;
            Buyer buyer = null;
            Product product = null;

            String header = bufferedReader.readLine(); // eats the header
            if (header == null) return;

            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");

                String customerName = data[0];
                String productName = data[1];

                for (Buyer c : listOfBuyers) {
                    if (c.getFirstName().equals(customerName)) buyer = c;
                }

                for (Product p : listOfProducts) {
                    if (p.getProductName().equals(productName)) product = p;
                }

                if (buyer == null || product == null) return;

                //? Makes a transaction based on the data to imitate loading from database.
                listOfMMovements.add(new BuyProcess(buyer, product));

                product.setAmount_toBuy(Integer.valueOf(data[3]));

                List<Product> CUSTOMER_BOUGHT_PRODUCTS = buyer.getBoughtProducts();
                CUSTOMER_BOUGHT_PRODUCTS.add(product);
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
            List<Product> customerCart = null;
            Product product = null;

            String header = bufferedReader.readLine(); // eats the header
            if (header == null) return;

            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");

                String customerName = data[0];
                String productName = data[1];

                for (Buyer c : listOfBuyers) {
                    if (c.getFirstName().equals(customerName)) {
                        buyer = c;
                        customerCart = buyer.getMyCart();
                    }
                }

                for (Product p : listOfProducts) {
                    if (p.getProductName().equals(productName)) product = p;
                }

                if (buyer == null || product == null || customerCart == null) return;

                //? Makes a product and store to the customerCart based on the data to imitate loading from database.
                Product loadedProduct = new Product(productName, Double.parseDouble(data[2]), product.getProductQuantity());

                loadedProduct.setAmount_toBuy(Integer.valueOf(data[3]));
                customerCart.add(loadedProduct);
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
