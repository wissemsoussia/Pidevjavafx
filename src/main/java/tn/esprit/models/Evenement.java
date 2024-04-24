package tn.esprit.models;

import java.util.HashSet;
import java.util.Set;

public class Evenement {
    private int id;
    private String nom,type;
    private Set<Equipement> equipements = new HashSet<>();

    public Set<Equipement> getEquipements() {
        return equipements;
    }

    public void addEquipement(Equipement equipement) {
        equipements.add(equipement);
        if (equipement != null && equipement.getEvenement() != this) {
            equipement.setEvenement(this);
        }
    }

    public Evenement() {
    }

    public Evenement(int id, String nom, String type) {
        this.id = id;
        this.nom = nom;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Evenement{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
