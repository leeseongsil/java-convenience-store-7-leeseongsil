package store.domain.inventory;

import store.domain.PromotionInventory;
import store.domain.promotion.Promotion;
import store.domain.receipt.PurchaseHistory;

public class InStockPromotionInventory implements PromotionInventory {

    private final String name;
    private final int perPrice;
    private int quantity;
    private final Promotion promotion;

    public InStockPromotionInventory(String name, int price, int quantity, Promotion promotion) {
        // TODO 유효성 검사 (price, quantity)
        this.name = name;
        this.perPrice = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    @Override
    public int countPurchasableQuantity(int purchaseCount) {
        return Math.min(purchaseCount, countPurchasableQuantity());
    }

    @Override
    public int countPurchasableQuantity() {
        if (promotion.isPromotionPeriod()) {
            return quantity;
        }
        return 0;
    }

    @Override
    public int countAddableFreeProductsWhenPurchase(int purchaseCount) {
        int afterPurchasableQuantity = Math.max(countPurchasableQuantity(purchaseCount) - purchaseCount, 0);
        return Math.min(promotion.countAddableFreeCount(purchaseCount), afterPurchasableQuantity);
    }

    @Override
    public PurchaseHistory buy(int quantity) {
        if (promotion.isPromotionPeriod()) {
            int freeCount = promotion.countFreeQuantity(quantity);
            PurchaseHistory purchaseHistory = new PurchaseHistory(name, perPrice, quantity, freeCount);

            this.quantity -= quantity;
            return purchaseHistory;
        }
        return PurchaseHistory.emptyHistory(name, perPrice);
    }
}
