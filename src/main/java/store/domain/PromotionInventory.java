package store.domain;

import store.domain.receipt.PurchaseHistory;

public interface PromotionInventory {

    int countPurchasableQuantity(int purchaseQuantity);

    int countPurchasableQuantity();

    int countAddableFreeProducts(int purchaseQuantity);

    boolean isLackPromotionQuantity(int purchaseQuantity);

    int countRegularPriceQuantity(int purchaseQuantity);

    PurchaseHistory buy(int quantity);
}
