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
import controleur.Client;
import controleur.Equipement;
import modele.CentreDAO;
import modele.ClientDAO;

public class VueFormulaireEquipement extends JDialog implements ActionListener {

    private JTextField txtNom = new JTextField();
    private JComboBox<String> cbCentre = new JComboBox<>();
    private JComboBox<String> cbClient = new JComboBox<>();

    private JButton btValider = new JButton("Valider");
    private JButton btAnnuler = new JButton("Annuler");

    private Equipement equipementSaisi = null; 
    private boolean estValide = false;

    public VueFormulaireEquipement(JFrame parent, String titre, Equipement unEquipementAModifier) {
        super(parent, titre, true);
        this.setSize(400, 250);
        this.setLocationRelativeTo(parent);
        this.setResizable(false);
        this.setLayout(new BorderLayout());

        JLabel lblTitre = new JLabel(titre, JLabel.CENTER);
        lblTitre.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitre.setForeground(new Color(34, 100, 50)); 
        lblTitre.setBorder(new EmptyBorder(10, 0, 10, 0));
        this.add(lblTitre, BorderLayout.NORTH);

        remplirListesDeroulantes();

        JPanel panelForm = new JPanel(new GridLayout(3, 2, 10, 10));
        panelForm.setBorder(new EmptyBorder(10, 20, 10, 20));
        
        panelForm.add(new JLabel("Nom de l'équipement :")); panelForm.add(txtNom);
        panelForm.add(new JLabel("Centre :")); panelForm.add(cbCentre);
        panelForm.add(new JLabel("Propriétaire :")); panelForm.add(cbClient);

        this.add(panelForm, BorderLayout.CENTER);

        JPanel panelBoutons = new JPanel();
        btValider.addActionListener(this);
        btAnnuler.addActionListener(this);
        panelBoutons.add(btAnnuler);
        panelBoutons.add(btValider);
        this.add(panelBoutons, BorderLayout.SOUTH);

        if (unEquipementAModifier != null) {
            txtNom.setText(unEquipementAModifier.getNomEquipement());
            
            for(int i=0; i<cbCentre.getItemCount(); i++) {
                if(cbCentre.getItemAt(i).startsWith(unEquipementAModifier.getIdCentre() + " -")) {
                    cbCentre.setSelectedIndex(i); break;
                }
            }
            for(int i=0; i<cbClient.getItemCount(); i++) {
                if(cbClient.getItemAt(i).startsWith(unEquipementAModifier.getIdProprietaire() + " -")) {
                    cbClient.setSelectedIndex(i); break;
                }
            }
        }

        this.setVisible(true); 
    }

    private void remplirListesDeroulantes() {
        ArrayList<Centre> lesCentres = CentreDAO.selectAllCentres();
        for (Centre c : lesCentres) { cbCentre.addItem(c.getIdCentre() + " - " + c.getNomCentre()); }
        
        ArrayList<Client> lesClients = ClientDAO.selectAllClients();
        for (Client cli : lesClients) { cbClient.addItem(cli.getIdClient() + " - " + cli.getNom() + " " + cli.getPrenom()); }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btAnnuler) {
            this.dispose(); 
        } 
        else if (e.getSource() == btValider) {
            if (txtNom.getText().trim().isEmpty() || cbCentre.getItemCount() == 0 || cbClient.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir le nom et vérifier qu'il y a des centres/clients enregistrés.", "Erreur", JOptionPane.ERROR_MESSAGE);
            } else {
                int idCentreChoisi = Integer.parseInt(cbCentre.getSelectedItem().toString().split(" - ")[0]);
                int idClientChoisi = Integer.parseInt(cbClient.getSelectedItem().toString().split(" - ")[0]);

                this.equipementSaisi = new Equipement(
                    txtNom.getText(),
                    idCentreChoisi,
                    idClientChoisi
                );
                this.estValide = true;
                this.dispose(); 
            }
        }
    }

    public boolean isValide() { return estValide; }
    public Equipement getEquipementSaisi() { return equipementSaisi; }
}