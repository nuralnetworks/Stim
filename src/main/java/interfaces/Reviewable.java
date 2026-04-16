package interfaces;

import models.Review;
import java.util.List;

public interface Reviewable {
    void addReview(Review review);
    List<Review> getReviews();
    double getAverageScore();
}
