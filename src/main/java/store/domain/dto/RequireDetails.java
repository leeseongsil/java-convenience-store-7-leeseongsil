package store.domain.dto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RequireDetails {

    private final List<RequireDetail> details;

    public RequireDetails(Map<String, Integer> requires) {
        this.details = requires.entrySet()
                .stream()
                .map(entry -> new RequireDetail(entry.getKey(), entry.getValue()))
                .toList();
    }

    public List<RequireDetail> getDetails() {
        return details;
    }

    public Map<String, Integer> toMap() {
        return details.stream()
                .collect(Collectors.toMap(RequireDetail::getName, RequireDetail::getQuantity));
    }
}
