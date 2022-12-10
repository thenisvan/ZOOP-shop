package View;

import Utils.SOUT_utils;
import Model.Buyer;

public class BasicView implements Views{
    public void showMENU() {
        System.out.println("""
                *** (sample menu) ***
                9 -> Logout
                """);
        System.out.print("#: ");
    }

    @Override
    public void showDashboard() {
        return;
    }

    public void showUserInfo(Buyer buyer) {
        System.out.println("\n┌──────────────────────────────────────────");
        System.out.printf("" +
                "│ Firstname : %s\n" +
                "│ Lastname : %s", buyer.getFirstName(), buyer.getLastName());
        System.out.println("└──────────────────────────────────────────");
    }

    public void showUserMoney(Buyer buyer) {
        SOUT_utils.delayMessage(1, "Money: $" + buyer.getMoney());
    }

}
