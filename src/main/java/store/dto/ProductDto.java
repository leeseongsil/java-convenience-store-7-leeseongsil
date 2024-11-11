package store.dto;

import store.domain.Inventory;
import store.domain.Product;
import store.domain.inventory.EmptyInventory;
import store.domain.inventory.PrintedEmptyInventory;

public record ProductDto(String name, int price, int count, PromotionDto promotionDto) {

    public ProductBuilder toProductBuilder() {
        return new ProductBuilder(name, price)
                .productDto(this);
    }

    public boolean isPromoted() {
        return promotionDto.isExist();
    }

    public static class ProductBuilder {
        private final String name;
        private final int price;
        private InventoryDto discountInventory;
        private InventoryDto noDiscountInventory;

        public ProductBuilder(String name, int price) {
            this.name = name;
            this.price = price;
        }

        public ProductBuilder productDto(ProductDto dto) {
            if (!dto.name().equals(name)) {
                throw new IllegalArgumentException();
            }

            if (dto.isPromoted()) {
                discountInventory = new InventoryDto(price, dto.count(), dto.promotionDto());
                return this;
            }
            noDiscountInventory = new InventoryDto(price, dto.count(), dto.promotionDto());
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
                return new PrintedEmptyInventory(price);
            }
            return noDiscountInventory.toInventory();
        }
    }
}
