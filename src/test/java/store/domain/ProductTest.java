package store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class ProductTest {

    private static final LocalDate START_DATE = LocalDate.of(2024, 1, 1);
    private static final LocalDate END_DATE = LocalDate.of(2024, 1, 31);
    private static final Promotion ONE_PLUS_ONE = new Promotion("1+1", 1, 1, START_DATE, END_DATE);

    @Test
    void canBuyTest_hasEqualOrMoreThanNeed_returnTrue() {
        ProductInventory promotionInventory = new ProductInventory(ONE_PLUS_ONE, 1_000, 3);
        ProductInventory noPromotionInventory = new ProductInventory(Promotion.noPromotion(), 1_000, 2);
        Product product = new Product("상품", promotionInventory, noPromotionInventory);

        boolean actual = product.canBuy(5);

        assertThat(actual).isTrue();
    }

    @Test
    void canBuyTest_hasLessThanNeed_returnFalse() {
        ProductInventory promotionInventory = new ProductInventory(ONE_PLUS_ONE, 1_000, 3);
        ProductInventory noPromotionInventory = new ProductInventory(Promotion.noPromotion(), 1_000, 2);
        Product product = new Product("상품", promotionInventory, noPromotionInventory);

        boolean actual = product.canBuy(6);

        assertThat(actual).isFalse();
    }

    @Test
    void buyTest_hasEqualOrMoreThanNeed() {
        ProductInventory promotionInventory = new ProductInventory(ONE_PLUS_ONE, 1_000, 3);
        ProductInventory noPromotionInventory = new ProductInventory(Promotion.noPromotion(), 1_000, 2);
        Product product = new Product("상품", promotionInventory, noPromotionInventory);

        product.buy(3);

        // TODO assert 문 추가
    }

    @Test
    void buyTest_hasLessThanNeed_throwException() {
        ProductInventory promotionInventory = new ProductInventory(ONE_PLUS_ONE, 1_000, 3);
        ProductInventory noPromotionInventory = new ProductInventory(Promotion.noPromotion(), 1_000, 2);
        Product product = new Product("상품", promotionInventory, noPromotionInventory);

        assertThatThrownBy(() -> product.buy(6))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
    }
}
