package store.view;

import static java.util.stream.Collectors.toMap;

import camp.nextstep.edu.missionutils.Console;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import store.domain.dto.InventoryInputDto;
import store.domain.dto.PromotionInputDto;
import store.domain.dto.StoreInputDto;

public class InputView {

    private static final String PRODUCTS_FILE_NAME = "/products.md";
    private static final String PROMOTIONS_FILE_NAME = "/promotions.md";

    private static final String TOKEN_DELIMITER = ",";
    private static final String INPUT_EMPTY_PROMOTION_NAME = "null";

    private static final Pattern PRODUCTS_PATTERN = Pattern.compile("\\[([^]]+)-([0-9]+)]");

    private static final Map<String, Boolean> INPUT_TO_BOOLEAN = Map.of("Y", Boolean.TRUE, "N", Boolean.FALSE);

    private InputView() {
    }

    public static StoreInputDto inputStore() {
        try {
            List<InventoryInputDto> inventories = inputInventories();
            Map<String, PromotionInputDto> promotions = inputPromotions();
            return new StoreInputDto(inventories, promotions);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("파일 입력 형식이 잘못되었습니다.", e);
        }
    }

    private static List<InventoryInputDto> inputInventories() {
        return FileInput.readAllLines(PRODUCTS_FILE_NAME)
                .stream()
                .skip(1)
                .map(InputView::toInventory)
                .toList();
    }

    private static InventoryInputDto toInventory(String input) {
        String[] tokens = input.split(TOKEN_DELIMITER);
        String name = tokens[0];
        int price = toInt(tokens[1]);
        int quantity = toInt(tokens[2]);
        Optional<String> promotionName = toPromotionName(tokens[3]);
        return new InventoryInputDto(name, price, quantity, promotionName);
    }

    private static Optional<String> toPromotionName(String input) {
        if (INPUT_EMPTY_PROMOTION_NAME.equals(input)) {
            return Optional.empty();
        }
        return Optional.of(input);
    }

    private static Map<String, PromotionInputDto> inputPromotions() {
        return FileInput.readAllLines(PROMOTIONS_FILE_NAME)
                .stream()
                .skip(1)
                .map(InputView::toPromotion)
                .collect(toMap(PromotionInputDto::name, Function.identity()));
    }

    private static PromotionInputDto toPromotion(String input) {
        String[] tokens = input.split(TOKEN_DELIMITER);
        String name = tokens[0];
        int buyCount = toInt(tokens[1]);
        int getCount = toInt(tokens[2]);
        LocalDate startDate = LocalDate.parse(tokens[3]);
        LocalDate endDate = LocalDate.parse(tokens[4]);
        return new PromotionInputDto(name, buyCount, getCount, startDate, endDate);
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

    public static boolean inputCanAdd(String productName, int count) {
        return retryWhenThrowException(() -> {
            System.out.printf("%n현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)%n", productName, count);
            return toBoolean(readConsole());
        });
    }

    public static boolean inputProgressPurchaseWhenNotPromoted(String productName, int count) {
        return retryWhenThrowException(() -> {
            System.out.printf("%n현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)%n", productName, count);
            return toBoolean(readConsole());
        });
    }

    public static boolean inputUsingMembership() {
        return retryWhenThrowException(() -> {

            System.out.println("\n멤버십 할인을 받으시겠습니까? (Y/N)");
            return toBoolean(readConsole());
        });
    }

    public static boolean inputPurchaseAgain() {
        return retryWhenThrowException(() -> {

            System.out.println("\n감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
            return toBoolean(readConsole());
        });
    }

    private static <T> T retryWhenThrowException(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException exception) {
                OutputView.printExceptionMessage(exception);
            }
        }
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
