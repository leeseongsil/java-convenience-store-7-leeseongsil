package store.domain.promotion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class EventPeriodTest {

    @Test
    void validateDatesTest_whenStartDateIsAfterThanEndDate_throwException() {
        LocalDate startDate = LocalDate.of(2024, 1, 2);
        LocalDate endDate = LocalDate.of(2024, 1, 1);

        assertThatThrownBy(() -> new EventPeriod(startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시작 날짜는 끝 날짜보다 이전이거나 같아야 합니다");
    }

    @Test
    void validateDatesTest_whenStartDateIsBeforeOrEqualThanEndDate() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 1);

        assertThatCode(() -> new EventPeriod(startDate, endDate))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource({
            "2024-01-01, 2024-01-31, 2023-12-31, false",
            "2024-01-01, 2024-01-31, 2024-01-01, true",
            "2024-01-01, 2024-01-31, 2024-01-31, true",
            "2024-01-01, 2024-01-31, 2024-02-01, false",})
    void containsTest(String startDateFormat, String endDateFormat, String currentDateFormat, boolean expected) {
        LocalDate startDate = LocalDate.parse(startDateFormat);
        LocalDate endDate = LocalDate.parse(endDateFormat);
        EventPeriod promotion = new EventPeriod(startDate, endDate);

        LocalDate currentDate = LocalDate.parse(currentDateFormat);
        boolean actaul = promotion.contains(currentDate);

        assertThat(actaul).isEqualTo(expected);
    }
}
