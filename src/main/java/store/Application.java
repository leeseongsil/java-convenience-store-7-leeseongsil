package store;

import java.util.function.Supplier;
import store.domain.Store;
import store.domain.require.RequireDetail;
import store.domain.require.RequireDetails;
import store.domain.result.Receipt;
import store.view.InputView;
import store.view.OutputView;

public class Application {

    private static final Store STORE = InputView.inputStore().toStore();

    public static void main(String[] args) {
        OutputView.printProducts(STORE.getProductResponses());

        RequireDetails requireDetails = retryWhenThrowException(Application::inputRequireDetails);
        requireDetails.details().forEach(Application::requestFreeProductWhenPurchased);
        requireDetails.details().forEach(Application::progressPurchaseWhenNotPromoted);

        boolean isDiscountMembership = InputView.inputUsingMembership();
        printReceipt(requireDetails, isDiscountMembership);
    }

    private static void requestFreeProductWhenPurchased(RequireDetail detail) {
        String productName = detail.getName();
        int countOfFreeProducts = STORE.countFreeProductsWhenPurchased(detail);
        if (countOfFreeProducts > 0
                && retryWhenThrowException(() -> InputView.inputCanPromote(productName, countOfFreeProducts))) {
            detail.plus(countOfFreeProducts);
        }
    }

    private static void progressPurchaseWhenNotPromoted(RequireDetail detail) {
        String productName = detail.getName();
        int countOfNonPromotedProduct = STORE.countRegularPriceProducts(detail);
        if (STORE.isLackPromotionProduct(detail) && countOfNonPromotedProduct > 0
                && retryWhenThrowException(
                () -> InputView.inputProgressPurchaseWhenNotPromoted(productName, countOfNonPromotedProduct))) {
            detail.minus(countOfNonPromotedProduct);
        }
    }

    private static void printReceipt(RequireDetails requireDetails, boolean isDiscountMembership) {
        Receipt receipt = STORE.buy(requireDetails, isDiscountMembership);
        OutputView.printReceipt(receipt);
    }

    private static RequireDetails inputRequireDetails() {
        RequireDetails requireDetails = new RequireDetails(InputView.inputPurchaseProducts());
        STORE.validateProducts(requireDetails);
        return requireDetails;
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
