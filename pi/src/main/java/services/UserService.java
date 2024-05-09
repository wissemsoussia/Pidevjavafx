package services;

import java.sql.SQLException;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.net.URL;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import models.User;
import utils.MyDataBase;



public class UserService implements IService<User> {
    private Connection connection = MyDataBase.getInstance().getConnection();




  /*  @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            showUsers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public void showUsers() throws SQLException {
        ObservableList<User> liste = getUser();
        table.setItems(liste);
        colCode.setCellValueFactory(new PropertyValueFactory<User,Integer>("code"));
        colNom.setCellValueFactory(new PropertyValueFactory<User,String>("nom"));
        colCredit.setCellValueFactory(new PropertyValueFactory<User,Float>("credit"));
        System.out.println("ahla");

    }


    @FXML
    void clearMatiere() {
        tcredit.setText(null);
        tcode.setText(null);
        tnom.setText(null);
        save_btn.setDisable(false);
    }*/





    @Override
    public void ajouter(User user) throws SQLException {
        String sql = "INSERT INTO User (email, roles, password, is_verified, nom, prenom, date_naissance, numero_telephone) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, user.getEmail());
        statement.setString(2, user.getRoles() != null ? user.getRoles() : "default_role"); // Utilisation de "default_role" comme valeur par défaut
        statement.setString(3, user.getPassword());
        statement.setBoolean(4, user.isVerified());
        statement.setString(5, user.getNom());
        statement.setString(6, user.getPrenom());
        Date date = user.getDateNaissance();
        statement.setDate(7, date != null ? new java.sql.Date(date.getTime()) : null);
        statement.setString(8, user.getNumeroTelephone());
        statement.executeUpdate();
    }


    @Override
    public void modifier(User user) throws SQLException {
        String sql = "UPDATE User SET email=?, password=?, is_verified=?, nom=?, prenom=?, date_naissance=?, numero_telephone=? WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, user.getEmail());
        ps.setString(2, user.getPassword());
        ps.setBoolean(3, user.isVerified());
        ps.setString(4, user.getNom());
        ps.setString(5, user.getPrenom());
        ps.setDate(6, user.getDateNaissance() != null ? new java.sql.Date(user.getDateNaissance().getTime()) : null);
        ps.setString(7, user.getNumeroTelephone());
        ps.setInt(8, user.getId());
        ps.executeUpdate();
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
            user.setNom(rs.getString("nom"));
            user.setPrenom(rs.getString("prenom"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            user.setNumeroTelephone(rs.getString("numero_telephone"));
            user.setRoles(rs.getString("roles"));
            user.setVerified(rs.getBoolean("is_verified"));
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
            user.setNom(rs.getString("nom"));
            user.setPrenom(rs.getString("prenom"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setNumeroTelephone(rs.getString("numero_telephone"));
            user.setRoles(rs.getString("roles"));
            user.setVerified(rs.getBoolean("is_verified"));
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
            user.setNom(rs.getString("nom"));
            user.setPrenom(rs.getString("prenom"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setNumeroTelephone(rs.getString("numero_telephone"));
            user.setRoles(rs.getString("roles"));
            user.setVerified(rs.getBoolean("is_verified"));
            userList.add(user);
        }
        return userList;
    }

    private Date parseDate(String dateString) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(dateString);
    }
}
