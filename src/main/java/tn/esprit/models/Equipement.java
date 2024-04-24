package tn.esprit.models;

public class Equipement {

    private int id, nbr;
    private String nom,type;
    private Evenement evenement;

    public Evenement getEvenement() {
        return evenement;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }
    public Equipement() {}

    public Equipement(int id, int nbr, String nom, String type) {
        this.id = id;
        this.nbr = nbr;
        this.nom = nom;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbr() {
        return nbr;
    }

    public void setNbr(int nbr) {
        this.nbr = nbr;
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
        return "Equipement{" +
                "id=" + id +
                ", nbr=" + nbr +
                ", nom='" + nom + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
