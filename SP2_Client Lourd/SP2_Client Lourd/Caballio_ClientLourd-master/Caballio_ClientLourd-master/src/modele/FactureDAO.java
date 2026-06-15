package modele;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import controleur.Facture;

public class FactureDAO {
    private static BDD uneBdd = new BDD();

    public static ArrayList<Facture> selectAllFactures() {
        ArrayList<Facture> lesFactures = new ArrayList<>();
        String requete = "SELECT * FROM facture;";
        try {
            uneBdd.seConnecter();
            Statement unStat = uneBdd.getMaConnexion().createStatement();
            ResultSet unRes = unStat.executeQuery(requete);
            while (unRes.next()) {
                lesFactures.add(new Facture(unRes.getInt("IdFacture"), unRes.getDate("DateBail"), 
                    unRes.getFloat("MontantTotal"), unRes.getDate("DateAjoutFacture"), 
                    unRes.getInt("IdClient"), unRes.getInt("IdCheval"), unRes.getInt("IdTypeLogement"), unRes.getInt("IdCentre")));
            }
            unStat.close(); unRes.close(); uneBdd.seDeconnecter();
        } catch (SQLException exp) { System.out.println("Erreur SELECT Factures : " + exp.getMessage()); }
        return lesFactures;
    }
}