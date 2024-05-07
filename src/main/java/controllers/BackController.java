package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


/**
     * FXML Controller class
     *
     * @author ASUS
     */
    public class BackController implements Initializable {




        @FXML
        private Button Fermer;
        @FXML
        private Button Gestion_User;

        /**
         * Initializes the controller class.
         */
        @FXML
        public void exit(){
            System.exit(0);
        }
        @Override
        public void initialize(URL url, ResourceBundle rb) {
            // TODO
        }



        @FXML
        private void Gestion_User(ActionEvent event) {
            try {

                Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Admin.fxml")));
                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }







}
