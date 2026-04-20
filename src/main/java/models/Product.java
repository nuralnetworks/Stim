package models;

import interfaces.Purchasable;
import interfaces.Reviewable;
import java.util.Objects;
import java.util.ArrayList;
import java.util.List;

public abstract class Product implements Purchasable, Reviewable {
    private String id;
    private String title;
    private double price;
    private String genre;
    private String imageUrl;
    private List<Review> reviews;

    public Product(String id, String title, double price, String genre, String imageUrl) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.genre = genre;
        this.imageUrl = imageUrl;
        this.reviews = new ArrayList<>();
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public double getPrice() { return price; }
    public String getGenre() { return genre; }
    public String getImageUrl() { return imageUrl; }

    public abstract String getProductType();

    @Override
    public void addReview(Review review) { reviews.add(review); }

    @Override
    public List<Review> getReviews() { return new ArrayList<>(reviews); }

    @Override
    public double getAverageScore() {
        if (reviews.isEmpty()) return 0.0;
        double sum = 0;
        for (Review r : reviews) sum += r.getScore();
        return sum / reviews.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
