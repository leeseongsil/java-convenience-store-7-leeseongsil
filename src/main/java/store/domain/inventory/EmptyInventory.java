package store.domain.inventory;

import java.util.Optional;
import store.domain.Inventory;
import store.domain.result.PurchaseHistory;
import store.dto.ProductResponseDto;

public class EmptyInventory implements Inventory {

    private static final String EMPTY_NAME = "";
    private static final int EMPTY_COUNT = 0;

    private final String name;
    private final int price;
    private final boolean isPrintable;

    public EmptyInventory(String name, int price, boolean isPrintable) {
        this.name = name;
        this.price = price;
        this.isPrintable = isPrintable;
    }

    @Override
    public boolean isInPeriod() {
        return false;
    }

    @Override
    public int countPurchasableProducts() {
        return EMPTY_COUNT;
    }

    @Override
    public int countPurchasableProducts(int purchaseCount) {
        return EMPTY_COUNT;
    }

    @Override
    public boolean isLackOfProducts(int purchaseCount) {
        return false;
    }

    @Override
    public int countFreeProducts(int purchaseCount) {
        return EMPTY_COUNT;
    }

    @Override
    public int countRemainedRegularPriceProducts(int purchaseCount) {
        return EMPTY_COUNT;
    }

    @Override
    public PurchaseHistory buy(int count) {
        return PurchaseHistory.emptyHistory(name);
    }

    @Override
    public Optional<ProductResponseDto> getProductResponse() {
        if (isPrintable) {
            return Optional.of(new ProductResponseDto(name, price, EMPTY_COUNT, EMPTY_NAME));
        }
        return Optional.empty();
    }
}
