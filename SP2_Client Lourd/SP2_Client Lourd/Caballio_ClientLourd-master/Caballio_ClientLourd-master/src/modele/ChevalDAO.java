package modele;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import controleur.Cheval;

public class ChevalDAO {
    
    private static BDD uneBdd = new BDD();

    // =========================================================
    // FILTRE DE SÉCURITÉ POUR L'ENUM MYSQL ('M' ou 'F')
    // =========================================================
    private static String formaterSexe(String sexeSaisi) {
        if (sexeSaisi != null) {
            // Si le mot commence par F ou f (ex: "Femelle", "F")
            if (sexeSaisi.toUpperCase().startsWith("F")) {
                return "F";
            }
        }
        // Par défaut, on renvoie "M" (pour "Mâle", "M", etc.)
        return "M";
    }

    public static ArrayList<Cheval> selectAllChevaux() {
        ArrayList<Cheval> lesChevaux = new ArrayList<>();
        String requete = "SELECT * FROM cheval;";
        try {
            uneBdd.seConnecter();
            Statement unStat = uneBdd.getMaConnexion().createStatement();
            ResultSet unRes = unStat.executeQuery(requete);
            while (unRes.next()) {
                lesChevaux.add(new Cheval(unRes.getInt("IdCheval"), unRes.getString("NomCheval"), 
                    unRes.getString("SexeCheval"), unRes.getString("RaceCheval"), 
                    unRes.getDate("DateAjoutCheval"), unRes.getInt("IdCentre"), unRes.getInt("IdClient")));
            }
            unStat.close(); unRes.close(); uneBdd.seDeconnecter();
        } catch (SQLException exp) { System.out.println("Erreur SELECT Chevaux : " + exp.getMessage()); }
        return lesChevaux;
    }

    public static void insertCheval(Cheval unCheval) {
        String requete = "INSERT INTO cheval (NomCheval, SexeCheval, RaceCheval, IdCentre, IdClient) VALUES (?, ?, ?, ?, ?);";
        try {
            uneBdd.seConnecter();
            PreparedStatement unStat = uneBdd.getMaConnexion().prepareStatement(requete);
            unStat.setString(1, unCheval.getNom());
            
            // UTILISATION DU FILTRE ICI
            unStat.setString(2, formaterSexe(unCheval.getSexe()));
            
            unStat.setString(3, unCheval.getRace());
            unStat.setInt(4, unCheval.getIdCentre()); 
            unStat.setInt(5, unCheval.getIdClient()); 
            
            unStat.executeUpdate(); 
            unStat.close(); uneBdd.seDeconnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur INSERT Cheval : " + exp.getMessage());
        }
    }

    public static void updateCheval(Cheval unCheval) {
        String requete = "UPDATE cheval SET NomCheval = ?, SexeCheval = ?, RaceCheval = ?, IdCentre = ?, IdClient = ? WHERE IdCheval = ?;";
        try {
            uneBdd.seConnecter();
            PreparedStatement unStat = uneBdd.getMaConnexion().prepareStatement(requete);
            unStat.setString(1, unCheval.getNom());
            
            // UTILISATION DU FILTRE ICI
            unStat.setString(2, formaterSexe(unCheval.getSexe()));
            
            unStat.setString(3, unCheval.getRace());
            unStat.setInt(4, unCheval.getIdCentre());
            unStat.setInt(5, unCheval.getIdClient());
            unStat.setInt(6, unCheval.getIdCheval()); 
            
            unStat.executeUpdate(); 
            unStat.close(); uneBdd.seDeconnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur UPDATE Cheval : " + exp.getMessage());
        }
    }

    public static void deleteCheval(int idCheval) {
        String requete = "DELETE FROM cheval WHERE IdCheval = ?;";
        try {
            uneBdd.seConnecter();
            PreparedStatement unStat = uneBdd.getMaConnexion().prepareStatement(requete);
            unStat.setInt(1, idCheval);
            unStat.executeUpdate(); 
            unStat.close(); uneBdd.seDeconnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur DELETE Cheval : " + exp.getMessage());
        }
    }
}