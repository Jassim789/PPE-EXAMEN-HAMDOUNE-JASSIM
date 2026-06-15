package controleur;
import java.sql.Date;

public class Equipement {
    private int idEquipement, idCentre, idProprietaire;
    private String nomEquipement;
    private Date dateAjoutEquipement;

    // Constructeur complet (Lecture depuis la BDD)
    public Equipement(int idEquipement, String nomEquipement, Date dateAjoutEquipement, int idCentre, int idProprietaire) {
        this.idEquipement = idEquipement; 
        this.nomEquipement = nomEquipement; 
        this.dateAjoutEquipement = dateAjoutEquipement; 
        this.idCentre = idCentre; 
        this.idProprietaire = idProprietaire;
    }

    // Constructeur pour l'ajout (Sans ID et sans Date générés par MySQL)
    public Equipement(String nomEquipement, int idCentre, int idProprietaire) {
        this.idEquipement = 0; 
        this.nomEquipement = nomEquipement; 
        this.idCentre = idCentre; 
        this.idProprietaire = idProprietaire;
        this.dateAjoutEquipement = null;
    }

    public int getIdEquipement() { return idEquipement; }
    public String getNomEquipement() { return nomEquipement; }
    public Date getDateAjoutEquipement() { return dateAjoutEquipement; }
    public int getIdCentre() { return idCentre; }
    public int getIdProprietaire() { return idProprietaire; }

    // NOUVEAU SETTER
    public void setIdEquipement(int idEquipement) { this.idEquipement = idEquipement; }
}