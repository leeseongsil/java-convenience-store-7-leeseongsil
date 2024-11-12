package store.domain.inventory;

import java.util.Optional;
import store.domain.Inventory;
import store.domain.result.PurchaseHistory;
import store.dto.ProductResponseDto;

public class EmptyInventory implements Inventory {

    @Override
    public boolean isInPeriod() {
        return false;
    }

    @Override
    public int countPurchasableProducts() {
        return 0;
    }

    @Override
    public int countPurchasableProducts(int purchaseCount) {
        return 0;
    }

    @Override
    public boolean isLackOfProducts(int purchaseCount) {
        return true;
    }

    @Override
    public int countFreeProducts(int purchaseCount) {
        return 0;
    }

    @Override
    public int countRemainedRegularPriceProducts(int purchaseCount) {
        return 0;
    }

    @Override
    public PurchaseHistory buy(String name, int count) {
        return PurchaseHistory.emptyHistory(name);
    }

    @Override
    public Optional<ProductResponseDto> getProductResponse(String productName) {
        return Optional.empty();
    }
}
