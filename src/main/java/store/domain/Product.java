package store.domain;

import store.domain.receipt.PurchaseHistory;

public class Product {

    private final PromotionInventory promotionInventory;
    private final NormalInventory normalInventory;

    public Product(PromotionInventory promotionInventory, NormalInventory normalInventory) {
        this.promotionInventory = promotionInventory;
        this.normalInventory = normalInventory;
    }

    public boolean canBuy(int purchaseQuantity) {
        return purchaseQuantity <= totalQuantity();
    }

    public int countAddableFreeProducts(int purchaseQuantity) {
        validateBuying(purchaseQuantity);
        return promotionInventory.countAddableFreeProducts(purchaseQuantity);
    }

    public boolean isLackPromotionQuantity(int purchaseQuantity) {
        validateBuying(purchaseQuantity);
        return promotionInventory.isLackPromotionQuantity(purchaseQuantity);
    }

    public int countRegularPriceQuantity(int purchaseQuantity) {
        validateBuying(purchaseQuantity);
        return promotionInventory.countRegularPriceQuantity(purchaseQuantity);
    }

    public PurchaseHistory buy(int count) {
        validateBuying(count);

        int promotionCount = promotionInventory.countPurchasableQuantity(count);
        int normalCount = count - promotionCount;
        PurchaseHistory promotionPurchaseHistory = promotionInventory.buy(promotionCount);
        PurchaseHistory normalPurchaseHistory = normalInventory.buy(normalCount);
        return promotionPurchaseHistory.join(normalPurchaseHistory);
    }

    private void validateBuying(int purchaseQuantity) {
        int totalQuantity = totalQuantity();
        if (purchaseQuantity > totalQuantity) {
            throw new IllegalStateException(
                    "Too Many Purchases purchased : %d, totol : %d".formatted(purchaseQuantity, totalQuantity));
        }
    }

    private int totalQuantity() {
        return promotionInventory.countPurchasableQuantity() + normalInventory.countQuantity();
    }
}
