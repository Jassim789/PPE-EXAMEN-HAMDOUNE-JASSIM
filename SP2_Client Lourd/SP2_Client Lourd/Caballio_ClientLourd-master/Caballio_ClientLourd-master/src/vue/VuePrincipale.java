package vue;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import controleur.Centre;
import controleur.Cheval;
import controleur.Client;
import controleur.Equipement;
import controleur.Facture;
import modele.CentreDAO;
import modele.ChevalDAO;
import modele.ClientDAO;
import modele.EquipementDAO;
import modele.FactureDAO;

public class VuePrincipale extends JFrame implements ActionListener {

    private Color vertFonce = new Color(34, 100, 50);
    private Color blanc = Color.WHITE;

    private JPanel panelMenu = new JPanel();
    private JPanel panelContenu = new JPanel();
    private CardLayout affichageCartes = new CardLayout();

    private JButton btAccueil = new JButton("Accueil");
    private JButton btCentres = new JButton("Centres");
    private JButton btClients = new JButton("Clients");
    private JButton btChevaux = new JButton("Chevaux");
    private JButton btEquipements = new JButton("Équipements");
    private JButton btFactures = new JButton("Factures");
    private JButton btQuitter = new JButton("Se déconnecter");

    // --- VARIABLES CRUD CLIENT ---
    private JTable tableClients;
    private DefaultTableModel modeleTableClients;
    private JButton btAjouterClient = new JButton("+ Ajouter");
    private JButton btModifierClient = new JButton("✎ Modifier");
    private JButton btSupprimerClient = new JButton("🗑 Supprimer");

    // --- VARIABLES CRUD CHEVAL ---
    private JTable tableChevaux;
    private DefaultTableModel modeleTableChevaux;
    private JButton btAjouterCheval = new JButton("+ Ajouter");
    private JButton btModifierCheval = new JButton("✎ Modifier");
    private JButton btSupprimerCheval = new JButton("🗑 Supprimer");

    // --- VARIABLES CRUD CENTRE ---
    private JTable tableCentres;
    private DefaultTableModel modeleTableCentres;
    private JButton btAjouterCentre = new JButton("+ Ajouter");
    private JButton btModifierCentre = new JButton("✎ Modifier");
    private JButton btSupprimerCentre = new JButton("🗑 Supprimer");

    // --- NOUVEAU : VARIABLES CRUD EQUIPEMENT ---
    private JTable tableEquipements;
    private DefaultTableModel modeleTableEquipements;
    private JButton btAjouterEquipement = new JButton("+ Ajouter");
    private JButton btModifierEquipement = new JButton("✎ Modifier");
    private JButton btSupprimerEquipement = new JButton("🗑 Supprimer");

    public VuePrincipale() {
        this.setTitle("Caballio - Tableau de bord Administrateur");
        this.setBounds(100, 100, 1100, 650);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        // MENU LATÉRAL
        this.panelMenu.setPreferredSize(new Dimension(220, 650));
        this.panelMenu.setBackground(vertFonce);
        this.panelMenu.setLayout(new GridLayout(10, 1, 10, 10));

        JLabel lblTitre = new JLabel("CABALLIO", SwingConstants.CENTER);
        lblTitre.setForeground(blanc); lblTitre.setFont(new Font("Arial", Font.BOLD, 26));
        this.panelMenu.add(lblTitre);

        styliserBoutonMenu(btAccueil); styliserBoutonMenu(btCentres); styliserBoutonMenu(btClients);
        styliserBoutonMenu(btChevaux); styliserBoutonMenu(btEquipements); styliserBoutonMenu(btFactures);
        this.panelMenu.add(new JLabel("")); styliserBoutonMenu(btQuitter);

        // CONTENU
        this.panelContenu.setLayout(affichageCartes);
        this.panelContenu.setBackground(blanc);

        JPanel panelAccueil = new JPanel(new BorderLayout());
        panelAccueil.setBackground(blanc);
        JLabel lblAccueil = new JLabel("Bienvenue sur l'espace d'administration Caballio", SwingConstants.CENTER);
        lblAccueil.setFont(new Font("Arial", Font.PLAIN, 22)); lblAccueil.setForeground(vertFonce);
        panelAccueil.add(lblAccueil, BorderLayout.CENTER);

        this.panelContenu.add(panelAccueil, "Accueil");
        this.panelContenu.add(construirePanelCentres(), "Centres");
        this.panelContenu.add(construirePanelClients(), "Clients"); 
        this.panelContenu.add(construirePanelChevaux(), "Chevaux"); 
        this.panelContenu.add(construirePanelEquipements(), "Equipements"); // Onglet mis à jour
        this.panelContenu.add(construirePanelFactures(), "Factures");

        this.add(this.panelMenu, BorderLayout.WEST);
        this.add(this.panelContenu, BorderLayout.CENTER);

        affichageCartes.show(this.panelContenu, "Accueil");
        this.setVisible(true);
    }

    // =========================================
    // PANEL EQUIPEMENTS (NOUVEAU)
    // =========================================
    private JPanel construirePanelEquipements() {
        JPanel panel = new JPanel(new BorderLayout(10, 10)); panel.setBackground(blanc);
        JLabel lblTitre = new JLabel("Gestion des Équipements", SwingConstants.CENTER);
        lblTitre.setFont(new Font("Arial", Font.BOLD, 22)); lblTitre.setForeground(vertFonce);
        panel.add(lblTitre, BorderLayout.NORTH);

        String[] colonnes = {"ID", "Nom", "Date d'ajout", "ID Centre", "ID Propriétaire"};
        modeleTableEquipements = new DefaultTableModel(null, colonnes);
        tableEquipements = new JTable(modeleTableEquipements);
        tableEquipements.setRowHeight(25); tableEquipements.getTableHeader().setBackground(vertFonce); tableEquipements.getTableHeader().setForeground(blanc);
        
        rafraichirTableauEquipements();
        panel.add(new JScrollPane(tableEquipements), BorderLayout.CENTER);

        JPanel panelBoutons = new JPanel(new FlowLayout()); panelBoutons.setBackground(blanc);
        btAjouterEquipement.addActionListener(this); btModifierEquipement.addActionListener(this); btSupprimerEquipement.addActionListener(this);
        panelBoutons.add(btAjouterEquipement); panelBoutons.add(btModifierEquipement); panelBoutons.add(btSupprimerEquipement);
        panel.add(panelBoutons, BorderLayout.SOUTH);

        return panel;
    }

    private void rafraichirTableauEquipements() {
        modeleTableEquipements.setRowCount(0); 
        ArrayList<Equipement> liste = EquipementDAO.selectAllEquipements();
        for (Equipement e : liste) modeleTableEquipements.addRow(new Object[]{e.getIdEquipement(), e.getNomEquipement(), e.getDateAjoutEquipement(), e.getIdCentre(), e.getIdProprietaire()});
    }

    // =========================================
    // PANEL CENTRES
    // =========================================
    private JPanel construirePanelCentres() {
        JPanel panel = new JPanel(new BorderLayout(10, 10)); panel.setBackground(blanc);
        JLabel lblTitre = new JLabel("Gestion des Centres Équestres", SwingConstants.CENTER);
        lblTitre.setFont(new Font("Arial", Font.BOLD, 22)); lblTitre.setForeground(vertFonce);
        panel.add(lblTitre, BorderLayout.NORTH);

        String[] colonnes = {"ID", "Nom", "Adresse", "Code Postal", "Ville", "Téléphone", "ID Gérant"};
        modeleTableCentres = new DefaultTableModel(null, colonnes);
        tableCentres = new JTable(modeleTableCentres);
        tableCentres.setRowHeight(25); tableCentres.getTableHeader().setBackground(vertFonce); tableCentres.getTableHeader().setForeground(blanc);
        
        rafraichirTableauCentres();
        panel.add(new JScrollPane(tableCentres), BorderLayout.CENTER);

        JPanel panelBoutons = new JPanel(new FlowLayout()); panelBoutons.setBackground(blanc);
        btAjouterCentre.addActionListener(this); btModifierCentre.addActionListener(this); btSupprimerCentre.addActionListener(this);
        panelBoutons.add(btAjouterCentre); panelBoutons.add(btModifierCentre); panelBoutons.add(btSupprimerCentre);
        panel.add(panelBoutons, BorderLayout.SOUTH);

        return panel;
    }

    private void rafraichirTableauCentres() {
        modeleTableCentres.setRowCount(0); 
        ArrayList<Centre> liste = CentreDAO.selectAllCentres();
        for (Centre c : liste) modeleTableCentres.addRow(new Object[]{c.getIdCentre(), c.getNomCentre(), c.getAdresseCentre(), c.getCodePostalCentre(), c.getVilleCentre(), c.getTelephoneCentre(), c.getIdUtilisateur()});
    }

    // =========================================
    // PANEL CLIENTS
    // =========================================
    private JPanel construirePanelClients() {
        JPanel panel = new JPanel(new BorderLayout(10, 10)); panel.setBackground(blanc);
        JLabel lblTitre = new JLabel("Gestion des Clients", SwingConstants.CENTER);
        lblTitre.setFont(new Font("Arial", Font.BOLD, 22)); lblTitre.setForeground(vertFonce);
        panel.add(lblTitre, BorderLayout.NORTH);

        String[] colonnes = {"ID", "Nom", "Prénom", "Téléphone", "Ville", "Date d'ajout", "Adresse", "Code Postal"};
        modeleTableClients = new DefaultTableModel(null, colonnes);
        tableClients = new JTable(modeleTableClients);
        tableClients.setRowHeight(25); tableClients.getTableHeader().setBackground(vertFonce); tableClients.getTableHeader().setForeground(blanc);
        
        rafraichirTableauClients();
        tableClients.getColumnModel().getColumn(6).setMinWidth(0); tableClients.getColumnModel().getColumn(6).setMaxWidth(0);
        tableClients.getColumnModel().getColumn(7).setMinWidth(0); tableClients.getColumnModel().getColumn(7).setMaxWidth(0);

        panel.add(new JScrollPane(tableClients), BorderLayout.CENTER);

        JPanel panelBoutons = new JPanel(new FlowLayout()); panelBoutons.setBackground(blanc);
        btAjouterClient.addActionListener(this); btModifierClient.addActionListener(this); btSupprimerClient.addActionListener(this);
        panelBoutons.add(btAjouterClient); panelBoutons.add(btModifierClient); panelBoutons.add(btSupprimerClient);
        panel.add(panelBoutons, BorderLayout.SOUTH);

        return panel;
    }

    private void rafraichirTableauClients() {
        modeleTableClients.setRowCount(0); 
        ArrayList<Client> liste = ClientDAO.selectAllClients();
        for (Client c : liste) modeleTableClients.addRow(new Object[]{c.getIdClient(), c.getNom(), c.getPrenom(), c.getTelephone(), c.getVille(), c.getDateAjout(), c.getAdresse(), c.getCodePostal()});
    }

    // =========================================
    // PANEL CHEVAUX
    // =========================================
    private JPanel construirePanelChevaux() {
        JPanel panel = new JPanel(new BorderLayout(10, 10)); panel.setBackground(blanc);
        JLabel lblTitre = new JLabel("Gestion des Chevaux", SwingConstants.CENTER);
        lblTitre.setFont(new Font("Arial", Font.BOLD, 22)); lblTitre.setForeground(vertFonce);
        panel.add(lblTitre, BorderLayout.NORTH);

        String[] colonnes = {"ID", "Nom", "Sexe", "Race", "ID Centre", "ID Propriétaire"};
        modeleTableChevaux = new DefaultTableModel(null, colonnes);
        tableChevaux = new JTable(modeleTableChevaux);
        tableChevaux.setRowHeight(25); tableChevaux.getTableHeader().setBackground(vertFonce); tableChevaux.getTableHeader().setForeground(blanc);
        
        rafraichirTableauChevaux();
        panel.add(new JScrollPane(tableChevaux), BorderLayout.CENTER);

        JPanel panelBoutons = new JPanel(new FlowLayout()); panelBoutons.setBackground(blanc);
        btAjouterCheval.addActionListener(this); btModifierCheval.addActionListener(this); btSupprimerCheval.addActionListener(this);
        panelBoutons.add(btAjouterCheval); panelBoutons.add(btModifierCheval); panelBoutons.add(btSupprimerCheval);
        panel.add(panelBoutons, BorderLayout.SOUTH);

        return panel;
    }

    private void rafraichirTableauChevaux() {
        modeleTableChevaux.setRowCount(0); 
        ArrayList<Cheval> liste = ChevalDAO.selectAllChevaux();
        for (Cheval c : liste) modeleTableChevaux.addRow(new Object[]{c.getIdCheval(), c.getNom(), c.getSexe(), c.getRace(), c.getIdCentre(), c.getIdClient()});
    }

    // =========================================
    // PANEL FACTURES (Inchangé - Lecture seule)
    // =========================================
    private JPanel construirePanelFactures() {
        String[] col = {"ID Facture", "Date", "Montant Total", "ID Client", "ID Centre"};
        ArrayList<Facture> liste = FactureDAO.selectAllFactures(); Object[][] donnees = new Object[liste.size()][col.length];
        for (int i = 0; i < liste.size(); i++) { donnees[i][0] = liste.get(i).getIdFacture(); donnees[i][1] = liste.get(i).getDateAjoutFacture(); donnees[i][2] = liste.get(i).getMontantTotal(); donnees[i][3] = liste.get(i).getIdClient(); donnees[i][4] = liste.get(i).getIdCentre(); }
        return creerPanelTableau("Historique des Factures", col, donnees);
    }
    private JPanel creerPanelTableau(String titre, String[] colonnes, Object[][] donnees) {
        JPanel panel = new JPanel(new BorderLayout(10, 10)); panel.setBackground(blanc);
        JLabel lblTitre = new JLabel(titre, SwingConstants.CENTER); lblTitre.setFont(new Font("Arial", Font.BOLD, 22)); lblTitre.setForeground(vertFonce); panel.add(lblTitre, BorderLayout.NORTH);
        DefaultTableModel modeleTable = new DefaultTableModel(donnees, colonnes); JTable table = new JTable(modeleTable);
        table.setRowHeight(25); table.getTableHeader().setBackground(vertFonce); table.getTableHeader().setForeground(blanc); panel.add(new JScrollPane(table), BorderLayout.CENTER); return panel;
    }
    private void styliserBoutonMenu(JButton bouton) {
        bouton.setBackground(blanc); bouton.setForeground(vertFonce); bouton.setFont(new Font("Arial", Font.BOLD, 14));
        bouton.setFocusPainted(false); bouton.addActionListener(this); this.panelMenu.add(bouton);
    }

    // =========================================
    // GESTION DES CLICS BOUTONS
    // =========================================
    @Override
    public void actionPerformed(ActionEvent e) {
        // Navigation
        if (e.getSource() == btAccueil) affichageCartes.show(this.panelContenu, "Accueil");
        else if (e.getSource() == btCentres) affichageCartes.show(this.panelContenu, "Centres");
        else if (e.getSource() == btClients) affichageCartes.show(this.panelContenu, "Clients");
        else if (e.getSource() == btChevaux) affichageCartes.show(this.panelContenu, "Chevaux");
        else if (e.getSource() == btEquipements) affichageCartes.show(this.panelContenu, "Equipements");
        else if (e.getSource() == btFactures) affichageCartes.show(this.panelContenu, "Factures");
        else if (e.getSource() == btQuitter) {
            if (JOptionPane.showConfirmDialog(this, "Se déconnecter ?", "Déconnexion", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                this.dispose(); new VueConnexion(); 
            }
        }
        
        // --- CRUD EQUIPEMENT ---
        else if (e.getSource() == btAjouterEquipement) {
            VueFormulaireEquipement form = new VueFormulaireEquipement(this, "Nouvel Équipement", null);
            if (form.isValide()) { EquipementDAO.insertEquipement(form.getEquipementSaisi()); rafraichirTableauEquipements(); }
        }
        else if (e.getSource() == btSupprimerEquipement) {
            int row = tableEquipements.getSelectedRow();
            if (row == -1) JOptionPane.showMessageDialog(this, "Veuillez sélectionner un équipement.");
            else {
                int id = (int) tableEquipements.getValueAt(row, 0);
                if (JOptionPane.showConfirmDialog(this, "Supprimer l'équipement n°" + id + " ?", "Suppression", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    EquipementDAO.deleteEquipement(id); rafraichirTableauEquipements();
                }
            }
        }
        else if (e.getSource() == btModifierEquipement) {
            int row = tableEquipements.getSelectedRow();
            if (row == -1) JOptionPane.showMessageDialog(this, "Veuillez sélectionner un équipement.");
            else {
                Equipement eq = new Equipement(
                    (int) tableEquipements.getValueAt(row, 0), 
                    tableEquipements.getValueAt(row, 1).toString(), 
                    null, 
                    (int) tableEquipements.getValueAt(row, 3), 
                    (int) tableEquipements.getValueAt(row, 4)
                );
                VueFormulaireEquipement form = new VueFormulaireEquipement(this, "Modifier l'Équipement", eq);
                if (form.isValide()) { Equipement eqMaj = form.getEquipementSaisi(); eqMaj.setIdEquipement(eq.getIdEquipement()); EquipementDAO.updateEquipement(eqMaj); rafraichirTableauEquipements(); }
            }
        }

        // --- CRUD CENTRE ---
        else if (e.getSource() == btAjouterCentre) {
            VueFormulaireCentre form = new VueFormulaireCentre(this, "Nouveau Centre", null);
            if (form.isValide()) { CentreDAO.insertCentre(form.getCentreSaisi()); rafraichirTableauCentres(); }
        }
        else if (e.getSource() == btSupprimerCentre) {
            int row = tableCentres.getSelectedRow();
            if (row == -1) JOptionPane.showMessageDialog(this, "Veuillez sélectionner un centre.");
            else {
                int id = (int) tableCentres.getValueAt(row, 0);
                if (JOptionPane.showConfirmDialog(this, "Supprimer le centre n°" + id + " ?", "Suppression", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    CentreDAO.deleteCentre(id); rafraichirTableauCentres();
                }
            }
        }
        else if (e.getSource() == btModifierCentre) {
            int row = tableCentres.getSelectedRow();
            if (row == -1) JOptionPane.showMessageDialog(this, "Veuillez sélectionner un centre.");
            else {
                Centre c = new Centre(
                    (int) tableCentres.getValueAt(row, 0), 
                    tableCentres.getValueAt(row, 1).toString(), 
                    tableCentres.getValueAt(row, 2).toString(), 
                    tableCentres.getValueAt(row, 3).toString(), 
                    tableCentres.getValueAt(row, 4).toString(), 
                    tableCentres.getValueAt(row, 5).toString(), 
                    null, 
                    (int) tableCentres.getValueAt(row, 6)
                );
                VueFormulaireCentre form = new VueFormulaireCentre(this, "Modifier le Centre", c);
                if (form.isValide()) { Centre cMaj = form.getCentreSaisi(); cMaj.setIdCentre(c.getIdCentre()); CentreDAO.updateCentre(cMaj); rafraichirTableauCentres(); }
            }
        }

        // --- CRUD CLIENT ---
        else if (e.getSource() == btAjouterClient) {
            VueFormulaireClient form = new VueFormulaireClient(this, "Nouveau Client", null);
            if (form.isValide()) { ClientDAO.insertClient(form.getClientSaisi()); rafraichirTableauClients(); }
        }
        else if (e.getSource() == btSupprimerClient) {
            int row = tableClients.getSelectedRow();
            if (row == -1) JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client.");
            else {
                int id = (int) tableClients.getValueAt(row, 0);
                if (JOptionPane.showConfirmDialog(this, "Supprimer le client n°" + id + " ?", "Suppression", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    ClientDAO.deleteClient(id); rafraichirTableauClients();
                }
            }
        }
        else if (e.getSource() == btModifierClient) {
            int row = tableClients.getSelectedRow();
            if (row == -1) JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client.");
            else {
                Client c = new Client((int) tableClients.getValueAt(row, 0), tableClients.getValueAt(row, 1).toString(), tableClients.getValueAt(row, 2).toString(), tableClients.getValueAt(row, 6).toString(), tableClients.getValueAt(row, 7).toString(), tableClients.getValueAt(row, 3).toString(), tableClients.getValueAt(row, 4).toString(), null);
                VueFormulaireClient form = new VueFormulaireClient(this, "Modifier le Client", c);
                if (form.isValide()) { Client cMaj = form.getClientSaisi(); cMaj.setIdClient(c.getIdClient()); ClientDAO.updateClient(cMaj); rafraichirTableauClients(); }
            }
        }

        // --- CRUD CHEVAL ---
        else if (e.getSource() == btAjouterCheval) {
            VueFormulaireCheval form = new VueFormulaireCheval(this, "Nouveau Cheval", null);
            if (form.isValide()) { ChevalDAO.insertCheval(form.getChevalSaisi()); rafraichirTableauChevaux(); }
        }
        else if (e.getSource() == btSupprimerCheval) {
            int row = tableChevaux.getSelectedRow();
            if (row == -1) JOptionPane.showMessageDialog(this, "Veuillez sélectionner un cheval.");
            else {
                int id = (int) tableChevaux.getValueAt(row, 0);
                if (JOptionPane.showConfirmDialog(this, "Supprimer le cheval n°" + id + " ?", "Suppression", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    ChevalDAO.deleteCheval(id); rafraichirTableauChevaux();
                }
            }
        }
        else if (e.getSource() == btModifierCheval) {
            int row = tableChevaux.getSelectedRow();
            if (row == -1) JOptionPane.showMessageDialog(this, "Veuillez sélectionner un cheval.");
            else {
                Cheval c = new Cheval(
                    (int) tableChevaux.getValueAt(row, 0), 
                    tableChevaux.getValueAt(row, 1).toString(), 
                    tableChevaux.getValueAt(row, 2).toString(), 
                    tableChevaux.getValueAt(row, 3).toString(), 
                    (int) tableChevaux.getValueAt(row, 4), 
                    (int) tableChevaux.getValueAt(row, 5)
                );
                VueFormulaireCheval form = new VueFormulaireCheval(this, "Modifier le Cheval", c);
                if (form.isValide()) { Cheval cMaj = form.getChevalSaisi(); cMaj.setIdCheval(c.getIdCheval()); ChevalDAO.updateCheval(cMaj); rafraichirTableauChevaux(); }
            }
        }
    }
}