package com.example.pidev;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        MainFX.mainStage = primaryStage;

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/SignIn.fxml")));
            Scene scene = new Scene(root);
            primaryStage.setMinWidth(1550);
            primaryStage.setMinHeight(860);

            primaryStage.setTitle("Ajouter Personne");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
