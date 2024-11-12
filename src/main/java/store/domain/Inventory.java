package store.domain;

import java.util.Optional;
import store.domain.result.PurchaseHistory;
import store.dto.ProductResponseDto;

public interface Inventory {

    boolean isInPeriod();

    int countPurchasableProducts();

    int countPurchasableProducts(int purchaseCount);

    boolean isLackOfProducts(int purchaseCount);

    int countFreeProducts(int purchaseCount);

    int countRemainedRegularPriceProducts(int purchaseCount);

    PurchaseHistory buy(String name, int count);

    Optional<ProductResponseDto> getProductResponse(String productName);
}
