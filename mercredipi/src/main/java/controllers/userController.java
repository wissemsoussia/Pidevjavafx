package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import javafx.scene.control.Label;

public class userController implements Initializable {



    public  static Stage userStage;

    UserService sv = new UserService();

    @FXML
    private TableView<User> tableviewUser;

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
    private Label labelNom;

    @FXML
    private Label labelPrenom;

    @FXML
    private Label labelEmail;

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

    public userController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("test");
        showRec();
    }

    @FXML
    public void exit() {
        System.exit(0);
    }


    public class Session {
        private static int currentUserId;

        public static int getCurrentUserId() {
            return User.getCurrent_User().getId();
        }

        public static void setCurrentUserId(int userId) {
            currentUserId = userId;
        }
    }


    public void showRec() {
        // Obtenez l'identifiant de l'utilisateur connecté à partir de la session
        int userId =    Session.getCurrentUserId();

        try {
            System.out.println(userId);
            // Utilisez l'identifiant pour récupérer les informations de l'utilisateur
            User user = sv.getUserById(userId);
            System.out.println(user);
            // Affichez les informations de l'utilisateur dans l'interface utilisateur (par exemple, des étiquettes ou des champs de texte)
            labelNom.setText(user.getNom());
            labelPrenom.setText(user.getPrenom());
            labelEmail.setText(user.getEmail());
            // Ajoutez d'autres champs selon les besoins

        } catch (SQLException e) {
            // Gérez les erreurs de récupération des informations de l'utilisateur
            e.printStackTrace();
        }
    }







    @FXML
    private void SupprimerUser() throws SQLException {
//        User user = (User) tableviewUser.getSelectionModel().getSelectedItem();
        sv.supprimer(User.getCurrent_User().getId());
      //  refresh();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Utilisateur supprimé");
        alert.showAndWait();
       try {


           userController.userStage.close();
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignIn.fxml"));
           Parent root = loader.load();

           System.out.println("FXML loaded successfully.");

           Scene scene = new Scene(root);
           Stage stage = new Stage();
           stage.setScene(scene);
           stage.show();
       } catch (IOException e) {
           throw new RuntimeException(e);
       }

    }

    @FXML
    private void ModifierUser(ActionEvent event) throws SQLException {
//        User user = (User) tableviewUser.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader ();
        loader.setLocation(getClass().getResource("/modiuser.fxml"));
        try {
            loader.load();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        modifieruser muc = loader.getController();
        // mrc.setUpdate(true);
        muc.setTextFields(User.Current_User);
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
        showRec();

    }






}

