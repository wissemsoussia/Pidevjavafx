package com.example.pidev.utils;

import javafx.scene.control.Alert;
import org.controlsfx.control.Notifications;

public class AlertUtils {

    public static void makeInformation(String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void makeError(String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void makeSuccessNotification(String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void makeInformationApi(String message) {
        Notifications.create()
                .title("Erreur de saisie")
                .text(message)
                .showInformation();
    }

    public static void makeErrorApi(String message) {
        Notifications.create()
                .title("Erreur")
                .text(message)
                .showError();
    }

    public static void makeSuccessNotificationApi(String message) {
        Notifications.create()
                .title("Succès")
                .text(message)
                .showConfirm();
    }

}
