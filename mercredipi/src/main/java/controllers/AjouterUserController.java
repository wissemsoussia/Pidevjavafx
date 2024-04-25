package controllers;
import java.time.Instant;
import java.time.ZoneId;

import javafx.fxml.FXML;
import models.User;
import services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

public class AjouterUserController {
    UserService sv = new UserService();
    @FXML
    private TextField txtNom;
    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;
    @FXML
    private TextField ajout;
    @FXML
    private TextField txtId;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField numField;

    @FXML
    private TextField txtBirthDate;
    @FXML
    void ajouterAction(javafx.event.ActionEvent event) {
        try {
            // Convertir la valeur de DatePicker en java.util.Date
            Instant instant = Instant.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()));
            Date date = Date.from(instant);

            // Créer un nouvel utilisateur en utilisant le constructeur approprié
            User user = new User(
                    emailField.getText(), // Email
                    "", // Roles (vous pouvez laisser vide ou définir une valeur par défaut)
                    passwordField.getText(), // Mot de passe
                    false, // isVerified (vous pouvez définir la valeur par défaut que vous utilisez habituellement)
                    txtNom.getText(), // Nom
                    prenomField.getText(), // Prénom
                    date, // Date de naissance
                    numField.getText() // Numéro de téléphone
            );

            // Ajouter l'utilisateur à la base de données
            int generatedId = sv.ajouter(user);

            // Afficher une alerte de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succes");
            alert.setContentText("Personne Ajoutée avec succès");
            alert.show();

            // Charger le fichier FXML pour l'affichage de l'utilisateur ajouté
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherP.fxml"));
            try {
                Parent root = loader.load();
                AfficherPControllers afficherPControllers = loader.getController();

                // Définir les valeurs affichées dans l'interface utilisateur
                afficherPControllers.setTxtPrenom(prenomField.getText());
                afficherPControllers.setTxtEmail(emailField.getText());
                afficherPControllers.setTxtPassword(passwordField.getText());
                afficherPControllers.setTxtId(String.valueOf(generatedId));

                // Remplacer la scène actuelle par l'interface utilisateur de l'utilisateur ajouté
                emailField.getScene().setRoot(root);
            } catch (IOException e) {
                // Gérer les exceptions d'E/S lors du chargement du fichier FXML
                e.printStackTrace();
            }
        } catch (SQLException e) {
            // Gérer les exceptions SQL
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    public void goToLogin(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignIn.fxml"));

        try {
            Parent root = loader.load();

            SignInController signInController = loader.getController();
            signInController.setEmail_signin("");
            signInController.setPassword_signin("");

            // Debug statement to check if root is loaded successfully
            System.out.println("FXML loaded successfully sign in.");

            emailField.getScene().setRoot(root);
        } catch (IOException e) {
            // Instead of just printing, handle the IOException
            e.printStackTrace();
        }
    }

    public void setPrenom(String txtLastname) {
        this.prenomField.setText(txtLastname);
    }

    public void setTxtNom(String txtNom) {
        this.txtNom.setText(txtNom);
    }

    public void setTxtEmail(String txtEmail) {
        this.emailField.setText(txtEmail);
    }

    public void setTxtPassword(String txtPassword) {
        this.passwordField.setText(txtPassword);
    }

    public void setTxtId(String txtId) {
        this.txtId.setText(txtId);
    }

    public void setTxtBirthDate(String txtBirthDate) {
        this.txtBirthDate.setText(txtBirthDate);
    }

    public void setTxtPhoneNumber(String txtPhoneNumber) {
        this.numField.setText(txtPhoneNumber);
    }

    public void setTxtnom(String s) {
    }
}
