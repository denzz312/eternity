package org.example.arccoscalculator.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ArccosApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/arccoscalculator/gui/arccos-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 200);
        stage.setTitle("Arccos Calculator");
        stage.setScene(scene);

        // Delay showing the window slightly
        Platform.runLater(stage::show);
    }

    public static void main(String[] args) {
        launch();
    }
}