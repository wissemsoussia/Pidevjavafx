package com.example.pidev.controllers.back.sponsor;


import com.example.pidev.controllers.back.MainWindowController;
import com.example.pidev.entities.Association;
import com.example.pidev.entities.Sponsor;
import com.example.pidev.services.SponsorService;
import com.example.pidev.utils.AlertUtils;
import com.example.pidev.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ManageController implements Initializable {

    @FXML
    public TextField nomTF;
    @FXML
    public TextField adresseTF;
    @FXML
    public TextField mailTF;
    @FXML
    public TextField telephoneTF;
    @FXML
    public DatePicker dateCreationDP;

    @FXML
    public ComboBox<Association> associationCB;

    @FXML
    public Button btnAjout;
    @FXML
    public Text topText;

    Sponsor currentSponsor;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        for (Association association : SponsorService.getInstance().getAllAssociations()) {
            associationCB.getItems().add(association);
        }

        currentSponsor = ShowAllController.currentSponsor;

        if (currentSponsor != null) {
            topText.setText("Modifier sponsor");
            btnAjout.setText("Modifier");

            try {
                nomTF.setText(currentSponsor.getNom());
                adresseTF.setText(currentSponsor.getAdresse());
                mailTF.setText(currentSponsor.getMail());
                telephoneTF.setText(currentSponsor.getTelephone());
                dateCreationDP.setValue(currentSponsor.getDateCreation());

                associationCB.setValue(currentSponsor.getAssociation());

            } catch (NullPointerException ignored) {
                System.out.println("NullPointerException");
            }
        } else {
            topText.setText("Ajouter sponsor");
            btnAjout.setText("Ajouter");
        }
    }

    @FXML
    private void manage(ActionEvent ignored) {

        if (controleDeSaisie()) {

            Sponsor sponsor = new Sponsor();
            sponsor.setNom(nomTF.getText());
            sponsor.setAdresse(adresseTF.getText());
            sponsor.setMail(mailTF.getText());
            sponsor.setTelephone(telephoneTF.getText());
            sponsor.setDateCreation(dateCreationDP.getValue());

            sponsor.setAssociation(associationCB.getValue());

            if (currentSponsor == null) {
                if (SponsorService.getInstance().add(sponsor)) {
                    AlertUtils.makeSuccessNotificationApi("Sponsor ajouté avec succés");
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_SPONSOR);
                } else {
                    AlertUtils.makeErrorApi("Error");
                }
            } else {
                sponsor.setId(currentSponsor.getId());
                if (SponsorService.getInstance().edit(sponsor)) {
                    AlertUtils.makeSuccessNotificationApi("Sponsor modifié avec succés");
                    ShowAllController.currentSponsor = null;
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_SPONSOR);
                } else {
                    AlertUtils.makeErrorApi("Error");
                }
            }

        }
    }


    private boolean controleDeSaisie() {


        if (nomTF.getText().isEmpty()) {
            AlertUtils.makeInformationApi("Le champ nom ne doit pas être vide.");
            return false;
        } else if (!nomTF.getText().matches("[a-zA-Z]+")) {
            AlertUtils.makeInformationApi("Le champ nom ne doit contenir que des caractères alphabétiques.");
            return false;
        }


        if (adresseTF.getText().isEmpty()) {
            AlertUtils.makeInformationApi("adresse ne doit pas etre vide");
            return false;
        }


        if (mailTF.getText().isEmpty()) {
            AlertUtils.makeInformationApi("mail ne doit pas etre vide");
            return false;
        }
        if (!Pattern.compile("^(.+)@(.+)$").matcher(mailTF.getText()).matches()) {
            AlertUtils.makeInformationApi("Email invalide");
            return false;
        }


        if (telephoneTF.getText().isEmpty()) {
            AlertUtils.makeInformationApi("telephone ne doit pas etre vide");
            return false;
        }

        if (telephoneTF.getText().length() != 8) {
            AlertUtils.makeInformationApi("telephone doit avoir 8 chiffres");
            return false;
        }


        if (dateCreationDP.getValue() == null) {
            AlertUtils.makeInformationApi("Choisir une date pour dateCreation");
            return false;
        }


        if (associationCB.getValue() == null) {
            AlertUtils.makeInformationApi("Veuillez choisir un association");
            return false;
        }
        return true;
    }
}
