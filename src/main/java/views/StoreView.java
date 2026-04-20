package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import patterns.StoreManager;
import application.MainFX;
import models.Player;
import models.Product;

public class StoreView extends BorderPane {
    
    public StoreView() {
        Player cur = StoreManager.getInstance().getLoggedInUser();
        String bal = cur != null ? String.format("$%.2f", cur.getWalletBalance()) : "$0.00";
        String usr = cur != null ? cur.getUsername() : "";

        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(20, 40, 20, 40));

        Label titleStr = new Label("DISCOVER");
        titleStr.getStyleClass().add("title-label");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label userLabel = new Label(usr + "  |  " + bal);
        userLabel.getStyleClass().add("subtitle-label");

        Button libBtn = new Button("LIBRARY");
        libBtn.getStyleClass().add("secondary-button");
        libBtn.setOnAction(e -> MainFX.getInstance().navigateTo("LIBRARY"));

        Button logoutBtn = new Button("LOGOUT");
        logoutBtn.getStyleClass().add("secondary-button");
        logoutBtn.setOnAction(e -> MainFX.getInstance().navigateTo("LOGIN"));

        header.getChildren().addAll(titleStr, spacer, userLabel, libBtn, logoutBtn);
        setTop(header);

        VBox content = new VBox(30);
        content.setPadding(new Insets(20, 40, 40, 40));

        Label recLabel = new Label("RECOMMENDED FOR YOU");
        recLabel.getStyleClass().add("subtitle-label");
        FlowPane recGrid = new FlowPane(20, 20);
        for (Product p : StoreManager.getInstance().getRecommendations()) {
            recGrid.getChildren().add(new GameCard(p, () -> MainFX.getInstance().showGameDetails(p)));
        }
        
        Label allLabel = new Label("ALL GAMES");
        allLabel.getStyleClass().add("subtitle-label");
        FlowPane allGrid = new FlowPane(20, 20);
        for (Product p : StoreManager.getInstance().getAllProducts()) {
            allGrid.getChildren().add(new GameCard(p, () -> MainFX.getInstance().showGameDetails(p)));
        }

        content.getChildren().addAll(recLabel, recGrid, allLabel, allGrid);

        ScrollPane scroll = new ScrollPane(content);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        setCenter(scroll);
    }
}
