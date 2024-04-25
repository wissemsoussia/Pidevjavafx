package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;

public class AfficherPControllers {

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtIsVerified;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtPrenom;

    @FXML
    private TextField txtDateNaissance;

    @FXML
    private TextField txtNumeroTelephone;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void setTxtEmail(String txtEmail) {
        this.txtEmail.setText(txtEmail);
    }

    public void setTxtPassword(String txtPassword) {
        this.txtPassword.setText(txtPassword);
    }

    public void setTxtIsVerified(String txtIsVerified) {
        this.txtIsVerified.setText(txtIsVerified);
    }


    public void setTxtNom(String txtNom) {
        this.txtNom.setText(txtNom);
    }

    public void setTxtPrenom(String txtPrenom) {
        this.txtPrenom.setText(txtPrenom);
    }

    public void setTxtDateNaissance(Date txtDateNaissance) {
        this.txtDateNaissance.setText(txtDateNaissance.toString());
    }

    public void setTxtNumeroTelephone(String txtNumeroTelephone) {
        this.txtNumeroTelephone.setText(txtNumeroTelephone);
    }

    public void goToModification(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierP.fxml"));

        try {
            Parent root = loader.load();
            ModifierPersonneController modifierController = loader.getController();
            modifierController.setTxtEmail(txtEmail.getText());
            modifierController.setTxtPassword(txtPassword.getText());
            modifierController.setTxtNom(txtNom.getText());
            modifierController.setPrenom(txtPrenom.getText());


            modifierController.setTxtBirthDate(txtDateNaissance.getText());
            modifierController.setTxtPhoneNumber(txtNumeroTelephone.getText());

            // Affichage du message de débogage pour vérifier si la racine est chargée avec succès
            System.out.println("FXML chargé avec succès.");

            txtEmail.getScene().setRoot(root);
        } catch (IOException e) {
            // Au lieu d'imprimer simplement, gérez l'IOException
            e.printStackTrace();
        }
    }

    public void setTxtId(String s) {
    }
}
