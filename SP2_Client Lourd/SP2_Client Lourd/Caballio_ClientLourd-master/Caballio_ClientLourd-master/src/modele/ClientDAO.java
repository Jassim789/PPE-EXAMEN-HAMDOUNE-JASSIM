package modele;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import controleur.Client;

public class ClientDAO {
    
    private static BDD uneBdd = new BDD();

    public static ArrayList<Client> selectAllClients() {
        ArrayList<Client> lesClients = new ArrayList<>();
        String requete = "SELECT * FROM client;";
        try {
            uneBdd.seConnecter();
            Statement unStat = uneBdd.getMaConnexion().createStatement();
            ResultSet unRes = unStat.executeQuery(requete);
            while (unRes.next()) {
                lesClients.add(new Client(unRes.getInt("IdClient"), unRes.getString("NomClient"), 
                    unRes.getString("PrenomClient"), unRes.getString("AdresseClient"), 
                    unRes.getString("CodePostalClient"), unRes.getString("TelClient"), 
                    unRes.getString("VilleClient"), unRes.getDate("DateAjoutClient")));
            }
            unStat.close(); unRes.close(); uneBdd.seDeconnecter();
        } catch (SQLException exp) { System.out.println("Erreur SELECT Clients : " + exp.getMessage()); }
        return lesClients;
    }

    public static void insertClient(Client unClient) {
        String requete = "INSERT INTO client (NomClient, PrenomClient, AdresseClient, CodePostalClient, TelClient, VilleClient, DateAjoutClient) VALUES (?, ?, ?, ?, ?, ?, CURDATE());";
        try {
            uneBdd.seConnecter();
            PreparedStatement unStat = uneBdd.getMaConnexion().prepareStatement(requete);
            unStat.setString(1, unClient.getNom());
            unStat.setString(2, unClient.getPrenom());
            unStat.setString(3, unClient.getAdresse());
            unStat.setString(4, unClient.getCodePostal());
            unStat.setString(5, unClient.getTelephone());
            unStat.setString(6, unClient.getVille());
            
            unStat.executeUpdate(); 
            unStat.close(); uneBdd.seDeconnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur INSERT Client : " + exp.getMessage());
        }
    }

    public static void updateClient(Client unClient) {
        String requete = "UPDATE client SET NomClient = ?, PrenomClient = ?, AdresseClient = ?, CodePostalClient = ?, TelClient = ?, VilleClient = ? WHERE IdClient = ?;";
        try {
            uneBdd.seConnecter();
            PreparedStatement unStat = uneBdd.getMaConnexion().prepareStatement(requete);
            unStat.setString(1, unClient.getNom());
            unStat.setString(2, unClient.getPrenom());
            unStat.setString(3, unClient.getAdresse());
            unStat.setString(4, unClient.getCodePostal());
            unStat.setString(5, unClient.getTelephone());
            unStat.setString(6, unClient.getVille());
            unStat.setInt(7, unClient.getIdClient()); 
            
            unStat.executeUpdate(); 
            unStat.close(); uneBdd.seDeconnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur UPDATE Client : " + exp.getMessage());
        }
    }

    public static void deleteClient(int idClient) {
        String requete = "DELETE FROM client WHERE IdClient = ?;";
        try {
            uneBdd.seConnecter();
            PreparedStatement unStat = uneBdd.getMaConnexion().prepareStatement(requete);
            unStat.setInt(1, idClient);
            unStat.executeUpdate(); 
            unStat.close(); uneBdd.seDeconnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur DELETE Client : " + exp.getMessage());
        }
    }
}