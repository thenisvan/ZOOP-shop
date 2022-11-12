package Helper.File;

import Model.Buyer;
import Model.Item;
import Model.Admin;
import Model.BuyProcess;

import java.io.*;
import java.util.List;

import static Helper.File.CSV_fileWriter.writeToFile;

public class CSV_fileHandler {
    protected static final List<Buyer> listOfBuyers = Admin.buyers;
    protected static final List<Item> inventoryItems = Admin.inventory;
    protected static final List<BuyProcess> listOfMMovements = Admin.movements;

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

    protected static void replaceFile(String pathOfOldFile, String pathOfNewFile) {
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
