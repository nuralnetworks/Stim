package models;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Product> ownedProducts;

    public Library() {
        this.ownedProducts = new ArrayList<>();
    }

    public void addProduct(Product product) {
        if (!ownedProducts.contains(product)) {
            ownedProducts.add(product);
        }
    }

    public void removeProduct(Product product) {
        ownedProducts.remove(product);
    }

    public List<Product> getOwnedProducts() {
        return new ArrayList<>(ownedProducts);
    }

    public boolean owns(Product product) {
        return ownedProducts.contains(product);
    }
}
