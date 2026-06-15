package modele;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import controleur.Equipement;

public class EquipementDAO {
    
    private static BDD uneBdd = new BDD();

    public static ArrayList<Equipement> selectAllEquipements() {
        ArrayList<Equipement> lesEquipements = new ArrayList<>();
        String requete = "SELECT * FROM equipement;";
        try {
            uneBdd.seConnecter();
            Statement unStat = uneBdd.getMaConnexion().createStatement();
            ResultSet unRes = unStat.executeQuery(requete);
            while (unRes.next()) {
                lesEquipements.add(new Equipement(unRes.getInt("IdEquipement"), unRes.getString("LibelleEquipement"), 
                    unRes.getDate("DateAjoutEquipement"), unRes.getInt("IdCentre"), unRes.getInt("IdClient")));
            }
            unStat.close(); unRes.close(); uneBdd.seDeconnecter();
        } catch (SQLException exp) { System.out.println("Erreur SELECT Equipements : " + exp.getMessage()); }
        return lesEquipements;
    }

    public static void insertEquipement(Equipement unEquipement) {
        String requete = "INSERT INTO equipement (LibelleEquipement, DateAjoutEquipement, IdCentre, IdClient) VALUES (?, CURDATE(), ?, ?);";
        try {
            uneBdd.seConnecter();
            PreparedStatement unStat = uneBdd.getMaConnexion().prepareStatement(requete);
            unStat.setString(1, unEquipement.getNomEquipement());
            unStat.setInt(2, unEquipement.getIdCentre());
            unStat.setInt(3, unEquipement.getIdProprietaire());
            unStat.executeUpdate(); 
            unStat.close(); uneBdd.seDeconnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur INSERT Equipement : " + exp.getMessage());
        }
    }

    public static void updateEquipement(Equipement unEquipement) {
        String requete = "UPDATE equipement SET LibelleEquipement = ?, IdCentre = ?, IdClient = ? WHERE IdEquipement = ?;";
        try {
            uneBdd.seConnecter();
            PreparedStatement unStat = uneBdd.getMaConnexion().prepareStatement(requete);
            unStat.setString(1, unEquipement.getNomEquipement());
            unStat.setInt(2, unEquipement.getIdCentre());
            unStat.setInt(3, unEquipement.getIdProprietaire());
            unStat.setInt(4, unEquipement.getIdEquipement());
            unStat.executeUpdate(); 
            unStat.close(); uneBdd.seDeconnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur UPDATE Equipement : " + exp.getMessage());
        }
    }

    public static void deleteEquipement(int idEquipement) {
        String requete = "DELETE FROM equipement WHERE IdEquipement = ?;";
        try {
            uneBdd.seConnecter();
            PreparedStatement unStat = uneBdd.getMaConnexion().prepareStatement(requete);
            unStat.setInt(1, idEquipement);
            unStat.executeUpdate(); 
            unStat.close(); uneBdd.seDeconnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur DELETE Equipement : " + exp.getMessage());
        }
    }
}