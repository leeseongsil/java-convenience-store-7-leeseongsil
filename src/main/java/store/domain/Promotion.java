package store.domain;

import java.time.Duration;
import java.time.LocalDate;

public class Promotion {

    private final String name;
    private final int buyCount;
    private final int getCount;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Promotion(String name, int buyCount, int getCount, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.buyCount = buyCount;
        this.getCount = getCount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean canPromote(LocalDate currentDate) {
        return isAfterOrEqualToStartDate(currentDate) && isBeforeOrEqualToEndDate(currentDate);
    }

    private boolean isAfterOrEqualToStartDate(LocalDate currentDate) {
        return currentDate.isAfter(startDate) || currentDate.isEqual(startDate);
    }

    private boolean isBeforeOrEqualToEndDate(LocalDate currentDate) {
        return currentDate.isBefore(endDate) || currentDate.isEqual(endDate);
    }
}
