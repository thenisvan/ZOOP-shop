package Helper.File;

import java.io.*;

public class CSV_fileUpdater extends CSV_fileWriter{

    public static void update_BuyerCardItems(String uName) {
        File tmp = new File("data/tmpCart.csv");
        File BuyerCardItemsFile = new File("data/cart.csv");

        try (BufferedReader bReader = new BufferedReader( new FileReader(BuyerCardItemsFile) ) ) {
            if (tmp.createNewFile()) {
                FileWriter fW = new FileWriter( tmp, true);

                String cL;

                while ((cL = bReader.readLine()) != null) {
                    String[] dChunk = cL.split(",");
                    String buyer_name = dChunk[0];

                    if (buyer_name.equals(uName)) continue;

                    fW.write(cL +"\n");
                }
                fW.close();
                replaceFile( tmp.toString(), BuyerCardItemsFile.toString());
            }

        } catch (IOException ext) { ext.printStackTrace(); }
    }

}
