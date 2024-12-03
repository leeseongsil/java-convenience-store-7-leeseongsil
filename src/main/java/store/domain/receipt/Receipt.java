package store.domain.receipt;

public class Receipt {

    private final PurchaseHistories histories;
    private final int membershipDiscountAmount;

    public Receipt(PurchaseHistories histories, int membershipDiscountAmount) {
        this.histories = histories;
        this.membershipDiscountAmount = membershipDiscountAmount;
    }
}
