package modele;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class BDD {
    private String serveur;
    private String bdd;
    private String user;
    private String mdp;
    private Connection maConnexion;

    public BDD() {
        this.chargerConfiguration();
        this.chargerPilote();
    }

    private void chargerConfiguration() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            props.load(fis);
            this.serveur = props.getProperty("db.serveur");
            this.bdd = props.getProperty("db.nom");
            this.user = props.getProperty("db.utilisateur");
            this.mdp = props.getProperty("db.motdepasse", ""); // Vide par défaut si non spécifié
        } catch (IOException ex) {
            System.out.println("Erreur : Impossible de lire le fichier config.properties. Utilisation des valeurs par défaut.");
            this.serveur = "localhost";
            this.bdd = "centre_equestre";
            this.user = "root";
            this.mdp = "";
        }
    }

    private void chargerPilote() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException exp) {
            System.out.println("Erreur de chargement du pilote JDBC");
        }
    }

    public void seConnecter() {
        String url = "jdbc:mysql://" + this.serveur + "/" + this.bdd + "?serverTimezone=UTC";
        try {
            this.maConnexion = DriverManager.getConnection(url, this.user, this.mdp);
        } catch (SQLException exp) {
            System.out.println("Erreur de connexion à la base de données : " + this.bdd);
        }
    }

    public void seDeconnecter() {
        try {
            if (this.maConnexion != null && !this.maConnexion.isClosed()) {
                this.maConnexion.close();
            }
        } catch (SQLException exp) {
            System.out.println("Erreur de fermeture de connexion");
        }
    }

    public Connection getMaConnexion() {
        return this.maConnexion;
    }
}