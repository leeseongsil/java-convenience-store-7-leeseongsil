package store.domain;

import store.domain.receipt.PurchaseHistory;

public interface PromotionInventory {

    int countPurchasableQuantity(int purchaseCount);

    int countPurchasableQuantity();

    PurchaseHistory buy(int quantity);

    int countAddableFreeProductsWhenPurchase(int purchaseCount);
}
