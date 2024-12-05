package store.domain.dto;

public class RequireDetail {

    private final String name;
    private int quantity;

    public RequireDetail(final String name, final int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public void plus(int quantity) {
        this.quantity += quantity;
    }

    public void minus(int quantity) {
        this.quantity -= quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
}
