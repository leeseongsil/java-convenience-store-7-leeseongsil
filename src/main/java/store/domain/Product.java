package store.domain;

import store.domain.inventory.ProductInventory;
import store.domain.result.PurchaseHistory;

public class Product {

    private final String name;
    private final ProductInventory promotionInventory;
    private final ProductInventory noPromotionInventory;

    public Product(String name, ProductInventory promotionInventory, ProductInventory noPromotionInventory) {
        this.name = name;
        this.promotionInventory = promotionInventory;
        this.noPromotionInventory = noPromotionInventory;
    }

    public boolean canBuy(int count) {
        return count <= totalPurchasableProduct();
    }

    private int totalPurchasableProduct() {
        return promotionInventory.countPurchasableProducts() + noPromotionInventory.countPurchasableProducts();
    }

    public int countFreeProductsWhenPurchased(int purchasedCount) {
        return promotionInventory.countFreeProductsWhenPurchased(purchasedCount);
    }

    public PurchaseHistory buy(int count) {
        validateBuying(count);

        int countOfPromotionProduct = promotionInventory.countPurchasableProducts(count);
        int countOfNoPromotionProduct = count - countOfPromotionProduct;
        PurchaseHistory promotionPurchaseHistory = promotionInventory.buy(name, countOfPromotionProduct);
        PurchaseHistory noPromotionPurchaseHistory = noPromotionInventory.buy(name, countOfNoPromotionProduct);
        return promotionPurchaseHistory.join(noPromotionPurchaseHistory);
    }

    private void validateBuying(int count) {
        if (!canBuy(count)) {
            throw new IllegalStateException("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }
    }
}
