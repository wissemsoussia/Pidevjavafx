package com.melocode.crudapp2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    public Button btnNewWindow2;
    @FXML
    private Button btnNewWindow;

    public void HandleBtnNewWindow(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/Fxml/Students.fxml"));
        Scene scene = new Scene(root);
        Stage Primarystage = new Stage();
        Primarystage.setTitle("CRUD");
        Primarystage.setScene(scene);
        Primarystage.initModality(Modality.NONE);
        Primarystage.show();
    }
    public void HandleBtnNewWindow2(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/Fxml/Afficher.fxml"));
        Scene scene = new Scene(root);
        Stage Primarystage = new Stage();
        Primarystage.setTitle("Affichage");
        Primarystage.setScene(scene);
        Primarystage.initModality(Modality.NONE);
        Primarystage.show();
    }
}
