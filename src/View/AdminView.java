package View;

import Utils.Banners;
import Utils.SOUT_utils;
import Utils.ShopUtils;
import Model.Buyer;
import Model.Item;
import Model.BuyProcess;

import java.util.List;

public class AdminView extends BasicView {
    public void showMENU() {
        System.out.println("""
                What do you want to do?
                1.) Create new item
                2.) Show Money Movements
                3.) Show info about Buyers
                4.) Show List of Items
                5.) Remove buyer
                6.) Logout
                """);
        System.out.print("-> ");
    }
    public void showDashboard(Boolean printBanner) {
        if (printBanner) Banners.printRandomAdminBanner(false);
        this.showMENU();
    }

    public void showMoneyMovement(List<BuyProcess> m) {
        int size = m.size();

        if (size == 0) {
            SOUT_utils.delayMessage(1, "No transactions has been mate till now!");
            return;
        }

        int[] i = {0};
        System.out.println("╔═════════════════════════════════════════╗");
        System.out.println("║            Money Movements              ║");
        System.out.println("├─────────────────────────────────────────┤");
        m.forEach(transaction -> {
            System.out.print("│ " + transaction.getBuyer().getFirstName() + " " + transaction.getBuyer().getLastName() + " bought " + transaction.showInfo());
            if (size != 1 && size != i[0] + 1) System.out.println("\n├─────────────────────────────────────────┤");
            i[0]++;
        });
        System.out.println("\n└─────────────────────────────────────────┘");


    }


    public void showBuyerInfo(List<Buyer> c) {
        if (!ShopUtils.hasBuyers(c)) return;
        int size = c.size();
        int[] i = {0};
        System.out.println("\n╔═════════════════════════════════════════╗");
        System.out.println("║             Buyer Information           ║");
        System.out.println("├─────────────────────────────────────────┤");
        c.forEach(buyer -> {
            System.out.printf("│ Firstname: %s\n│ Lastname: %s", buyer.getFirstName(), buyer.getLastName());
            if (size != 1 && size != i[0] + 1)
                System.out.println("\n├─────────────────────────────────────────┤");
            i[0]++;
        });
        System.out.println("\n└─────────────────────────────────────────┘");
    }

    public void showItems(List<Item> p) {
        if (p.size() == 0) {
            SOUT_utils.delayMessage(1, "You haven't add items yet!");
            return;
        }

        SOUT_utils.delayMessage(1, "");
        p.forEach(item -> {
            System.out.println("\n┌──────────────────────────────────────────");
            System.out.printf("""
            │ Items name: %s
            │ Items price: %.2f
            │ Items quantity: %d
            """, item.getItemName(), item.getItemPrice(), item.getItemAmount());
        });
        System.out.println("└──────────────────────────────────────────");
    }
}
