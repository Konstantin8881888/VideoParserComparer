package com.videoparsercomparer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class MainApp extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        MainApp.primaryStage = primaryStage;

        Locale locale = new Locale("en");
        ResourceBundle bundle = ResourceBundle.getBundle("com.videoparsercomparer.i18n.strings", locale, new UTF8Control());

        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/main.fxml"), bundle);
        Scene scene = new Scene(loader.load(), 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle(bundle.getString("app.title"));
        primaryStage.show();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    static class UTF8Control extends ResourceBundle.Control {
        @Override
        public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
                throws IOException {
            String bundleName = toBundleName(baseName, locale);
            String resourceName = toResourceName(bundleName, "properties");
            ResourceBundle bundle = null;
            try (InputStream stream = loader.getResourceAsStream(resourceName)) {
                if (stream != null) {
                    try (InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
                        bundle = new PropertyResourceBundle(reader);
                    }
                }
            }
            return bundle;
        }
    }
}
