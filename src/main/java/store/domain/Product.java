package store.domain;

public class Product {

    private final String name;
    private final int count;

    public Product(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public boolean canBuy(int count) {
        return count <= this.count;
    }
}
