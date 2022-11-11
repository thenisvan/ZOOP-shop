package Model;

public class BuyProcess {
    private final Buyer buyer;
    private final Product product;

    public BuyProcess(Buyer c, Product p) {
        this.buyer = c;
        this.product = p;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%.1f,%d", buyer.getFirstName(), product.getProductName(), product.getProductPrice(), product.getAmount_toBuy());
    }

    public String showInfo() {
        return product.getProductQuantity() + "x of " + product.getProductName();
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public Product getProduct() {
        return product;
    }

}
