package store;

import java.util.function.Function;
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

        RequireDetails requireDetails = new RequireDetails(InputView.inputPurchaseProducts());
        STORE.validateProducts(requireDetails);

        for (RequireDetail detail : requireDetails.details()) {
            String productName = detail.getName();
            int countOfFreeProducts = STORE.countFreeProductsWhenPurchased(detail);
            if (countOfFreeProducts > 0 && InputView.inputCanPromote(productName, countOfFreeProducts)) {
                detail.plus(countOfFreeProducts);
            }
        }

        for (RequireDetail detail : requireDetails.details()) {
            String productName = detail.getName();
            int countOfNonPromotedProduct = STORE.countRegularPriceProducts(detail);
            if (STORE.isLackPromotionProduct(detail) && countOfNonPromotedProduct > 0
                    && InputView.inputCanPromote(productName, countOfNonPromotedProduct)) {
                detail.minus(countOfNonPromotedProduct);
            }
        }

        boolean isDiscountMembership = InputView.inputUsingMembership();
        Receipt receipt = STORE.buy(requireDetails, isDiscountMembership);
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

    private static <T, R> R retryWhenThrowException(Function<T, R> function, T argument) {
        while (true) {
            try {
                return function.apply(argument);
            } catch (IllegalArgumentException exception) {
                OutputView.printExceptionMessage(exception);
            }
        }
    }
}
