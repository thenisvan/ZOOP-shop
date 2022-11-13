import Helper.UserInput.ShopAction;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try
        {
            ShopAction.startShop();
        }
        catch (IOException exc)
        {
            throw new RuntimeException(exc);
        }
    }
}