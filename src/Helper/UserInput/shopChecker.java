package Helper.UserInput;

import Utils.SOUT_utils;
import Model.Buyer;
import Model.Item;

import java.util.List;

public class shopChecker {
    public static boolean isInputInvalid(String... params) {
        boolean result = false;

        for (String param : params) {
            if (param == null || param.trim().isEmpty()) {
                result = true;
                SOUT_utils.delayMessage(1, "Please perform proper input!");
                break;
            }
        }

        return result;
    }

    public static boolean containLetter(String i) {
        if (i.matches("[A-Za-z]*"))
        {
            System.out.println("""
                    |+++++++++++++++++++++++++++++++++++++++++++++++|
                    |+   Your Input Contains Alpha Character  !!   +|
                    |+++++++++++++++++++++++++++++++++++++++++++++++|
                    """);
            return true;
        }
        return false;
    }

    public static void numFormatException() {
        System.out.println("""
                |+++++++++++++++++++++++++++++++++++++++++++|
                |+        This Type Is Not Defined !!      +|
                |+++++++++++++++++++++++++++++++++++++++++++|
                """);
        SOUT_utils.delayMessage(1, "Going back...");
    }

    public static void outOfRangeException() {
        System.out.println("""
                                
                |+++++++++++++++++++++++++++++++++++++++++++|
                |*        Undefined Number Range!!!        *|
                |+++++++++++++++++++++++++++++++++++++++++++|
                """);
        SOUT_utils.delayMessage(1, "Going back...");
    }

    public static boolean hasCustomers(List<Buyer> buyers) {
        boolean result = true;

        if (buyers.size() == 0) {
            SOUT_utils.delayMessage(1, "There are no buyers!");
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
