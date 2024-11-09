package store.domain;

import java.time.LocalDate;

public class Promotion {


    private static final int MIN_GET_COUNT = 0;
    private static final int MAX_GET_COUNT = 1;
    private final String name;
    private final int buyCount;
    private final int getCount;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Promotion(String name, int buyCount, int getCount, LocalDate startDate, LocalDate endDate) {
        validateGetCount(getCount);
        validateDates(startDate, endDate);

        this.name = name;
        this.buyCount = buyCount;
        this.getCount = getCount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validateGetCount(int getCount) {
        if (getCount < MIN_GET_COUNT || getCount > MAX_GET_COUNT) {
            throw new IllegalArgumentException("get 개수는 %d와 %d 사이여야 합니다".formatted(MIN_GET_COUNT, MAX_GET_COUNT));
        }
    }

    private void validateDates(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("시작 날짜는 끝 날짜보다 이전이거나 같아야 합니다");
        }
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
