package store.domain.inventory;

import java.time.LocalDate;
import store.domain.result.PromotionResult;

public interface Promotion {

    boolean isPromotionPeriod(LocalDate currentDate);

    PromotionResult calculatePromotion(int count);
}
