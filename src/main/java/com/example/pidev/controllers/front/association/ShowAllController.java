package com.example.pidev.controllers.front.association;


import com.example.pidev.controllers.front.MainWindowController;
import com.example.pidev.entities.Association;
import com.example.pidev.services.AssociationService;
import com.example.pidev.utils.AlertUtils;
import com.example.pidev.utils.Constants;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ShowAllController implements Initializable {

    public static Association currentAssociation;

    @FXML
    public Text topText;
    @FXML
    public Button addButton;
    @FXML
    public VBox mainVBox;
    @FXML
    public ComboBox<String> sortCB;
    @FXML
    private TextField searchField;

    List<Association> listAssociation;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listAssociation = AssociationService.getInstance().getAll();

        displayData("");
    }

    void displayData(String searchText) {
        mainVBox.getChildren().clear();

        sortCB.getItems().addAll(
                "Tri par nom",
                "Tri par description",
                "Tri par adresse",
                "Tri par email",
                "Tri par numeroTelephone",
                "Tri par dateCreation"
        );

        Collections.reverse(listAssociation);

        if (!listAssociation.isEmpty()) {
            for (Association association : listAssociation) {
                if (searchText.isEmpty()
                        || association.getNom().toLowerCase().contains(searchText.toLowerCase())) {
                    mainVBox.getChildren().add(makeAssociationModel(association));
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

    public Parent makeAssociationModel(Association association) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_FRONT_MODEL_ASSOCIATION)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#nomText")).setText("Nom : " + association.getNom());
            ((Text) innerContainer.lookup("#descriptionText")).setText("Description : " + association.getDescription());
            ((Text) innerContainer.lookup("#adresseText")).setText("Adresse : " + association.getAdresse());
            ((Text) innerContainer.lookup("#emailText")).setText("Email : " + association.getEmail());
            ((Text) innerContainer.lookup("#numeroTelephoneText")).setText("NumeroTelephone : " + association.getNumeroTelephone());
            ((Text) innerContainer.lookup("#dateCreationText")).setText("DateCreation : " + association.getDateCreation());

            try {
                String data = association.allAttrToString();
                String path = "./qr_code.jpg";
                BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 500, 500);
                MatrixToImageWriter.writeToPath(matrix, "jpg", Paths.get(path));

                Path qrPath = FileSystems.getDefault().getPath(path);
                if (qrPath.toFile().exists()) {
                    ((ImageView) innerContainer.lookup("#qrImage")).setImage(new Image(qrPath.toUri().toString()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            ((Button) innerContainer.lookup("#editButton")).setOnAction((ignored) -> modifierAssociation(association));
            ((Button) innerContainer.lookup("#deleteButton")).setOnAction((ignored) -> supprimerAssociation(association));


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

    @FXML
    private void ajouterAssociation(ActionEvent ignored) {
        currentAssociation = null;
        MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_MANAGE_ASSOCIATION);
    }

    private void modifierAssociation(Association association) {
        currentAssociation = association;
        MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_MANAGE_ASSOCIATION);
    }

    private void supprimerAssociation(Association association) {
        currentAssociation = null;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer la suppression");
        alert.setHeaderText(null);
        alert.setContentText("Etes vous sûr de vouloir supprimer association ?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.isPresent()) {
            if (action.get() == ButtonType.OK) {
                if (AssociationService.getInstance().delete(association.getId())) {
                    MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_ASSOCIATION);
                } else {
                    AlertUtils.makeErrorApi("Could not delete association");
                }
            }
        }
    }

    public void searchAssociations(KeyEvent keyEvent) {
        displayData(searchField.getText());
    }


    @FXML
    private void trierAssociations() {
        Association.compareVar = sortCB.getValue();
        Collections.sort(listAssociation);
        displayData(searchField.getText());
    }
}
