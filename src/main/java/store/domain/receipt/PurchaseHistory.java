package store.domain.receipt;

public class PurchaseHistory {

    private final String name;
    private final int perPrice;
    private final int totalCount;
    private final int appliedPromotionCount;
    private final int freeCount;

    public PurchaseHistory(String name, int perPrice, int totalCount, int appliedPromotionCount, int freeCount) {
        this.name = name;
        this.perPrice = perPrice;
        this.totalCount = totalCount;
        this.appliedPromotionCount = appliedPromotionCount;
        this.freeCount = freeCount;
    }

    public PurchaseHistory(String name, int perPrice, int totalCount) {
        this(name, perPrice, totalCount, 0, 0);
    }

    public static PurchaseHistory emptyHistory(String name, int perPrice) {
        return new PurchaseHistory(name, perPrice, 0, 0, 0);
    }

    public PurchaseHistory join(PurchaseHistory history) {
        if (isSameProduct(history)) {
            int totalCount = this.totalCount + history.totalCount;
            int appliedPromotionCount = this.appliedPromotionCount + history.appliedPromotionCount;
            int freeCount = this.freeCount + history.freeCount;
            return new PurchaseHistory(this.name, this.perPrice, totalCount, appliedPromotionCount, freeCount);
        }
        throw new IllegalStateException("같은 제품 내역만 합칠 수 있습니다");
    }

    private boolean isSameProduct(PurchaseHistory history) {
        return this.name.equals(history.name) && this.perPrice == history.perPrice;
    }

    public int calculateRegularPrice() {
        return totalCount * perPrice;
    }

    public int calculatePromotionDiscountPrice() {
        return freeCount * perPrice;
    }

    public int calculateNotAppliedPromotionPrice() {
        return (totalCount - appliedPromotionCount) * perPrice;
    }

    public int calculateFinalPrice() {
        return (totalCount - freeCount) * perPrice;
    }

    public String getName() {
        return name;
    }

    public int getPerPrice() {
        return perPrice;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getFreeCount() {
        return freeCount;
    }
}
