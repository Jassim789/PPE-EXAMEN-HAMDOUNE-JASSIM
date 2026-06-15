package controleur;

public class Utilisateur {
    private int idUtilisateur;
    private String pseudonyme, mail, mdp, roles, prenom, nom, sexe, adresse, ville, codePostal;
    private boolean activite, bloque, expire;

    public Utilisateur(int idUtilisateur, String pseudonyme, String mail, String mdp, boolean activite, 
                       boolean bloque, String roles, boolean expire, String prenom, 
                       String nom, String sexe, String adresse, String ville, String codePostal) {
        this.idUtilisateur = idUtilisateur; this.pseudonyme = pseudonyme; this.mail = mail; this.mdp = mdp;
        this.activite = activite; this.bloque = bloque; this.roles = roles; this.expire = expire;
        this.prenom = prenom; this.nom = nom; this.sexe = sexe; this.adresse = adresse;
        this.ville = ville; this.codePostal = codePostal;
    }

    public int getIdUtilisateur() { return idUtilisateur; }
    public String getPseudonyme() { return pseudonyme; }
    public String getMail() { return mail; }
    public String getMdp() { return mdp; }
    public boolean isActivite() { return activite; }
    public boolean isBloque() { return bloque; }
    public String getRoles() { return roles; }
    public boolean isExpire() { return expire; }
    public String getPrenom() { return prenom; }
    public String getNom() { return nom; }
    public String getSexe() { return sexe; }
    public String getAdresse() { return adresse; }
    public String getVille() { return ville; }
    public String getCodePostal() { return codePostal; }
}