package controleur;
import java.sql.Date;

public class Centre {
    private int idCentre, idUtilisateur;
    private String nomCentre, adresseCentre, codePostalCentre, villeCentre, telephoneCentre;
    private Date dateAjoutCentre;

    public Centre(int idCentre, String nomCentre, String adresseCentre, String codePostalCentre, 
                  String villeCentre, String telephoneCentre, Date dateAjoutCentre, int idUtilisateur) {
        this.idCentre = idCentre; this.nomCentre = nomCentre; this.adresseCentre = adresseCentre;
        this.codePostalCentre = codePostalCentre; this.villeCentre = villeCentre; 
        this.telephoneCentre = telephoneCentre; this.dateAjoutCentre = dateAjoutCentre; this.idUtilisateur = idUtilisateur;
    }

    public Centre(String nomCentre, String adresseCentre, String codePostalCentre, String villeCentre, String telephoneCentre, int idUtilisateur) {
        this.idCentre = 0; this.nomCentre = nomCentre; this.adresseCentre = adresseCentre;
        this.codePostalCentre = codePostalCentre; this.villeCentre = villeCentre; 
        this.telephoneCentre = telephoneCentre; this.idUtilisateur = idUtilisateur;
    }

    public int getIdCentre() { return idCentre; }
    public String getNomCentre() { return nomCentre; }
    public String getAdresseCentre() { return adresseCentre; }
    public String getCodePostalCentre() { return codePostalCentre; }
    public String getVilleCentre() { return villeCentre; }
    public String getTelephoneCentre() { return telephoneCentre; }
    public Date getDateAjoutCentre() { return dateAjoutCentre; }
    public int getIdUtilisateur() { return idUtilisateur; }
    
    // NOUVEAU SETTER
    public void setIdCentre(int idCentre) { this.idCentre = idCentre; }
}