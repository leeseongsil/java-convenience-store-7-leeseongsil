package store.domain.inventory;

import store.domain.NormalInventory;

public class EmptyNormalInventory implements NormalInventory {

    @Override
    public int countQuantity() {
        return 0;
    }
}
