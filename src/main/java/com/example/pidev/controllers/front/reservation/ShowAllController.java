package com.example.pidev.controllers.front.reservation;

import com.example.pidev.controllers.front.MainWindowController;
import com.example.pidev.entities.Reservation;
import com.example.pidev.utils.AlertUtils;
import com.example.pidev.utils.Constants;
import com.itextpdf.text.Font;
import com.itextpdf.text.*;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import services.ReservationService;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.*;


public class ShowAllController implements Initializable {

    public static Reservation currentReservation;

    @FXML
    public Text topText;
    @FXML
    public Button addButton;
    @FXML
    public VBox mainVBox;
    @FXML
    public TextField searchTF;

    List<Reservation> listReservation;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listReservation = ReservationService.getInstance().getAll();

        displayData("");
    }

    void displayData(String searchText) {
        mainVBox.getChildren().clear();

        Collections.reverse(listReservation);

        if (!listReservation.isEmpty()) {
            for (Reservation reservation : listReservation) {
                if (searchText.isEmpty()
                        || reservation.getNom().toLowerCase().contains(searchText.toLowerCase())
                        || reservation.getPrenom().toLowerCase().contains(searchText.toLowerCase())
                        || reservation.getEmail().toLowerCase().contains(searchText.toLowerCase())
                        || reservation.getNumeroTelephone().toLowerCase().contains(searchText.toLowerCase())
                        || reservation.getVoyage().toString().toLowerCase().contains(searchText.toLowerCase())
                ) {
                    mainVBox.getChildren().add(makeReservationModel(reservation));
                }
            }
        } else {
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setPrefHeight(200);
            stackPane.getChildren().add(new Text("Aucune donnée"));
            mainVBox.getChildren().add(stackPane);
        }
    }

    public Parent makeReservationModel(
            Reservation reservation
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_FRONT_MODEL_RESERVATION)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#nomText")).setText("Nom : " + reservation.getNom());
            ((Text) innerContainer.lookup("#prenomText")).setText("Prenom : " + reservation.getPrenom());
            ((Text) innerContainer.lookup("#emailText")).setText("Email : " + reservation.getEmail());
            ((Text) innerContainer.lookup("#numeroTelephoneText")).setText("NumeroTelephone : " + reservation.getNumeroTelephone());

            ((Text) innerContainer.lookup("#voyageText")).setText("Voyage : " + reservation.getVoyage().toString());


            ((Button) innerContainer.lookup("#pdfButton")).setOnAction((ignored) -> genererPDF(reservation));
            ((Button) innerContainer.lookup("#editButton")).setOnAction((ignored) -> modifierReservation(reservation));
            ((Button) innerContainer.lookup("#deleteButton")).setOnAction((ignored) -> supprimerReservation(reservation));


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

    @FXML
    private void ajouterReservation(ActionEvent ignored) {
        ManageController.selectedVoyage = null;
        currentReservation = null;
        MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_MANAGE_RESERVATION);
    }

    private void modifierReservation(Reservation reservation) {
        ManageController.selectedVoyage = null;
        currentReservation = reservation;
        MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_MANAGE_RESERVATION);
    }

    private void supprimerReservation(Reservation reservation) {
        ManageController.selectedVoyage = null;
        currentReservation = null;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer la suppression");
        alert.setHeaderText(null);
        alert.setContentText("Etes vous sûr de vouloir supprimer reservation ?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.isPresent()) {
            if (action.get() == ButtonType.OK) {
                if (ReservationService.getInstance().delete(reservation.getId())) {
                    MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_RESERVATION);
                } else {
                    AlertUtils.makeError("Could not delete reservation");
                }
            }
        }
    }

    private void genererPDF(Reservation reservation) {
        String filename = "reservation.pdf";

        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, Files.newOutputStream(Paths.get(filename)));
            document.open();

            com.itextpdf.text.Font titleFont = new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLD, BaseColor.BLACK);
            com.itextpdf.text.Font normalFont = new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLACK);

            // Ajouter un espace avant le titre "Reservation"


            // Ajouter le titre avec une couleur orange et centré
            Paragraph title = new Paragraph("Reservation ", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            titleFont.setColor(BaseColor.BLACK);
            document.add(title);

            // Ajouter un espace après le titre "Reservation"
            document.add(new Paragraph("\n"));
            // Ajouter un espace avant le titre "Reservation"
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));

            // Créer un tableau avec 2 colonnes et le centrer
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);

            // Ajouter les coordonnées de la réservation avec une couleur bleu ciel
            PdfPCell cell = new PdfPCell(new Phrase("Nom : ", normalFont));
            cell.setBackgroundColor(BaseColor.CYAN);
            table.addCell(cell);
            table.addCell(new Phrase(reservation.getNom(), normalFont));

            cell = new PdfPCell(new Phrase("Prenom : ", normalFont));
            cell.setBackgroundColor(BaseColor.CYAN);
            table.addCell(cell);
            table.addCell(new Phrase(reservation.getPrenom(), normalFont));

            cell = new PdfPCell(new Phrase("Email : ", normalFont));
            cell.setBackgroundColor(BaseColor.CYAN);
            table.addCell(cell);
            table.addCell(new Phrase(reservation.getEmail(), normalFont));

            cell = new PdfPCell(new Phrase("NumeroTelephone : ", normalFont));
            cell.setBackgroundColor(BaseColor.CYAN);
            table.addCell(cell);
            table.addCell(new Phrase(reservation.getNumeroTelephone(), normalFont));

            cell = new PdfPCell(new Phrase("Voyage : ", normalFont));
            cell.setBackgroundColor(BaseColor.CYAN);
            table.addCell(cell);
            // Changer la couleur du texte en saumon
            Phrase phrase = new Phrase(reservation.getVoyage().toString(), normalFont);
            phrase.setFont(new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.ORANGE));
            table.addCell(phrase);
            table.addCell(new Phrase(reservation.getVoyage().toString(), normalFont));

            // Ajouter le tableau au document
            document.add(table);

            document.newPage();
            document.close();

            writer.close();

            Desktop.getDesktop().open(new File(filename));
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void search(KeyEvent keyEvent) {
        displayData(searchTF.getText());
    }
}
