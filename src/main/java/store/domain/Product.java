package store.domain;

import store.domain.receipt.PurchaseHistory;

public class Product {

    private final PromotionInventory promotionInventory;
    private final NormalInventory normalInventory;

    public Product(PromotionInventory promotionInventory, NormalInventory normalInventory) {
        this.promotionInventory = promotionInventory;
        this.normalInventory = normalInventory;
    }

    public boolean canBuy(int count) {
        int totalQuantity = promotionInventory.countPurchasableQuantity() + normalInventory.countQuantity();
        return count >= totalQuantity;
    }

    public PurchaseHistory buy(int count) {
        validateBuying(count);

        int promotionCount = promotionInventory.countPurchasableQuantity(count);
        int normalCount = count - promotionCount;
        PurchaseHistory promotionPurchaseHistory = promotionInventory.buy(promotionCount);
        PurchaseHistory normalPurchaseHistory = normalInventory.buy(normalCount);
        return promotionPurchaseHistory.join(normalPurchaseHistory);
    }

    private void validateBuying(int count) {
        if (canBuy(count)) {
            return;
        }
        throw new IllegalStateException("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
    }
}
