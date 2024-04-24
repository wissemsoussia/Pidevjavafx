package tn.esprit.services;

import tn.esprit.interfaces.IEquipement;
import tn.esprit.models.Equipement;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceEquipement implements IEquipement<Equipement> {
    private Connection cnx;
    public ServiceEquipement() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    @Override
    public void Add(Equipement equipement) {
        String qry = "INSERT INTO `equipement`(`nom_equipe`, `nbr`, `type`) VALUES (?,?,?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1, equipement.getNom());
            stm.setInt(2, equipement.getNbr());
            stm.setString(3, equipement.getType());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Equipement> afficher() {
        List<Equipement> categories = new ArrayList<>();
        String sql = "SELECT `id`, `nom_equipe`, `nbr`, `type` FROM `equipement`";
        try {
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Equipement cat = new Equipement();
                cat.setId(rs.getInt("id"));
                cat.setNom(rs.getString("nom_equipe"));
                cat.setNbr(rs.getInt("nbr"));
                cat.setType(rs.getString("type"));
                categories.add(cat);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return categories;
    }
    @Override
    public ArrayList<Equipement> getAll() {
        ArrayList<Equipement> categories = new ArrayList<>();
        String qry = "SELECT * FROM `equipement`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                Equipement cat = new Equipement();
                cat.setId(rs.getInt("id"));
                cat.setNom(rs.getString("nom_equipe"));
                cat.setNbr(rs.getInt("nbr"));
                cat.setType(rs.getString("type"));
                categories.add(cat);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categories;
    }
    @Override
    public List<Equipement> TriparName() {
        List<Equipement> categories = new ArrayList<>();
        String sql = "SELECT `id`, `nom_equipe`, `nbr`, `type` FROM `equipement` ORDER BY `nom`";
        try {
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Equipement cat = new Equipement();
                cat.setId(rs.getInt("id"));
                cat.setNom(rs.getString("nom_equipe"));
                cat.setNbr(rs.getInt("nbr"));
                cat.setType(rs.getString("type"));
                categories.add(cat);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return categories;
    }

    @Override
    public List<Equipement> TriparType() {
        List<Equipement> categories = new ArrayList<>();
        String sql = "SELECT `id`, `nom_equipe`, `nbr`, `type` FROM `equipement` ORDER BY `type`";
        try {
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Equipement cat = new Equipement();
                cat.setId(rs.getInt("id"));
                cat.setNom(rs.getString("nom_equipe"));
                cat.setNbr(rs.getInt("nbr"));
                cat.setType(rs.getString("type"));
                categories.add(cat);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return categories;
    }

    @Override
    public List<Equipement> Rechreche(String recherche) {
        List<Equipement> categories = new ArrayList<>();
        String sql = "SELECT `id`, `nom_equipe`, `nbr`, `type` FROM `equipement` WHERE `type` LIKE '%" + recherche + "%'";
        try {
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Equipement cat = new Equipement();
                cat.setId(rs.getInt("id"));
                cat.setNom(rs.getString("nom_equipe"));
                cat.setNbr(rs.getInt("nbr"));
                cat.setType(rs.getString("type"));
                categories.add(cat);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return categories;
    }
    @Override
    public void Update(Equipement equipement) {
        try {
            String qry = "UPDATE `equipement` SET `nom_equipe`=?,`nbr`=?,`type`=? WHERE `id`=?";
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1, equipement.getNom());
            stm.setInt(2, equipement.getNbr());
            stm.setString(3, equipement.getType());
            stm.setInt(4, equipement.getId());
            stm.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void DeleteByID(int id) {
        try {
            String qry = "DELETE FROM `equipement` WHERE id=?";
            PreparedStatement smt = cnx.prepareStatement(qry);
            smt.setInt(1, id);
            smt.executeUpdate();
            System.out.println("Suppression Effectue");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
