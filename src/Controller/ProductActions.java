package Controller;

import Model.Admin;
import Model.Item;

import java.util.List;

public class ProductActions {
    private final Item item;
    private final List<Item> itemList = Admin.inventory;

    public ProductActions(Item item) {
        this.item = item;
    }

    public void updateProductQuantity(int bought) {
        item.setProductQuantity(item.getProductQuantity() - bought);
    }

    public void updateProduct() {
        if (item.getProductQuantity() == 0) {
            itemList.remove(item);
        }
    }
}
