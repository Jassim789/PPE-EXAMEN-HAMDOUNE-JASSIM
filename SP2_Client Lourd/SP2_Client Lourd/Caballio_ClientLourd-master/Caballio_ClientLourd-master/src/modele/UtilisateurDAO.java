package modele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt; 

import controleur.Utilisateur;

public class UtilisateurDAO {
    
    private static BDD uneBdd = new BDD();

    // ========================================================================
    // SÉCURITÉ : HACHAGE BCRYPT
    // ========================================================================
    public static String hacherMdp(String mdpAClair) {
        return BCrypt.hashpw(mdpAClair, BCrypt.gensalt());
    }
    
    // ========================================================================
    // VÉRIFICATION DE LA CONNEXION
    // ========================================================================
    public static Utilisateur verifierConnexion(String email, String mdp) {
        Utilisateur unUtilisateur = null;
        
        String requete = "SELECT * FROM utilisateur WHERE MailUtilisateur = ? AND BloqueUtilisateur = 0";
        
        try {
            uneBdd.seConnecter();
            Connection maConnexion = uneBdd.getMaConnexion();
            PreparedStatement unStat = maConnexion.prepareStatement(requete);
            unStat.setString(1, email);
            ResultSet unRes = unStat.executeQuery();
            
            if (unRes.next()) {
                // CORRECTION 1 : Utilisation du vrai nom de colonne de votre BDD
                String hashEnBdd = unRes.getString("MotDePasseUtilisateur");
                
                // CORRECTION 2 : Hack de compatibilité PHP ($2y$) vers Java ($2a$)
                String hashPourJava = hashEnBdd;
                if (hashEnBdd.startsWith("$2y$")) {
                    hashPourJava = hashEnBdd.replaceFirst("^\\$2y\\$", "\\$2a\\$");
                }
                
                // Comparaison avec BCrypt
                if (BCrypt.checkpw(mdp, hashPourJava)) {
                    
                    unUtilisateur = new Utilisateur(
                        unRes.getInt("IdUtilisateur"), unRes.getString("PseudonymeUtilisateur"),
                        unRes.getString("MailUtilisateur"), hashEnBdd,
                        unRes.getBoolean("ActiviteUtilisateur"), unRes.getBoolean("BloqueUtilisateur"), 
                        unRes.getString("RolesUtilisateur"), unRes.getBoolean("ExpireUtilisateur"), 
                        unRes.getString("PrenomUtilisateur"), unRes.getString("NomUtilisateur"), 
                        unRes.getString("SexeUtilisateur"), unRes.getString("AdresseUtilisateur"), 
                        unRes.getString("VilleUtilisateur"), unRes.getString("CodePostalUtilisateur")
                    );
                } else {
                    System.out.println("Échec : Mot de passe incorrect.");
                }
            } else {
                System.out.println("Échec : Email introuvable ou compte bloqué.");
            }
            unStat.close();
            unRes.close();
            uneBdd.seDeconnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur Connexion SQL : " + exp.getMessage());
        } catch (IllegalArgumentException exp) {
            System.out.println("Erreur de format BCrypt : " + exp.getMessage());
        }
        return unUtilisateur;
    }
}