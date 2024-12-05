package store.domain.inventory;

import java.util.Optional;
import store.domain.NormalInventory;
import store.domain.dto.InventoryOutputDto;
import store.domain.receipt.PurchaseHistory;

public class EmptyNormalInventory implements NormalInventory {

    private final String name;
    private final int perPrice;

    public EmptyNormalInventory(String name, int perPrice) {
        // TODO 유효성 검사 (price)
        this.name = name;
        this.perPrice = perPrice;
    }

    @Override
    public int countQuantity() {
        return 0;
    }

    @Override
    public PurchaseHistory buy(int quantity) {
        return PurchaseHistory.emptyHistory(name, perPrice);
    }

    @Override
    public Optional<InventoryOutputDto> getStatus() {
        return Optional.of(new InventoryOutputDto(name, perPrice, 0));
    }
}
