package store.view;

import java.text.DecimalFormat;
import java.util.List;
import store.dto.ProductResponseDto;

public class OutputView {

    private static final DecimalFormat INT_FORMATTER = new DecimalFormat("###,###");

    private OutputView() {
    }

    public static void printProducts(List<ProductResponseDto> products) {
        System.out.println(toProductView(products));
    }

    private static String toProductView(List<ProductResponseDto> products) {
        StringBuilder sb = new StringBuilder();
        sb.append("""
                안녕하세요. W편의점입니다.
                현재 보유하고 있는 상품입니다.%n%n
                """.formatted());
        products.forEach(product -> sb.append(toProductView(product)));
        return sb.toString().trim();
    }

    private static String toProductView(ProductResponseDto product) {
        return "- %s %,d원 %s %s%n".formatted(
                product.productName(), product.price(), toCountView(product.count()), product.promotionName());
    }

    private static String toCountView(int productCount) {
        if (productCount <= 0) {
            return "재고 없음";
        }
        return "%,d개".formatted(productCount);
    }

    private String toIntFormat(int value) {
        return INT_FORMATTER.format(value);
    }
}
