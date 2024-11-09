package store.domain.inventory;

import java.time.LocalDate;

public interface Promotion {

    boolean canPromote(LocalDate currentDate, int currentCount);

    boolean isPromotePeriod(LocalDate currentDate);
}
