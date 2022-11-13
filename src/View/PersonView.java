package View;

import Utils.SOUT_utils;
import Utils.ShopUtils;
import Model.Item;
import java.util.List;

public class PersonView extends BasicView {
    public void showMENU() {
        System.out.println("""
                \n
                0.) Print All My Information
                1.) Add Money to account
                2.) Go shoppin'
                3.) View basic Info
                4.) View My Balance
                5.) Display Cart
                6.) Delete Cart
                7.) Buy items osaved on Card
                8.) View my inventory
                9.) Log Out
                """);
        System.out.print("#: ");
    }

    public void viewMyCart(List<Item> cC) {
        if (ShopUtils.hasNoItems(cC))
            return;

        System.out.println("\nU have following items on cart");
        cC.forEach(cartItem -> {
            System.out.println("\n┌────────────────────────────────");
            System.out.printf("""
                    │ Item name: %s
                    │ Item price: %.1f
                    │ Amount: %d
                    """, cartItem.getItemName(), cartItem.getItemPrice(), cartItem.getAmount_toBuy());
            System.out.println("\n└────────────────────────────────");
        });
    }

    public void viewOwnedItems(List<Item> ownedItems) {
        if (ownedItems.size() == 0) {
            SOUT_utils.delayMessage(1, "You have no Items!");
            return;
        }

        System.out.println("\nYour Items");
        ownedItems.forEach(ownedItem -> {
            System.out.println("\n┌────────────────────────────────");
            System.out.printf("""
                  │ Item name: %s
                  │ Item price: %.1f
                  │ Amount: %d
                  """, ownedItem.getItemName(), ownedItem.getItemPrice(), ownedItem.getAmount_toBuy());
            System.out.println("\n└────────────────────────────────");
        });
    }
}
