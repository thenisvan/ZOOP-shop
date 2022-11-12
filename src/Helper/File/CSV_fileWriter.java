package Helper.File;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSV_fileWriter extends CSV_fileHandler {
        public static void writeToFile(File f, String ctx) {
        try ( FileWriter fw = new FileWriter(f, true ) ) {
            fw.append(ctx);
            fw.flush();
        }
        catch (IOException e) {
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
}
