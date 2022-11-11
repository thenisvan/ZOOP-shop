package Helper.UserInput;

import Helper.Output_STD_functions;
import Model.Buyer;
import Model.Product;

import java.util.List;

public class InputChecker {
    /*
     * @param params... {n number of params to be checked.}
     * @return boolean {returns true if one of the params is null or empty.}
     */
    public static boolean isInputInvalid(String... params) {
        boolean result = false;

        for (String param : params) {
            if (param == null || param.trim().isEmpty()) {
                result = true;
                Output_STD_functions.sleep(1, "Please answer properly!");
                break;
            }
        }

        return result;
    }

    /*
     * @param input {User input to be checked.}
     * @return boolean {returns true if the input has letters.}
     */
    public static boolean containLetter(String input) {
        if (input.matches("[A-Za-z]*")) {
            System.out.println("""
                                            
                    |+++++++++++++++++++++++++++++++++++|
                    |*   Please enter numbers only!!   *|
                    |+++++++++++++++++++++++++++++++++++|
                    """);
            return true;
        }
        return false;
    }

    public static void printNumberFormatExceptionMessage() {
        System.out.println("""
                                
                |+++++++++++++++++++++++++++++++++++++++++++|
                |*   Please enter the appropriate type!!   *|
                |+++++++++++++++++++++++++++++++++++++++++++|
                """);
        Output_STD_functions.sleep(1, "Redirecting you back to the shop...");
    }

    public static void printIndexOutOfBoundsExceptionMessage() {
        System.out.println("""
                                
                |+++++++++++++++++++++++++++++++++++++++++++|
                |*   Please input from the range only!!!   *|
                |+++++++++++++++++++++++++++++++++++++++++++|
                """);
        Output_STD_functions.sleep(1, "Redirecting you back to the shop...");
    }

    public static boolean hasCustomers(List<Buyer> buyers) {
        boolean result = true;

        if (buyers.size() == 0) {
            Output_STD_functions.sleep(1, "No customers for now!");
            result = false;
        }
        return result;
    }

    public static boolean isCartEmpty(List<Product> products) {
        boolean result = false;

        if (products.size() == 0) {
            Output_STD_functions.sleep(1, "Your cart is empty!");
            result = true;
        }
        return result;
    }
}
