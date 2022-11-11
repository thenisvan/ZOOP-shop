package Controller;

import Model.shopOwner;
import Model.Product;

import java.util.List;

public class ProductActions {
    private final Product product;
    private final List<Product> productList = shopOwner.inventory;

    public ProductActions(Product product) {
        this.product = product;
    }

    public void updateProductQuantity(int bought) {
        product.setProductQuantity(product.getProductQuantity() - bought);
    }

    public void updateProduct() {
        if (product.getProductQuantity() == 0) {
            productList.remove(product);
        }
    }
}
