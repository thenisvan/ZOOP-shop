import Helper.UserInput.ShopAction;
import Utils.ConsoleUtils;
import Utils.SOUT_utils;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try
        {
            ConsoleUtils.clearConsole();
            ShopAction.startShop();
        }
        catch (IOException exc)
        {
            throw new RuntimeException(exc);
        }
    }
}