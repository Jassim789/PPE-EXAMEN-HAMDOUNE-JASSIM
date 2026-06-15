package controleur;
import java.sql.Date;

public class Client {
    private int idClient;
    private String nom, prenom, adresse, codePostal, telephone, ville;
    private Date dateAjout; 

    public Client(int idClient, String nom, String prenom, String adresse, String codePostal, String telephone, String ville, Date dateAjout) {
        this.idClient = idClient; this.nom = nom; this.prenom = prenom; this.adresse = adresse;
        this.codePostal = codePostal; this.telephone = telephone; this.ville = ville; this.dateAjout = dateAjout;
    }

    public Client(String nom, String prenom, String adresse, String codePostal, String telephone, String ville) {
        this.idClient = 0; this.nom = nom; this.prenom = prenom; this.adresse = adresse;
        this.codePostal = codePostal; this.telephone = telephone; this.ville = ville;
    }

    public int getIdClient() { return idClient; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getAdresse() { return adresse; }
    public String getCodePostal() { return codePostal; }
    public String getTelephone() { return telephone; }
    public String getVille() { return ville; }
    public Date getDateAjout() { return dateAjout; }
    
    public void setIdClient(int unId) {
        this.idClient = unId;
    }
}