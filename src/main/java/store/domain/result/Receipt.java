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
}
