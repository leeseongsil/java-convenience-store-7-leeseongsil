package store.domain;

import java.util.Optional;
import store.domain.dto.InventoryOutputDto;
import store.domain.receipt.PurchaseHistory;

public interface PromotionInventory {

    int countPurchasableQuantity(int purchaseQuantity);

    int countPurchasableQuantity();

    int countAddableFreeProducts(int purchaseQuantity);

    boolean isLackPromotionQuantity(int purchaseQuantity);

    int countRegularPriceQuantity(int purchaseQuantity);

    PurchaseHistory buy(int quantity);

    Optional<InventoryOutputDto> getStatus();
}
