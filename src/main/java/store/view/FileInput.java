package store.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class FileInput {

    private FileInput() {
    }

    public static List<String> readAllLines(String fileName) {
        try (InputStream inputStream = FileInput.class.getResourceAsStream(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            if (inputStream == null) {
                throw new IllegalStateException("해당 파일을 읽을 수 없습니다 : " + fileName);
            }

            return reader.lines()
                    .toList();
        } catch (NullPointerException | IOException e) {
            throw new IllegalStateException("해당 파일을 읽을 수 없습니다 : " + fileName, e);
        }
    }
}
