package store.domain.dto;

import java.util.Optional;
import store.domain.NormalInventory;
import store.domain.PromotionInventory;
import store.domain.inventory.InStockNormalInventory;
import store.domain.inventory.InStockPromotionInventory;
import store.domain.promotion.Promotion;

public record InventoryInputDto(
        String productName,
        int price,
        int quantity,
        Optional<String> promotionName
) {

    public boolean isPromotionInventory() {
        return promotionName.isPresent();
    }

    public NormalInventory toNormalInventory() {
        if (promotionName.isPresent()) {
            throw new IllegalArgumentException();
        }
        return new InStockNormalInventory(productName, price, quantity);
    }

    public PromotionInventory toPromotionInventory(Promotion promotion) {
        if (promotionName.isEmpty() || !promotion.getName().equals(promotionName.get())) {
            throw new IllegalArgumentException();
        }
        return new InStockPromotionInventory(productName, price, quantity, promotion);
    }
}
