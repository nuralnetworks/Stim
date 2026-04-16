package patterns;

import models.Product;
import models.Player;
import java.util.ArrayList;
import java.util.List;

public class GenreRecommendationStrategy implements RecommendationStrategy {
    @Override
    public List<Product> recommendGames(Player player, List<Product> allProducts) {
        List<Product> owned = player.getLibrary().getOwnedProducts();
        if (owned.isEmpty()) {
            return new ArrayList<>(allProducts);
        }

        Product lastPlayed = owned.get(owned.size() - 1);
        String favGenre = lastPlayed.getGenre();

        List<Product> recommendations = new ArrayList<>();
        for (Product p : allProducts) {
            if (p.getGenre().equals(favGenre) && !player.getLibrary().owns(p)) {
                recommendations.add(p);
            }
        }

        if (recommendations.isEmpty()) {
            for (Product p : allProducts) {
                if (!player.getLibrary().owns(p)) {
                    recommendations.add(p);
                }
            }
        }

        return recommendations;
    }
}
