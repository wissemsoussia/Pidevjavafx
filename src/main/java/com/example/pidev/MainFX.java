package com.example.pidev;

import com.example.pidev.utils.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainFX {

    public static Stage mainStage;
    private static MainFX instance;

    public static MainFX getInstance() {
        if (instance == null) {
            instance = new MainFX();
        }
        return instance;
    }

    public void loadLogin() {
        loadScene(
                Constants.FXML_LOGIN,
                "Connexion",
                1550,
                860
        );
    }

    public void loadFront() {
        loadScene(
                Constants.FXML_FRONT_MAIN_WINDOW,
                "",
                1550,
                860
        );
    }

    public void loadBack() {
        loadScene(
                Constants.FXML_BACK_MAIN_WINDOW,
                "",
                1550,
                860
        );
    }

    public void logout() {

        System.out.println("Deconnexion ..");
        loadLogin();
    }

    private void loadScene(String fxmlLink, String title, int width, int height) {
        try {
            Stage primaryStage = mainStage;
            primaryStage.close();

            Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlLink))));
            scene.setFill(Color.TRANSPARENT);

            //primaryStage.getIcons().add(new Image("app/images/app-icon.png"));
            primaryStage.setTitle(title);
            primaryStage.setWidth(width);
            primaryStage.setHeight(height);
            primaryStage.setMinWidth(width);
            primaryStage.setMinHeight(height);
            primaryStage.setScene(scene);
            primaryStage.setX((Screen.getPrimary().getBounds().getWidth() / 2) - (width / 2.0));
            primaryStage.setY((Screen.getPrimary().getBounds().getHeight() / 2) - (height / 2.0));

            primaryStage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
