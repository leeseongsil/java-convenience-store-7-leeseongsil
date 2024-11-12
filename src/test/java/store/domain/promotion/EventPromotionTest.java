package store.domain.promotion;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class EventPromotionTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 2})
    void validateGetCountTest_whenGetCountIsOutOfRange_throwException(int getCount) {
        assertThatThrownBy(
                () -> new EventPromotion("프로모션", 2, getCount, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 31)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("get 개수는 1 이여야 합니다");
    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    void validateGetCountTest_whenGetCountIsInRange(int getCount) {
        assertThatCode(
                () -> new EventPromotion("프로모션", 2, getCount, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 31)))
                .doesNotThrowAnyException();
    }
}
