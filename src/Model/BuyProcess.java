package Model;

public class BuyProcess {
    private final Buyer buyer;
    private final Item item;

    public BuyProcess(Buyer c, Item p) {
        this.buyer = c;
        this.item = p;
    }

//    @Override
//    public String toString() {
//        return String.format("%s,%s,%.1f,%d", buyer.getFirstName(), item.getItemName(), item.getItemPrice(), item.getAmount_toBuy());
//    }

    public String showInfo() {
        return item.getItemAmount() + "ks - " + item.getItemName();
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public Item getItem() {
        return item;
    }

}
