package store.domain.require;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record RequireDetails(List<RequireDetail> details) {

    public RequireDetails(Map<String, Integer> details) {
        this(toDetailsList(details));
    }

    private static List<RequireDetail> toDetailsList(Map<String, Integer> details) {
        return details.entrySet()
                .stream()
                .map(entry -> new RequireDetail(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public void minus(String name, int count) {
        getDetail(name).minus(count);
    }

    private RequireDetail getDetail(String name) {
        return details.stream()
                .filter(detail -> detail.isNameMatch(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No detail found with name " + name));
    }
}
