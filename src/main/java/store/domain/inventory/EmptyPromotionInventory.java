package store.domain.inventory;

import java.util.Optional;
import store.domain.PromotionInventory;
import store.domain.dto.InventoryOutputDto;
import store.domain.receipt.PurchaseHistory;

public class EmptyPromotionInventory implements PromotionInventory {

    private final String name;
    private final int perPrice;

    public EmptyPromotionInventory(String name, int price) {
        // TODO 유효성 검사 (price)
        this.name = name;
        this.perPrice = price;
    }

    @Override
    public int countPurchasableQuantity(int purchaseQuantity) {
        return 0;
    }

    @Override
    public int countPurchasableQuantity() {
        return 0;
    }

    @Override
    public int countAddableFreeProducts(int purchaseQuantity) {
        return 0;
    }

    @Override
    public boolean isLackPromotionQuantity(int purchaseQuantity) {
        return false;
    }

    @Override
    public int countRegularPriceQuantity(int purchaseQuantity) {
        return purchaseQuantity;
    }

    @Override
    public PurchaseHistory buy(int quantity) {
        return new PurchaseHistory(name, perPrice, 0);
    }

    @Override
    public Optional<InventoryOutputDto> getStatus() {
        return Optional.empty();
    }
}
