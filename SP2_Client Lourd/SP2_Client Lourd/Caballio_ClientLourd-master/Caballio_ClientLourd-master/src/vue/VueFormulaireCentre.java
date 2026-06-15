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

import controleur.Centre;

public class VueFormulaireCentre extends JDialog implements ActionListener {

    private JTextField txtNom = new JTextField();
    private JTextField txtAdresse = new JTextField();
    private JTextField txtCodePostal = new JTextField();
    private JTextField txtVille = new JTextField();
    private JTextField txtTelephone = new JTextField();
    private JTextField txtIdUtilisateur = new JTextField("1");

    private JButton btValider = new JButton("Valider");
    private JButton btAnnuler = new JButton("Annuler");

    private Centre centreSaisi = null; 
    private boolean estValide = false;

    public VueFormulaireCentre(JFrame parent, String titre, Centre unCentreAModifier) {
        super(parent, titre, true);
        this.setSize(400, 350);
        this.setLocationRelativeTo(parent);
        this.setResizable(false);
        this.setLayout(new BorderLayout());

        JLabel lblTitre = new JLabel(titre, JLabel.CENTER);
        lblTitre.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitre.setForeground(new Color(34, 100, 50)); 
        lblTitre.setBorder(new EmptyBorder(10, 0, 10, 0));
        this.add(lblTitre, BorderLayout.NORTH);

        JPanel panelForm = new JPanel(new GridLayout(6, 2, 10, 10));
        panelForm.setBorder(new EmptyBorder(10, 20, 10, 20));
        
        panelForm.add(new JLabel("Nom du Centre :")); panelForm.add(txtNom);
        panelForm.add(new JLabel("Adresse :")); panelForm.add(txtAdresse);
        panelForm.add(new JLabel("Code Postal :")); panelForm.add(txtCodePostal);
        panelForm.add(new JLabel("Ville :")); panelForm.add(txtVille);
        panelForm.add(new JLabel("Téléphone :")); panelForm.add(txtTelephone);
        panelForm.add(new JLabel("ID Gérant :")); panelForm.add(txtIdUtilisateur);

        this.add(panelForm, BorderLayout.CENTER);

        JPanel panelBoutons = new JPanel();
        btValider.addActionListener(this);
        btAnnuler.addActionListener(this);
        panelBoutons.add(btAnnuler);
        panelBoutons.add(btValider);
        this.add(panelBoutons, BorderLayout.SOUTH);

        if (unCentreAModifier != null) {
            txtNom.setText(unCentreAModifier.getNomCentre());
            txtAdresse.setText(unCentreAModifier.getAdresseCentre());
            txtCodePostal.setText(unCentreAModifier.getCodePostalCentre());
            txtVille.setText(unCentreAModifier.getVilleCentre());
            txtTelephone.setText(unCentreAModifier.getTelephoneCentre());
            txtIdUtilisateur.setText(String.valueOf(unCentreAModifier.getIdUtilisateur()));
        }

        this.setVisible(true); 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btAnnuler) {
            this.dispose(); 
        } 
        else if (e.getSource() == btValider) {
            if (txtNom.getText().trim().isEmpty() || txtVille.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir au moins le nom et la ville.", "Erreur", JOptionPane.ERROR_MESSAGE);
            } else {
                int idUser = 1;
                try {
                    idUser = Integer.parseInt(txtIdUtilisateur.getText());
                } catch (NumberFormatException ex) {
                    idUser = 1; 
                }
                
                this.centreSaisi = new Centre(
                    0, 
                    txtNom.getText(),
                    txtAdresse.getText(),
                    txtCodePostal.getText(),
                    txtVille.getText(),
                    txtTelephone.getText(),
                    null, 
                    idUser
                );
                this.estValide = true;
                this.dispose(); 
            }
        }
    }

    public boolean isValide() { return estValide; }
    public Centre getCentreSaisi() { return centreSaisi; }
}