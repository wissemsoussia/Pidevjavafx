package com.example.pidev.controllers.back.sponsor;

import com.example.pidev.controllers.back.MainWindowController;
import com.example.pidev.entities.Sponsor;
import com.example.pidev.services.SponsorService;
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

    public static Sponsor currentSponsor;

    @FXML
    public Text topText;
    @FXML
    public Button addButton;
    @FXML
    public VBox mainVBox;


    List<Sponsor> listSponsor;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listSponsor = SponsorService.getInstance().getAll();

        displayData();
    }

    void displayData() {
        mainVBox.getChildren().clear();

        Collections.reverse(listSponsor);

        if (!listSponsor.isEmpty()) {
            for (Sponsor sponsor : listSponsor) {

                mainVBox.getChildren().add(makeSponsorModel(sponsor));

            }
        } else {
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setPrefHeight(200);
            stackPane.getChildren().add(new Text("Aucune donnée"));
            mainVBox.getChildren().add(stackPane);
        }
    }

    public Parent makeSponsorModel(
        Sponsor sponsor
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_BACK_MODEL_SPONSOR)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#nomText")).setText("Nom : " + sponsor.getNom());
            ((Text) innerContainer.lookup("#adresseText")).setText("Adresse : " + sponsor.getAdresse());
            ((Text) innerContainer.lookup("#mailText")).setText("Mail : " + sponsor.getMail());
            ((Text) innerContainer.lookup("#telephoneText")).setText("Telephone : " + sponsor.getTelephone());
            ((Text) innerContainer.lookup("#dateCreationText")).setText("DateCreation : " + sponsor.getDateCreation());

            ((Text) innerContainer.lookup("#associationText")).setText("Association : " + sponsor.getAssociation().toString());


            ((Button) innerContainer.lookup("#deleteButton")).setOnAction((ignored) -> supprimerSponsor(sponsor));


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

    @FXML
    private void ajouterSponsor(ActionEvent ignored) {
        currentSponsor = null;
        MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_MANAGE_SPONSOR);
    }

    private void modifierSponsor(Sponsor sponsor) {
        currentSponsor = sponsor;
        MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_MANAGE_SPONSOR);
    }

    private void supprimerSponsor(Sponsor sponsor) {
        currentSponsor = null;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer la suppression");
        alert.setHeaderText(null);
        alert.setContentText("Etes vous sûr de vouloir supprimer sponsor ?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.isPresent()) {
            if (action.get() == ButtonType.OK) {
                if (SponsorService.getInstance().delete(sponsor.getId())) {
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_SPONSOR);
                } else {
                    AlertUtils.makeError("Could not delete sponsor");
                }
            }
        }
    }


}
