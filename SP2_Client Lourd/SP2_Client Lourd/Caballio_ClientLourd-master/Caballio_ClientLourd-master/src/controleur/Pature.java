package controleur;
import java.sql.Date;

public class Pature {
    private int idPature, idCentre;
    private String nomPature;
    private float taillePature;
    private Date dateAjoutPature;

    public Pature(int idPature, String nomPature, float taillePature, Date dateAjoutPature, int idCentre) {
        this.idPature = idPature; this.nomPature = nomPature; this.taillePature = taillePature;
        this.dateAjoutPature = dateAjoutPature; this.idCentre = idCentre;
    }

    public int getIdPature() { return idPature; }
    public String getNomPature() { return nomPature; }
    public float getTaillePature() { return taillePature; }
    public Date getDateAjoutPature() { return dateAjoutPature; }
    public int getIdCentre() { return idCentre; }
}