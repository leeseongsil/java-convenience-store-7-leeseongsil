package store.domain.dto;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import store.domain.NormalInventory;
import store.domain.Product;
import store.domain.PromotionInventory;
import store.domain.Store;
import store.domain.inventory.EmptyNormalInventory;
import store.domain.inventory.EmptyPromotionInventory;
import store.domain.promotion.Promotion;

public record StoreInputDto(
        List<InventoryInputDto> inventories,
        Map<String, PromotionInputDto> promotions
) {

    public Store toStore() {
        Map<String, ProductSlot> productSlots = new LinkedHashMap<>();
        for (InventoryInputDto inventory : inventories) {
            ProductSlot slot = productSlots.computeIfAbsent(
                    inventory.productName(),
                    name -> new ProductSlot(name, inventory.price()));
            setInventory(slot, inventory);
        }
        return new Store(toProducts(productSlots));
    }

    private void setInventory(ProductSlot slot, InventoryInputDto inventory) {
        if (inventory.isPromotionInventory()) {
            Promotion promotion = findPromotion(inventory.promotionName().get());
            slot.setPromotionInventory(inventory.toPromotionInventory(promotion));
            return;
        }
        slot.setNormalInventory(inventory.toNormalInventory());
    }

    private Promotion findPromotion(String promotionName) {
        PromotionInputDto promotionDto = promotions.get(promotionName);
        if (promotionDto == null) {
            throw new IllegalArgumentException(promotionName + " is not a valid promotion");
        }
        return promotionDto.toPromotion();
    }

    private Map<String, Product> toProducts(Map<String, ProductSlot> productSlots) {
        Map<String, Product> products = new LinkedHashMap<>();
        for (Map.Entry<String, ProductSlot> entry : productSlots.entrySet()) {
            products.put(entry.getKey(), entry.getValue().toProduct());
        }
        return products;
    }

    private class ProductSlot {

        private final String name;
        private final int price;
        private PromotionInventory promotionInventory;
        private NormalInventory normalInventory;

        public ProductSlot(String name, int price) {
            this.name = name;
            this.price = price;
        }

        private void setPromotionInventory(PromotionInventory promotionInventory) {
            if (this.promotionInventory == null) {
                this.promotionInventory = promotionInventory;
                return;
            }
            throw new IllegalArgumentException("Promotion inventory already exists");
        }

        private void setNormalInventory(NormalInventory normalInventory) {
            if (this.normalInventory == null) {
                this.normalInventory = normalInventory;
                return;
            }
            throw new IllegalArgumentException("Normal inventory already exists");
        }

        public Product toProduct() {
            if (promotionInventory != null && normalInventory != null) {
                return new Product(promotionInventory, normalInventory);
            }
            if (promotionInventory != null && normalInventory == null) {
                return new Product(promotionInventory, new EmptyNormalInventory(name, price));
            }
            if (promotionInventory == null && normalInventory != null) {
                return new Product(new EmptyPromotionInventory(name, price), normalInventory);
            }
            throw new IllegalArgumentException("Invalid promotion inventory");
        }
    }
}
