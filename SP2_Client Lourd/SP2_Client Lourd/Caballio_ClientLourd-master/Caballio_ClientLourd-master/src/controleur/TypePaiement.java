package controleur;

public class TypePaiement {
    private int idTP;
    private String nomTP;

    public TypePaiement(int idTP, String nomTP) {
        this.idTP = idTP; this.nomTP = nomTP;
    }

    public int getIdTP() { return idTP; }
    public String getNomTP() { return nomTP; }
}