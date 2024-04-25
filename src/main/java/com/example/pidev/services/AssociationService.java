package com.example.pidev.services;

import com.example.pidev.entities.Association;
import com.example.pidev.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AssociationService implements AssociationIservice {

    private static AssociationService instance;
    PreparedStatement preparedStatement;
    Connection connection;

    public AssociationService() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public static AssociationService getInstance() {
        if (instance == null) {
            instance = new AssociationService();
        }
        return instance;
    }

    public List<Association> getAll() {
        List<Association> listAssociation = new ArrayList<>();
        try {

            preparedStatement = connection.prepareStatement("SELECT * FROM `association`");


            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Association association = new Association();
                association.setId(resultSet.getInt("id"));
                association.setNom(resultSet.getString("nom"));
                association.setDescription(resultSet.getString("description"));
                association.setAdresse(resultSet.getString("adresse"));
                association.setEmail(resultSet.getString("email"));
                association.setNumeroTelephone(resultSet.getString("numero_telephone"));
                association.setDateCreation(resultSet.getDate("date_creation") != null ? resultSet.getDate("date_creation").toLocalDate() : null);


                listAssociation.add(association);
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) association : " + exception.getMessage());
        }
        return listAssociation;
    }


    public boolean add(Association association) {


        String request = "INSERT INTO `association`(`nom`, `description`, `adresse`, `email`, `numero_telephone`, `date_creation`) VALUES(?, ?, ?, ?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setString(1, association.getNom());
            preparedStatement.setString(2, association.getDescription());
            preparedStatement.setString(3, association.getAdresse());
            preparedStatement.setString(4, association.getEmail());
            preparedStatement.setString(5, association.getNumeroTelephone());
            preparedStatement.setDate(6, association.getDateCreation() == null ? null : Date.valueOf(association.getDateCreation()));


            preparedStatement.executeUpdate();
            System.out.println("Association added");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (add) association : " + exception.getMessage());
        }
        return false;
    }

    public boolean edit(Association association) {

        String request = "UPDATE `association` SET `nom` = ?, `description` = ?, `adresse` = ?, `email` = ?, `numero_telephone` = ?, `date_creation` = ? WHERE `id` = ?";

        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setString(1, association.getNom());
            preparedStatement.setString(2, association.getDescription());
            preparedStatement.setString(3, association.getAdresse());
            preparedStatement.setString(4, association.getEmail());
            preparedStatement.setString(5, association.getNumeroTelephone());
            preparedStatement.setDate(6, association.getDateCreation() == null ? null : Date.valueOf(association.getDateCreation()));


            preparedStatement.setInt(7, association.getId());

            preparedStatement.executeUpdate();
            System.out.println("Association edited");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (edit) association : " + exception.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM `association` WHERE `id`=?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Association deleted");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (delete) association : " + exception.getMessage());
        }
        return false;
    }
}
