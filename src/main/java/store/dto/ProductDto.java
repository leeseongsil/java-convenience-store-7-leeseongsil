package store.dto;

import store.domain.Product;

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
        private int promotionCount = 0;
        private int noPromotionCount = 0;
        private PromotionDto promotion;

        public ProductBuilder(String name, int price) {
            this.name = name;
            this.price = price;
        }

        public ProductBuilder productDto(ProductDto dto) {
            if (!dto.name().equals(this.name) || dto.price != this.price) {
                throw new IllegalArgumentException();
            }

            if (dto.isPromoted()) {
                promotionCount = dto.count;
                promotion = dto.promotionDto;
                return this;
            }
            noPromotionCount = dto.count;
            return this;
        }

        public Product build() {
            if (promotionCount > 0 && noPromotionCount == 0) {
                return Product.createPromotionedProduct(name, price, promotionCount, promotion.toPromotion());
            }
            if (promotionCount == 0 && noPromotionCount > 0) {
                return Product.createNoPromotionedProduct(name, price, noPromotionCount);
            }
            if (promotionCount > 0 && noPromotionCount > 0) {
                return Product.createProduct(name, price, promotionCount, noPromotionCount, promotion.toPromotion());
            }
            throw new IllegalArgumentException();
        }
    }
}
