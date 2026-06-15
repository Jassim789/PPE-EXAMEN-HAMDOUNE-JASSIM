package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controleur.Client;

public class VueFormulaireClient extends JDialog implements ActionListener {

    // --- Les champs de saisie ---
    private JTextField txtNom = new JTextField();
    private JTextField txtPrenom = new JTextField();
    private JTextField txtAdresse = new JTextField();
    private JTextField txtCodePostal = new JTextField();
    private JTextField txtVille = new JTextField();
    private JTextField txtTelephone = new JTextField();

    // --- Les boutons ---
    private JButton btValider = new JButton("Valider");
    private JButton btAnnuler = new JButton("Annuler");

    // --- Variables de contrôle ---
    private Client clientSaisi = null; 
    private boolean estValide = false;

    // Le constructeur reçoit le "parent" (VuePrincipale), le titre, et potentiellement un client à modifier
    public VueFormulaireClient(JFrame parent, String titre, Client unClientAModifier) {
        super(parent, titre, true); // true = Modal (bloque l'application derrière tant que ce n'est pas fermé)
        this.setSize(400, 350);
        this.setLocationRelativeTo(parent); // Centre la pop-up par rapport à la fenêtre principale
        this.setResizable(false);
        this.setLayout(new BorderLayout());

        // --- Zone de Titre ---
        JLabel lblTitre = new JLabel(titre, JLabel.CENTER);
        lblTitre.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitre.setForeground(new Color(34, 100, 50)); // Le vert de Caballio
        lblTitre.setBorder(new EmptyBorder(10, 0, 10, 0));
        this.add(lblTitre, BorderLayout.NORTH);

        // --- Zone du Formulaire (Grille 6 lignes, 2 colonnes) ---
        JPanel panelForm = new JPanel(new GridLayout(6, 2, 10, 10));
        panelForm.setBorder(new EmptyBorder(10, 20, 10, 20));
        
        panelForm.add(new JLabel("Nom :")); panelForm.add(txtNom);
        panelForm.add(new JLabel("Prénom :")); panelForm.add(txtPrenom);
        panelForm.add(new JLabel("Adresse :")); panelForm.add(txtAdresse);
        panelForm.add(new JLabel("Code Postal :")); panelForm.add(txtCodePostal);
        panelForm.add(new JLabel("Ville :")); panelForm.add(txtVille);
        panelForm.add(new JLabel("Téléphone :")); panelForm.add(txtTelephone);

        this.add(panelForm, BorderLayout.CENTER);

        // --- Zone des Boutons ---
        JPanel panelBoutons = new JPanel();
        btValider.addActionListener(this);
        btAnnuler.addActionListener(this);
        panelBoutons.add(btAnnuler);
        panelBoutons.add(btValider);
        this.add(panelBoutons, BorderLayout.SOUTH);

        // --- Pré-remplissage (Si on a cliqué sur "Modifier") ---
        if (unClientAModifier != null) {
            txtNom.setText(unClientAModifier.getNom());
            txtPrenom.setText(unClientAModifier.getPrenom());
            txtAdresse.setText(unClientAModifier.getAdresse());
            txtCodePostal.setText(unClientAModifier.getCodePostal());
            txtVille.setText(unClientAModifier.getVille());
            txtTelephone.setText(unClientAModifier.getTelephone());
        }

        // Affiche la fenêtre (le code se met en pause ici tant que l'utilisateur n'a pas fermé la pop-up)
        this.setVisible(true); 
    }

    // --- Gestion des Clics ---
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btAnnuler) {
            this.dispose(); // On détruit la fenêtre
        } 
        else if (e.getSource() == btValider) {
            // CONTRÔLE DE SAISIE : On vérifie que les champs obligatoires ne sont pas vides
            if (txtNom.getText().trim().isEmpty() || txtPrenom.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir au moins le nom et le prénom.", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
            } else {
                // On fabrique un objet Client avec ce qui a été tapé
                this.clientSaisi = new Client(
                    0, // ID factice, MySQL s'en chargera
                    txtNom.getText(),
                    txtPrenom.getText(),
                    txtAdresse.getText(),
                    txtCodePostal.getText(),
                    txtTelephone.getText(),
                    txtVille.getText(),
                    null // Date factice, MySQL fera un CURDATE()
                );
                this.estValide = true;
                this.dispose(); // On ferme la fenêtre
            }
        }
    }

    // --- Méthodes pour récupérer les données depuis la VuePrincipale ---
    public boolean isValide() {
        return estValide;
    }

    public Client getClientSaisi() {
        return clientSaisi;
    }
}