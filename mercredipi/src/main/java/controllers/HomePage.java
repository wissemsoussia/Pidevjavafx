package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HomePage {


    public Button login;
    public Button register;

    @FXML
    public void initialize(){
        login.setOnAction(event -> {
          try {
              Parent root = FXMLLoader.load(getClass().getResource("/SignIn.fxml"));
              Scene scene;

              scene = new Scene(root);
              Stage stage = new Stage();
              stage.setScene(scene);

              stage.show();
          } catch (IOException e) {
              throw new RuntimeException(e);
          }
        });
//        register.setOnAction(event -> {
//            try {
//                Parent root = FXMLLoader.load(getClass().getResource("/SignIn.fxml"));
//                Scene scene;
//
//                scene = new Scene(root);
//                Stage stage = new Stage();
//                stage.setScene(scene);
//
//                stage.show();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        });
    }
}
