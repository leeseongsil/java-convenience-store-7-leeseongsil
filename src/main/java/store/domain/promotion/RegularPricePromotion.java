package store.domain.promotion;

import java.time.LocalDate;
import store.domain.inventory.Promotion;
import store.domain.result.PromotionResult;

public class RegularPricePromotion implements Promotion {

    private static final String EMPTY_NAME = "";

    @Override
    public boolean isPromotionPeriod(LocalDate currentDate) {
        return true;
    }

    @Override
    public int calculateFreeProducts(int purchaseCount) {
        return 0;
    }

    @Override
    public int calculateRemainedRegularPriceProducts(int purchaseCount) {
        return purchaseCount;
    }

    @Override
    public PromotionResult calculatePromotion(int count) {
        return new PromotionResult(count, 0);
    }

    @Override
    public String getName() {
        return EMPTY_NAME;
    }
}
