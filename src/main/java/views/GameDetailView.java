package views;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import patterns.StoreManager;
import application.MainFX;
import models.Player;
import models.Product;
import models.Review;

public class GameDetailView extends BorderPane {
    public GameDetailView(Product product) {
        Player currentPlayer = StoreManager.getInstance().getLoggedInUser();
        
        Button backBtn = new Button("← BACK");
        backBtn.getStyleClass().add("secondary-button");
        backBtn.setOnAction(e -> MainFX.getInstance().navigateTo("STORE"));
        
        HBox header = new HBox(backBtn);
        header.setPadding(new Insets(20, 20, 20, 20));

        VBox content = new VBox();
        content.setStyle("-fx-background-color: #171a21;");
        
        StackPane heroPane = new StackPane();
        heroPane.setPrefHeight(300);
        
        ImageView heroImg = new ImageView();
        heroImg.setImage(new Image(product.getImageUrl(), 1000, 400, false, true, true));
        heroImg.setFitWidth(1000);
        heroImg.setFitHeight(300);
        Rectangle clip = new Rectangle(1000, 300);
        heroImg.setClip(clip);

        Rectangle overlay = new Rectangle(1000, 300);
        overlay.setFill(new LinearGradient(
            0, 0, 0, 1, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
            new Stop(0, Color.web("#171a21", 0.3)),
            new Stop(1, Color.web("#171a21", 1.0))
        ));

        heroPane.getChildren().addAll(heroImg, overlay);
        content.getChildren().add(heroPane);

        VBox details = new VBox(15);
        details.setPadding(new Insets(20, 50, 50, 50));
        
        Label titleLabel = new Label(product.getTitle().toUpperCase());
        titleLabel.getStyleClass().add("title-label");
        
        Label genreLabel = new Label(product.getGenre() + "  |  " + product.getProductType());
        genreLabel.getStyleClass().add("subtitle-label");

        Label scoreLabel = new Label(String.format("⭐ %.1f / 5.0", product.getAverageScore()));
        scoreLabel.getStyleClass().add("accent-label");

        HBox actionPanel = new HBox(20);
        if (currentPlayer.getLibrary().owns(product)) {
            Button refBtn = new Button("REFUND ($" + String.format("%.2f", product.getPrice()) + ")");
            refBtn.getStyleClass().add("secondary-button");
            refBtn.setOnAction(e -> {
                if (product.refund(currentPlayer)) MainFX.getInstance().navigateTo("STORE");
            });
            actionPanel.getChildren().add(refBtn);
        } else {
            Button buyBtn = new Button("BUY FOR $" + String.format("%.2f", product.getPrice()));
            buyBtn.getStyleClass().add("steam-button");
            buyBtn.setOnAction(e -> {
                if (product.purchase(currentPlayer)) MainFX.getInstance().navigateTo("LIBRARY");
            });
            actionPanel.getChildren().add(buyBtn);
        }

        details.getChildren().addAll(titleLabel, genreLabel, scoreLabel, actionPanel);

        Label revTitle = new Label("PLAYER REVIEWS");
        revTitle.getStyleClass().add("subtitle-label");
        revTitle.setPadding(new Insets(30, 0, 5, 0));
        details.getChildren().add(revTitle);

        for (Review r : product.getReviews()) {
            VBox rCard = new VBox(5);
            rCard.getStyleClass().add("card-pane");
            rCard.setPadding(new Insets(15));
            Label rAuthor = new Label(r.getAuthor() + "  ⭐ " + r.getScore());
            rAuthor.getStyleClass().add("card-title");
            Label rText = new Label(r.getText());
            rText.getStyleClass().add("body-label");
            rText.setWrapText(true);
            rCard.getChildren().addAll(rAuthor, rText);
            details.getChildren().add(rCard);
        }

        if (currentPlayer.getLibrary().owns(product)) {
            Label wRev = new Label("WRITE YOUR REVIEW");
            wRev.getStyleClass().add("subtitle-label");
            wRev.setPadding(new Insets(30, 0, 5, 0));

            ComboBox<String> scoreCombo = new ComboBox<>();
            scoreCombo.getItems().addAll("5 Stars", "4 Stars", "3 Stars", "2 Stars", "1 Star");
            scoreCombo.getSelectionModel().selectFirst();
            scoreCombo.getStyleClass().add("combo-box");

            TextArea area = new TextArea();
            area.getStyleClass().add("text-field-dark");
            area.setPrefRowCount(3);
            
            Button sub = new Button("POST REVIEW");
            sub.getStyleClass().add("steam-button");
            sub.setOnAction(e -> {
                if (!area.getText().trim().isEmpty()) {
                    product.addReview(new Review(currentPlayer.getUsername(), 5 - scoreCombo.getSelectionModel().getSelectedIndex(), area.getText()));
                    MainFX.getInstance().showGameDetails(product);
                }
            });

            details.getChildren().addAll(wRev, scoreCombo, area, sub);
        }

        content.getChildren().add(details);
        
        ScrollPane scroll = new ScrollPane(content);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: #171a21; -fx-border-color: #171a21; -fx-control-inner-background: #171a21;");

        setTop(header);
        setCenter(scroll);
    }
}
