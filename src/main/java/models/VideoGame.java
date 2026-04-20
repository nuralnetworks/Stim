package models;

public class VideoGame extends Product {
    private String developer;

    public VideoGame(String id, String title, double price, String genre, String developer, String imageUrl) {
        super(id, title, price, genre, imageUrl);
        this.developer = developer;
    }

    public String getDeveloper() {
        return developer;
    }

    @Override
    public String getProductType() {
        return "Video Game";
    }

    @Override
    public boolean purchase(Player player) {
        if (player.getLibrary().owns(this)) return false;
        if (player.deductBalance(getPrice())) {
            player.getLibrary().addProduct(this);
            return true;
        }
        return false;
    }

    @Override
    public boolean refund(Player player) {
        if (player.getLibrary().owns(this)) {
            player.getLibrary().removeProduct(this);
            player.addBalance(getPrice());
            return true;
        }
        return false;
    }
}
