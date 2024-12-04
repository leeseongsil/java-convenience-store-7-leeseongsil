package store.domain.dto;

import java.time.LocalDate;
import store.domain.promotion.Promotion;

public record PromotionInputDto(
        String name,
        int buyCount,
        int getCount,
        LocalDate startDate,
        LocalDate endDate
) {

    public Promotion toPromotion() {
        return new Promotion(name, buyCount, getCount, startDate, endDate);
    }
}
