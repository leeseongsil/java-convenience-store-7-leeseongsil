package store.domain.result;

import java.util.List;

public class Receipt {

    private static final int DEFAULT_DISCOUNT_PRICE = 0;

    private final List<PurchaseHistory> histories;
    private final int membershipDiscountPrice;

    public Receipt(List<PurchaseHistory> histories, int membershipDiscountPrice) {
        this.histories = histories;
        this.membershipDiscountPrice = membershipDiscountPrice;
    }

    public Receipt(List<PurchaseHistory> histories) {
        this(histories, DEFAULT_DISCOUNT_PRICE);
    }

    public List<PurchaseHistory> getHistories() {
        return histories;
    }

    public int totalCount() {
        return histories.stream()
                .mapToInt(PurchaseHistory::getTotalCount)
                .sum();
    }

    public int totalBasePrice() {
        return histories.stream()
                .mapToInt(PurchaseHistory::getBasePrice)
                .sum();
    }

    public int totalEventDiscountPrice() {
        return histories.stream()
                .mapToInt(PurchaseHistory::getEventDiscountPrice)
                .sum();
    }

    public int finalPrice() {
        return totalBasePrice() - totalEventDiscountPrice() - membershipDiscountPrice;
    }

    public int getMembershipDiscountPrice() {
        return membershipDiscountPrice;
    }
}
