package Controller;

import Utils.SOUT_utils;
import Model.Buyer;
import Model.Item;
import Model.BuyProcess;

public class MoneyMovementActions {
    private final BuyProcess BuyProcess;

    public int calculatePoints(Item item, int amount){
        return (int) (item.getItemPrice() * amount / 10);
    }
    public double calculateDiscount(Double price, int points){
        if (price > 10)
            if (points <= 10)
                return price - (price*0.10);
            else if (points <=20)
                return price - (price*0.15); //15% off)
            else if (points <=50)
                return price - (price*0.40); //15% off)
            else
                return price - (price*0.50); //15% off)
        else
            return price - (price*0);
    }
    public MoneyMovementActions(BuyProcess buyProcess) {
        this.BuyProcess = buyProcess;
    }

    public boolean startTransaction() {
        Buyer buyer = BuyProcess.getBuyer();
        Item item = BuyProcess.getItem();
        double subTotal = item.getItemPrice() * item.getAmount_toBuy();
        double discount = calculateDiscount(subTotal, buyer.getPoints());
        double discountedPrice = subTotal-discount;
        int pointsFetched = calculatePoints(item, item.getAmount_toBuy());

//        TODO: ask how many points buyer wants to use // or nah

        if (buyer.getMoney() < ( discountedPrice )) {
            System.out.println("You need more money. ");
            return false;
        }

        double newBalance = ( ( buyer.getMoney() -( discountedPrice )));
        System.out.printf("Points used: %d\nDiscount: %f\n",buyer.getPoints(),discount );
        buyer.setBalance(newBalance);
        buyer.setPoints(/*buyer.getPoints()+*/pointsFetched);

        SOUT_utils.delayMessage(1, String.format("Your money: $%.2f ", newBalance));
        return true;
    }
}
