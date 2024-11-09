package store.domain.inventory;

import store.domain.Inventory;

public class EmptyInventory implements Inventory {

    @Override
    public boolean isValidPromotion() {
        return false;
    }

    @Override
    public boolean isExistProduct() {
        return false;
    }

    @Override
    public int getCurrentCount() {
        return 0;
    }
}
