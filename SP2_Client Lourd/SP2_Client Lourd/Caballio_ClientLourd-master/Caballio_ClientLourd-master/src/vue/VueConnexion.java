package vue;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controleur.Utilisateur;

public class VueConnexion extends JFrame implements ActionListener {
    
    private JPanel panelForm = new JPanel();
    private JTextField txtEmail = new JTextField();
    private JPasswordField txtMdp = new JPasswordField();
    private JButton btAnnuler = new JButton("Annuler");
    private JButton btValider = new JButton("Se connecter");
    
    // Couleurs de la charte graphique Caballio
    private Color vertFonce = new Color(34, 100, 50);
    private Color blanc = Color.WHITE;
    
    public VueConnexion() {
        this.setTitle("Caballio - Authentification");
        this.setBounds(500, 200, 350, 450); // Positionné plus au centre
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.getContentPane().setBackground(blanc);
        this.setLayout(null);
        
        // Titre provisoire (en attendant de remettre le logo.png)
        JLabel lblTitre = new JLabel("CABALLIO", SwingConstants.CENTER);
        lblTitre.setFont(new Font("Arial", Font.BOLD, 30));
        lblTitre.setForeground(vertFonce);
        lblTitre.setBounds(30, 40, 280, 50);
        this.add(lblTitre);
        
        JLabel lblSousTitre = new JLabel("Gestion du Centre Équestre", SwingConstants.CENTER);
        lblSousTitre.setForeground(vertFonce);
        lblSousTitre.setBounds(30, 80, 280, 30);
        this.add(lblSousTitre);
        
        // Placement du formulaire
        this.panelForm.setBounds(50, 150, 240, 150);
        this.panelForm.setBackground(blanc);
        this.panelForm.setLayout(new GridLayout(3, 2, 10, 10));
        
        JLabel lblEmail = new JLabel("Email :");
        lblEmail.setForeground(vertFonce);
        this.panelForm.add(lblEmail);
        this.panelForm.add(this.txtEmail);
        
        JLabel lblMdp = new JLabel("Mot de passe : ");
        lblMdp.setForeground(vertFonce);
        this.panelForm.add(lblMdp);
        this.panelForm.add(this.txtMdp);
        
        this.btAnnuler.setBackground(vertFonce);
        this.btAnnuler.setForeground(blanc);
        this.panelForm.add(this.btAnnuler);
        
        this.btValider.setBackground(vertFonce);
        this.btValider.setForeground(blanc);
        this.panelForm.add(this.btValider);
        
        this.add(this.panelForm);
        
        // Rendre les boutons écoutables (cliquables)
        this.btAnnuler.addActionListener(this);
        this.btValider.addActionListener(this);
        
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btAnnuler) {
            this.txtEmail.setText("");
            this.txtMdp.setText("");
        } else if (e.getSource() == this.btValider) {
            this.traitement();
        }
    }
    
    public void traitement() {
        String email = this.txtEmail.getText();
        String mdp = new String(this.txtMdp.getPassword());
        
        if (email.equals("") || mdp.equals("")) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);
        } else {
            // Appel au Modele pour vérifier en base de données
        	Utilisateur unUtilisateur = modele.UtilisateurDAO.verifierConnexion(email, mdp);
            
            if (unUtilisateur != null) {
                this.dispose(); // Ferme la petite fenêtre de connexion
                new VuePrincipale(); // Ouvre la grande fenêtre du tableau de bord
            } else {
                JOptionPane.showMessageDialog(this, "Identifiants incorrects ou utilisateur bloqué.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}