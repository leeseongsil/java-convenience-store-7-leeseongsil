package store.domain.promotion;

import java.time.LocalDate;
import store.domain.inventory.Promotion;

public class RegularPricePromotion implements Promotion {

    @Override
    public boolean isPromotionPeriod(LocalDate currentDate) {
        return false;
    }
}
