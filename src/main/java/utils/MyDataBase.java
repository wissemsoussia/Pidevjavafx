package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDataBase {






    private final String URL = "jdbc:mysql://localhost:3306/pidev1";
    private final String USER = "root";
    private final String PASS = "";
    private Connection connection;

    private static MyDataBase instance;

    private MyDataBase() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Connection established");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static MyDataBase getInstance() {
        if (instance == null)
            instance = new MyDataBase();
        return instance;
    }


    public Connection getCnx() {
        return connection;
    }

}