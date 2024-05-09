/*package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class signin {

    @FXML
    private TextField email;

    @FXML
    private PasswordField pass;

    @FXML
    public void loginonaction(ActionEvent event) {
        if (email.getText().equals("aicha.werghi@esprit.tn") && pass.getText().equals("aicha")) {
            // Admin login
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Travel Me :: Success Message");
            alert.setHeaderText(null);
            alert.setContentText("Bienvenue Admin");
            alert.showAndWait();

            // Close the current stage (login stage)
            Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            loginStage.close();

            try {
                // Load AdminDashboard.fxml on a new stage
                Parent root = FXMLLoader.load(getClass().getResource("/Dashboard.fxml"));
                Scene scene = new Scene(root);
                Stage adminStage = new Stage();
                adminStage.setScene(scene);
                adminStage.initStyle(StageStyle.UNDECORATED);
                adminStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Handle regular user login
            // Implement your regular user login logic here
        }
    }

    @FXML
    private void create(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/signup.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.show();
    }
}
*/