package store.view;

import java.util.List;
import store.domain.result.PurchaseHistory;
import store.domain.result.Receipt;
import store.dto.ProductResponseDto;

public class OutputView {

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

    public static void printReceipt(Receipt receipt) {
        System.out.print(toPurchaseView(receipt));
        System.out.println(toGiftView(receipt));
        System.out.println(toReceiptResultView(receipt));
    }

    private static String toPurchaseView(Receipt receipt) {
        StringBuilder sb = new StringBuilder();
        sb.append("""
                %n==============W 편의점================
                상품명		수량	금액%n
                """.formatted());
        for (PurchaseHistory history : receipt.getHistories()) {
            sb.append("%-10s\t%-6d\t%,6d%n".formatted(
                    history.getName(), history.getRegularCount(), history.getPricePerCount()));
        }
        return sb.toString();
    }

    private static String toGiftView(Receipt receipt) {
        StringBuilder sb = new StringBuilder();
        sb.append("=============증\t정===============%n".formatted());
        for (PurchaseHistory history : receipt.getHistories()) {
            sb.append("%-10s\t%-6d%n".formatted(history.getName(), history.getFreeCount()));
        }
        return sb.toString();
    }

    private static String toReceiptResultView(Receipt receipt) {
        StringBuilder sb = new StringBuilder();
        sb.append("%-10s\t%-6d\t%,6d%n".formatted("총구매액", receipt.totalRegularCount(), receipt.totalBasePrice()));
        sb.append("%-15s\t%6d%n".formatted("행사할인", -receipt.totalEventDiscountPrice()));
        sb.append("%-15s\t%6d%n".formatted("멤버십할인", -receipt.getMembershipDiscountPrice()));
        sb.append("%-15s\t%6d%n".formatted("내실돈", receipt.finalPrice()));
        return sb.toString();
    }
}
