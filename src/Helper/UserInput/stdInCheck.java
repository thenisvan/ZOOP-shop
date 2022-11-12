package Helper.UserInput;

import Utils.SOUT_utils;
import Model.Buyer;
import Model.Item;

import java.util.List;

public class stdInCheck {
    public static boolean isInputInvalid(String... params) {
        boolean result = false;

        for (String param : params) {
            if (param == null || param.trim().isEmpty()) {
                result = true;
                SOUT_utils.delayMessage(1, "Please answer properly!");
                break;
            }
        }

        return result;
    }

    public static boolean containLetter(String i) {
        if (i.matches("[A-Za-z]*")) {
            System.out.println("""
                    |+++++++++++++++++++++++++++++++++++|
                    |*   Only Numbers Are Allowed !!   *|
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
        SOUT_utils.delayMessage(1, "Going back...");
    }

    public static void printIndexOutOfBoundsExceptionMessage() {
        System.out.println("""
                                
                |+++++++++++++++++++++++++++++++++++++++++++|
                |*   Please input from the range only!!!   *|
                |+++++++++++++++++++++++++++++++++++++++++++|
                """);
        SOUT_utils.delayMessage(1, "Redirecting you back to the shop...");
    }

    public static boolean hasCustomers(List<Buyer> buyers) {
        boolean result = true;

        if (buyers.size() == 0) {
            SOUT_utils.delayMessage(1, "No customers for now!");
            result = false;
        }
        return result;
    }

    public static boolean isCartEmpty(List<Item> items) {
        boolean result = false;

        if (items.size() == 0) {
            SOUT_utils.delayMessage(1, "Your cart is empty!");
            result = true;
        }
        return result;
    }
}
