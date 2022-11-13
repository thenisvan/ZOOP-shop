package Controller;

import Model.Admin;
import Model.Item;

import java.util.ArrayList;
import java.util.List;

public class ProductActions {
    private final Item item;
    private final ArrayList<Item> itemList = Admin.inventory;

    public ProductActions(Item item) {
        this.item = item;
    }
    public void updateItemAmount(int toBuy) {
        item.setItemAmount(item.getItemAmount() - toBuy);
    }
    public void updateItem() {
        if (item.getItemAmount() == 0) {
            itemList.remove(item);
        }
    }
}
