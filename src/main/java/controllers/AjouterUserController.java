package controllers;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.User;
import services.SendMail;
import services.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
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
    private Label labelNom;
    @FXML
    private Label labelEmail;
    @FXML
    private Label labelPrenom;

    @FXML
    private TextField txtBirthDate;
    @FXML
    void ajouterAction(javafx.event.ActionEvent event) {
        if (controlesSaisie()) {
            try {
                // Convertir la valeur de DatePicker en java.util.Date
                Instant instant = Instant.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()));
                Date date = Date.from(instant);

                // Créer un nouvel utilisateur en utilisant le constructeur approprié
                User user = new User(
                        emailField.getText(), // Email
                        "ROLE_USER", // Roles (vous pouvez laisser vide ou définir une valeur par défaut)
                        passwordField.getText(), // Mot de passe
                        false, // isVerified (vous pouvez définir la valeur par défaut que vous utilisez habituellement)
                        txtNom.getText(), // Nom
                        prenomField.getText(), // Prénom (à récupérer depuis le champ prenomField)
                        date, // Date de naissance
                        numField.getText() // Numéro de téléphone

                );

                String userName = user.getNom();

                TwilioSMS twilioSMS = new TwilioSMS("+15082134790", "+21629885139");
                twilioSMS.sendSMS("New user added: " + userName);


                // Ajouter l'utilisateur à la base de données
                int generatedId = sv.ajouter(user);

                // Afficher une alerte de succès
                afficherAlerteSucces("Succes", "Personne Ajoutée avec succès");


                // Exemple : Création d'un objet user avec une adresse e-mail
                SendMail s = new SendMail() ;

                s.sendEmail("werghiaicha73@gmail.com","welcome","Dear "+user.getNom()+user.getPrenom() + ",\n" +
                        "\n" +
                        "Welcome to IMPACTFUL JOURNYES ! We are thrilled to have you as a valued member of our travel community.\n" +
                        "\n" +
                        "At IMPACTFUL JOURNYES , our mission is to create unforgettable travel experiences for our clients. " +
                        "Whether you're planning a relaxing beach getaway, an adventurous trek through the mountains," +
                        " or a cultural exploration of a vibrant city, we are here to turn your travel dreams into reality.");





                // Charger le fichier FXML pour l'affichage de l'utilisateur ajouté
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherP.fxml"));
                try {
                    Parent root = loader.load();
                    AfficherPControllers afficherPControllers = loader.getController();

                    // Définir les valeurs affichées dans l'interface utilisateur
                    afficherPControllers.setTxtPrenom(""); // Champ non disponible ici
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
                afficherAlerteErreur("Error", e.getMessage());
            }
        }
    }

    private boolean controlesSaisie() {

        if (txtNom.getText().isEmpty()) {
            afficherAlerteErreur("Erreur", "Le champ Nom est obligatoire.");
            return false;
        }


        if (emailField.getText().isEmpty()) {
            afficherAlerteErreur("Erreur", "Le champ Email est obligatoire.");
            return false;
        }


        if (passwordField.getText().isEmpty()) {
            afficherAlerteErreur("Erreur", "Le champ Mot de passe est obligatoire.");
            return false;
        }


        if (numField.getText().isEmpty()) {
            afficherAlerteErreur("Erreur", "Le champ Numéro de téléphone est obligatoire.");
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

    public class TwilioSMS {
        private String fromPhoneNumber;
        private String toPhoneNumber;

        public TwilioSMS(String fromPhoneNumber, String toPhoneNumber) {
            this.fromPhoneNumber = fromPhoneNumber;
            this.toPhoneNumber = toPhoneNumber;
        }

        public void sendSMS(String message) {
            Message twilioMessage = Message.creator(
                    new PhoneNumber(toPhoneNumber),
                    new PhoneNumber(fromPhoneNumber),
                    message
            ).create();
            System.out.println("Message SID: " + twilioMessage.getSid());
        }
    }
}
