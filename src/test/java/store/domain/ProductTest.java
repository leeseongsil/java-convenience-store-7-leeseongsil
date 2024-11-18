package store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import store.domain.inventory.ProductInventory;
import store.domain.inventory.Promotion;
import store.domain.promotion.EventPromotion;
import store.domain.promotion.RegularPricePromotion;

class ProductTest {

    private static final LocalDate START_DATE = LocalDate.of(2024, 1, 1);
    private static final LocalDate END_DATE = LocalDate.of(2024, 12, 31);
    private static final Promotion ONE_PLUS_ONE = new EventPromotion("1+1", 1, 1, START_DATE, END_DATE);
    private static final Promotion REGULAR_PRICE = new RegularPricePromotion();

    @Test
    void canBuyTest_hasEqualOrMoreThanNeed_returnTrue() {
        Product product = Product.createProduct("name", 1_000, 3, 2, ONE_PLUS_ONE);

        boolean actual = product.canBuy(5);

        assertThat(actual).isTrue();
    }

    @Test
    void canBuyTest_hasLessThanNeed_returnFalse() {
        Product product = Product.createProduct("name", 1_000, 3, 2, ONE_PLUS_ONE);

        boolean actual = product.canBuy(6);

        assertThat(actual).isFalse();
    }

    @Test
    void buyTest_hasLessThanNeed_throwException() {
        Product product = Product.createProduct("name", 1_000, 3, 2, ONE_PLUS_ONE);

        assertThatThrownBy(() -> product.buy(6))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
    }
}
