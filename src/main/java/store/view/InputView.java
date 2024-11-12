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

    private static final Map<String, Boolean> INPUT_TO_BOOLEAN = Map.of("Y", Boolean.TRUE, "N", Boolean.FALSE);

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
                .skip(1)
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
                .skip(1)
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
        if (promotionName.trim().equals(INPUT_EMPTY_PROMOTION)) {
            return PromotionDto.emptyPromotion();
        }
        PromotionDto promotion = promotions.get(promotionName);
        if (promotion == null) {
            throw new IllegalArgumentException("해당 프로모션이 존재하지 않습니다");
        }
        return promotion;
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

    public static boolean inputCanPromote(String productName, int count) {
        System.out.printf("%n현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)%n", productName, count);
        return toBoolean(readConsole());
    }

    public static boolean inputProgressPurchaseWhenNotPromoted(String productName, int count) {
        System.out.printf("%n현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)%n", productName, count);
        return toBoolean(readConsole());
    }

    public static boolean inputUsingMembership() {
        System.out.println("\n멤버십 할인을 받으시겠습니까? (Y/N)");
        return toBoolean(readConsole());
    }

    private static int toInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자를 입력해야 합니다 input : " + input, e);
        }
    }

    private static boolean toBoolean(String input) {
        Boolean result = INPUT_TO_BOOLEAN.get(input);
        if (result == null) {
            throw new IllegalArgumentException("잘못된 입력입니다. 다시 입력해 주세요.");
        }
        return result;
    }

    private static String readConsole() {
        return Console.readLine();
    }
}
