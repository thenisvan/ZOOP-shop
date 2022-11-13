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
        Item item = BuyProcess.getItem();

        if (buyer.getMoney() < item.getItemPrice() * item.getAmount_toBuy()) {
            System.out.println("You need more money. ");
            return false;
        }

        double newBalance = (buyer.getMoney() - item.getItemPrice() * item.getAmount_toBuy());
        buyer.setBalance(newBalance);

        SOUT_utils.delayMessage(1, String.format("Your money: $%.2f ", newBalance));
        return true;
    }
}
