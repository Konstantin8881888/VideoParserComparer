package com.videoparsercompiler;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class MainController {

    @FXML
    public MenuBar menuBar;
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

    private ResourceBundle bundle;
    private Locale locale;

    @FXML
    private void initialize() {
        locale = new Locale("en");
        bundle = ResourceBundle.getBundle("com.videoparsercompiler.i18n.strings", locale, new MainApp.UTF8Control());
        updateTexts();
    }

    @FXML
    private void showAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(bundle.getString("about.title"));
        alert.setHeaderText(null);
        alert.setContentText(bundle.getString("about.content"));
        alert.showAndWait();
    }

    @FXML
    private void exitApp() {
        Stage stage = (Stage) MainApp.getPrimaryStage().getScene().getWindow();
        stage.close();
    }

    @FXML
    private void changeLanguageToEnglish() {
        locale = new Locale("en");
        bundle = ResourceBundle.getBundle("com.videoparsercompiler.i18n.strings", locale, new MainApp.UTF8Control());
        updateTexts();
    }

    @FXML
    private void changeLanguageToRussian() {
        locale = new Locale("ru");
        bundle = ResourceBundle.getBundle("com.videoparsercompiler.i18n.strings", locale, new MainApp.UTF8Control());
        updateTexts();
    }

    private void updateTexts() {
        menuFile.setText(bundle.getString("menu.file"));
        menuItemAbout.setText(bundle.getString("menu.about"));
        menuItemExit.setText(bundle.getString("menu.exit"));
        menuSettings.setText(bundle.getString("menu.settings"));
        menuChangeLanguage.setText(bundle.getString("menu.changeLanguage"));
        menuItemEnglish.setText("English");
        menuItemRussian.setText("Русский");
    }
}
