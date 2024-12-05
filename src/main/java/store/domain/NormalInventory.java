package store.domain;

import java.util.Optional;
import store.domain.dto.InventoryOutputDto;
import store.domain.receipt.PurchaseHistory;

public interface NormalInventory {

    int countQuantity();

    PurchaseHistory buy(int quantity);

    Optional<InventoryOutputDto> getStatus();
}
