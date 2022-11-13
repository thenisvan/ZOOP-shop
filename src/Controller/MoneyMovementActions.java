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
            System.out.println("You need more money. ");
            return false;
        }

        double newBalance = buyer.getBalance() - item.getProductPrice() * item.getAmount_toBuy();
        buyer.setBalance(newBalance);

        SOUT_utils.delayMessage(1, String.format("Your current balance is: %.2f", newBalance));
        return true;
    }
}
