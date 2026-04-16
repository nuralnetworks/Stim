package patterns;

import models.Product;
import models.Player;
import java.util.List;

public interface RecommendationStrategy {
    List<Product> recommendGames(Player player, List<Product> allProducts);
}
