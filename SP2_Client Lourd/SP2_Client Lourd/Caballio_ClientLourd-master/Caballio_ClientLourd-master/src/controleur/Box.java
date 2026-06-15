package controleur;
import java.sql.Date;

public class Box {
    private int idBox, numBox, idCheval, idTL, idCentre;
    private Date dateAjoutBox;

    public Box(int idBox, int numBox, Date dateAjoutBox, int idCheval, int idTL, int idCentre) {
        this.idBox = idBox; this.numBox = numBox; this.dateAjoutBox = dateAjoutBox;
        this.idCheval = idCheval; this.idTL = idTL; this.idCentre = idCentre;
    }

    public int getIdBox() { return idBox; }
    public int getNumBox() { return numBox; }
    public Date getDateAjoutBox() { return dateAjoutBox; }
    public int getIdCheval() { return idCheval; }
    public int getIdTL() { return idTL; }
    public int getIdCentre() { return idCentre; }
}