package store.domain.promotion;

import java.time.LocalDate;
import store.domain.inventory.Promotion;
import store.domain.result.PromotionResult;

public class EventPromotion implements Promotion {

    private static final int GET_COUNT = 1;

    private final String name;
    private final int buyCount;
    private final EventPeriod eventPeriod;

    public EventPromotion(String name, int buyCount, int getCount, LocalDate startDate, LocalDate endDate) {
        validateGetCount(getCount);

        this.name = name;
        this.buyCount = buyCount;
        this.eventPeriod = new EventPeriod(startDate, endDate);
    }

    private void validateGetCount(int getCount) {
        if (getCount != GET_COUNT) {
            throw new IllegalArgumentException("get 개수는 %d 이여야 합니다".formatted(GET_COUNT));
        }
    }

    @Override
    public boolean isPromotionPeriod(LocalDate currentDate) {
        return eventPeriod.contains(currentDate);
    }

    @Override
    public int countFreeProductsWhenPurchased(int purchaseCount) {
        int countOfBundle = buyCount + GET_COUNT;
        int remainOfBundle = purchaseCount % countOfBundle;

        if (remainOfBundle < buyCount) {
            return 0;
        }
        return countOfBundle - buyCount;
    }

    @Override
    public PromotionResult calculatePromotion(int count) {
        int freeCount = count / (buyCount + GET_COUNT);
        int regularCount = count - freeCount;
        return new PromotionResult(regularCount, freeCount);
    }
}
