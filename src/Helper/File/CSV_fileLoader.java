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

        try (BufferedReader buffReader = new BufferedReader( new FileReader(file_csvMovements) ) ) {
            String cL;
            Buyer buyer = null;
            Item item = null;

            String header = buffReader.readLine(); // skop first line
            if (header == null) // if subor je empty tak return
                return;

            while ((cL = buffReader.readLine()) != null) {
                String[] dChunk = cL.split(",");
                String buyerName = dChunk[0];
                String itemName = dChunk[1];

                for (Buyer c : listOfBuyers)
                    if (c.getFirstName().equals(buyerName)) buyer = c;

                for (Item p : inventoryItems)
                    if (p.getItemName().equals(itemName)) item = p;

                if (buyer == null || item == null) return;

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

    public static void buyerCart_load(File file_buyerCartItems) {
        if (!file_buyerCartItems.exists()) return;

        try (BufferedReader buffReader = new BufferedReader(new FileReader(file_buyerCartItems))) {
            String curLine;
            Buyer buyer = null;
            List<Item> buyerCart = null;
            Item item = null;

            String firstLine = buffReader.readLine(); // eats the firstLine
            if (firstLine == null) return;

            while ((curLine = buffReader.readLine()) != null) {
                String[] dChunk = curLine.split(",");
                String buyerName = dChunk[0];
                String itemName = dChunk[1];

                for (Buyer c : listOfBuyers) {
                    if (c.getFirstName().equals(buyerName)) {
                        buyer = c;
                        buyerCart = buyer.getMyCart();
                    }
                }

                for (Item p : inventoryItems) {
                    if (p.getItemName().equals(itemName)) item = p;
                }

                if (buyer == null || item == null || buyerCart == null) return;

                Item loadedItem = new Item(itemName, Double.parseDouble(dChunk[2]), item.getItemAmount());

                loadedItem.kolkoKupit(Integer.valueOf(dChunk[3]));
                buyerCart.add(loadedItem);
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

                Buyer buyer = new Buyer(data[0], data[1], data[2], data[3]);
                buyer.setBalance(Double.parseDouble(data[4]));

                listOfBuyers.add(buyer);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
