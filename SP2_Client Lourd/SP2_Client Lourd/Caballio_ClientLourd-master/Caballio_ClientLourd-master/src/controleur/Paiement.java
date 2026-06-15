package controleur;
import java.sql.Date;

public class Paiement {
    private int idPaiement, idFacture, idTP;
    private float montant;
    private Date datePaiement, dateEncaissement;

    public Paiement(int idPaiement, Date datePaiement, Date dateEncaissement, float montant, int idFacture, int idTP) {
        this.idPaiement = idPaiement; this.datePaiement = datePaiement; this.dateEncaissement = dateEncaissement;
        this.montant = montant; this.idFacture = idFacture; this.idTP = idTP;
    }

    public int getIdPaiement() { return idPaiement; }
    public Date getDatePaiement() { return datePaiement; }
    public Date getDateEncaissement() { return dateEncaissement; }
    public float getMontant() { return montant; }
    public int getIdFacture() { return idFacture; }
    public int getIdTP() { return idTP; }
}