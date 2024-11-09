package store.domain;

public class Product {

    private final String name;
    private int count;

    public Product(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public boolean canBuy(int count) {
        return count <= this.count;
    }

    public void buy(int count) {
        if (canBuy(count)) {
            this.count -= count;
            return;
        }
        throw new IllegalStateException("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
    }

    public int getCount() {
        return count;
    }
}
