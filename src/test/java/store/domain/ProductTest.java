package store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void canBuyTest_hasEqualOrMoreThanNeed_returnTrue() {
        Product product = new Product("상품", 3);

        boolean actual = product.canBuy(3);

        assertThat(actual).isTrue();
    }

    @Test
    void canBuyTest_hasLessThanNeed_returnFalse() {
        Product product = new Product("상품", 3);

        boolean actual = product.canBuy(4);

        assertThat(actual).isFalse();
    }

    @Test
    void buyTest_hasEqualOrMoreThanNeed() {
        Product product = new Product("상품", 3);
        int expectedCount = 0;

        product.buy(3);

        assertThat(product.getCount()).isEqualTo(expectedCount);
    }

    @Test
    void buyTest_hasLessThanNeed_throwException() {
        Product product = new Product("상품", 3);

        assertThatThrownBy(() -> product.buy(4))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
    }
}
