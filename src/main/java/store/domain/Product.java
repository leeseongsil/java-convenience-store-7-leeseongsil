package store.domain;

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
}
