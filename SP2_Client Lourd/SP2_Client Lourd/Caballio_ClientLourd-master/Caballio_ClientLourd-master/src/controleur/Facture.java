package controleur;
import java.sql.Date;

public class Facture {
    private int idFacture, idClient, idCheval, idTL, idCentre;
    private float montantTotal;
    private Date dateBail, dateAjoutFacture;

    public Facture(int idFacture, Date dateBail, float montantTotal, Date dateAjoutFacture, int idClient, int idCheval, int idTL, int idCentre) {
        this.idFacture = idFacture; this.dateBail = dateBail; this.montantTotal = montantTotal;
        this.dateAjoutFacture = dateAjoutFacture; this.idClient = idClient; this.idCheval = idCheval; 
        this.idTL = idTL; this.idCentre = idCentre;
    }

    public int getIdFacture() { return idFacture; }
    public Date getDateBail() { return dateBail; }
    public float getMontantTotal() { return montantTotal; }
    public Date getDateAjoutFacture() { return dateAjoutFacture; }
    public int getIdClient() { return idClient; }
    public int getIdCheval() { return idCheval; }
    public int getIdTL() { return idTL; }
    public int getIdCentre() { return idCentre; }
}