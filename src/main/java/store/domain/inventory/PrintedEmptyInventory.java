package store.domain.inventory;

import java.util.Optional;
import store.domain.Inventory;
import store.domain.result.PurchaseHistory;
import store.dto.ProductResponseDto;

public class PrintedEmptyInventory implements Inventory {

    private static final String EMPTY_NAME = "";
    private static final int EMPTY_COUNT = 0;

    private final int price;

    public PrintedEmptyInventory(int price) {
        this.price = price;
    }

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
        return false;
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
        return null;
    }

    @Override
    public Optional<ProductResponseDto> getProductResponse(String productName) {
        return Optional.of(new ProductResponseDto(productName, price, EMPTY_COUNT, EMPTY_NAME));
    }
}
