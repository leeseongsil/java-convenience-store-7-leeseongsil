package store.dto;

import store.domain.Inventory;
import store.domain.Product;
import store.domain.inventory.EmptyInventory;

public record ProductDto(String name, int price, int count, PromotionDto promotionDto) {

    public ProductBuilder toProductBuilder() {
        return new ProductBuilder(name);
    }

    public boolean isPromoted() {
        return promotionDto.isExist();
    }

    public static class ProductBuilder {
        private final String name;
        private InventoryDto discountInventory;
        private InventoryDto noDiscountInventory;

        public ProductBuilder(String name) {
            this.name = name;
        }

        public ProductBuilder productDto(ProductDto dto) {
            if (!dto.name().equals(name)) {
                throw new IllegalArgumentException();
            }

            if (dto.isPromoted()) {
                discountInventory = new InventoryDto(dto.price(), dto.count(), dto.promotionDto());
                return this;
            }
            noDiscountInventory = new InventoryDto(dto.price(), dto.count(), dto.promotionDto());
            return this;
        }

        public Product build() {
            return new Product(name, createDiscountInventory(), createNoDiscountInventory());
        }

        private Inventory createDiscountInventory() {
            if (discountInventory == null) {
                return new EmptyInventory();
            }
            return discountInventory.toInventory();
        }

        private Inventory createNoDiscountInventory() {
            if (noDiscountInventory == null) {
                return new EmptyInventory();
            }
            return noDiscountInventory.toInventory();
        }
    }
}
