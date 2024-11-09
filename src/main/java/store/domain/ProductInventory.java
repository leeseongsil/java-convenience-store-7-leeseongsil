package store.domain;

public class ProductInventory {

    private final Promotion promotion;
    private final int price;
    private int currentCount;

    public ProductInventory(Promotion promotion, int price, int currentCount) {
        this.promotion = promotion;
        this.price = price;
        this.currentCount = currentCount;
    }

    public boolean isValidPromotion() {
        return promotion.isValid();
    }

    public int getCurrentCount() {
        return currentCount;
    }
}
