import Helper.UserInput.ShopHelper;
import Utils.SOUT_utils;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        SOUT_utils.delayMessage(0,"Bithces gonna have myb money");
        try {
            ShopHelper.openShop();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}