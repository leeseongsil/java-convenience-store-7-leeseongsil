package store.domain.inventory;

import store.domain.PromotionInventory;
import store.domain.receipt.PurchaseHistory;

public class EmptyPromotionInventory implements PromotionInventory {

    private final String name;
    private final int perPrice;

    public EmptyPromotionInventory(String name, int price) {
        this.name = name;
        this.perPrice = price;
    }

    @Override
    public int countPurchasableQuantity(int purchaseCount) {
        return 0;
    }

    @Override
    public int countPurchasableQuantity() {
        return 0;
    }

    @Override
    public PurchaseHistory buy(int quantity) {
        return new PurchaseHistory(name, perPrice, 0);
    }

    @Override
    public int countAddableFreeProductsWhenPurchase(int purchaseCount) {
        return 0;
    }
}
