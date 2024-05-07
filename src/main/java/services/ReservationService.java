package services;

import com.example.pidev.entities.Reservation;
import com.example.pidev.entities.Voyage;
import com.example.pidev.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationService {

    private static ReservationService instance;
    PreparedStatement preparedStatement;
    Connection connection;

    public ReservationService() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }
        return instance;
    }

    public List<Reservation> getAll() {
        List<Reservation> listReservation = new ArrayList<>();
        try {
// Construction de la requête SQL pour récupérer les réservations avec les détails du voyage associé
            String query = "SELECT * FROM `reservation` AS x "
                    + "RIGHT JOIN `voyage` AS y1 ON x.voyage_id = y1.id "

                    + "WHERE  x.voyage_id = y1.id  " ;
            preparedStatement = connection.prepareStatement(query);
// Exécution de la requête SQL

            ResultSet resultSet = preparedStatement.executeQuery();
// Parcours des résultats de la requête et création des objets Reservation
            while (resultSet.next()) {
                Reservation reservation = new Reservation();
                reservation.setId(resultSet.getInt("id"));
                reservation.setNom(resultSet.getString("nom"));
                reservation.setPrenom(resultSet.getString("prenom"));
                reservation.setEmail(resultSet.getString("email"));
                reservation.setNumeroTelephone(resultSet.getString("numero_telephone"));
// Création de l'objet Voyage associé à la réservation
                Voyage voyage = new Voyage();
                voyage.setId(resultSet.getInt("y1.id"));
                voyage.setVilleDepart(resultSet.getString("y1.ville_depart"));
                reservation.setVoyage(voyage);
// Ajout de la réservation à la liste
                listReservation.add(reservation);
            }
        } catch (SQLException exception) {
            // Gestion des erreurs SQL
            System.out.println("Error (getAll) reservation : " + exception.getMessage());
        }
        // Gestion des erreurs SQL
        return listReservation;
    }

    public List<Voyage> getAllVoyages() {
        List<Voyage> listVoyages = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `voyage`");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Voyage voyage = new Voyage();
                voyage.setId(resultSet.getInt("id"));
                voyage.setVilleDepart(resultSet.getString("ville_depart"));
                listVoyages.add(voyage);
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) voyages : " + exception.getMessage());
        }
        return listVoyages;
    }


    public boolean add(Reservation reservation) {


        String request = "INSERT INTO `reservation`(`nom`, `prenom`, `email`, `numero_telephone`, `voyage_id`) VALUES(?, ?, ?, ?, ?)" ;

        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setString(1, reservation.getNom());
            preparedStatement.setString(2, reservation.getPrenom());
            preparedStatement.setString(3, reservation.getEmail());
            preparedStatement.setString(4, reservation.getNumeroTelephone());

            preparedStatement.setInt(5, reservation.getVoyage().getId());


            preparedStatement.executeUpdate();
            System.out.println("Reservation added");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (add) reservation : " + exception.getMessage());
        }
        return false;
    }

    public boolean edit(Reservation reservation) {

        String request = "UPDATE `reservation` SET `nom` = ?, `prenom` = ?, `email` = ?, `numero_telephone` = ?, `voyage_id` = ? WHERE `id` = ?" ;

        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setString(1, reservation.getNom());
            preparedStatement.setString(2, reservation.getPrenom());
            preparedStatement.setString(3, reservation.getEmail());
            preparedStatement.setString(4, reservation.getNumeroTelephone());

            preparedStatement.setInt(5, reservation.getVoyage().getId());

            preparedStatement.setInt(6, reservation.getId());

            preparedStatement.executeUpdate();
            System.out.println("Reservation edited");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (edit) reservation : " + exception.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM `reservation` WHERE `id`=?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Reservation deleted");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (delete) reservation : " + exception.getMessage());
        }
        return false;
    }
}
