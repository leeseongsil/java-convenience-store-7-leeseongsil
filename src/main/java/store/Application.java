package store;

import java.util.Map;
import java.util.function.Supplier;
import store.domain.Store;
import store.domain.dto.RequireDetail;
import store.domain.dto.RequireDetails;
import store.domain.receipt.Receipt;
import store.view.InputView;
import store.view.OutputView;

public class Application {

    private static final Store STORE = InputView.inputStore().toStore();

    private Application() {
    }

    public static void main(String[] args) {
        do {
            OutputView.printProducts(STORE.getInventories());

            RequireDetails requireDetails = retryWhenThrowException(Application::inputRequireDetails);
            requireDetails.getDetails().forEach(Application::requestFreeProductWhenPurchased);
            requireDetails.getDetails().forEach(Application::progressPurchaseWhenNotPromoted);

            boolean isDiscountMembership = InputView.inputUsingMembership();
            printReceipt(requireDetails, isDiscountMembership);
        } while (InputView.inputPurchaseAgain());
    }

    private static RequireDetails inputRequireDetails() {
        Map<String, Integer> requires = InputView.inputPurchaseProducts();
        if (!STORE.canBuy(requires)) {
            throw new IllegalArgumentException("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }
        return new RequireDetails(requires);
    }

    private static void requestFreeProductWhenPurchased(RequireDetail detail) {
        int countOfFreeProducts = STORE.countAddableFreeProducts(detail.getName(), detail.getQuantity());
        if (countOfFreeProducts > 0
                && InputView.inputCanAdd(detail.getName(), countOfFreeProducts)) {
            detail.plus(countOfFreeProducts);
        }
    }

    private static void progressPurchaseWhenNotPromoted(RequireDetail detail) {
        String productName = detail.getName();
        int countOfNonPromotedProduct = STORE.countRegularPriceQuantity(detail.getName(), detail.getQuantity());
        if (STORE.isLackPromotionQuantity(detail.getName(), detail.getQuantity())
                && countOfNonPromotedProduct > 0
                && InputView.inputProgressPurchaseWhenNotPromoted(productName, countOfNonPromotedProduct)) {
            detail.minus(countOfNonPromotedProduct);
        }
    }

    private static void printReceipt(RequireDetails requireDetails, boolean isDiscountMembership) {
        Receipt receipt = STORE.buy(requireDetails.toMap(), isDiscountMembership);
        OutputView.printReceipt(receipt);
    }

    private static <T> T retryWhenThrowException(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException exception) {
                OutputView.printExceptionMessage(exception);
            }
        }
    }
}
