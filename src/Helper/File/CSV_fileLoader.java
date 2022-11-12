package Helper.File;

import Model.BuyProcess;
import Model.Buyer;
import Model.Item;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CSV_fileLoader extends CSV_fileHandler {

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
                inventoryItems.add(item);
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

                for (Buyer c : listOfBuyers) {
                    if (c.getFirstName().equals(customerName)) buyer = c;
                }

                for (Item p : inventoryItems) {
                    if (p.getProductName().equals(productName)) item = p;
                }

                if (buyer == null || item == null) return;

                //? Makes a transaction based on the data to imitate loading from database.
                listOfMMovements.add(new BuyProcess(buyer, item));

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

                for (Buyer c : listOfBuyers) {
                    if (c.getFirstName().equals(customerName)) {
                        buyer = c;
                        customerCart = buyer.getMyCart();
                    }
                }

                for (Item p : inventoryItems) {
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
}
