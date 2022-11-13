package Model;

public class Item {
    private final String itemName;
    private final Double itemPrice;
    private Integer amount_available;
    private Integer amount_toBuy;

    public Item(String itemName, Double itemPrice, Integer itemAmount) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.amount_available = itemAmount;
    }

    @Override
    public String toString() {
        return String.format("%s,%.2f,%d", getItemName(), getItemPrice(), getItemAmount());
    }

    public String getItemName() {
        return itemName;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public Integer getItemAmount() {
        return amount_available;
    }

    public void setItemAmount(Integer i) {
        amount_available = i;
    }

    public Integer getAmount_toBuy() {
        return amount_toBuy;
    }

    public void kolkoKupit(Integer amount_toBuy) {
        this.amount_toBuy = amount_toBuy;
    }
}
