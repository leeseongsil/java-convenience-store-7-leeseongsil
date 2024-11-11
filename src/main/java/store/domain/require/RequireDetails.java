package store.domain.require;

import java.util.List;
import java.util.Map;

public record RequireDetails(List<RequireDetail> details) {

    public RequireDetails(Map<String, Integer> details) {
        this(toDetailsList(details));
    }

    private static List<RequireDetail> toDetailsList(Map<String, Integer> details) {
        return details.entrySet()
                .stream()
                .map(entry -> new RequireDetail(entry.getKey(), entry.getValue()))
                .toList();
    }
}
