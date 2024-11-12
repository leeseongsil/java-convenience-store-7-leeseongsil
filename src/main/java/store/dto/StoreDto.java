package store.dto;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import store.domain.Product;
import store.domain.Store;
import store.dto.ProductDto.ProductBuilder;

public record StoreDto(List<ProductDto> products) {

    public Store toStore() {
        Map<String, ProductBuilder> builders = toBuilders();
        Map<String, Product> products = toProducts(builders);
        return new Store(products);
    }

    private Map<String, ProductBuilder> toBuilders() {
        Map<String, ProductBuilder> builders = new LinkedHashMap<>();
        for (ProductDto productDto : products) {
            builders.compute(productDto.name(),
                    (name, existingProduct) -> toProductBuilder(existingProduct, productDto));
        }
        return builders;
    }

    private Map<String, Product> toProducts(Map<String, ProductBuilder> builders) {
        Map<String, Product> products = new LinkedHashMap<>();
        for (Entry<String, ProductBuilder> entry : builders.entrySet()) {
            products.put(entry.getKey(), entry.getValue().build());
        }
        return products;
    }

    private ProductBuilder toProductBuilder(ProductBuilder existedBuilder, ProductDto addedDto) {
        if (existedBuilder == null) {
            return addedDto.toProductBuilder();
        }
        return existedBuilder.productDto(addedDto);
    }
}
