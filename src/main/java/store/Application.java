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

    public static void main(String[] args) {
        new Application().run();
    }

    private final Store store;

    private Application() {
        this.store = InputView.inputStore().toStore();
    }

    public void run() {
        do {
            OutputView.printProducts(store.getInventories());

            RequireDetails requireDetails = retryWhenThrowException(this::inputRequireDetails);
            requireDetails.getDetails().forEach(this::requestFreeProductWhenPurchased);
            requireDetails.getDetails().forEach(this::progressPurchaseWhenNotPromoted);

            boolean isDiscountMembership = InputView.inputUsingMembership();
            printReceipt(requireDetails, isDiscountMembership);
        } while (InputView.inputPurchaseAgain());
    }

    private RequireDetails inputRequireDetails() {
        Map<String, Integer> requires = InputView.inputPurchaseProducts();
        if (!store.canBuy(requires)) {
            throw new IllegalArgumentException("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }
        return new RequireDetails(requires);
    }

    private void requestFreeProductWhenPurchased(RequireDetail detail) {
        int countOfFreeProducts = store.countAddableFreeProducts(detail.getName(), detail.getQuantity());
        if (countOfFreeProducts > 0
                && InputView.inputCanAdd(detail.getName(), countOfFreeProducts)) {
            detail.plus(countOfFreeProducts);
        }
    }

    private void progressPurchaseWhenNotPromoted(RequireDetail detail) {
        String productName = detail.getName();
        int countOfNonPromotedProduct = store.countRegularPriceQuantity(detail.getName(), detail.getQuantity());
        if (store.isLackPromotionQuantity(detail.getName(), detail.getQuantity())
                && countOfNonPromotedProduct > 0
                && !InputView.inputProgressPurchaseWhenNotPromoted(productName, countOfNonPromotedProduct)) {
            detail.minus(countOfNonPromotedProduct);
        }
    }

    private void printReceipt(RequireDetails requireDetails, boolean isDiscountMembership) {
        Receipt receipt = store.buy(requireDetails.toMap(), isDiscountMembership);
        OutputView.printReceipt(receipt);
    }

    private <T> T retryWhenThrowException(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException exception) {
                OutputView.printExceptionMessage(exception);
            }
        }
    }
}
