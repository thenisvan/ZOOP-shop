package Controller;

import Utils.SOUT_utils;
import Model.Buyer;
import Model.Item;
import Model.BuyProcess;

public class MoneyMovementActions {
    private final BuyProcess BuyProcess;

    public int calculatePoints(Item item, int amount){
        return 50;
    }
    public Double calculateDiscount(Double price, int points){
        //this is where magic happen
        return 0.15;
    }
    public MoneyMovementActions(BuyProcess buyProcess) {
        this.BuyProcess = buyProcess;
    }

    public boolean startTransaction() {
        Buyer buyer = BuyProcess.getBuyer();
        Item item = BuyProcess.getItem();

        if (buyer.getMoney() < item.getItemPrice() * item.getAmount_toBuy() /*TODO: implement discount based on user points*/) {
            System.out.println("You need more money. ");
            return false;
        }

        double newBalance = (buyer.getMoney() - item.getItemPrice() * item.getAmount_toBuy() /*TODO: implement discount based on user points*/);
        buyer.setBalance(newBalance);
        buyer.setPoints(buyer.getPoints()*item.getAmount_toBuy());

        SOUT_utils.delayMessage(1, String.format("Your money: $%.2f ", newBalance));
        return true;
    }
}
