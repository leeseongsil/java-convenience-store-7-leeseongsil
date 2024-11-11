package store.domain.promotion;

import java.time.LocalDate;
import store.domain.inventory.Promotion;

public class EventPromotion implements Promotion {

    private static final int VALIDATE_GET_COUNT = 1;

    private final String name;
    private final int buyCount;
    private final int getCount;
    private final EventPeriod eventPeriod;

    public EventPromotion(String name, int buyCount, int getCount, LocalDate startDate, LocalDate endDate) {
        validateGetCount(getCount);

        this.name = name;
        this.buyCount = buyCount;
        this.getCount = getCount;
        this.eventPeriod = new EventPeriod(startDate, endDate);
    }

    private void validateGetCount(int getCount) {
        if (getCount != VALIDATE_GET_COUNT) {
            throw new IllegalArgumentException("get 개수는 %d 이여야 합니다".formatted(VALIDATE_GET_COUNT));
        }
    }

    @Override
    public boolean isPromotionPeriod(LocalDate currentDate) {
        return eventPeriod.contains(currentDate);
    }
}
