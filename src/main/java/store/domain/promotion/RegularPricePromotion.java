package store.domain.promotion;

import java.time.LocalDate;
import store.domain.inventory.Promotion;

public class RegularPricePromotion implements Promotion {

    @Override
    public boolean canPromote(LocalDate currentDate, int currentCount) {
        return false;
    }

    @Override
    public boolean isPromotePeriod(LocalDate currentDate) {
        return false;
    }
}
