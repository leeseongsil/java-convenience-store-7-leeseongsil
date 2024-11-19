package store.view;

import java.util.List;
import java.util.stream.Collectors;
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
                현재 보유하고 있는 상품입니다.%n
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
        String title = """
                %n==============W 편의점================
                상품명		수량	금액
                """.formatted();
        String contents = receipt.getHistories()
                .stream()
                .filter(history -> history.getTotalCount() != 0)
                .map(history -> "%-10s\t%-6d\t%,6d%n".formatted(
                        history.getName(), history.getTotalCount(), history.getBasePrice()))
                .collect(Collectors.joining());
        return title.concat(contents);
    }

    private static String toGiftView(Receipt receipt) {
        String title = "=============증\t정===============%n".formatted();
        String contents = receipt.getHistories()
                .stream()
                .filter(history -> history.getFreeCount() != 0)
                .map(history -> "%-10s\t%-6d%n".formatted(history.getName(), history.getFreeCount()))
                .collect(Collectors.joining());
        return title.concat(contents);
    }

    private static String toReceiptResultView(Receipt receipt) {
        StringBuilder sb = new StringBuilder();
        sb.append("%-10s\t%-6d\t%,6d%n".formatted("총구매액", receipt.totalCount(), receipt.totalBasePrice()));
        sb.append("%-15s\t%,6d%n".formatted("행사할인", -receipt.totalEventDiscountPrice()));
        sb.append("%-15s\t%,6d%n".formatted("멤버십할인", -receipt.getMembershipDiscountPrice()));
        sb.append("%-15s\t%,6d%n".formatted("내실돈", receipt.finalPrice()));
        return sb.toString();
    }

    public static void printExceptionMessage(Exception exception) {
        System.out.println("[ERROR] " + exception.getMessage());
    }
}
