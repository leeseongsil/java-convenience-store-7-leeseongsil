package store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class PromotionTest {

    @ParameterizedTest
    @ValueSource(ints = {-1, 2})
    void validateGetCountTest_whenGetCountIsOutOfRange_throwException(int getCount) {
        assertThatThrownBy(() -> new Promotion("프로모션", 2, getCount, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 31)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("get 개수는 0와 1 사이여야 합니다");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    void validateGetCountTest_whenGetCountIsInRange(int getCount) {
        assertThatCode(() -> new Promotion("프로모션", 2, getCount, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 31)))
                .doesNotThrowAnyException();
    }

    @Test
    void validateDatesTest_whenStartDateIsAfterThanEndDate_throwException() {
        LocalDate startDate = LocalDate.of(2024, 1, 2);
        LocalDate endDate = LocalDate.of(2024, 1, 1);

        assertThatThrownBy(() -> new Promotion("프로모션", 2, 1, startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시작 날짜는 끝 날짜보다 이전이거나 같아야 합니다");
    }

    @Test
    void validateDatesTest_whenStartDateIsBeforeOrEqualThanEndDate() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 1);

        assertThatCode(() -> new Promotion("프로모션", 2, 1, startDate, endDate))
                .doesNotThrowAnyException();
    }


    @ParameterizedTest
    @CsvSource({
            "2024-01-01, 2024-01-31, 2023-12-31, false",
            "2024-01-01, 2024-01-31, 2024-01-01, true",
            "2024-01-01, 2024-01-31, 2024-01-31, true",
            "2024-01-01, 2024-01-31, 2024-02-01, false",})
    void canPromoteTest(String startDateFormat, String endDateFormat, String currentDateFormat, boolean expected) {
        LocalDate startDate = LocalDate.parse(startDateFormat);
        LocalDate endDate = LocalDate.parse(endDateFormat);
        Promotion promotion = new Promotion("프로모션", 2, 1, startDate, endDate);

        LocalDate currentDate = LocalDate.parse(currentDateFormat);
        boolean actaul = promotion.canPromote(currentDate);

        assertThat(actaul).isEqualTo(expected);
    }
}
