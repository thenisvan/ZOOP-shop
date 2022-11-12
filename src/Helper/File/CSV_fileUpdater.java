package Helper.File;

import java.io.*;

public class CSV_fileUpdater extends CSV_fileWriter{

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

}
