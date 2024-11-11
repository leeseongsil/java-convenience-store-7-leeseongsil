package store.domain.inventory;

import store.domain.Inventory;
import store.domain.result.PurchaseHistory;

public class EmptyInventory implements Inventory {

    @Override
    public boolean isInPeriod() {
        return false;
    }

    @Override
    public boolean isExistProduct() {
        return false;
    }

    @Override
    public int countPurchasableProducts() {
        return 0;
    }

    @Override
    public int countPurchasableProducts(int purchaseCount) {
        return 0;
    }

    @Override
    public PurchaseHistory buy(String name, int count) {
        return PurchaseHistory.emptyHistory(name);
    }
}
