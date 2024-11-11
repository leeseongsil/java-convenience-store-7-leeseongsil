package store.domain.result;

public class PurchaseHistory {

    private final String name;
    private final int regularCount;
    private final int freeCount;
    private final int basePrice;
    private final int eventDiscountPrice;

    public PurchaseHistory(String name, int regularCount, int freeCount, int pricePerCount) {
        this.name = name;
        this.regularCount = regularCount;
        this.freeCount = freeCount;
        this.basePrice = pricePerCount * (regularCount + freeCount);
        this.eventDiscountPrice = pricePerCount * freeCount;
    }

    public PurchaseHistory(String name, int regularCount, int freeCount, int basePrice, int eventDiscountPrice) {
        this.name = name;
        this.regularCount = regularCount;
        this.freeCount = freeCount;
        this.basePrice = basePrice;
        this.eventDiscountPrice = eventDiscountPrice;
    }

    public static PurchaseHistory emptyHistory(String name) {
        return new PurchaseHistory(name, 0, 0, 0, 0);
    }

    public PurchaseHistory join(PurchaseHistory history) {
        validateSameName(this.name, history.name);

        int totalRegularCount = this.regularCount + history.regularCount;
        int totalFreeCount = this.freeCount + history.freeCount;
        int totalBasePrice = this.basePrice + history.basePrice;
        int totalEventDiscountPrice = this.eventDiscountPrice + history.eventDiscountPrice;
        return new PurchaseHistory(
                this.name, totalRegularCount, totalFreeCount, totalBasePrice, totalEventDiscountPrice);
    }

    private void validateSameName(String name, String otherName) {
        if (!name.equals(otherName)) {
            throw new IllegalArgumentException("이름이 같은 내역만 합칠 수 있습니다");
        }
    }
}