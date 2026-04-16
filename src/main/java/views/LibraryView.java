package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import patterns.StoreManager;
import application.MainFX;
import models.Player;
import models.Product;

public class LibraryView extends BorderPane {
    
    public LibraryView() {
        Player cur = StoreManager.getInstance().getLoggedInUser();
        String bal = cur != null ? String.format("$%.2f", cur.getWalletBalance()) : "$0.00";
        String usr = cur != null ? cur.getUsername() : "";

        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(20, 40, 20, 40));

        Label titleStr = new Label("MY LIBRARY");
        titleStr.getStyleClass().add("title-label");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label userLabel = new Label(usr + "  |  " + bal);
        userLabel.getStyleClass().add("subtitle-label");

        Button storeBtn = new Button("STORE");
        storeBtn.getStyleClass().add("secondary-button");
        storeBtn.setOnAction(e -> MainFX.getInstance().navigateTo("STORE"));

        Button logoutBtn = new Button("LOGOUT");
        logoutBtn.getStyleClass().add("secondary-button");
        logoutBtn.setOnAction(e -> MainFX.getInstance().navigateTo("LOGIN"));

        header.getChildren().addAll(titleStr, spacer, userLabel, storeBtn, logoutBtn);
        setTop(header);

        FlowPane grid = new FlowPane(20, 20);
        grid.setPadding(new Insets(20, 40, 40, 40));
        
        java.util.List<Product> owned = cur.getLibrary().getOwnedProducts();
        if (owned.isEmpty()) {
            Label empty = new Label("Your library is empty.");
            empty.getStyleClass().add("subtitle-label");
            grid.getChildren().add(empty);
        } else {
            for (Product p : owned) {
                grid.getChildren().add(new GameCard(p, () -> MainFX.getInstance().showGameDetails(p)));
            }
        }

        ScrollPane scroll = new ScrollPane(grid);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        setCenter(scroll);
    }
}
