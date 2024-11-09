package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PromotionTest {

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
