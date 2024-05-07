package com.example.pidev.controllers.front.voyage;

import com.example.pidev.controllers.front.MainWindowController;
import com.example.pidev.controllers.front.reservation.ManageController;
import com.example.pidev.entities.Voyage;
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
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import services.VoyageService;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ShowAllController implements Initializable {

    public static Voyage currentVoyage;

    @FXML
    public Text topText;
    @FXML
    public Button addButton;
    @FXML
    public VBox mainVBox;
    @FXML
    public ComboBox<String> sortCB;

    List<Voyage> listVoyage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listVoyage = VoyageService.getInstance().getAll();

        sortCB.getItems().addAll(
                "Tri par ville de depart",
                "Tri par destination",
                "Tri par heure de depart",
                "Tri par heure d'arrivee",
                "Tri par type de voyage",
                "Tri par type",
                "Tri par activites"
        );

        displayData();
    }

    void displayData() {
        mainVBox.getChildren().clear();

        Collections.reverse(listVoyage);

        if (!listVoyage.isEmpty()) {
            for (Voyage voyage : listVoyage) {

                mainVBox.getChildren().add(makeVoyageModel(voyage));

            }
        } else {
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setPrefHeight(200);
            stackPane.getChildren().add(new Text("Aucune donnée"));
            mainVBox.getChildren().add(stackPane);
        }
    }

    public Parent makeVoyageModel(
            Voyage voyage
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_FRONT_MODEL_VOYAGE)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#villeDepartText")).setText("VilleDepart : " + voyage.getVilleDepart());
            ((Text) innerContainer.lookup("#destinationText")).setText("Destination : " + voyage.getDestination());
            ((Text) innerContainer.lookup("#heureDepartText")).setText("HeureDepart : " + voyage.getHeureDepart());
            ((Text) innerContainer.lookup("#heureArriveeText")).setText("HeureArrivee : " + voyage.getHeureArrivee());
            ((Text) innerContainer.lookup("#typeVoyageText")).setText("TypeVoyage : " + voyage.getTypeVoyage());
            ((Text) innerContainer.lookup("#typeText")).setText("Type : " + voyage.getType());
            ((Text) innerContainer.lookup("#activitesText")).setText("Activites : " + voyage.getActivites());


            ((Button) innerContainer.lookup("#reserverButton")).setOnAction((ignored) -> reserverVoyage(voyage));
            ((Button) innerContainer.lookup("#editButton")).setOnAction((ignored) -> modifierVoyage(voyage));
            ((Button) innerContainer.lookup("#deleteButton")).setOnAction((ignored) -> supprimerVoyage(voyage));


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

    @FXML
    private void ajouterVoyage(ActionEvent ignored) {
        currentVoyage = null;
        MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_MANAGE_VOYAGE);
    }

    private void reserverVoyage(Voyage voyage) {
        currentVoyage = voyage;
        com.example.pidev.controllers.front.reservation.ShowAllController.currentReservation = null;
        ManageController.selectedVoyage = voyage;
        MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_MANAGE_RESERVATION);
    }

    private void modifierVoyage(Voyage voyage) {
        currentVoyage = voyage;
        MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_MANAGE_VOYAGE);
    }

    private void supprimerVoyage(Voyage voyage) {
        currentVoyage = null;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer la suppression");
        alert.setHeaderText(null);
        alert.setContentText("Etes vous sûr de vouloir supprimer voyage ?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.isPresent()) {
            if (action.get() == ButtonType.OK) {
                if (VoyageService.getInstance().delete(voyage.getId())) {
                    MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_VOYAGE);
                } else {
                    AlertUtils.makeError("Could not delete voyage");
                }
            }
        }
    }

    public void sort(ActionEvent actionEvent) {
        Voyage.compareVar = sortCB.getValue();
        Collections.sort(listVoyage);
        displayData();
    }
}