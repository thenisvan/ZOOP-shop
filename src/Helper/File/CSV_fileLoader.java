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

    public static void items_load(File file_csvItems) {
        if (!file_csvItems.exists()) return;

        try (BufferedReader buffReader = new BufferedReader( new FileReader(file_csvItems)) ) {
            String cLine;
            String header = buffReader.readLine(); // this skips

            if (header == null)
                return; // nothing to-do here

            while ( (cLine =  buffReader.readLine()) !=  null) {
                String[] dChunk = cLine.split(",");
                Item i = new Item(dChunk[0], Double.parseDouble(dChunk[1]), Integer.parseInt(dChunk[2]));
                inventoryItems.add(i);
            }
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    public static void movements_load(File file_csvMovements) {
        if (!file_csvMovements.exists() )
            return;

        try (BufferedReader bufferedReader = new BufferedReader( new FileReader(file_csvMovements) ) ) {
            String cL;
            Buyer buyer = null;
            Item item = null;

            String header = bufferedReader.readLine(); // skop first line
            if (header == null) // if subor je empty tak return
                return;

            while ((cL = bufferedReader.readLine()) != null) {
                String[] dChunk = cL.split(",");
                String customerName = dChunk[0];
                String productName = dChunk[1];

                for (Buyer c : listOfBuyers) {
                    if (c.getFirstName().equals(customerName)) buyer = c;
                }

                for (Item p : inventoryItems) {
                    if (p.getItemName().equals(productName)) item = p;
                }

                if (buyer == null || item == null)
                    return;

                //? Makes a transaction based on the data to imitate loading from database.
                listOfMMovements.add(new BuyProcess(buyer, item));
                item.kolkoKupit(Integer.valueOf(dChunk[3]));

                List<Item> buyerBoughtItems = buyer.getOwnedItems();
                buyerBoughtItems.add(item);
            }


        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    public static void buyerCart_load(File CUSTOMER_CART_CSV) {
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
                    if (p.getItemName().equals(productName)) item = p;
                }

                if (buyer == null || item == null || customerCart == null) return;

                //? Makes a product and store to the customerCart based on the data to imitate loading from database.
                Item loadedItem = new Item(productName, Double.parseDouble(data[2]), item.getItemAmount());

                loadedItem.kolkoKupit(Integer.valueOf(data[3]));
                customerCart.add(loadedItem);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

      public static void buyers_load(File ACCOUNTS_CSV) {
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
