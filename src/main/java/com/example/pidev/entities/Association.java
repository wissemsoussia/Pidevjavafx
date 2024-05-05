package com.example.pidev.entities;


import java.time.LocalDate;

public class Association implements Comparable<Association> {

    private int id;
    private String nom;
    private String description;
    private String adresse;
    private String email;
    private String numeroTelephone;
    private LocalDate dateCreation;
    private String qrCodePath; // Chemin vers le fichier QR code

    public Association() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String toString() {
        return nom;
    }

    public String allAttrToString() {
        return " - Produit - " +
                "Nom : " + nom
                + " Description : " + description
                + " Adresse : " + adresse
                + " Email : " + email
                + " NumeroTelephone : " + numeroTelephone
                + " DateCreation : " + dateCreation;
    }

    public static String compareVar = "";

    @Override
    public int compareTo(Association association) {
        return switch (compareVar) {
            case "Tri par nom" -> association.getNom().compareToIgnoreCase(this.getNom());
            case "Tri par description" -> association.getDescription().compareToIgnoreCase(this.getDescription());
            case "Tri par adresse" -> association.getAdresse().compareToIgnoreCase(this.getAdresse());
            case "Tri par email" -> association.getEmail().compareToIgnoreCase(this.getEmail());
            case "Tri par numeroTelephone" ->
                    association.getNumeroTelephone().compareToIgnoreCase(this.getNumeroTelephone());
            case "Tri par dateCreation" -> association.getDateCreation().compareTo(this.getDateCreation());
            default -> 0;
        };
    }
}

