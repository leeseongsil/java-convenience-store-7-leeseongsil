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
    public int countPurchasableQuantity(int purchaseQuantity) {
        return Math.min(purchaseQuantity, countPurchasableQuantity());
    }

    @Override
    public int countPurchasableQuantity() {
        if (promotion.isPromotionPeriod()) {
            return quantity;
        }
        return 0;
    }

    @Override
    public int countAddableFreeProducts(int purchaseQuantity) {
        int afterPurchasableQuantity = Math.max(countPurchasableQuantity(purchaseQuantity) - purchaseQuantity, 0);
        return Math.min(promotion.countAddableFreeCount(purchaseQuantity), afterPurchasableQuantity);
    }

    @Override
    public boolean isLackPromotionQuantity(int purchaseQuantity) {
        if (promotion.isPromotionPeriod()) {
            return purchaseQuantity + promotion.countAddableFreeCount(purchaseQuantity) > quantity;
        }
        return false;
    }

    @Override
    public int countRegularPriceQuantity(int purchaseQuantity) {
        int appliedPromotionQuantity = promotion.countAppliedPromotionQuantity(purchaseQuantity);
        return Math.max(purchaseQuantity - appliedPromotionQuantity, 0);
    }

    @Override
    public PurchaseHistory buy(int quantity) {
        if (promotion.isPromotionPeriod()) {
            PurchaseHistory purchaseHistory = createPurchaseHistory(quantity);
            this.quantity -= quantity;
            return purchaseHistory;
        }
        return PurchaseHistory.emptyHistory(name, perPrice);
    }

    private PurchaseHistory createPurchaseHistory(int quantity) {
        int freeQuantity = promotion.countFreeQuantity(quantity);
        int appliedPromotionQuantity = promotion.countAppliedPromotionQuantity(quantity);
        return new PurchaseHistory(name, perPrice, quantity, appliedPromotionQuantity, freeQuantity);
    }
}
