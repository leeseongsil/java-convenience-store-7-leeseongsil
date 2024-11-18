package store.domain;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import store.domain.inventory.EmptyInventory;
import store.domain.inventory.ProductInventory;
import store.domain.inventory.Promotion;
import store.domain.promotion.RegularPricePromotion;
import store.domain.result.PurchaseHistory;
import store.dto.ProductResponseDto;

public class Product {

    private final Inventory promotionInventory;
    private final Inventory noPromotionInventory;

    public Product(Inventory promotionInventory, Inventory noPromotionInventory) {
        this.promotionInventory = promotionInventory;
        this.noPromotionInventory = noPromotionInventory;
    }

    public static Product createPromotionedProduct(String name, int price, int count, Promotion promotion) {
        return new Product(
                new ProductInventory(name, price, promotion, count),
                new EmptyInventory(name, price, true));
    }

    public static Product createNoPromotionedProduct(String name, int price, int count) {
        return new Product(
                new EmptyInventory(name, price, false),
                new ProductInventory(name, price, new RegularPricePromotion(), count));
    }

    public static Product createProduct(String name, int price, int promotedCount, int noPromotedCount,
                                        Promotion promotion) {
        return new Product(
                new ProductInventory(name, price, promotion, promotedCount),
                new ProductInventory(name, price, new RegularPricePromotion(), noPromotedCount));
    }

    public boolean canBuy(int count) {
        return count <= totalPurchasableProduct();
    }

    private int totalPurchasableProduct() {
        return promotionInventory.countPurchasableProducts() + noPromotionInventory.countPurchasableProducts();
    }

    public int countFreeProductsWhenPurchased(int purchaseCount) {
        return promotionInventory.countFreeProducts(purchaseCount);
    }

    public boolean isLackPromotionProduct(int purchaseCount) {
        return promotionInventory.isInPeriod() && promotionInventory.isLackOfProducts(purchaseCount);
    }

    public int countRegularPriceProducts(int purchaseCount) {
        int countOfPromotionProduct = promotionInventory.countPurchasableProducts(purchaseCount);
        int countOfNoPromotionProduct = purchaseCount - countOfPromotionProduct;
        return promotionInventory.countRemainedRegularPriceProducts(countOfPromotionProduct)
                + noPromotionInventory.countRemainedRegularPriceProducts(countOfNoPromotionProduct);
    }

    public PurchaseHistory buy(int count) {
        validateBuying(count);

        int countOfPromotionProduct = promotionInventory.countPurchasableProducts(count);
        int countOfNoPromotionProduct = count - countOfPromotionProduct;
        PurchaseHistory promotionPurchaseHistory = promotionInventory.buy(countOfPromotionProduct);
        PurchaseHistory noPromotionPurchaseHistory = noPromotionInventory.buy(countOfNoPromotionProduct);
        return promotionPurchaseHistory.join(noPromotionPurchaseHistory);
    }

    private void validateBuying(int count) {
        if (!canBuy(count)) {
            throw new IllegalStateException("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }
    }

    public List<ProductResponseDto> getProductResponses() {
        return Stream.of(promotionInventory, noPromotionInventory)
                .map(inventory -> inventory.getProductResponse())
                .flatMap(Optional::stream)
                .toList();
    }
}
