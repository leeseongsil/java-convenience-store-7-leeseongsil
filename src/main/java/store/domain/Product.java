package store.domain;

import store.domain.inventory.ProductInventory;

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
        return count <= totalCount();
    }

    public void buy(int count) {
        if (canBuy(count)) {
            // this.count -= count;
            return;
        }
        throw new IllegalStateException("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
    }

    private int totalCount() {
        return promotionInventory.getCurrentCount() + noPromotionInventory.getCurrentCount();
    }

    public boolean isExistPromotedProduct() {
        return promotionInventory.isExistProduct();
    }

    public int countOfRegularPriceProduct(int requireCount) {
        return requireCount - promotionInventory.getCurrentCount();
    }
}
