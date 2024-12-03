package store.domain.inventory;

import store.domain.PromotionInventory;

public class EmptyPromotionInventory implements PromotionInventory {

    @Override
    public int countPurchasableQuantity() {
        return 0;
    }
}
