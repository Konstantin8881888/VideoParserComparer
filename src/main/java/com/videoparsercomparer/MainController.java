package com.videoparsercomparer;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
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

    @FXML
    private VBox mainContent;

    @FXML
    private ListView<String> videoFileListView;

    private ResourceBundle bundle;
    private Locale locale;
    private boolean isDarkTheme = false;
    private Scene scene;
    private final ObservableList<String> videoFiles = FXCollections.observableArrayList();
    private final List<CheckBox> diskCheckBoxes = new ArrayList<>();

    @FXML
    private void initialize() {
        locale = new Locale("en");
        bundle = ResourceBundle.getBundle("com.videoparsercomparer.i18n.strings", locale, new MainApp.UTF8Control());
        updateTexts();
        updateStatusLabel("Ready");
        detectAndDisplayDisks();
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

    @FXML
    private void scanSelectedDisks() {
        List<File> selectedDisks = getSelectedDisks();

        videoFiles.clear(); // Очищаем предыдущий список файлов

        for (File disk : selectedDisks) {
            try {
                Files.walkFileTree(disk.toPath(), EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE, new SimpleFileVisitor<>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                        if (isVideoFile(file)) {
                            videoFiles.add(file.toString());
                        }
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFileFailed(Path file, IOException exc) {
                        return FileVisitResult.CONTINUE; // Игнорируем ошибки доступа
                    }
                });
            } catch (IOException e) {
                e.printStackTrace(); // Обработка ошибок при сканировании файлов
            }
        }

        videoFileListView.setItems(videoFiles); // Отображаем найденные видеофайлы в ListView
        updateStatusLabel("Scan complete");
    }

    private void detectAndDisplayDisks() {
        File[] disks = File.listRoots();
        for (File disk : disks) {
            CheckBox checkBox = new CheckBox(disk.toString());
            diskCheckBoxes.add(checkBox);
            mainContent.getChildren().add(checkBox);
        }

        Button scanButton = new Button("Scan Disks");
        scanButton.setOnAction(e -> scanSelectedDisks());
        mainContent.getChildren().add(scanButton);
    }

    private List<File> getSelectedDisks() {
        List<File> selectedDisks = new ArrayList<>();
        for (CheckBox checkBox : diskCheckBoxes) {
            if (checkBox.isSelected()) {
                selectedDisks.add(new File(checkBox.getText()));
            }
        }
        return selectedDisks;
    }

    private boolean isVideoFile(Path file) {
        String fileName = file.getFileName().toString().toLowerCase();
        return fileName.endsWith(".mp4") || fileName.endsWith(".avi") || fileName.endsWith(".mkv");
        // Добавьте другие расширения видеоформатов по необходимости
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
