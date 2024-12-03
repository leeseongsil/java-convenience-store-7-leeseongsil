package store.domain.inventory;

import store.domain.PromotionInventory;
import store.domain.promotion.Promotion;

public class InStockPromotionInventory implements PromotionInventory {

    private final String name;
    private final int price;
    private final int quantity;
    private final Promotion promotion;

    public InStockPromotionInventory(String name, int price, int quantity, Promotion promotion) {
        // TODO 유효성 검사 (price, quantity)
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    @Override
    public int countPurchasableQuantity() {
        if (promotion.isPromotionPeriod()) {
            return quantity;
        }
        return 0;
    }
}
