package store.view;

import static java.util.stream.Collectors.toMap;

import camp.nextstep.edu.missionutils.Console;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import store.dto.ProductDto;
import store.dto.PromotionDto;
import store.dto.StoreDto;

public class InputView {

    private static final String PROMOTIONS_FILE_NAME = "/promotions.md";
    private static final String PRODUCTS_FILE_NAME = "/products.md";

    private static final String TOKEN_DELIMITER = ",";
    private static final String INPUT_EMPTY_PROMOTION = "null";

    private static final Pattern PRODUCTS_PATTERN = Pattern.compile("\\[([^]]+)-([0-9]+)]");

    private InputView() {
    }

    public static StoreDto inputStore() {
        Map<String, PromotionDto> promotions = inputPromotions();
        List<ProductDto> products = inputProducts(promotions);
        return new StoreDto(products);
    }

    private static Map<String, PromotionDto> inputPromotions() {
        return FileInput.readAllLines(PROMOTIONS_FILE_NAME)
                .stream()
                .map(InputView::toPromotion)
                .collect(toMap(PromotionDto::name, Function.identity()));
    }

    private static PromotionDto toPromotion(String input) {
        String[] tokens = input.split(TOKEN_DELIMITER);
        String name = tokens[0];
        int buyCount = toInt(tokens[1]);
        int getCount = toInt(tokens[2]);
        LocalDate startDate = LocalDate.parse(tokens[3]);
        LocalDate endDate = LocalDate.parse(tokens[4]);
        return new PromotionDto(name, buyCount, getCount, startDate, endDate);
    }

    private static List<ProductDto> inputProducts(Map<String, PromotionDto> promotions) {
        return FileInput.readAllLines(PRODUCTS_FILE_NAME)
                .stream()
                .map(input -> toProduct(input, promotions))
                .toList();
    }

    private static ProductDto toProduct(String input, Map<String, PromotionDto> promotions) {
        String[] tokens = input.split(TOKEN_DELIMITER);
        String name = tokens[0];
        int price = toInt(tokens[1]);
        int quantity = toInt(tokens[2]);
        String promotionName = tokens[3];
        PromotionDto promotion = findPromotion(promotionName, promotions);
        return new ProductDto(name, price, quantity, promotion);
    }

    private static PromotionDto findPromotion(String promotionName, Map<String, PromotionDto> promotions) {
        if (promotionName.equals(INPUT_EMPTY_PROMOTION)) {
            return PromotionDto.emptyPromotion();
        }
        return promotions.computeIfAbsent(promotionName, key -> {
            throw new IllegalArgumentException("해당 프로모션이 존재하지 않습니다");
        });
    }

    public static Map<String, Integer> inputPurchaseProducts() {
        System.out.println("\n구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        return toPurchasedProducts(readConsole());
    }

    private static Map<String, Integer> toPurchasedProducts(String text) {
        Matcher matcher = PRODUCTS_PATTERN.matcher(text);
        Map<String, Integer> result = new LinkedHashMap<>();
        while (matcher.find()) {
            String product = matcher.group(1);
            int quantity = toInt(matcher.group(2));
            result.put(product, quantity);
        }
        return result;
    }

    private static int toInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자를 입력해야 합니다 input : " + input, e);
        }
    }

    private static String readConsole() {
        return Console.readLine();
    }
}
