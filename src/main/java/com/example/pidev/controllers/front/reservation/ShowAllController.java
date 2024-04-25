package com.example.pidev.controllers.front.reservation;

import com.example.pidev.controllers.front.MainWindowController;
import com.example.pidev.controllers.front.voyage.ManageController;
import com.example.pidev.entities.Reservation;
import com.example.pidev.services.ReservationService;
import com.example.pidev.utils.AlertUtils;
import com.example.pidev.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ShowAllController implements Initializable {

    public static Reservation currentReservation;

    @FXML
    public Text topText;
    @FXML
    public Button addButton;
    @FXML
    public VBox mainVBox;


    List<Reservation> listReservation;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listReservation = ReservationService.getInstance().getAll();

        displayData();
    }

    void displayData() {
        mainVBox.getChildren().clear();

        Collections.reverse(listReservation);

        if (!listReservation.isEmpty()) {
            for (Reservation reservation : listReservation) {
                mainVBox.getChildren().add(makeReservationModel(reservation));
            }
        } else {
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setPrefHeight(200);
            stackPane.getChildren().add(new Text("Aucune donnée"));
            mainVBox.getChildren().add(stackPane);
        }
    }

    public Parent makeReservationModel(
            Reservation reservation
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_FRONT_MODEL_RESERVATION)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#nomText")).setText("Nom : " + reservation.getNom());
            ((Text) innerContainer.lookup("#prenomText")).setText("Prenom : " + reservation.getPrenom());
            ((Text) innerContainer.lookup("#emailText")).setText("Email : " + reservation.getEmail());
            ((Text) innerContainer.lookup("#numeroTelephoneText")).setText("NumeroTelephone : " + reservation.getNumeroTelephone());

            ((Text) innerContainer.lookup("#voyageText")).setText("Voyage : " + reservation.getVoyage().toString());


            ((Button) innerContainer.lookup("#editButton")).setOnAction((ignored) -> modifierReservation(reservation));
            ((Button) innerContainer.lookup("#deleteButton")).setOnAction((ignored) -> supprimerReservation(reservation));


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

    @FXML
    private void ajouterReservation(ActionEvent ignored) {
        com.example.pidev.controllers.front.reservation.ManageController.selectedVoyage = null;
        currentReservation = null;
        MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_MANAGE_RESERVATION);
    }

    private void modifierReservation(Reservation reservation) {
        com.example.pidev.controllers.front.reservation.ManageController.selectedVoyage = null;
        currentReservation = reservation;
        MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_MANAGE_RESERVATION);
    }

    private void supprimerReservation(Reservation reservation) {
        com.example.pidev.controllers.front.reservation.ManageController.selectedVoyage = null;
        currentReservation = null;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer la suppression");
        alert.setHeaderText(null);
        alert.setContentText("Etes vous sûr de vouloir supprimer reservation ?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.isPresent()) {
            if (action.get() == ButtonType.OK) {
                if (ReservationService.getInstance().delete(reservation.getId())) {
                    MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_RESERVATION);
                } else {
                    AlertUtils.makeError("Could not delete reservation");
                }
            }
        }
    }


}
