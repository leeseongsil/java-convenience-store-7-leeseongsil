package store.domain.inventory;

import camp.nextstep.edu.missionutils.DateTimes;
import store.domain.Inventory;
import store.domain.result.PromotionResult;
import store.domain.result.PurchaseHistory;

public class ProductInventory implements Inventory {

    private final Promotion promotion;
    private final int price;
    private int currentCount;

    public ProductInventory(Promotion promotion, int price, int currentCount) {
        this.promotion = promotion;
        this.price = price;
        this.currentCount = currentCount;
    }

    @Override
    public boolean isInPeriod() {
        return promotion.isPromotionPeriod(DateTimes.now().toLocalDate());
    }

    @Override
    public int countPurchasableProducts() {
        if (isInPeriod()) {
            return currentCount;
        }
        return 0;
    }

    @Override
    public int countPurchasableProducts(int purchaseCount) {
        if (isInPeriod()) {
            return Math.min(purchaseCount, currentCount);
        }
        return 0;
    }

    @Override
    public boolean isLackOfProducts(int purchaseCount) {
        int countOfBenefitedProducts = purchaseCount + countFreeProducts(purchaseCount);
        return currentCount < countOfBenefitedProducts;
    }

    @Override
    public int countFreeProducts(int purchaseCount) {
        if (!isInPeriod() || purchaseCount >= currentCount) {
            return 0;
        }

        int remainCount = currentCount - purchaseCount;
        int promotionFreeCount = promotion.calculateFreeProducts(purchaseCount);
        return Math.min(remainCount, promotionFreeCount);
    }

    @Override
    public int countRemainedRegularPriceProducts(int purchaseCount) {
        int remainCount = currentCount - purchaseCount;
        int promotionCount = promotion.calculateRemainedRegularPriceProducts(purchaseCount);
        return Math.min(remainCount, promotionCount);
    }

    @Override
    public PurchaseHistory buy(String name, int count) {
        validateBuying(count);
        if (!isInPeriod()) {
            return PurchaseHistory.emptyHistory(name);
        }

        PromotionResult promotionResult = promotion.calculatePromotion(count);
        PurchaseHistory history =
                new PurchaseHistory(name, promotionResult.regularCount(), promotionResult.freeCount(), price);
        currentCount -= count;
        return history;
    }

    private void validateBuying(int count) {
        if (!isInPeriod()) {
            throw new IllegalStateException("프로모션 기간이 지났습니다");
        }
        if (currentCount < count) {
            throw new IllegalStateException("현재 재고 양이 부족합니다");
        }
    }
}
