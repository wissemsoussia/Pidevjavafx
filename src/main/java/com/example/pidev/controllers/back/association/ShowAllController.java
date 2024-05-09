package com.example.pidev.controllers.back.association;

import com.example.pidev.controllers.back.MainWindowController;
import com.example.pidev.entities.Association;
import com.example.pidev.services.AssociationService;
import com.example.pidev.utils.AlertUtils;
import com.example.pidev.utils.Constants;
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
import javafx.stage.FileChooser;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
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
    private TextField searchField;


    List<Association> listAssociation;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listAssociation = AssociationService.getInstance().getAll();

        displayData("");
    }

    void displayData(String searchText) {
        mainVBox.getChildren().clear();

        Collections.reverse(listAssociation);

        if (!listAssociation.isEmpty()) {
            for (Association association : listAssociation) {
                if (searchText.isEmpty() || association.getNom().toLowerCase().contains(searchText.toLowerCase())) {
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

    public Parent makeAssociationModel(
            Association association
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_BACK_MODEL_ASSOCIATION)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#nomText")).setText("Nom : " + association.getNom());
            ((Text) innerContainer.lookup("#descriptionText")).setText("Description : " + association.getDescription());
            ((Text) innerContainer.lookup("#adresseText")).setText("Adresse : " + association.getAdresse());
            ((Text) innerContainer.lookup("#emailText")).setText("Email : " + association.getEmail());
            ((Text) innerContainer.lookup("#numeroTelephoneText")).setText("NumeroTelephone : " + association.getNumeroTelephone());
            ((Text) innerContainer.lookup("#dateCreationText")).setText("DateCreation : " + association.getDateCreation());


            ((Button) innerContainer.lookup("#deleteButton")).setOnAction((ignored) -> supprimerAssociation(association));


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

    @FXML
    private void ajouterAssociation(ActionEvent ignored) {
        currentAssociation = null;
        MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_MANAGE_ASSOCIATION);
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
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_ASSOCIATION);
                } else {
                    AlertUtils.makeErrorApi("Could not delete association");
                }
            }
        }
    }

    @FXML
    private void searchAssociations(KeyEvent event) {
        displayData(searchField.getText());
    }

    public void generateExcel(ActionEvent ignored) {
        String fileName = "association.xls";

        HSSFWorkbook workbook = new HSSFWorkbook();

        try {
            CreationHelper createHelper = workbook.getCreationHelper();

            HSSFFont font = workbook.createFont();
            font.setFontName("Arial");
            font.setFontHeightInPoints((short) 12);
            font.setBold(true);

            FileChooser chooser = new FileChooser();
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Excel Files(.xls)", ".xls");
            chooser.getExtensionFilters().add(filter);

            HSSFSheet workSheet = workbook.createSheet("Associations");

            int columnCount = 7;
            int defaultColumnWidthInChars = 25;

            for (int i = 0; i < columnCount; i++) {
                workSheet.setColumnWidth(i, (defaultColumnWidthInChars + 1) * 256);
            }

            HSSFCellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
            headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerCellStyle.setBorderBottom(BorderStyle.THIN);
            headerCellStyle.setBorderTop(BorderStyle.THIN);
            headerCellStyle.setBorderLeft(BorderStyle.THIN);
            headerCellStyle.setBorderRight(BorderStyle.THIN);
            headerCellStyle.setWrapText(true);
            headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerCellStyle.setFont(font);

            HSSFCellStyle dataCellStyle = workbook.createCellStyle();
            dataCellStyle.setAlignment(HorizontalAlignment.LEFT);
            dataCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            dataCellStyle.setBorderBottom(BorderStyle.THIN);
            dataCellStyle.setBorderTop(BorderStyle.THIN);
            dataCellStyle.setBorderLeft(BorderStyle.THIN);
            dataCellStyle.setBorderRight(BorderStyle.THIN);
            dataCellStyle.setWrapText(true);
            dataCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
            dataCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            dataCellStyle.setFont(font);

            Row headerRow = workSheet.createRow(0);
            String[] headers = {"Id", "Nom", "Description", "Adresse", "Email", "Numero de telephone", "Date de creation"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerCellStyle);
            }

            int rowNum = 1;
            for (Association association : listAssociation) {
                Row row = workSheet.createRow(rowNum++);

                row.createCell(0).setCellValue(association.getId());
                row.createCell(1).setCellValue(association.getNom());
                row.createCell(2).setCellValue(association.getDescription());
                row.createCell(3).setCellValue(association.getAdresse());
                row.createCell(4).setCellValue(association.getEmail());
                row.createCell(5).setCellValue(association.getNumeroTelephone());
                row.createCell(6).setCellValue(association.getDateCreation().toString());

                // Appliquer le style de cellule aux données
                for (int i = 0; i < headers.length; i++) {
                    row.getCell(i).setCellStyle(dataCellStyle);
                }
            }

            workbook.write(Files.newOutputStream(Paths.get(fileName)));
            Desktop.getDesktop().open(new File(fileName));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
