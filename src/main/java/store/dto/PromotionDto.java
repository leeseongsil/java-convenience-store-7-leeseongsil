package store.dto;

import java.time.LocalDate;
import store.domain.inventory.Promotion;
import store.domain.promotion.EventPromotion;
import store.domain.promotion.RegularPricePromotion;

public record PromotionDto(String name,
                           int buyCount,
                           int getCount,
                           LocalDate startDate,
                           LocalDate endDate,
                           boolean isExist) {

    public PromotionDto(String name, int buyCount, int getCount, LocalDate startDate, LocalDate endDate) {
        this(name, buyCount, getCount, startDate, endDate, true);
    }

    public static PromotionDto emptyPromotion() {
        return new PromotionDto(null, 0, 0, null, null, false);
    }

    public Promotion toPromotion() {
        if (isExist) {
            return new EventPromotion(name, buyCount, getCount, startDate, endDate);
        }
        return new RegularPricePromotion();
    }
}
