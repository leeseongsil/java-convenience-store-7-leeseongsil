package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

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
}
