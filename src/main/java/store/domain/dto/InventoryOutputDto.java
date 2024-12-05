package store.domain.dto;

public record InventoryOutputDto(
        String productName,
        int price,
        int quantity,
        String promotionName
) {

    private static final String EMPTY_PROMOTION_NAME = "";

    public InventoryOutputDto(String productName, int price, int quantity) {
        this(productName, price, quantity, EMPTY_PROMOTION_NAME);
    }
}
