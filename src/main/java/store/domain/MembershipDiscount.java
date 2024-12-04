package store.domain;

import store.domain.receipt.PurchaseHistories;

public class MembershipDiscount {

    private static final int DEFAULT_DISCOUNT_AMOUNT = 0;
    private static final int MAX_DISCOUNT_AMOUNT = 8_000;
    private static final int DISCOUNT_PERCENTAGE = 30;

    public int calculateDiscountAmount(PurchaseHistories histories, boolean isAppliedDiscount) {
        if (isAppliedDiscount) {
            int rateDiscountPrice = histories.calculateNotAppliedPromotionPrice() * DISCOUNT_PERCENTAGE / 100;
            return Math.min(rateDiscountPrice, MAX_DISCOUNT_AMOUNT);
        }
        return DEFAULT_DISCOUNT_AMOUNT;
    }
}
