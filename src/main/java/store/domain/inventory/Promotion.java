package store.domain.inventory;

import java.time.LocalDate;

public interface Promotion {

    boolean isPromotionPeriod(LocalDate currentDate);
}
