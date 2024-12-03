package store.domain.promotion;

import java.util.Arrays;

public enum PromotionType {
    ONE_PLUS_ONE(1, 1),
    TWO_PLUS_ONE(2, 1),
    ;

    private final int getCount;
    private final int freeCount;

    PromotionType(int getCount, int freeCount) {
        this.getCount = getCount;
        this.freeCount = freeCount;
    }

    public static PromotionType of(int getCount, int freeCount) {
       return Arrays.stream(PromotionType.values())
                .filter(type -> type.freeCount == freeCount && type.getCount == getCount)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Invalid promotion type"));
    }

    public int getGetCount() {
        return getCount;
    }

    public int getFreeCount() {
        return freeCount;
    }
}
