package com.videoparsercomparer;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController {

    @FXML
    private MenuBar menuBar;

    @FXML
    private Menu menuFile;

    @FXML
    private Menu menuSettings;

    @FXML
    private Menu menuChangeLanguage;

    @FXML
    private MenuItem menuItemAbout;

    @FXML
    private MenuItem menuItemExit;

    @FXML
    private MenuItem menuItemEnglish;

    @FXML
    private MenuItem menuItemRussian;

    @FXML
    private MenuItem menuItemToggleTheme;

    @FXML
    private Label statusLabel;

    private ResourceBundle bundle;
    private Locale locale;
    private boolean isDarkTheme = false;
    private Scene scene;

    @FXML
    private void initialize() {
        locale = new Locale("en");
        bundle = ResourceBundle.getBundle("com.videoparsercomparer.i18n.strings", locale, new MainApp.UTF8Control());
        updateTexts();
        updateStatusLabel("Ready");
    }

    @FXML
    private void showAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(bundle.getString("about.title"));
        alert.setHeaderText(null);
        alert.setContentText(bundle.getString("about.content"));
        alert.showAndWait();
        updateStatusLabel("Displayed About information");
    }

    @FXML
    private void exitApp() {
        Stage stage = (Stage) MainApp.getPrimaryStage().getScene().getWindow();
        stage.close();
        updateStatusLabel("Application closed");
    }

    @FXML
    private void changeLanguageToEnglish() {
        locale = new Locale("en");
        bundle = ResourceBundle.getBundle("com.videoparsercomparer.i18n.strings", locale, new MainApp.UTF8Control());
        updateTexts();
        playFadeAnimation();
        updateStatusLabel("Language changed to English");
    }

    @FXML
    private void changeLanguageToRussian() {
        locale = new Locale("ru");
        bundle = ResourceBundle.getBundle("com.videoparsercomparer.i18n.strings", locale, new MainApp.UTF8Control());
        updateTexts();
        playFadeAnimation();
        updateStatusLabel("Язык изменен на русский");
    }

    @FXML
    private void toggleTheme() {
        if (scene == null) {
            scene = MainApp.getPrimaryStage().getScene();
        }
        String darkTheme = Objects.requireNonNull(getClass().getResource("/dark-theme.css")).toExternalForm();
        if (isDarkTheme) {
            scene.getStylesheets().remove(darkTheme);
            updateStatusLabel("Switched to Light Theme");
        } else {
            scene.getStylesheets().add(darkTheme);
            updateStatusLabel("Switched to Dark Theme");
        }
        isDarkTheme = !isDarkTheme;
    }

    private void playFadeAnimation() {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), menuBar);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    private void updateTexts() {
        menuFile.setText(bundle.getString("menu.file"));
        menuItemAbout.setText(bundle.getString("menu.about"));
        menuItemExit.setText(bundle.getString("menu.exit"));
        menuSettings.setText(bundle.getString("menu.settings"));
        menuChangeLanguage.setText(bundle.getString("menu.changeLanguage"));
        menuItemEnglish.setText("English");
        menuItemRussian.setText("Русский");
        menuItemToggleTheme.setText(bundle.getString("menu.toggleTheme"));
    }

    private void updateStatusLabel(String message) {
        statusLabel.setText(message);
    }
}
