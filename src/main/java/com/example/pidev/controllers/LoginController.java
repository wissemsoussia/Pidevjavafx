package com.example.pidev.controllers;

import com.example.pidev.MainFX;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {


    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }


    @FXML
    public void frontend(ActionEvent ignored) {
        MainFX.getInstance().loadFront();
    }

    @FXML
    public void backend(ActionEvent ignored) {
        MainFX.getInstance().loadBack();
    }
}
