package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.User;
import services.UserService;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    UserService sv = new UserService();

    @FXML
    private TableView<  User> tableviewUser;

    @FXML
    private TableColumn<?, ?> lastnameUser;

    @FXML
    private TableColumn<User,String> colNom;

    @FXML
    private TableColumn<User,String> passwordField;
    @FXML
    private TableColumn<User,String> txtNom;
    @FXML
    private TableColumn<User,String> numField;
    @FXML
    private TableColumn<User,String> emailField;

    @FXML
    private TableColumn<User,String> colPrenom;
    @FXML
    private ChoiceBox<String> roleChoiceBox;

    @FXML
    private TableColumn<User,String> colEmail;
    @FXML
    private TableColumn<User,Integer> colid;

    @FXML
    private TableColumn<User,String> colDateNaissance;

    @FXML
    private TableColumn<User,String> colNumeroTelephone;
    @FXML
    private TableColumn<User,String> tnom;
    @FXML
    private TableColumn<User,String> tprenom;
    @FXML
    private TableColumn<User,String> temail;

    @FXML
    private TableColumn<User,String> colRole; // Added role column
    @FXML
    private DatePicker datePicker;

    ToggleGroup toggleGroup= new ToggleGroup();

    int id;


    @FXML
    private TableColumn<?, ?> EmailUser;

    @FXML
    private TableColumn<?, ?> rolesFiled;
    @FXML
    private TableColumn<?, ?> prenomField;
    @FXML
    private TextField Recherche_User;

    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet result;
    User user = null;

    public AdminController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            showRec();
            searchRec();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void exit() {
        System.exit(0);
    }

    public ObservableList<User> getPersonneList() throws SQLException {
        ObservableList<User> userList = FXCollections.observableArrayList();
        try {
            userList.addAll(sv.recuperer());
            System.out.println(userList);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return userList;
    }

    public void showRec() throws SQLException {
        ObservableList<User>  List = getPersonneList();
        tableviewUser.setItems(List);

        //colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        //colPrenom.setCellValueFactory(new PropertyValueFactory<>("p"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colDateNaissance.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));
        colNumeroTelephone.setCellValueFactory(new PropertyValueFactory<>("numeroTelephone"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("roles")); // Added this line
    }
    @FXML
    private void deconnexion(ActionEvent event) throws IOException {
        // Charger le fichier FXML pour l'écran de connexion (SignIn.fxml)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignIn.fxml"));
        Parent root = loader.load();

        // Afficher l'écran de connexion dans une nouvelle fenêtre
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

        // Fermer la fenêtre actuelle
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }






  /*  @FXML
    private void onExcel(ActionEvent event) throws IOException {
        //text file, should be opening in default text editor
        File file = new File("");

        //first check if Desktop is supported by Platform or not
        if(!Desktop.isDesktopSupported()){
            System.out.println("Desktop is not supported");
            return;
        }

        Desktop desktop = Desktop.getDesktop();
        if(file.exists()) desktop.open(file);

        //let's try to open PDF file
        file = new File("C:\\Users\\user\\Desktop\\User\\src\\main\\resources\\Users.xlsx");
        if(file.exists()) desktop.open(file);
        sv.excel();
    }
*/




    @FXML
    private void refresh() throws SQLException {
        ObservableList<User> list = getPersonneList();
       // colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        //PasswordUser.setCellValueFactory(new PropertyValueFactory<>("password"));

        tableviewUser.setItems(list);
    }


    @FXML
    private void SupprimerUser() throws SQLException {
        User user = (User) tableviewUser.getSelectionModel().getSelectedItem();
        sv.supprimer(user.getId());
        refresh();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Travel Me :: Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Utilisateur supprimé");
        alert.showAndWait();
    }

    @FXML
    private void ModifierUser(ActionEvent event) throws SQLException {
        User user = (User) tableviewUser.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader ();
        loader.setLocation(getClass().getResource("/ModifierP.fxml"));
        try {
            loader.load();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        ModifierPersonneController muc = loader.getController();
        // mrc.setUpdate(true);
        muc.setTextFields(user);
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
        showRec();

}



    @FXML
    private void ajout (ActionEvent event) throws SQLException {
        User user = (User) tableviewUser.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader ();
        loader.setLocation(getClass().getResource("/ajout.fxml"));
        try {
            System.out.println("aaa");
            loader.load();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        AjouterUserController muc = loader.getController();
        // mrc.setUpdate(true);
       // muc.setTextFields(user);
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        System.out.println("bbbb");
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
        showRec();

    }








    @FXML
    private void searchRec() throws SQLException {
        //idUser.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        //PasswordUser.setCellValueFactory(new PropertyValueFactory<>("password"));

        ObservableList<User> list = getPersonneList();
        tableviewUser.setItems(list);

        FilteredList<User> filteredData = new FilteredList<>(list, b -> true);

        Recherche_User.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(user -> {
                if (newValue == null || newValue.trim().isEmpty()) {

                    return true; // Show all items if filter is empty
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return user.getEmail().toLowerCase().contains(lowerCaseFilter)
                        || user.getNom().toLowerCase().contains(lowerCaseFilter);

            });
        });
        SortedList<User> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableviewUser.comparatorProperty());
        tableviewUser.setItems(sortedData);
    }


    public void ajouterAction(ActionEvent event) {

            try {
                // Convertir la valeur de DatePicker en java.util.Date
                Instant instant = Instant.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()));
                Date date = Date.from(instant);
               // String selectedRole = roleChoiceBox.getValue();

                // Créer un nouvel utilisateur en utilisant le constructeur approprié
                User user = new User(
                        emailField.getText(), // Email
                       // selectedRole,
                        "ROLE_USER", // Roles (vous pouvez laisser vide ou définir une valeur par défaut)
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
                    datePicker.getScene().setRoot(root);
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

}

