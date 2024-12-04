package store.domain.receipt;

import java.util.List;

public class Receipt {

    private final PurchaseHistories histories;
    private final int membershipDiscountAmount;

    public Receipt(PurchaseHistories histories, int membershipDiscountAmount) {
        this.histories = histories;
        this.membershipDiscountAmount = membershipDiscountAmount;
    }

    public int getTotalCount() {
        return histories.calculateTotalCount();
    }

    public int getTotalRegularPrice() {
        return histories.calculateTotalRegularPrice();
    }

    public int getTotalPromotionDiscountPrice() {
        return histories.calculateTotalPromotionDiscountPrice();
    }

    public int getNotAppliedPromotionPrice() {
        return histories.calculateNotAppliedPromotionPrice();
    }

    public int getFinalPrice() {
        return histories.calculateTotalFinalPrice() - membershipDiscountAmount;
    }

    public int getMembershipDiscountAmount() {
        return membershipDiscountAmount;
    }

    public List<PurchaseHistory> getHistories() {
        return histories.getHistories();
    }
}
