package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.User;
import services.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class modifieruser {


    UserService sv = new UserService();

    private User personneToUpdate;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField txtNom;

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
        try {
            User existingPersonne = new User();
            existingPersonne.setEmail(txtEmail.getText());
            existingPersonne.setPassword(txtPassword.getText());
            existingPersonne.setNom(txtNom.getText());
            existingPersonne.setPrenom(prenomField.getText());

            // Conversion de la chaîne de date en objet Date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = dateFormat.parse(txtBirthDate.getText());
            existingPersonne.setDateNaissance(parsedDate);

            existingPersonne.setNumeroTelephone(txtPhoneNumber.getText());

            sv.modifier(existingPersonne);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setContentText(" modifiée avec succès");
            alert.show();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/user.fxml"));
            Parent root = loader.load();

            System.out.println("FXML loaded successfully.");

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();


        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.show();
        } catch (ParseException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Erreur de conversion de la date : " + e.getMessage());
            alert.show();
        }
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

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignIn.fxml"));

            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText(e.getMessage());
                alert.show();
            }
        }
    }

    public void setTextFields(User R) {
        txtId.setText(String.valueOf(R.getId()));
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



