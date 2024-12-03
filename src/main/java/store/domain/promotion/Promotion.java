package store.domain.promotion;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;

public class Promotion {

    private final String name;
    private final PromotionType type;
    private final PromotionPeriod period;

    public Promotion(String name, int getCount, int freeCount, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.type = PromotionType.of(getCount, freeCount);
        this.period = new PromotionPeriod(startDate, endDate);
    }

    public boolean isPromotionPeriod() {
        return period.contains(DateTimes.now().toLocalDate());
    }

    public int countFreeQuantity(int purchaseCount) {
        return type.countFreeQuantity(purchaseCount);
    }
}
