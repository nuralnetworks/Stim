package views;

import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import models.Product;

public class GameCard extends VBox {
    
    public GameCard(Product product, Runnable onClick) {
        getStyleClass().add("card-pane");
        setPrefSize(280, 240);
        setMaxSize(280, 240);
        
        ImageView imageView = new ImageView();
        Image img = new Image(product.getImageUrl(), 280, 160, false, true, true);
        imageView.setImage(img);
        imageView.setFitWidth(280);
        imageView.setFitHeight(160);
        
        javafx.scene.shape.Rectangle clip = new javafx.scene.shape.Rectangle(280, 160);
        clip.setArcWidth(24);
        clip.setArcHeight(24);
        imageView.setClip(clip);

        VBox content = new VBox(5);
        content.setStyle("-fx-padding: 10;");
        
        Label title = new Label(product.getTitle());
        title.getStyleClass().add("card-title");
        
        HBox bottom = new HBox();
        bottom.setAlignment(Pos.CENTER_LEFT);
        
        Label genre = new Label(product.getGenre() + " | $" + String.format("%.2f", product.getPrice()));
        genre.getStyleClass().add("body-label");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Button viewBtn = new Button("VIEW");
        viewBtn.getStyleClass().add("steam-button");
        viewBtn.setOnAction(e -> {
            if (onClick != null) onClick.run();
        });
        
        bottom.getChildren().addAll(genre, spacer, viewBtn);
        content.getChildren().addAll(title, bottom);
        
        getChildren().addAll(imageView, content);
        
        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(150), this);
        scaleIn.setToX(1.05);
        scaleIn.setToY(1.05);
        
        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(150), this);
        scaleOut.setToX(1.0);
        scaleOut.setToY(1.0);
        
        setOnMouseEntered(e -> scaleIn.playFromStart());
        setOnMouseExited(e -> scaleOut.playFromStart());
        setOnMouseClicked(e -> {
            if (onClick != null) onClick.run();
        });
    }
}
