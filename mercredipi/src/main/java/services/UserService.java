package services;
import controllers.AfficherPControllers;

import controllers.userController;
import models.User;
import utils.MyDataBase;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;




public class UserService implements IService<User> {
    private Connection connection = MyDataBase.getInstance().getCnx();








    @Override
    public int ajouter(User user) throws SQLException {
        String sql = "INSERT INTO User (email, roles, password, is_verified, nom, prenom, date_naissance, numero_telephone) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, user.getEmail());
        statement.setString(2, "[ROLE_USER]"); // Utilisation de "default_role" comme valeur par défaut
        statement.setString(3, user.getPassword());
        statement.setBoolean(4, user.isVerified());
        statement.setString(5, user.getNom());
        statement.setString(6, user.getPrenom());
        Date date = user.getDateNaissance();
        statement.setDate(7, date != null ? new java.sql.Date(date.getTime()) : null);
        statement.setString(8, user.getNumeroTelephone());
        statement.executeUpdate();
        return 0;
    }


    @Override
    public void modifier(User user) throws SQLException {
//        System.out.println();
        String sql = "UPDATE User SET email=?, password=?, is_verified=?, nom=?, prenom=?, date_naissance=?, numero_telephone=? WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, user.getEmail());
        ps.setString(2, user.getPassword());
        ps.setBoolean(3, user.isVerified());
        ps.setString(4, user.getNom());
        ps.setString(5, user.getPrenom());
        ps.setDate(6, user.getDateNaissance() != null ? new java.sql.Date(user.getDateNaissance().getTime()) : null);
        ps.setString(7, user.getNumeroTelephone());
        ps.setInt(8, User.Current_User.getId());
        ps.executeUpdate();
        System.out.println("done");
    }


    private String formatDate(String date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }


    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM User WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public List<User> recuperer() throws SQLException {
        String sql = "SELECT * FROM User";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<User> users = new ArrayList<>();
        while (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setEmail(rs.getString("email"));
            user.setRoles(rs.getString("roles"));
            user.setDateNaissance(rs.getDate("date_naissance"));
            user.setPassword(rs.getString("password"));
            user.setVerified(rs.getBoolean("is_verified"));
            user.setNom(rs.getString("nom"));
            user.setPrenom(rs.getString("prenom"));
            user.setNumeroTelephone(rs.getString("numero_telephone"));
            System.out.println(user);

            users.add(user);
        }
        return users;
    }

    public User getUserById(int id) throws SQLException {
        String sql = "SELECT * FROM User WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setEmail(rs.getString("email"));
            user.setRoles(rs.getString("roles"));
            user.setPassword(rs.getString("password"));
            user.setVerified(rs.getBoolean("is_verified"));
            user.setNom(rs.getString("nom"));
            user.setPrenom(rs.getString("prenom"));


            user.setNumeroTelephone(rs.getString("numero_telephone"));


            return user;
        }
        return null;
    }

    public List<User> getAllUsers() throws SQLException {
        String sql = "SELECT * FROM User";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<User> userList = new ArrayList<>();
        while (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setEmail(rs.getString("email"));
            user.setRoles(rs.getString("roles"));
            user.setPassword(rs.getString("password"));
            user.setVerified(rs.getBoolean("is_verified"));
            user.setNom(rs.getString("nom"));
            user.setPrenom(rs.getString("prenom"));


            user.setNumeroTelephone(rs.getString("numero_telephone"));


            userList.add(user);
        }
        return userList;
    }

    private Date parseDate(String dateString) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(dateString);
    }

    public void login(String email, String pass) throws IOException {

        String query = "SELECT * FROM user WHERE email=? AND password=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, pass);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                User currentUser = new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("roles"),
                        rs.getString("password"),
                        rs.getBoolean("is_verified"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getDate("date_naissance"),
                        rs.getString("numero_telephone")
                );


//                System.out.println(User.Current_User.getEmail());

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Travel Me :: Message de succès");
                alert.setHeaderText(null);
                alert.setContentText("Vous êtes connecté");
                alert.showAndWait();


                User.Current_User = currentUser;
                System.out.println(User.Current_User.getRoles().substring(2,User.Current_User.getRoles().length()-2));
                if(User.Current_User.getRoles().substring(2,User.Current_User.getRoles().length()-2).equals("ROLE_ADMIN")){
                    System.out.println("admin admin");
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back.fxml"));
                Parent root = loader.load();
//                AfficherPControllers afficherPControllers = loader.getController();
//                afficherPControllers.setTxtNom(currentUser.getNom());
//                afficherPControllers.setTxtEmail(currentUser.getEmail());
//                afficherPControllers.setTxtPassword(currentUser.getPassword());
//                afficherPControllers.setTxtId(String.valueOf(currentUser.getId()));

                System.out.println("FXML loaded successfully.");

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
                }else {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/user.fxml"));
                    Parent root = loader.load();
//                    AfficherPControllers afficherPControllers = loader.getController();
//                    afficherPControllers.setTxtNom(currentUser.getNom());
//                    afficherPControllers.setTxtEmail(currentUser.getEmail());
//                    afficherPControllers.setTxtPassword(currentUser.getPassword());
//                    afficherPControllers.setTxtId(String.valueOf(currentUser.getId()));

                    System.out.println("FXML loaded successfully.");

                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                    userController.userStage = stage;
                }
//
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Travel Me :: Message d'erreur");
                alert.setHeaderText(null);
                alert.setContentText("Mauvais email/mot de passe !!");
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


}
