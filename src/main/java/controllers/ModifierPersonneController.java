package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import models.User;
import services.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;


public class ModifierPersonneController {

    UserService sv = new UserService();

    private User personneToUpdate;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField txtNom;
    @FXML
    private ToggleGroup roleToggleGroup;


    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtBirthDate;

    @FXML
    private TextField txtPhoneNumber;

    public void setPrenom(String prenomField) {
        this.prenomField.setText(prenomField);
    }

    public void setTxtNom(String txtNom) {
        this.txtNom.setText(txtNom);
    }

    public void setTxtEmail(String txtEmail) {
        this.txtEmail.setText(txtEmail);
    }

    public void setTxtPassword(String txtPassword) {
        this.txtPassword.setText(txtPassword);
    }

    public void setTxtId(String txtId) {
        this.txtId.setText(txtId);
    }

    public void setTxtBirthDate(String txtBirthDate) {
        this.txtBirthDate.setText(txtBirthDate);
    }

    public void setTxtPhoneNumber(String txtPhoneNumber) {
        this.txtPhoneNumber.setText(txtPhoneNumber);
    }


    @FXML
    public void ModifierAction() {
        if (champsSontValides()) {
            if (controlesSaisie()) {
                try {
                    User existingPersonne = new User();;
                    existingPersonne.setEmail(txtEmail.getText());
                    String selectedRole = ((RadioButton) roleToggleGroup.getSelectedToggle()).getText();
                    existingPersonne.setRoles(selectedRole);
                    existingPersonne.setPassword(txtPassword.getText());
                    existingPersonne.setNom(txtNom.getText());
                    existingPersonne.setPrenom(prenomField.getText());

                    // Conversion de la chaîne de date en objet Date
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date parsedDate = dateFormat.parse(txtBirthDate.getText());
                    existingPersonne.setDateNaissance(parsedDate);

                    existingPersonne.setNumeroTelephone(txtPhoneNumber.getText());

                    sv.modifier(existingPersonne);

                    afficherAlerteSucces("Succès", "Personne modifiée avec succès");

                } catch (SQLException e) {
                    afficherAlerteErreur("Erreur", e.getMessage());
                } catch (ParseException e) {
                    afficherAlerteErreur("Erreur", "Erreur de conversion de la date : " + e.getMessage());
                }
            }
        }
    }

    private boolean champsSontValides() {
        if (txtEmail.getText().isEmpty() || txtPassword.getText().isEmpty() || txtNom.getText().isEmpty() ||
                prenomField.getText().isEmpty() || txtBirthDate.getText().isEmpty() || txtPhoneNumber.getText().isEmpty()) {
            afficherAlerteErreur("Erreur", "Tous les champs sont obligatoires.");
            return false;
        } else {
            return true;
        }
    }

    private boolean controlesSaisie() {

        if (!txtNom.getText().matches("[a-zA-Z]+") || !prenomField.getText().matches("[a-zA-Z]+")) {
            afficherAlerteErreur("Erreur", "Le nom et le prénom doivent contenir uniquement des lettres.");
            return false;
        }


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dateNaissance = dateFormat.parse(txtBirthDate.getText());
            if (dateNaissance.after(new Date())) {
                afficherAlerteErreur("Erreur", "La date de naissance ne peut pas être dans le futur.");
                return false;
            }
        } catch (ParseException e) {
            afficherAlerteErreur("Erreur", "Format de date invalide. Utilisez le format yyyy-MM-dd.");
            return false;
        }


        if (!txtPhoneNumber.getText().matches("\\d{1,8}")) {
            afficherAlerteErreur("Erreur", "Le numéro de téléphone doit contenir uniquement des chiffres et ne pas dépasser 8 chiffres.");
            return false;
        }

        if (!txtEmail.getText().endsWith("@gmail.com")) {
            afficherAlerteErreur("Erreur", "L'adresse email doit se terminer par @gmailcom.");
            return false;
        }

        return true;
    }

    private void afficherAlerteErreur(String titre, String contenu) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setContentText(contenu);
        alert.show();
    }

    private void afficherAlerteSucces(String titre, String contenu) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setContentText(contenu);
        alert.show();
    }




    @FXML
    void SupprimerAction() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer cet utilisateur ?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {

                sv.supprimer(Integer.parseInt(txtId.getText()));

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setContentText(" supprimée avec succès");
                alert.show();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterPersonne.fxml"));

                try {
                    Parent root = loader.load();

                    AjouterUserController a = loader.getController();
                    a.setTxtEmail("");
                    a.setTxtPassword("");
                    a.setTxtNom("");
                    a.setPrenom("");


                    a.setTxtBirthDate("");
                    a.setTxtPhoneNumber("");

                    txtEmail.getScene().setRoot(root);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText(e.getMessage());
                alert.show();
            }
        }
    }

    public void setTextFields(User R) {
       // txtId.setText(String.valueOf(R.getId()));
        txtEmail.setText(R.getEmail());
        txtPassword.setText(R.getPassword());
        txtNom.setText(R.getNom());
        prenomField.setText(R.getPrenom());

        Date dateNaissance = R.getDateNaissance();
        if (dateNaissance != null) {
            txtBirthDate.setText(dateNaissance.toString()); // You may need to format the date
        } else {
            txtBirthDate.setText(""); // Or any default value you want to display
        }

        txtPhoneNumber.setText(R.getNumeroTelephone());
    }
}


