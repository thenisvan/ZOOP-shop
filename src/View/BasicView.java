package View;

import Utils.SOUT_utils;
import Model.Buyer;

public class BasicView {
    public void showDashboard() {
        System.out.println("""
                What do you want to do?
                
                *** (sample menu) ***
                
                9 -> Logout
                """);
        System.out.print("#: ");
    }

    public void showUserInfo(Buyer buyer) {
        System.out.println("\n+++++++++++++++++++++++++++++");
        System.out.printf("Firstname : %s\nLastname : %s", buyer.getFirstName(), buyer.getLastName());
        System.out.println("+++++++++++++++++++++++++++++");
    }

    public void showUserBalance(Buyer buyer) {
        SOUT_utils.sleep(1, "Balance: P" + buyer.getBalance());
    }

}
