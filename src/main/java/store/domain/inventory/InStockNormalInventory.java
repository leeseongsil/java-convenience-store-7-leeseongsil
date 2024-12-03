package store.domain.inventory;

import store.domain.NormalInventory;
import store.domain.receipt.PurchaseHistory;

public class InStockNormalInventory implements NormalInventory {

    private final String name;
    private final int perPrice;
    private int quantity;

    public InStockNormalInventory(String name, int price, int quantity) {
        // TODO 유효성 검사 (price, quantity)
        this.name = name;
        this.perPrice = price;
        this.quantity = quantity;
    }

    @Override
    public int countQuantity() {
        return quantity;
    }

    @Override
    public PurchaseHistory buy(int quantity) {
        validateBuying(quantity);

        PurchaseHistory purchaseHistory = new PurchaseHistory(name, perPrice, quantity);
        this.quantity -= quantity;
        return purchaseHistory;
    }

    private void validateBuying(int quantity) {
        if (quantity >= 0 || quantity <= this.quantity) {
            return;
        }
        throw new IllegalStateException("Not enough quantity");
    }
}
