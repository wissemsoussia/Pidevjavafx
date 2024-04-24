package tn.esprit.services;

import tn.esprit.interfaces.IEvenement;
import tn.esprit.models.Equipement;
import tn.esprit.models.Evenement;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceEvenement implements IEvenement<Evenement> {
    private Connection cnx;
    public ServiceEvenement() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    @Override
    public void Add(Evenement evenement) {
        String qry = "INSERT INTO `evenement`(`nom`, `type`) VALUES (?,?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1, evenement.getNom());
            stm.setString(2, evenement.getType());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public ArrayList<Equipement> getAll() {
        ArrayList<Equipement> categories = new ArrayList<>();
        String qry = "SELECT * FROM `evenement`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                Equipement cat = new Equipement();
                cat.setId(rs.getInt("id"));
                cat.setNom(rs.getString("nom"));
                cat.setType(rs.getString("type"));
                categories.add(cat);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categories;
    }
    @Override
    public List<Evenement> afficher() {
        List<Evenement> prods = new ArrayList<>();
        String sql = "SELECT `id`, `nom`, `type` FROM `evenement`";
        try {
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Evenement prod = new Evenement();
                prod.setId(rs.getInt("id"));
                prod.setNom(rs.getString("nom"));
                prod.setType(rs.getString("type"));
                prods.add(prod);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return prods;
    }

    @Override
    public List<Evenement> TriparName() {
        List<Evenement> prods = new ArrayList<>();
        String sql = "SELECT `id`, `nom`, `type` FROM `evenement` ORDER BY `nom`" ;
        try {
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Evenement prod = new Evenement();
                prod.setId(rs.getInt("id"));
                prod.setNom(rs.getString("nom"));
                prod.setType(rs.getString("type"));
                prods.add(prod);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return prods;
    }

    @Override
    public List<Evenement> TriparType() {
        List<Evenement> prods = new ArrayList<>();
        String sql = "SELECT `id`, `nom`, `type` FROM `evenement` ORDER BY `type`" ;
        try {
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Evenement prod = new Evenement();
                prod.setId(rs.getInt("id"));
                prod.setNom(rs.getString("nom"));
                prod.setType(rs.getString("type"));
                prods.add(prod);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return prods;
    }

    @Override
    public List<Evenement> Rechreche(String recherche) {
        List<Evenement> prods = new ArrayList<>();
        String sql = "SELECT `id`, `nom`, `type` FROM `evenement` WHERE `nom` LIKE '%" + recherche + "%'" ;
        try {
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Evenement prod = new Evenement();
                prod.setId(rs.getInt("id"));
                prod.setNom(rs.getString("nom"));
                prod.setType(rs.getString("type"));
                prods.add(prod);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return prods;
    }

    @Override
    public void Update(Evenement evenement) {
        try {
            String qry = "UPDATE `evenement` SET `nom`=?,`type`=? WHERE `id`=?";
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1, evenement.getNom());
            stm.setString(2, evenement.getType());
            stm.setInt(3, evenement.getId());
            stm.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void DeleteByID(int id) {
        try {
            String qry = "DELETE FROM `evenement` WHERE id=?";
            PreparedStatement smt = cnx.prepareStatement(qry);
            smt.setInt(1, id);
            smt.executeUpdate();
            System.out.println("Suppression Effectue");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
