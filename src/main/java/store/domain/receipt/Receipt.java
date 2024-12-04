package store.domain.receipt;

import java.util.List;

public class Receipt {

    private final PurchaseHistories histories;
    private final int membershipDiscountAmount;

    public Receipt(PurchaseHistories histories, int membershipDiscountAmount) {
        this.histories = histories;
        this.membershipDiscountAmount = membershipDiscountAmount;
    }

    public int calculateTotalCount() {
        return histories.calculateTotalCount();
    }

    public int calculateTotalRegularPrice() {
        return histories.calculateTotalRegularPrice();
    }

    public int calculateTotalPromotionDiscountPrice() {
        return histories.calculateTotalPromotionDiscountPrice();
    }

    public int calculateNotAppliedPromotionPrice() {
        return histories.calculateNotAppliedPromotionPrice();
    }

    public int calculateTotalFinalPrice() {
        return histories.calculateTotalFinalPrice();
    }

    public int calculateFinalPrice() {
        return histories.calculateTotalFinalPrice() - membershipDiscountAmount;
    }

    public int getMembershipDiscountAmount() {
        return membershipDiscountAmount;
    }

    public List<PurchaseHistory> getHistories() {
        return histories.getHistories();
    }
}
