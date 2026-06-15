package controleur;
import java.sql.Date;

public class TypeLogement {
    private int idTL, idCentre;
    private String nomTL;
    private float tailleTL, prixTL;
    private Date dateAjoutTL;

    public TypeLogement(int idTL, String nomTL, float tailleTL, float prixTL, Date dateAjoutTL, int idCentre) {
        this.idTL = idTL; this.nomTL = nomTL; this.tailleTL = tailleTL; this.prixTL = prixTL;
        this.dateAjoutTL = dateAjoutTL; this.idCentre = idCentre;
    }

    public int getIdTL() { return idTL; }
    public String getNomTL() { return nomTL; }
    public float getTailleTL() { return tailleTL; }
    public float getPrixTL() { return prixTL; }
    public Date getDateAjoutTL() { return dateAjoutTL; }
    public int getIdCentre() { return idCentre; }
}