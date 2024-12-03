package store.domain;

import store.domain.receipt.PurchaseHistory;

public interface NormalInventory {

    int countQuantity();

    PurchaseHistory buy(int quantity);
}
