package com.example.pidev.controllers.front.voyage;


import com.example.pidev.controllers.front.MainWindowController;
import com.example.pidev.entities.Voyage;
import com.example.pidev.utils.AlertUtils;
import com.example.pidev.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import services.VoyageService;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ManageController implements Initializable {

    @FXML
    public TextField villeDepartTF;
    @FXML
    public TextField destinationTF;
    @FXML
    public TextField heureDepartTF;
    @FXML
    public TextField heureArriveeTF;
    @FXML
    public TextField typeVoyageTF;
    @FXML
    public TextField typeTF;
    @FXML
    public TextField activitesTF;


    @FXML
    public Button btnAjout;
    @FXML
    public Text topText;

    Voyage currentVoyage;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        currentVoyage = ShowAllController.currentVoyage;

        if (currentVoyage != null) {
            topText.setText("Modifier voyage");
            btnAjout.setText("Modifier");

            try {
                villeDepartTF.setText(currentVoyage.getVilleDepart());
                destinationTF.setText(currentVoyage.getDestination());
                heureDepartTF.setText(currentVoyage.getHeureDepart());
                heureArriveeTF.setText(currentVoyage.getHeureArrivee());
                typeVoyageTF.setText(currentVoyage.getTypeVoyage());
                typeTF.setText(currentVoyage.getType());
                activitesTF.setText(currentVoyage.getActivites());


            } catch (NullPointerException ignored) {
                System.out.println("NullPointerException");
            }
        } else {
            topText.setText("Ajouter voyage");
            btnAjout.setText("Ajouter");
        }
    }

    @FXML
    private void manage(ActionEvent ignored) {

        if (controleDeSaisie()) {

            Voyage voyage = new Voyage();
            voyage.setVilleDepart(villeDepartTF.getText());
            voyage.setDestination(destinationTF.getText());
            voyage.setHeureDepart(heureDepartTF.getText());
            voyage.setHeureArrivee(heureArriveeTF.getText());
            voyage.setTypeVoyage(typeVoyageTF.getText());
            voyage.setType(typeTF.getText());
            voyage.setActivites(activitesTF.getText());


            if (currentVoyage == null) {
                if (VoyageService.getInstance().add(voyage)) {
                    AlertUtils.makeSuccessNotification("Voyage ajouté avec succés");
                    MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_VOYAGE);
                } else {
                    AlertUtils.makeError("Error");
                }
            } else {
                voyage.setId(currentVoyage.getId());
                if (VoyageService.getInstance().edit(voyage)) {
                    AlertUtils.makeSuccessNotification("Voyage modifié avec succés");
                    ShowAllController.currentVoyage = null;
                    MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_VOYAGE);
                } else {
                    AlertUtils.makeError("Error");
                }
            }

        }
    }


    private boolean controleDeSaisie() {


        if (villeDepartTF.getText().isEmpty()) {
            AlertUtils.makeInformation("villeDepart ne doit pas etre vide");
            return false;
        }
        if (!Pattern.compile("^[a-zA-Z]+$").matcher(villeDepartTF.getText()).matches()) {
            AlertUtils.makeInformation("villeDepart ne doit contenir que des lettres");
            return false;
        }


        if (destinationTF.getText().isEmpty()) {
            AlertUtils.makeInformation("destination ne doit pas etre vide");
            return false;
        }
        if (!Pattern.compile("^[a-zA-Z]+$").matcher(destinationTF.getText()).matches()) {
            AlertUtils.makeInformation("destination ne doit contenir que des lettres");
            return false;
        }


        if (heureDepartTF.getText().isEmpty()) {
            AlertUtils.makeInformation("heureDepart ne doit pas etre vide");
            return false;
        }
        if (!Pattern.compile("^([01]?[0-9]|2[0-3]):[0-5][0-9]$").matcher(heureDepartTF.getText()).matches()) {
            AlertUtils.makeInformation("Heure de départ invalide");
            return false;
        }

        if (heureArriveeTF.getText().isEmpty()) {
            AlertUtils.makeInformation("heureArrivee ne doit pas etre vide");
            return false;
        }
        if (!Pattern.compile("^([01]?[0-9]|2[0-3]):[0-5][0-9]$").matcher(heureArriveeTF.getText()).matches()) {
            AlertUtils.makeInformation("Heure d'arrivée invalide");
            return false;
        }

        if (typeVoyageTF.getText().isEmpty()) {
            AlertUtils.makeInformation("typeVoyage ne doit pas etre vide");
            return false;
        }
        if (!Pattern.compile("^[a-zA-Z]+$").matcher(typeVoyageTF.getText()).matches()) {
            AlertUtils.makeInformation("type voyage ne doit contenir que des lettres");
            return false;
        }


        if (typeTF.getText().isEmpty()) {
            AlertUtils.makeInformation("type ne doit pas etre vide");
            return false;
        }
        if (!Pattern.compile("^[a-zA-Z]+$").matcher(typeTF.getText()).matches()) {
            AlertUtils.makeInformation("type ne doit contenir que des lettres");
            return false;
        }

        if (activitesTF.getText().isEmpty()) {
            AlertUtils.makeInformation("activites ne doit pas etre vide");
            return false;
        }
        if (!Pattern.compile("^[a-zA-Z]+$").matcher(activitesTF.getText()).matches()) {
            AlertUtils.makeInformation("champs activités ne doit contenir que des lettres");
            return false;
        }

        return true;
    }
}