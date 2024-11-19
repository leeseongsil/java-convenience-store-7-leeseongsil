package store.domain;

import java.util.List;
import store.domain.result.PurchaseHistory;
import store.domain.result.Receipt;

public class ReceiptCreator {

    private static final int MEMBERSHIP_DISCOUNT_PERCENTAGE = 30;
    private static final int MAX_MEMBERSHIP_DISCOUNT_PRICE = 8_000;

    private ReceiptCreator() {
    }

    public static Receipt create(List<PurchaseHistory> purchaseHistories, boolean isDiscountedMembership) {
        if (isDiscountedMembership) {
            int notAppliedPromotionPrice = calculateNotAppliedPromotionPrice(purchaseHistories);
            int membershipDiscountPrice = calculateMembershipDiscountPrice(notAppliedPromotionPrice);

            return new Receipt(purchaseHistories, membershipDiscountPrice);
        }
        return new Receipt(purchaseHistories);
    }

    private static int calculateNotAppliedPromotionPrice(List<PurchaseHistory> purchaseHistories) {
        return purchaseHistories.stream()
                .mapToInt(PurchaseHistory::getNotAppliedPromotionPrice)
                .sum();
    }

    private static int calculateMembershipDiscountPrice(int price) {
        int rateDiscountPrice = price * MEMBERSHIP_DISCOUNT_PERCENTAGE / 100;
        return Math.min(rateDiscountPrice, MAX_MEMBERSHIP_DISCOUNT_PRICE);
    }
}
