package store.domain;

public class Product {

    private final String name;
    private final ProductInventory promotionInventory;
    private final ProductInventory noPromotionInventory;

    public Product(String name, ProductInventory promotionInventory, ProductInventory noPromotionInventory) {
        validatePromotionInventory(promotionInventory, noPromotionInventory);

        this.name = name;
        this.promotionInventory = promotionInventory;
        this.noPromotionInventory = noPromotionInventory;
    }

    private void validatePromotionInventory(ProductInventory promotionInventory,
                                            ProductInventory noPromotionInventory) {
        if (!promotionInventory.isValidPromotion()) {
            throw new IllegalArgumentException("정가로 구매하는 상품들이 필요로 합니다");
        }
        if (noPromotionInventory.isValidPromotion()) {
            throw new IllegalArgumentException("정가로 구매하는 상품들이 필요로 합니다");
        }
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
}
