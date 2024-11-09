package store.domain;

public interface Inventory {

    boolean isValidPromotion();

    boolean isExistProduct();

    int getCurrentCount();
}
