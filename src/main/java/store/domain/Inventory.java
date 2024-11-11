package store.domain;

import store.domain.result.PurchaseHistory;

public interface Inventory {

    boolean isInPeriod();

    int countPurchasableProducts();

    int countPurchasableProducts(int purchaseCount);

    int countFreeProductsWhenPurchased(int purchaseCount);

    PurchaseHistory buy(String name, int count);
}
