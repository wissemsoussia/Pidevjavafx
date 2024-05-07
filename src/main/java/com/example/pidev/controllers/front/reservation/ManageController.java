package com.example.pidev.controllers.front.reservation;

import com.example.pidev.controllers.front.MainWindowController;
import com.example.pidev.entities.Reservation;
import com.example.pidev.entities.Voyage;
import com.example.pidev.utils.AlertUtils;
import com.example.pidev.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import services.ReservationService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ManageController implements Initializable {

    public static Voyage selectedVoyage;

    @FXML
    public TextField nomTF;
    @FXML
    public TextField prenomTF;
    @FXML
    public TextField emailTF;
    @FXML
    public TextField numeroTelephoneTF;

    @FXML
    public ComboBox<Voyage> voyageCB;

    @FXML
    public Button btnAjout;
    @FXML
    public Text topText;

    Reservation currentReservation;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        for (Voyage voyage : ReservationService.getInstance().getAllVoyages()) {
            voyageCB.getItems().add(voyage);
        }
        if (selectedVoyage != null) {
            voyageCB.setValue(selectedVoyage);
        }


        currentReservation = ShowAllController.currentReservation;

        if (currentReservation != null) {
            topText.setText("Modifier reservation");
            btnAjout.setText("Modifier");

            try {
                nomTF.setText(currentReservation.getNom());
                prenomTF.setText(currentReservation.getPrenom());
                emailTF.setText(currentReservation.getEmail());
                numeroTelephoneTF.setText(currentReservation.getNumeroTelephone());

                voyageCB.setValue(currentReservation.getVoyage());

            } catch (NullPointerException ignored) {
                System.out.println("NullPointerException");
            }
        } else {
            topText.setText("Ajouter reservation");
            btnAjout.setText("Ajouter");
        }
    }

    @FXML
    private void manage(ActionEvent ignored) {

        if (controleDeSaisie()) {

            Reservation reservation = new Reservation();
            reservation.setNom(nomTF.getText());
            reservation.setPrenom(prenomTF.getText());
            reservation.setEmail(emailTF.getText());
            reservation.setNumeroTelephone(numeroTelephoneTF.getText());

            reservation.setVoyage(voyageCB.getValue());

            if (currentReservation == null) {
                if (ReservationService.getInstance().add(reservation)) {
                    AlertUtils.makeSuccessNotification("Reservation ajouté avec succés");

                    try {
                        sendMail(reservation.getEmail());
                    } catch (Exception e) {
                        System.out.println("Error sending mail");
                    }

                    MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_RESERVATION);
                } else {
                    AlertUtils.makeError("Error");
                }
            } else {
                reservation.setId(currentReservation.getId());
                if (ReservationService.getInstance().edit(reservation)) {
                    AlertUtils.makeSuccessNotification("Reservation modifié avec succés");
                    ShowAllController.currentReservation = null;
                    MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_RESERVATION);
                } else {
                    AlertUtils.makeError("Error");
                }
            }

        }
    }


    private boolean controleDeSaisie() {


        if (nomTF.getText().isEmpty()) {
            AlertUtils.makeInformation("nom ne doit pas etre vide");
            return false;
        }
        if (!Pattern.compile("^[a-zA-Z]+$").matcher(nomTF.getText()).matches()) {
            AlertUtils.makeInformation("nom ne doit contenir que des lettres");
            return false;
        }

        if (prenomTF.getText().isEmpty()) {
            AlertUtils.makeInformation("prenom ne doit pas etre vide");
            return false;
        }

        if (!Pattern.compile("^[a-zA-Z]+$").matcher(prenomTF.getText()).matches()) {
            AlertUtils.makeInformation("prenom ne doit contenir que des lettres");
            return false;
        }
        if (emailTF.getText().isEmpty()) {
            AlertUtils.makeInformation("email ne doit pas etre vide");
            return false;
        }
        if (!Pattern.compile("^(.+)@(.+)$").matcher(emailTF.getText()).matches()) {
            AlertUtils.makeInformation("Email invalide");
            return false;
        }


        if (numeroTelephoneTF.getText().isEmpty()) {
            AlertUtils.makeInformation("numeroTelephone ne doit pas etre vide");
            return false;
        }

        if (numeroTelephoneTF.getText().length() != 8) {
            AlertUtils.makeInformation("Numero de telephone doit contenir 8 chiffres");
            return false;
        }

        try {
            Integer.parseInt(numeroTelephoneTF.getText());
        } catch (NumberFormatException e) {
            AlertUtils.makeInformation("Numero de telephone doit etre un nombre");
            return false;
        }

        if (voyageCB.getValue() == null) {
            AlertUtils.makeInformation("Veuillez choisir un voyage");
            return false;
        }
        return true;
    }

    public static void sendMail(String recipient) throws Exception {
        System.out.println("Preparing to send email");
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.zoho.com");
        properties.put("mail.smtp.port", "587");
        String myAccountEmail = "pidev.esprit@zohomail.com";
        String password = "tFS7s957HzVd";

        // Create a session with account credentials
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        // Prepare email message
        Message message;
        try {
            message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("Commande");
            message.setContent("Votre commande a été bien enregistrée", "text/html");
        } catch (MessagingException ex) {
            System.out.println("Error in sending email");
            ex.printStackTrace();
            return;
        }

        // Send mail
        Transport.send(message);
        System.out.println("Mail sent successfully");
    }

}