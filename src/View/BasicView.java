package View;

import Helper.UIHelper;
import Model.Customer;

public class BasicView {
    public void showDashboard() {
        System.out.println("""
                What do you want to do?
                
                *** (sample menu) ***
                
                9 -> Logout
                """);
        System.out.print("#: ");
    }

    public void showUserInfo(Customer customer) {
        System.out.println("\n+++++++++++++++++++++++++++++");
        System.out.printf("Firstname : %s\nLastname : %s", customer.getFirstName(), customer.getLastName());
        System.out.println("+++++++++++++++++++++++++++++");
    }

    public void showUserBalance(Customer customer) {
        UIHelper.sleep(1, "Balance: P" + customer.getBalance());
    }

}
