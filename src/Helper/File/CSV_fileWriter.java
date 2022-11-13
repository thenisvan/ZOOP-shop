package Helper.File;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSV_fileWriter extends CSV_fileHandler {
        public static void writeToCSV(File f, String ctx) {
        try ( FileWriter fw = new FileWriter(f, true ) ) {
            fw.append(ctx);
            fw.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
        public static void moneyMovementsWriter(String ctx) {
        File f = new File("data/movements.csv");

        try (FileWriter wr = new FileWriter(f, true)) {
            wr.append(ctx);
            wr.flush();
        } catch (IOException ext) {ext.printStackTrace();}
    }
}
