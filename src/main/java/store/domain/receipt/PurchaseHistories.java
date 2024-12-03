package store.domain.receipt;

import java.util.List;

public class PurchaseHistories {

    private final List<PurchaseHistory> histories;

    public PurchaseHistories(List<PurchaseHistory> histories) {
        this.histories = histories;
    }

    public int calculateTotalRegularPrice() {
        return histories.stream()
                .mapToInt(PurchaseHistory::calculateRegularPrice)
                .sum();
    }
}
