package modele;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import controleur.Centre;

public class CentreDAO {
    
    private static BDD uneBdd = new BDD();

    public static ArrayList<Centre> selectAllCentres() {
        ArrayList<Centre> lesCentres = new ArrayList<>();
        String requete = "SELECT * FROM centre;";
        try {
            uneBdd.seConnecter();
            Statement unStat = uneBdd.getMaConnexion().createStatement();
            ResultSet unRes = unStat.executeQuery(requete);
            while (unRes.next()) {
                lesCentres.add(new Centre(unRes.getInt("IdCentre"), unRes.getString("NomCentre"), 
                    unRes.getString("AdresseCentre"), unRes.getString("CPCentre"), 
                    unRes.getString("VilleCentre"), unRes.getString("TelCentre"), 
                    unRes.getDate("DateAjoutCentre"), unRes.getInt("IdGerant")));
            }
            unStat.close(); unRes.close(); uneBdd.seDeconnecter();
        } catch (SQLException exp) { System.out.println("Erreur SELECT Centres : " + exp.getMessage()); }
        return lesCentres;
    }

    public static void insertCentre(Centre unCentre) {
        String requete = "INSERT INTO centre (NomCentre, AdresseCentre, CPCentre, VilleCentre, TelCentre, DateAjoutCentre, IdGerant) VALUES (?, ?, ?, ?, ?, CURDATE(), ?);";
        try {
            uneBdd.seConnecter();
            PreparedStatement unStat = uneBdd.getMaConnexion().prepareStatement(requete);
            unStat.setString(1, unCentre.getNomCentre());
            unStat.setString(2, unCentre.getAdresseCentre());
            unStat.setString(3, unCentre.getCodePostalCentre());
            unStat.setString(4, unCentre.getVilleCentre());
            unStat.setString(5, unCentre.getTelephoneCentre());
            unStat.setInt(6, unCentre.getIdUtilisateur());
            unStat.executeUpdate(); 
            unStat.close(); uneBdd.seDeconnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur INSERT Centre : " + exp.getMessage());
        }
    }

    public static void updateCentre(Centre unCentre) {
        String requete = "UPDATE centre SET NomCentre = ?, AdresseCentre = ?, CPCentre = ?, VilleCentre = ?, TelCentre = ?, IdGerant = ? WHERE IdCentre = ?;";
        try {
            uneBdd.seConnecter();
            PreparedStatement unStat = uneBdd.getMaConnexion().prepareStatement(requete);
            unStat.setString(1, unCentre.getNomCentre());
            unStat.setString(2, unCentre.getAdresseCentre());
            unStat.setString(3, unCentre.getCodePostalCentre());
            unStat.setString(4, unCentre.getVilleCentre());
            unStat.setString(5, unCentre.getTelephoneCentre());
            unStat.setInt(6, unCentre.getIdUtilisateur());
            unStat.setInt(7, unCentre.getIdCentre());
            unStat.executeUpdate(); 
            unStat.close(); uneBdd.seDeconnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur UPDATE Centre : " + exp.getMessage());
        }
    }

    public static void deleteCentre(int idCentre) {
        String requete = "DELETE FROM centre WHERE IdCentre = ?;";
        try {
            uneBdd.seConnecter();
            PreparedStatement unStat = uneBdd.getMaConnexion().prepareStatement(requete);
            unStat.setInt(1, idCentre);
            unStat.executeUpdate(); 
            unStat.close(); uneBdd.seDeconnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur DELETE Centre : " + exp.getMessage());
        }
    }
}