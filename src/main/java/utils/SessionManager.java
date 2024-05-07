package utils;

import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class SessionManager {

    private static SessionManager instance;
    private static User current_User;
    private Connection connection;
    private static int id;

    private SessionManager() {
        connection = MyDataBase.getInstance().getCnx();
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public User getCurrent_User() {
        return current_User;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public User auth(String email, String password) throws SQLException {
        String query = "SELECT * FROM user WHERE email=? AND password=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User(
                           resultSet.getInt("id"),
                           resultSet.getString("email"),
                           resultSet.getString("roles"),

                      resultSet.getString("password"),
                        false ,
                      resultSet.getString("nom"),
                        resultSet.getString("prenom"),

                          resultSet.getDate("dateNaissance"),
                            resultSet.getString("numeroTelephone")

                            );
                    setId(user.getId()); // Set the id attribute when user is authenticated
                    System.out.println("Login successful");
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Login not successful");
        return null;
    }


    public void cleanUserSession() {
        current_User = null;
        id = 0; // Reset id attribute when user session is cleaned
    }
}


