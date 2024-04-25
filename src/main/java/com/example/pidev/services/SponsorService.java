package com.example.pidev.services;

import com.example.pidev.entities.Association;
import com.example.pidev.entities.Sponsor;
import com.example.pidev.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SponsorService implements SponsorIservice {

    private static SponsorService instance;
    PreparedStatement preparedStatement;
    Connection connection;


    // Constructeur privé pour empêcher l'instanciation directe de cette classe

    public SponsorService() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    // Méthode statique pour obtenir l'instance unique de la classe SponsorService

    public static SponsorService getInstance() {
        if (instance == null) {
            instance = new SponsorService();
        }
        return instance;
    }
    // Méthode pour récupérer tous les sponsors depuis la base de données

    public List<Sponsor> getAll() {
        List<Sponsor> listSponsor = new ArrayList<>();
        try {
            // Requête SQL pour récupérer tous les sponsors et leurs associations associées

            String query = "SELECT * FROM `sponsor` AS x "
                + "RIGHT JOIN `association` AS y1 ON x.association_id = y1.id "
                + "WHERE  x.association_id = y1.id  ";
            preparedStatement = connection.prepareStatement(query);

            // Requête SQL pour récupérer tous les sponsors et leurs associations associées

            ResultSet resultSet = preparedStatement.executeQuery();
            // Parcours des résultats et création des objets Sponsor correspondants

            while (resultSet.next()) {
                Sponsor sponsor = new Sponsor();
                sponsor.setId(resultSet.getInt("id"));
                sponsor.setNom(resultSet.getString("nom"));
                sponsor.setAdresse(resultSet.getString("adresse"));
                sponsor.setMail(resultSet.getString("mail"));
                sponsor.setTelephone(resultSet.getString("telephone"));
                sponsor.setDateCreation(resultSet.getDate("date_creation") != null ? resultSet.getDate("date_creation").toLocalDate() : null);
                // Création de l'objet Association associé au sponsor

                Association association = new Association();
                association.setId(resultSet.getInt("y1.id"));
                association.setNom(resultSet.getString("y1.nom"));
                sponsor.setAssociation(association);

                listSponsor.add(sponsor);
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) sponsor : " + exception.getMessage());
        }
        return listSponsor;
    }
    // Méthode pour récupérer toutes les associations depuis la base de données

    public List<Association> getAllAssociations() {
        List<Association> listAssociations = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `association`");
            ResultSet resultSet = preparedStatement.executeQuery();
            // Parcours des résultats et création des objets Association correspondants

            while (resultSet.next()) {
                Association association = new Association();
                association.setId(resultSet.getInt("id"));
                association.setNom(resultSet.getString("nom"));
                listAssociations.add(association);
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) associations : " + exception.getMessage());
        }
        return listAssociations;
    }
    // Méthode pour ajouter un nouveau sponsor dans la base de données

    public boolean add(Sponsor sponsor) {


        String request = "INSERT INTO `sponsor`(`nom`, `adresse`, `mail`, `telephone`, `date_creation`, `association_id`) VALUES(?, ?, ?, ?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setString(1, sponsor.getNom());
            preparedStatement.setString(2, sponsor.getAdresse());
            preparedStatement.setString(3, sponsor.getMail());
            preparedStatement.setString(4, sponsor.getTelephone());
            preparedStatement.setDate(5, Date.valueOf(sponsor.getDateCreation()));

            preparedStatement.setInt(6, sponsor.getAssociation().getId());


            preparedStatement.executeUpdate();
            System.out.println("Sponsor added");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (add) sponsor : " + exception.getMessage());
        }
        return false;
    }

    public boolean edit(Sponsor sponsor) {

        String request = "UPDATE `sponsor` SET `nom` = ?, `adresse` = ?, `mail` = ?, `telephone` = ?, `date_creation` = ?, `association_id` = ? WHERE `id` = ?";

        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setString(1, sponsor.getNom());
            preparedStatement.setString(2, sponsor.getAdresse());
            preparedStatement.setString(3, sponsor.getMail());
            preparedStatement.setString(4, sponsor.getTelephone());
            preparedStatement.setDate(5, Date.valueOf(sponsor.getDateCreation()));

            preparedStatement.setInt(6, sponsor.getAssociation().getId());

            preparedStatement.setInt(7, sponsor.getId());

            preparedStatement.executeUpdate();
            System.out.println("Sponsor edited");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (edit) sponsor : " + exception.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM `sponsor` WHERE `id`=?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Sponsor deleted");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (delete) sponsor : " + exception.getMessage());
        }
        return false;
    }
}
