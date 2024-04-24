package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import tn.esprit.models.Equipement;
import tn.esprit.models.Evenement;
import tn.esprit.services.ServiceEquipement;
import tn.esprit.services.ServiceEvenement;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class GProduitAdminController implements Initializable {
    @FXML
    private GridPane ProdContainer;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    public TextField idtf;

    @FXML
    private Label labelinfo;

    @FXML
    public TextField nomtf;

    @FXML
    private TextField prodsearch;



    @FXML
    public TextField typetf;
    private final ServiceEvenement EventS = new ServiceEvenement();
    private final ServiceEquipement EquiS = new ServiceEquipement();
    private Connection cnx;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        load();
        loadCat();
    }



    @FXML
    void ajouterprod(ActionEvent event) {
        String NOM = nomtf.getText();
        String TYPE = typetf.getText();
        labelinfo.setText("Evenement ajouter");
        if (NOM.length() > 10 || !Pattern.matches("[a-zA-Z0-9]+", NOM)) {
            labelinfo.setText("Nom invalide : doit comporter au maximum 10 caractères alphanumériques");
            return;
        }
        EventS.Add(new Evenement(0, NOM, TYPE));
        EquiS.Add(new Equipement(0, 1, NOM,TYPE));

    }

    @FXML
    void modifierprod(ActionEvent event) {
        int ID = Integer.parseInt(idtf.getText());
        String NOM = nomtf.getText();
        String TYPE = typetf.getText();
        EventS.Update(new Evenement(0, NOM, TYPE));
        labelinfo.setText("Evenement Modifier");
    }

    public void loadCat(){

    }
    public void load() {
        int column = 0;
        int row = 1;
        try {
            for (Evenement evenement : EventS.afficher()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ProdCard.fxml"));
                Pane userBox = fxmlLoader.load();
                EventCardController ProdCardC = fxmlLoader.getController();
                ProdCardC.setData(evenement);
                if (column == 5) {
                    column = 0;
                    ++row;
                }
                ProdContainer.add(userBox, column++, row);
                GridPane.setMargin(userBox, new Insets(12));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void refresh(ActionEvent event) {
        ProdContainer.getChildren().clear();
        load();
    }


    @FXML
    void triparnom(ActionEvent event) {
       /* int column = 0;
        int row = 1;
        try {
            for (Evenement evenement : ProdS.TriparName()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ProdCard.fxml"));
                Pane userBox = fxmlLoader.load();
                ProdCardController ProdCardC = fxmlLoader.getController();
                ProdCardC.setData(evenement);
                if (column == 5) {
                    column = 0;
                    ++row;
                }
                ProdContainer.add(userBox, column++, row);
                GridPane.setMargin(userBox, new Insets(12));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @FXML
    void triparprix(ActionEvent event) {
       /* int column = 0;
        int row = 1;
        try {
            for (Evenement evenement : ProdS.TriparPrix()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ProdCard.fxml"));
                Pane userBox = fxmlLoader.load();
                ProdCardController ProdCardC = fxmlLoader.getController();
                ProdCardC.setData(evenement);
                if (column == 5) {
                    column = 0;
                    ++row;
                }
                ProdContainer.add(userBox, column++, row);
                GridPane.setMargin(userBox, new Insets(12));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @FXML
    public void RechercheNom(ActionEvent actionEvent) {
        /*int column = 0;
        int row = 1;
        String recherche = prodsearch.getText();
        try {
            ProdContainer.getChildren().clear();
            for (Evenement evenement : ProdS.Rechreche(recherche)) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ProdCard.fxml"));
                Pane userBox = fxmlLoader.load();
                ProdCardController ProdCardC = fxmlLoader.getController();
                ProdCardC.setData(evenement);
                if (column == 5) {
                    column = 0;
                    ++row;
                }
                ProdContainer.add(userBox, column++, row);
                GridPane.setMargin(userBox, new Insets(12));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }


}
