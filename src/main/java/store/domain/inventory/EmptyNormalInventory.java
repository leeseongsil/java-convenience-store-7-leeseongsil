package store.domain.inventory;

import store.domain.NormalInventory;
import store.domain.receipt.PurchaseHistory;

public class EmptyNormalInventory implements NormalInventory {

    private final String name;
    private final int perPrice;

    public EmptyNormalInventory(String name, int perPrice) {
        this.name = name;
        this.perPrice = perPrice;
    }

    @Override
    public int countQuantity() {
        return 0;
    }

    @Override
    public PurchaseHistory buy(int quantity) {
        return PurchaseHistory.emptyHistory(name, perPrice);
    }
}
