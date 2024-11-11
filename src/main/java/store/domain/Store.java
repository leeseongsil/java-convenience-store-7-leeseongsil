package store.domain;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import store.domain.require.RequireDetail;
import store.domain.require.RequireDetails;
import store.domain.result.PurchaseHistory;
import store.domain.result.Receipt;
import store.dto.ProductResponseDto;

public class Store {

    private final Map<String, Product> products;

    public Store(Map<String, Product> products) {
        this.products = new LinkedHashMap<>(products);
    }

    public Receipt buy(RequireDetails requireDetails, boolean isDiscountedMemberShip) {
        List<PurchaseHistory> purchaseHistories = requireDetails.details()
                .stream()
                .map(this::buy)
                .toList();
        return ReceiptCreator.create(purchaseHistories, isDiscountedMemberShip);
    }

    private PurchaseHistory buy(RequireDetail requireDetail) {
        Product product = getProduct(requireDetail.name());
        return product.buy(requireDetail.count());
    }

    private Product getProduct(String name) {
        return products.computeIfAbsent(name, key -> {
            throw new IllegalArgumentException("존재하지 않는 상품입니다. 다시 입력해 주세요.");
        });
    }

    public List<ProductResponseDto> getProductResponses() {
        return products.values().stream()
                .map(Product::getProductResponses)
                .flatMap(List::stream)
                .toList();
    }
}
