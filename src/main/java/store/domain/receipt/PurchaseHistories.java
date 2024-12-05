package store.domain.receipt;

import java.util.List;

public class PurchaseHistories {

    private final List<PurchaseHistory> histories;

    public PurchaseHistories(List<PurchaseHistory> histories) {
        this.histories = histories;
    }

    public int calculateTotalCount() {
        return histories.stream()
                .mapToInt(PurchaseHistory::getTotalCount)
                .sum();
    }

    public int calculateTotalRegularPrice() {
        return histories.stream()
                .mapToInt(PurchaseHistory::calculateRegularPrice)
                .sum();
    }

    public int calculateTotalPromotionDiscountPrice() {
        return histories.stream()
                .mapToInt(PurchaseHistory::calculatePromotionDiscountPrice)
                .sum();
    }

    public int calculateNotAppliedPromotionPrice() {
        return histories.stream()
                .mapToInt(PurchaseHistory::calculateNotAppliedPromotionPrice)
                .sum();
    }

    public int calculateTotalFinalPrice() {
        return histories.stream()
                .mapToInt(PurchaseHistory::calculateFinalPrice)
                .sum();
    }

    public List<PurchaseHistory> getHistories() {
        return histories;
    }
}
