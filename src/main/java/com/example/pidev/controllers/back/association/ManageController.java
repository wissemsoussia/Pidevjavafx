package com.example.pidev.controllers.back.association;


import com.example.pidev.controllers.back.MainWindowController;
import com.example.pidev.entities.Association;
import services.AssociationService;
import com.example.pidev.utils.AlertUtils;
import com.example.pidev.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    public TextField descriptionTF;
    @FXML
    public TextField adresseTF;
    @FXML
    public TextField emailTF;
    @FXML
    public TextField numeroTelephoneTF;
    @FXML
    public DatePicker dateCreationDP;


    @FXML
    public Button btnAjout;
    @FXML
    public Text topText;

    Association currentAssociation;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        currentAssociation = ShowAllController.currentAssociation;

        if (currentAssociation != null) {
            topText.setText("Modifier association");
            btnAjout.setText("Modifier");

            try {
                nomTF.setText(currentAssociation.getNom());
                descriptionTF.setText(currentAssociation.getDescription());
                adresseTF.setText(currentAssociation.getAdresse());
                emailTF.setText(currentAssociation.getEmail());
                numeroTelephoneTF.setText(currentAssociation.getNumeroTelephone());
                dateCreationDP.setValue(currentAssociation.getDateCreation());


            } catch (NullPointerException ignored) {
                System.out.println("NullPointerException");
            }
        } else {
            topText.setText("Ajouter association");
            btnAjout.setText("Ajouter");
        }
    }

    @FXML
    private void manage(ActionEvent ignored) {

        if (controleDeSaisie()) {

            Association association = new Association();
            association.setNom(nomTF.getText());
            association.setDescription(descriptionTF.getText());
            association.setAdresse(adresseTF.getText());
            association.setEmail(emailTF.getText());
            association.setNumeroTelephone(numeroTelephoneTF.getText());
            association.setDateCreation(dateCreationDP.getValue());


            if (currentAssociation == null) {
                if (AssociationService.getInstance().add(association)) {
                    AlertUtils.makeSuccessNotificationApi("Association ajouté avec succés");
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_ASSOCIATION);
                } else {
                    AlertUtils.makeErrorApi("Error");
                }
            } else {
                association.setId(currentAssociation.getId());
                if (AssociationService.getInstance().edit(association)) {
                    AlertUtils.makeSuccessNotificationApi("Association modifié avec succés");
                    ShowAllController.currentAssociation = null;
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_ASSOCIATION);
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



        if (descriptionTF.getText().isEmpty()) {
            AlertUtils.makeInformationApi("description ne doit pas etre vide");
            return false;
        }


        if (adresseTF.getText().isEmpty()) {
            AlertUtils.makeInformationApi("adresse ne doit pas etre vide");
            return false;
        }


        if (emailTF.getText().isEmpty()) {
            AlertUtils.makeInformationApi("email ne doit pas etre vide");
            return false;
        }
        if (!Pattern.compile("^(.+)@(.+)$").matcher(emailTF.getText()).matches()) {
            AlertUtils.makeInformationApi("Email invalide");
            return false;
        }


        if (numeroTelephoneTF.getText().length() != 8) {
            AlertUtils.makeInformationApi("telephone doit avoir 8 chiffres");
            return false;
        }



        if (dateCreationDP.getValue() == null) {
            AlertUtils.makeInformationApi("Choisir une date pour dateCreation");
            return false;
        }


        return true;
    }
}
