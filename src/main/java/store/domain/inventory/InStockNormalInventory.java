package store.domain.inventory;

import store.domain.NormalInventory;

public class InStockNormalInventory implements NormalInventory {

    private final String name;
    private final int price;
    private final int quantity;

    public InStockNormalInventory(String name, int price, int quantity) {
        // TODO 유효성 검사 (price, quantity)
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public int countQuantity() {
        return quantity;
    }
}
