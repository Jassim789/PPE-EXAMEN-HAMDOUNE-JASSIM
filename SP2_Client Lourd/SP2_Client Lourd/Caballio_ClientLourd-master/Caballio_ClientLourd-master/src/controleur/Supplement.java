package controleur;

public class Supplement {
    private int idSupplement, idFacture;
    private String nomSupplement;
    private float prixSupplement;

    public Supplement(int idSupplement, String nomSupplement, float prixSupplement, int idFacture) {
        this.idSupplement = idSupplement; this.nomSupplement = nomSupplement;
        this.prixSupplement = prixSupplement; this.idFacture = idFacture;
    }

    public int getIdSupplement() { return idSupplement; }
    public String getNomSupplement() { return nomSupplement; }
    public float getPrixSupplement() { return prixSupplement; }
    public int getIdFacture() { return idFacture; }
}