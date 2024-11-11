package store.domain.inventory;

import camp.nextstep.edu.missionutils.DateTimes;
import store.domain.Inventory;

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
    public boolean isValidPromotion() {
        return promotion.isPromotionPeriod(DateTimes.now().toLocalDate());
    }

    @Override
    public boolean isExistProduct() {
        return currentCount > 0;
    }

    @Override
    public int getCurrentCount() {
        return currentCount;
    }
}
