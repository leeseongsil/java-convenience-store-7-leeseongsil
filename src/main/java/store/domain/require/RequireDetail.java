package store.domain.require;

public record RequireDetail(String name, int count) {

    public RequireDetail {
        if (count <= 0) {
            throw new IllegalArgumentException("주문 개수는 0보다 커야 합니다");
        }
    }
}
