package controllers;

  /*

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.UserService;
import models.User;
import services.UserService;

import java.util.Properties;


import utils.DBUtils;
import utils.MyDataBase;

//import utils.SessionManager;

public class SignInController {

    public SignInController() {
        // Initialize your connection here
        cnx = MyDataBase.getInstance().getConnection();
    }

   UserService sv = new UserService();

    @FXML
    private TextField email_signin;

    @FXML
    private TextField password_signin;

    private Connection cnx;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;

    public void setEmail_signin(String email_signin) {
        this.email_signin.setText(email_signin);
    }

    public void setPassword_signin(String password_signin) {
        this.password_signin.setText(password_signin);
    }

    public void goToSignUp(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterPersonne.fxml"));

        try {
            Parent root = loader.load();

            AjouterUserController ajouteruserController = loader.getController();

            ajouteruserController.setTxtLastname("");
            ajouteruserController.setTxtEmail("");
            ajouteruserController.setTxtPassword("");


            // Debug statement to check if root is loaded successfully
            System.out.println("FXML loaded successfully sign up.");

            email_signin.getScene().setRoot(root);
        } catch (IOException e) {
            // Instead of just printing, handle the IOException
            e.printStackTrace();
        }
    }

    @FXML
    void loginAction(javafx.event.ActionEvent event) throws IOException {
        if(email_signin.getText().equals("admin") && password_signin.getText().equals("admin") )
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Travel Me :: Success Message");
            alert.setHeaderText(null);
            alert.setContentText("Bienvenu Admin");
            alert.showAndWait();

            Parent root = FXMLLoader.load(getClass().getResource("/Back.fxml"));
            Scene scene;

            scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.show();
        }else {
            String email = email_signin.getText();
            String pass = password_signin.getText();
            sv.login(email,pass);
        }

    }

  void sendPassword() throws SQLException {
        PreparedStatement smt=sv.sendPass();
        String email1="empty";
        try {
            //PreparedStatement smt = cnx.prepareStatement(query2);
            smt.setString(1, email_signin.getText());
            ResultSet rs= smt.executeQuery();
            if(rs.next()){
                email1=rs.getString("email");
                System.out.println(email1);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        sendMail(email1);
    }
    public void sendMail(String recepient){
        System.out.println("Preparing to send email");
        Properties properties = new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        //properties.put("mail.smtp.host","smtp.gmail.com");
        //properties.put("mail.smtp.port","587");
        properties.put("mail.smtp.host","sandbox.smtp.mailtrap.io");
        properties.put("mail.smtp.port","2525");
        String myAccountEmail = "ec36e3ee17ac2f";
        String passwordd = "d6d851b44b0ce8";

        Session session = Session.getInstance(properties, new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(myAccountEmail,passwordd);
            }
        });
        Message message =preparedMessage(session,myAccountEmail,recepient);
        try{
            Transport.send(message);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("TravelMe :: Boite Mail");
            alert.setHeaderText(null);
            alert.setContentText("consulter votre boite mail !!");
            alert.showAndWait();

        }catch(Exception ex){
            ex.printStackTrace();

        }

    }
    private Message preparedMessage(Session session, String myAccountEmail, String recepient){
        String query2="select * from user where email=?";
        String userEmail="null" ;
        String pass="empty";
        try {
            //cnx = MyConnection.getInstance().getCnx();
            PreparedStatement smt = cnx.prepareStatement(query2);
            smt.setString(1, email_signin.getText());
            ResultSet rs= smt.executeQuery();
            System.out.println(rs);
            if(rs.next()){
                pass=rs.getString("password");
                userEmail=rs.getString("email");                }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.print("c est en cours");
        String text="Votre mot de pass est :"+pass+"";
        String object ="Recupération de mot de passe";
        Message message = new MimeMessage(session);
        try{
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
            message.setSubject(object);
            String htmlcode ="<h1> "+text+" </h1> <h2> <b> </b2> </h2> ";
            message.setContent(htmlcode, "text/html");
            System.out.println("Message envoyeer");

            System.out.println(pass);

            return message;

        }catch(MessagingException ex){
            ex.printStackTrace();
        }
        return null;
    }

    @FXML
    void sendPaswword_btn(ActionEvent event) throws SQLException {
        //sendMail(email_signin.getText());
        sendPassword();
   } }*/

