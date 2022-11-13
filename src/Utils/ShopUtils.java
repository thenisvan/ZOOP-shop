package Utils;

import Utils.SOUT_utils;
import Model.Buyer;
import Model.Item;

import java.util.List;

public class ShopUtils {
    public static boolean isInputInvalid(String... strings) {
        boolean flag = false;

        for (String str : strings) {
            if (str == null || str.trim().isEmpty()) {
                flag = true;
                SOUT_utils.delayMessage(1, "Input is wrong!");
                break;
            }
        }
        return flag;
    }

    public static boolean containLetter(String i) {
        if (i.matches("[A-Za-z]*"))
        {
            System.out.println("""
                    |+++++++++++++++++++++++++++++++++++++++++++++++|
                    |+   Your Input Contains Alpha Characters !!   +|
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

    public static boolean hasBuyers(List<Buyer> buyers) {
        boolean flag = true;

        if (buyers.size() == 0) {
            SOUT_utils.delayMessage(1, "There are no buyers!");
            flag = false;
        }
        return flag;
    }

    public static boolean isCartEmpty(List<Item> items) {
        boolean flag = false;

        if (items.size() ==0 )
        {
            SOUT_utils.delayMessage(1, "Your cart is empty!");
            flag = true;
        }
        return flag;
    }
}
