package utils;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class DBUtils {

    public static void changeScene(ActionEvent event, String fxmlFile, String title){
        Parent root=null;
        try {
            FXMLLoader loader= new FXMLLoader(DBUtils.class.getResource(fxmlFile));
            root=loader.load();
            System.out.println(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root,755,491));
        stage.show();
    }

    public static Connection getConnexion() throws SQLException{
        Connection connection=null;
        String URL = "jdbc:mysql://localhost:3306/aicha";
        String USER = "root";
        String PASS = "";
        connection = DriverManager.getConnection(URL,USER,PASS);
        return connection;

    }

    public static void closeConnection(){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        if (resultSet != null){
            try {
                resultSet.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        if (preparedStatement != null){
            try {
                preparedStatement.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        if (connection != null){
            try {
                connection.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
    public static void logIn(ActionEvent event, String email, String password) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnexion();
            preparedStatement = connection.prepareStatement("SELECT roles FROM user WHERE email=? AND password=?");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("User not found in database!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Invalid email or password!");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String role = resultSet.getString("roles");
                    if (role.equals("admin")) {
                        System.out.println("Logged in as admin");
                        changeScene(event, "dashAdmin.fxml", "Admin Dashboard");
                    } else if (role.equals("user")) {
                        System.out.println("Logged in as user");
                        changeScene(event, "user.fxml", "User Dashboard");
                    } else {
                        System.out.println("Invalid role");
                        // Handle other roles or errors
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close JDBC objects in finally block
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }

        }

    }






}
