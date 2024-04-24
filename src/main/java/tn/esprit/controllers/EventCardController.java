package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tn.esprit.models.Evenement;
import tn.esprit.services.ServiceEvenement;
import tn.esprit.utils.MyDataBase;

import java.io.IOException;
import java.sql.Connection;

public class EventCardController {
    @FXML
    private Pane Card;

    @FXML
    private Label idcard;

    @FXML
    private Label nomprod;

    @FXML
    private Label typeprod;
    private final ServiceEvenement ProdS = new ServiceEvenement();
    int id;
    String nom, type;
    private Connection cnx;

    public EventCardController() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    private String[] colors = {"#ff854d", "#ff9767", "#ffa880", "#ffb99a", "#ffcbb3", "#ffdccd"};

    public void setData(Evenement evenement) {

        idcard.setText(String.valueOf(evenement.getId()));
        nomprod.setText(evenement.getNom());
        typeprod.setText(evenement.getNom());


        id = evenement.getId();
        nom = evenement.getNom();
        type = evenement.getType();
    }

    @FXML
    void ModifierProd(ActionEvent event) {
       try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionProduits.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            GProduitAdminController GPAC = loader.getController();
            GPAC.idtf.setText(String.valueOf(id));
            GPAC.nomtf.setText(nom);
            GPAC.typetf.setText(type);


            stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    void SupprimerProd(ActionEvent event) {
        ProdS.DeleteByID(id);
    }
}
