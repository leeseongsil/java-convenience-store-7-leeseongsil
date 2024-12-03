package store.domain;

import java.util.List;
import java.util.Map;
import store.domain.receipt.PurchaseHistories;
import store.domain.receipt.PurchaseHistory;
import store.domain.receipt.Receipt;

public class Store {

    private final Map<String, Product> products;
    private final MembershipDiscount membershipDiscount;

    public Store(Map<String, Product> products, MembershipDiscount membershipDiscount) {
        this.products = Map.copyOf(products);
        this.membershipDiscount = membershipDiscount;
    }

    public Store(Map<String, Product> products) {
        this(products, new MembershipDiscount());
    }

    public boolean canBuy(String name, int quantity) {
        return getProduct(name).canBuy(quantity);
    }

    public int countAddableFreeProducts(String name, int quantity) {
        return getProduct(name).countAddableFreeProducts(quantity);
    }

    public boolean isLackPromotionQuantity(String name, int quantity) {
        return getProduct(name).isLackPromotionQuantity(quantity);
    }

    public int countRegularPriceQuantity(String name, int quantity) {
        return getProduct(name).countRegularPriceQuantity(quantity);
    }

    public Receipt buy(Map<String, Integer> purchaseProducts, boolean isAppliedMembershipDiscount) {
        List<PurchaseHistory> histories = purchaseProducts.entrySet().stream()
                .map(entry -> getProduct(entry.getKey()).buy(entry.getValue()))
                .toList();
        PurchaseHistories purchaseHistories = new PurchaseHistories(histories);

        int membershipDiscountAmount = membershipDiscount.calculateDiscountAmount(
                purchaseHistories, isAppliedMembershipDiscount);
        return new Receipt(purchaseHistories, membershipDiscountAmount);
    }

    private Product getProduct(String name) {
        if (products.containsKey(name)) {
            return products.get(name);
        }
        throw new IllegalArgumentException("Product " + name + " not found");
    }
}
