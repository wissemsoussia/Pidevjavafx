package com.example.pidev.utils;

import org.controlsfx.control.Notifications;

public class AlertUtils {

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
