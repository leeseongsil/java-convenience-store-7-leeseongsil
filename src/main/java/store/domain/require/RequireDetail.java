package store.domain.require;

public class RequireDetail {

    private final String name;
    private int count;

    public RequireDetail(String name, int count) {
        validateCount(count);

        this.name = name;
        this.count = count;
    }

    private void validateCount(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("주문 개수는 0보다 커야 합니다");
        }
    }

    public boolean isNameMatch(String name) {
        return this.name.equals(name);
    }

    public void plus(int count) {
        this.count += count;
    }

    public void minus(int count) {
        if (this.count - count <= 0) {
            throw new IllegalArgumentException("주문 개수는 0보다 커야 합니다");
        }
        this.count -= count;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }
}
