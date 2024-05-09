package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.User;
import utils.DBUtils;
import utils.MyDataBase;

import javax.mail.Session;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogInController implements Initializable {
    @FXML
    private Button loginButton;

    @FXML
    private TextField email;

    @FXML
    private TextField password;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    DBUtils.logIn(event, email.getText(), password.getText());
                } catch (ClassNotFoundException | SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

  /*  public boolean ValidationEmail() {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9._]+([.][a-zA-Z0-9]+)+");
        Matcher match = pattern.matcher(this.email.getText());
        if (match.find() && match.group().equals(this.email.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid Email");
            alert.showAndWait();
            return false;
        }
    }

    public User login() {
        try {
            // Assurez-vous que la classe SessionManager est importée correctement ou définie si elle n'existe pas
            User user = MyDataBase.getInstance().auth(email.getText(), password.getText());
            System.out.println("user session connect is " + MyDataBase.getInstance().getUser_id());

            if (user != null) {
                System.out.println(user);

                String fxmlPath = null;
                switch (user.getRoles()) {
                    case "ROLE_ADMIN":
                        fxmlPath = "/admin.fxml";
                        break;
                    case "ROLE_USER":
                        fxmlPath = "/user.fxml";
                        break;
                    default:
                        // Handle other roles or errors
                        return null;
                }

                // Load FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                Parent root = loader.load();

                // Create a new stage
                Stage stage = new Stage();
                stage.setTitle("Your Title"); // Set your title here

                // Set the scene
                Scene scene = new Scene(root);
                stage.setScene(scene);

                // Close the current stage
                Stage currentStage = (Stage) email.getScene().getWindow();
                currentStage.close();

                // Show the new stage
                stage.show();
            } else {
                // Handle authentication failure
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Error");
                alert.setHeaderText(null);
                alert.setContentText("Invalid username or password");
                alert.showAndWait();

            }
            return user;
        } catch (SQLException | IOException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }*/
}
