package test;

import models.User;
import services.UserService;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();

        try {
            // Création d'un nouvel utilisateur avec une date de naissance
            /*User newUser = new User("sfdf", "Doe", "john@gmail.com", "password123", "123456789");
            newUser.setDateNaissance(new Date(1990, 5, 15)); // Exemple de date de naissance : 15 juin 1990

            // Spécification du rôle pour le nouvel utilisateur
            String role = "ROLE_USER"; // Remplacez "ROLE_USER" par le rôle souhaité

            // Ajout du rôle à l'utilisateur avant d'appeler la méthode ajouter
            newUser.setRoles(role);

            // Appel de la méthode ajouter avec le nouvel utilisateur
            userService.ajouter(newUser);*/

            // Modification de l'utilisateur avec l'ID 1 et mise à jour de la date de naissance
            User userToUpdate = userService.getUserById(14);
            if (userToUpdate != null) {
                // Modification de tous les attributs de l'utilisateur
                userToUpdate.setEmail("nouveauemail@example.com");
                userToUpdate.setRoles("NOUVEAU_ROLE");
                userToUpdate.setPassword("nouveaumotdepasse");
                userToUpdate.setVerified(true);
                userToUpdate.setNom("NouveauNom");
                userToUpdate.setPrenom("NouveauPrenom");
                userToUpdate.setDateNaissance(new Date(5000, 10, 20)); // Exemple de nouvelle date de naissance : 20 octobre 1985
                userToUpdate.setNumeroTelephone("NouveauNumero");

                // Appel de la méthode de modification avec tous les attributs mis à jour
                userService.modifier(userToUpdate);
            }

            // Suppression de l'utilisateur avec l'ID 1
            int userIdToDelete = 79;
            userService.supprimer(userIdToDelete);
            System.out.println("Utilisateur avec l'ID " + userIdToDelete + " supprimé avec succès.");

            // Récupération de tous les utilisateurs et affichage de leurs informations
            System.out.println("Liste des utilisateurs : ");
            List<User> users = userService.recuperer();
            users.forEach(System.out::println);
        } catch (SQLException e) {
            // Gestion de l'exception
            System.err.println("Erreur lors de l'exécution de l'opération sur la base de données pour l'utilisateur : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
