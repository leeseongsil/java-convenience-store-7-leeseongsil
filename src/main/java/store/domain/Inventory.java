package store.domain;

import store.domain.result.PurchaseHistory;

public interface Inventory {

    boolean isInPeriod();

    boolean isExistProduct();

    int countPurchasableProducts();

    int countPurchasableProducts(int purchaseCount);

    PurchaseHistory buy(String name, int count);
}
