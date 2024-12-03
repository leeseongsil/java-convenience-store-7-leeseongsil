package store.domain;

import store.domain.receipt.PurchaseHistories;

public class MembershipDiscount {

    private static final int DEFAULT_DISCOUNT_AMOUNT = 0;
    private static final int MAX_DISCOUNT_AMOUNT = 8_000;
    private static final int DISCOUNT_PERCENTAGE = 30;

    public int calculateDiscountAmount(PurchaseHistories histories, boolean isAppliedDiscount) {
        if (isAppliedDiscount) {
            // TODO 프로모션 적용 금액의 30%
            // ex. 1000원 제품, 2+1 할인을 받은 경우 -> 3000 원이 빠진 금액의 30%
            int rateDiscountPrice = histories.calculateTotalRegularPrice() * DISCOUNT_PERCENTAGE / 100;
            return Math.min(rateDiscountPrice, MAX_DISCOUNT_AMOUNT);
        }
        return DEFAULT_DISCOUNT_AMOUNT;
    }
}
