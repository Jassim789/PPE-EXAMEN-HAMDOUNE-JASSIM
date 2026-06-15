package controleur;

public class Aliment {
    private int idAliment, idCentre;
    private String nomAliment;

    public Aliment(int idAliment, String nomAliment, int idCentre) {
        this.idAliment = idAliment; this.nomAliment = nomAliment; this.idCentre = idCentre;
    }

    public Aliment(String nomAliment, int idCentre) {
        this.idAliment = 0; this.nomAliment = nomAliment; this.idCentre = idCentre;
    }

    public int getIdAliment() { return idAliment; }
    public String getNomAliment() { return nomAliment; }
    public int getIdCentre() { return idCentre; }
}