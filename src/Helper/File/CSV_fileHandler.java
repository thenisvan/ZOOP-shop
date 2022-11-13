package Helper.File;

import Model.Buyer;
import Model.Item;
import Model.Admin;
import Model.BuyProcess;

import java.io.*;
import java.util.ArrayList;

import static Helper.File.CSV_fileWriter.writeToCSV;

public class CSV_fileHandler {
    protected static final ArrayList<Buyer> listOfBuyers = Admin.buyers;
    protected static final ArrayList<Item> inventoryItems = Admin.inventory;
    protected static final ArrayList<BuyProcess> listOfMMovements = Admin.movements;

    public static void createCSV(String fPath, String toBeWritten) {
        File file = new File(fPath);

        try {
            if (new File("data").mkdir()) {
                if (file.createNewFile())
                    writeToCSV(file, toBeWritten);
            } else {
                if (file.createNewFile())
                    writeToCSV(file, toBeWritten);
                System.out.print("");
            }
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    protected static void replaceFile(String old_filePath, String pathOfNewFile) {
        String currLine;

        try {
            BufferedReader buffReader = new BufferedReader(new FileReader(old_filePath));
            BufferedWriter buffWriter = new BufferedWriter(new FileWriter(pathOfNewFile));

            while ( (currLine = buffReader.readLine()) != null ) {
                buffWriter.write(currLine);
                buffWriter.newLine();
            }

            buffWriter.close();
            buffReader.close();

/*
              Delete Old file
              File toBeDeleted = new File(old_filePath);
              toBeDeleted.delete();
*/

        }
        catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}
