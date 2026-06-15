package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controleur.Centre;
import controleur.Cheval;
import controleur.Client;
import modele.CentreDAO;
import modele.ClientDAO;

public class VueFormulaireCheval extends JDialog implements ActionListener {

    // --- Champs de saisie ---
    private JTextField txtNom = new JTextField();
    private JTextField txtRace = new JTextField();
    
    // --- Listes déroulantes (JComboBox) ---
    private String[] sexes = {"Mâle", "Femelle"};
    private JComboBox<String> cbSexe = new JComboBox<>(sexes);
    private JComboBox<String> cbCentre = new JComboBox<>();
    private JComboBox<String> cbClient = new JComboBox<>();

    // --- Boutons ---
    private JButton btValider = new JButton("Valider");
    private JButton btAnnuler = new JButton("Annuler");

    private Cheval chevalSaisi = null; 
    private boolean estValide = false;

    public VueFormulaireCheval(JFrame parent, String titre, Cheval unChevalAModifier) {
        super(parent, titre, true); 
        this.setSize(450, 350);
        this.setLocationRelativeTo(parent);
        this.setResizable(false);
        this.setLayout(new BorderLayout());

        // --- Titre ---
        JLabel lblTitre = new JLabel(titre, JLabel.CENTER);
        lblTitre.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitre.setForeground(new Color(34, 100, 50)); 
        lblTitre.setBorder(new EmptyBorder(10, 0, 10, 0));
        this.add(lblTitre, BorderLayout.NORTH);

        // --- Remplissage automatique des listes déroulantes via la BDD ---
        remplirListesDeroulantes();

        // --- Formulaire ---
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 10, 10));
        panelForm.setBorder(new EmptyBorder(10, 20, 10, 20));
        
        panelForm.add(new JLabel("Nom du cheval :")); panelForm.add(txtNom);
        panelForm.add(new JLabel("Sexe :")); panelForm.add(cbSexe);
        panelForm.add(new JLabel("Race :")); panelForm.add(txtRace);
        panelForm.add(new JLabel("Affecté au Centre :")); panelForm.add(cbCentre);
        panelForm.add(new JLabel("Propriétaire (Client) :")); panelForm.add(cbClient);

        this.add(panelForm, BorderLayout.CENTER);

        // --- Boutons ---
        JPanel panelBoutons = new JPanel();
        btValider.addActionListener(this);
        btAnnuler.addActionListener(this);
        panelBoutons.add(btAnnuler);
        panelBoutons.add(btValider);
        this.add(panelBoutons, BorderLayout.SOUTH);

        // --- Pré-remplissage pour la Modification ---
        if (unChevalAModifier != null) {
            txtNom.setText(unChevalAModifier.getNom());
            cbSexe.setSelectedItem(unChevalAModifier.getSexe());
            txtRace.setText(unChevalAModifier.getRace());
            
            // On cherche le bon centre dans la liste pour le sélectionner
            for(int i=0; i<cbCentre.getItemCount(); i++) {
                if(cbCentre.getItemAt(i).startsWith(unChevalAModifier.getIdCentre() + " -")) {
                    cbCentre.setSelectedIndex(i); break;
                }
            }
            // Pareil pour le client
            for(int i=0; i<cbClient.getItemCount(); i++) {
                if(cbClient.getItemAt(i).startsWith(unChevalAModifier.getIdClient() + " -")) {
                    cbClient.setSelectedIndex(i); break;
                }
            }
        }
        this.setVisible(true); 
    }

    private void remplirListesDeroulantes() {
        // Remplissage des centres
        ArrayList<Centre> lesCentres = CentreDAO.selectAllCentres();
        for (Centre c : lesCentres) {
            cbCentre.addItem(c.getIdCentre() + " - " + c.getNomCentre());
        }
        
        // Remplissage des clients
        ArrayList<Client> lesClients = ClientDAO.selectAllClients();
        for (Client cli : lesClients) {
            cbClient.addItem(cli.getIdClient() + " - " + cli.getNom() + " " + cli.getPrenom());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btAnnuler) {
            this.dispose(); 
        } 
        else if (e.getSource() == btValider) {
            if (txtNom.getText().trim().isEmpty() || txtRace.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir le nom et la race.", "Erreur", JOptionPane.ERROR_MESSAGE);
            } else {
                // L'ASTUCE : On récupère l'ID caché dans le texte de la liste déroulante (ex: "5 - Dupont Jean" devient "5")
                String choixCentre = cbCentre.getSelectedItem().toString();
                int idCentreChoisi = Integer.parseInt(choixCentre.split(" - ")[0]);
                
                String choixClient = cbClient.getSelectedItem().toString();
                int idClientChoisi = Integer.parseInt(choixClient.split(" - ")[0]);

                this.chevalSaisi = new Cheval(
                    0, 
                    txtNom.getText(),
                    cbSexe.getSelectedItem().toString(),
                    txtRace.getText(),
                    idCentreChoisi,
                    idClientChoisi
                );
                this.estValide = true;
                this.dispose(); 
            }
        }
    }

    public boolean isValide() { return estValide; }
    public Cheval getChevalSaisi() { return chevalSaisi; }
}