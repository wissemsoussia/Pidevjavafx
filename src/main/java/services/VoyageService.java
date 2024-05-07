package services;

import com.example.pidev.entities.Voyage;
import com.example.pidev.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VoyageService {

    private static VoyageService instance;
    PreparedStatement preparedStatement;
    Connection connection;

    public VoyageService() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public static VoyageService getInstance() {
        if (instance == null) {
            instance = new VoyageService();
        }
        return instance;
    }

    public List<Voyage> getAll() {
        List<Voyage> listVoyage = new ArrayList<>();
        try {

            preparedStatement = connection.prepareStatement("SELECT * FROM `voyage`");


            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Voyage voyage = new Voyage();
                voyage.setId(resultSet.getInt("id"));
                voyage.setVilleDepart(resultSet.getString("ville_depart"));
                voyage.setDestination(resultSet.getString("destination"));
                String heureDepart = resultSet.getString("heure_depart");
                if (heureDepart != null && heureDepart.length() >= 5) {
                    heureDepart = heureDepart.substring(0, 5); // Extract the first 5 characters
                }
                voyage.setHeureDepart(heureDepart);
                String heureArrivee = resultSet.getString("heure_arrivee");
                if (heureArrivee != null && heureArrivee.length() >= 5) {
                    heureArrivee = heureArrivee.substring(0, 5); // Extract the first 5 characters
                }
                voyage.setHeureArrivee(heureArrivee);
                voyage.setTypeVoyage(resultSet.getString("type_voyage"));
                voyage.setType(resultSet.getString("type"));
                voyage.setActivites(resultSet.getString("activites"));


                listVoyage.add(voyage);
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) voyage : " + exception.getMessage());
        }
        return listVoyage;
    }


    public boolean add(Voyage voyage) {


        String request = "INSERT INTO `voyage`(`ville_depart`, `destination`, `heure_depart`, `heure_arrivee`, `type_voyage`, `type`, `activites`) VALUES(?, ?, ?, ?, ?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setString(1, voyage.getVilleDepart());
            preparedStatement.setString(2, voyage.getDestination());
            preparedStatement.setString(3, voyage.getHeureDepart());
            preparedStatement.setString(4, voyage.getHeureArrivee());
            preparedStatement.setString(5, voyage.getTypeVoyage());
            preparedStatement.setString(6, voyage.getType());
            preparedStatement.setString(7, voyage.getActivites());


            preparedStatement.executeUpdate();
            System.out.println("Voyage added");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (add) voyage : " + exception.getMessage());
        }
        return false;
    }

    public boolean edit(Voyage voyage) {

        String request = "UPDATE `voyage` SET `ville_depart` = ?, `destination` = ?, `heure_depart` = ?, `heure_arrivee` = ?, `type_voyage` = ?, `type` = ?, `activites` = ? WHERE `id` = ?";

        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setString(1, voyage.getVilleDepart());
            preparedStatement.setString(2, voyage.getDestination());
            preparedStatement.setString(3, voyage.getHeureDepart());
            preparedStatement.setString(4, voyage.getHeureArrivee());
            preparedStatement.setString(5, voyage.getTypeVoyage());
            preparedStatement.setString(6, voyage.getType());
            preparedStatement.setString(7, voyage.getActivites());


            preparedStatement.setInt(8, voyage.getId());

            preparedStatement.executeUpdate();
            System.out.println("Voyage edited");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (edit) voyage : " + exception.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM `voyage` WHERE `id`=?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Voyage deleted");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (delete) voyage : " + exception.getMessage());
        }
        return false;
    }
}
