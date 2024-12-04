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
        if (isPromotionPeriod()) {
            return type.countFreeQuantity(purchaseCount);
        }
        return 0;
    }

    public int countAddableFreeCount(int purchaseCount) {
        if (isPromotionPeriod()) {
            return type.countAddableFreeCount(purchaseCount);
        }
        return 0;
    }

    public int countAppliedPromotionQuantity(int quantity) {
        if (isPromotionPeriod()) {
            return type.countAppliedPromotionQuantity(quantity);
        }
        return 0;
    }
}
