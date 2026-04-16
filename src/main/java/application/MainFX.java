package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import views.LoginView;
import views.StoreView;
import views.LibraryView;
import views.GameDetailView;
import models.Product;
import patterns.StoreManager;

public class MainFX extends Application {
    private static StackPane rootPane;
    private static MainFX instance;

    @Override
    public void start(Stage primaryStage) {
        instance = this;
        rootPane = new StackPane();
        Scene scene = new Scene(rootPane, 1000, 700);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        primaryStage.setTitle("Stim");
        primaryStage.setScene(scene);
        primaryStage.show();

        navigateTo("LOGIN");
    }

    public static MainFX getInstance() {
        return instance;
    }

    // clear rootPane and add new view onto it.
    public void navigateTo(String viewName) {
        rootPane.getChildren().clear();
        if (viewName.equals("LOGIN")) {
            StoreManager.getInstance().logout();
            rootPane.getChildren().add(new LoginView());
        } else if (viewName.equals("STORE")) {
            rootPane.getChildren().add(new StoreView());
        } else if (viewName.equals("LIBRARY")) {
            rootPane.getChildren().add(new LibraryView());
        }
    }

    public void showGameDetails(Product product) {
        rootPane.getChildren().clear();
        rootPane.getChildren().add(new GameDetailView(product));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
