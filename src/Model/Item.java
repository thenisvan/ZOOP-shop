package Model;

public class Item {
    private final String productName;
    private final Double productPrice;
    private Integer amount_available;
    private Integer amount_toBuy;

    public Item(String productName, Double productPrice, Integer productQuantity) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.amount_available = productQuantity;
    }

    @Override
    public String toString() {
        return String.format("%s,%.2f,%d", getProductName(), getProductPrice(), getProductQuantity());
    }

    public String getProductName() {
        return productName;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public Integer getProductQuantity() {
        return amount_available;
    }

    public void setProductQuantity(Integer i) {
        amount_available = i;
    }

    public Integer getAmount_toBuy() {
        return amount_toBuy;
    }

    public void setAmount_toBuy(Integer amount_toBuy) {
        this.amount_toBuy = amount_toBuy;
    }
}
