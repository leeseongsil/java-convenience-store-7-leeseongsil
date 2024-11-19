package store.view;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class FileInputTest {

    @Test
    void readAllLinesTest() {
        String fileName = "/test.md";
        List<String> expected = List.of("test1", "test2");

        List<String> actual = FileInput.readAllLines(fileName);

        assertThat(actual).isEqualTo(expected);
    }
}
