package views;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import patterns.StoreManager;
import application.MainFX;

public class LoginView extends VBox {
    public LoginView() {
        setAlignment(Pos.CENTER);
        setSpacing(20);
        setStyle("-fx-background-color: #171a21;");

        VBox card = new VBox(20);
        card.setAlignment(Pos.CENTER);
        card.setMaxSize(400, 400);
        card.getStyleClass().add("card-pane");
        card.setStyle("-fx-padding: 40;");

        Label titleLabel = new Label("WELCOME TO STIM");
        titleLabel.getStyleClass().add("title-label");

        TextField userField = new TextField();
        userField.setPromptText("Username");
        userField.getStyleClass().add("text-field-dark");
        userField.setMaxWidth(300);

        PasswordField passField = new PasswordField();
        passField.setPromptText("Password");
        passField.getStyleClass().add("text-field-dark");
        passField.setMaxWidth(300);

        Button loginBtn = new Button("LOGIN");
        loginBtn.getStyleClass().add("steam-button");
        loginBtn.setPrefWidth(300);

        Button signupBtn = new Button("CREATE ACCOUNT");
        signupBtn.getStyleClass().add("secondary-button");
        signupBtn.setPrefWidth(300);

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: #ff5555;");

        loginBtn.setOnAction(e -> {
            boolean success = StoreManager.getInstance().login(userField.getText(), passField.getText());
            if (success) {
                MainFX.getInstance().navigateTo("STORE");
            } else {
                errorLabel.setText("Invalid credentials. Try again or create an account.");
            }
        });

        signupBtn.setOnAction(e -> {
            boolean success = StoreManager.getInstance().createAccount(userField.getText(), passField.getText());
            if (success) {
                MainFX.getInstance().navigateTo("STORE");
            } else {
                errorLabel.setText("Username missing, empty, or already exists.");
            }
        });

        card.getChildren().addAll(titleLabel, userField, passField, loginBtn, signupBtn, errorLabel);
        getChildren().add(card);
    }
}
