package patterns;

import models.Player;
import models.Product;
import models.VideoGame;
import models.Review;

import java.util.ArrayList;
import java.util.List;

public class StoreManager {
    private static StoreManager instance;
    private List<Product> products;
    private List<Player> players;
    private Player loggedInUser;
    private RecommendationStrategy recommendationStrategy;

    private StoreManager() {
        products = new ArrayList<>();
        players = new ArrayList<>();
        recommendationStrategy = new GenreRecommendationStrategy();
        initializeDummyData();
    }

    public static StoreManager getInstance() {
        if (instance == null) {
            instance = new StoreManager();
        }
        return instance;
    }

    private void initializeDummyData() {
        players.add(new Player("testuser", "password123", 500.0));

        products.add(new VideoGame("g1", "Red Dead Redemption 2", 59.99, "Open World", "Rockstar Games", "https://cdn.akamai.steamstatic.com/steam/apps/1174180/header.jpg"));
        products.add(new VideoGame("g2", "Cyberpunk 2077", 49.99, "RPG", "CD Projekt Red", "https://cdn.akamai.steamstatic.com/steam/apps/1091500/header.jpg"));
        products.add(new VideoGame("g3", "The Witcher 3: Wild Hunt", 39.99, "RPG", "CD Projekt Red", "https://cdn.akamai.steamstatic.com/steam/apps/292030/header.jpg"));
        products.add(new VideoGame("g4", "Hades", 24.99, "Roguelike", "Supergiant Games", "https://cdn.akamai.steamstatic.com/steam/apps/1145360/header.jpg"));
        products.add(new VideoGame("g5", "DOOM Eternal", 39.99, "Shooter", "id Software", "https://cdn.akamai.steamstatic.com/steam/apps/782330/header.jpg"));
        products.add(new VideoGame("g6", "Stardew Valley", 14.99, "Simulation", "ConcernedApe", "https://cdn.akamai.steamstatic.com/steam/apps/413150/header.jpg"));
        products.add(new VideoGame("g7", "God of War", 49.99, "Action", "Santa Monica Studio", "https://cdn.akamai.steamstatic.com/steam/apps/1593500/header.jpg"));
        products.add(new VideoGame("g8", "Hollow Knight", 14.99, "Metroidvania", "Team Cherry", "https://cdn.akamai.steamstatic.com/steam/apps/367520/header.jpg"));
        products.add(new VideoGame("g9", "Terraria", 9.99, "Sandbox", "Re-Logic", "https://cdn.akamai.steamstatic.com/steam/apps/105600/header.jpg"));
        products.add(new VideoGame("g10", "Grand Theft Auto V", 29.99, "Open World", "Rockstar North", "https://cdn.akamai.steamstatic.com/steam/apps/271590/header.jpg"));
        products.add(new VideoGame("g11", "Celeste", 19.99, "Platformer", "Maddy Makes Games", "https://cdn.akamai.steamstatic.com/steam/apps/504230/header.jpg"));
        products.add(new VideoGame("g12", "Dark Souls III", 59.99, "RPG", "FromSoftware", "https://cdn.akamai.steamstatic.com/steam/apps/374320/header.jpg"));

        products.get(0).addReview(new Review("critic", 5, "Absolutely legendary cowboy experience."));
        products.get(0).addReview(new Review("testuser", 4, "Too realistic sometimes, but great."));
        products.get(1).addReview(new Review("testuser", 5, "Fixed all the bugs, amazing city."));
    }

    public boolean login(String username, String password) {
        for (Player p : players) {
            if (p.getUsername().equals(username) && p.getPassword().equals(password)) {
                loggedInUser = p;
                return true;
            }
        }
        return false;
    }

    public boolean createAccount(String username, String password) {
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            return false;
        }
        for (Player p : players) {
            if (p.getUsername().equals(username)) {
                return false;
            }
        }
        Player newPlayer = new Player(username, password, 150.00);
        players.add(newPlayer);
        loggedInUser = newPlayer;
        return true;
    }

    public void logout() {
        loggedInUser = null;
    }

    public Player getLoggedInUser() {
        return loggedInUser;
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    public List<Product> getRecommendations() {
        if (loggedInUser != null) {
            return recommendationStrategy.recommendGames(loggedInUser, products);
        }
        return new ArrayList<>();
    }
}
