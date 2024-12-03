package store.domain.promotion;

import java.time.LocalDate;

public class PromotionPeriod {

    private final LocalDate startDate;
    private final LocalDate endDate;

    public PromotionPeriod(LocalDate startDate, LocalDate endDate) {
        validateDates(startDate, endDate);

        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validateDates(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("시작 날짜는 끝 날짜보다 이전이거나 같아야 합니다");
        }
    }

    public boolean contains(LocalDate currentDate) {
        return isAfterOrEqualToStartDate(currentDate) && isBeforeOrEqualToEndDate(currentDate);
    }

    private boolean isAfterOrEqualToStartDate(LocalDate currentDate) {
        return currentDate.isAfter(startDate) || currentDate.isEqual(startDate);
    }

    private boolean isBeforeOrEqualToEndDate(LocalDate currentDate) {
        return currentDate.isBefore(endDate) || currentDate.isEqual(endDate);
    }
}
