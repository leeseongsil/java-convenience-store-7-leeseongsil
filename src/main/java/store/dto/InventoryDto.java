package store.dto;

import store.domain.Inventory;
import store.domain.inventory.ProductInventory;

public record InventoryDto(int price, int count, PromotionDto promotionDto) {

    public Inventory toInventory() {
        return new ProductInventory(promotionDto.toPromotion(), price, count);
    }
}
