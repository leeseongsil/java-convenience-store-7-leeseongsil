package store.domain.inventory;

import java.time.LocalDate;
import store.domain.result.PromotionResult;

public interface Promotion {

    boolean isPromotionPeriod(LocalDate currentDate);

    int countFreeProductsWhenPurchased(int purchaseCount);

    PromotionResult calculatePromotion(int count);
}
