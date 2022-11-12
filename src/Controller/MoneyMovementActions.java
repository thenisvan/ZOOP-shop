package Controller;

import Utils.SOUT_utils;
import Model.Buyer;
import Model.Item;
import Model.BuyProcess;

public class MoneyMovementActions {
    private final BuyProcess BuyProcess;

    public MoneyMovementActions(BuyProcess buyProcess) {
        this.BuyProcess = buyProcess;
    }

    public boolean startTransaction() {
        Buyer buyer = BuyProcess.getBuyer();
        Item item = BuyProcess.getProduct();

        if (buyer.getBalance() < item.getProductPrice() * item.getAmount_toBuy()) {
            System.out.println("You have insufficient amount of money. ");
            return false;
        }

        double newBalance = buyer.getBalance() - item.getProductPrice() * item.getAmount_toBuy();
        buyer.setBalance(newBalance);

        SOUT_utils.sleep(1, String.format("Success! your new balance is P%.1f", newBalance));
        return true;
    }
}
