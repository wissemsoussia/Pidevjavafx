package com.example.pidev.entities;


public class Voyage {

    private int id;
    private String villeDepart;
    private String destination;
    private String heureDepart;
    private String heureArrivee;
    private String typeVoyage;
    private String type;
    private String activites;


    public Voyage() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVilleDepart() {
        return villeDepart;
    }

    public void setVilleDepart(String villeDepart) {
        this.villeDepart = villeDepart;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getHeureDepart() {
        return heureDepart;
    }

    public void setHeureDepart(String heureDepart) {
        this.heureDepart = heureDepart;
    }

    public String getHeureArrivee() {
        return heureArrivee;
    }

    public void setHeureArrivee(String heureArrivee) {
        this.heureArrivee = heureArrivee;
    }

    public String getTypeVoyage() {
        return typeVoyage;
    }

    public void setTypeVoyage(String typeVoyage) {
        this.typeVoyage = typeVoyage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getActivites() {
        return activites;
    }

    public void setActivites(String activites) {
        this.activites = activites;
    }


    public String toString() {
        return villeDepart;
    }
}