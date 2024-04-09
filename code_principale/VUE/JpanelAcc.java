package code_principale.VUE;

import BDD.ScoreManager;
import code_principale.ControleurOfficiel;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Desktop;
import java.net.URI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class JpanelAcc extends JPanel {
	private ControleurOfficiel ctrl;
	private PanelInfosJoueur panelInfosJoueur;
	private JButton btnJouerEnLigne;
	private JButton btnJouerAvecAmi;
	private JButton btnJouerContreOrdi;
	private JButton btnScores;
	private JButton btnGameGuides;
	private boolean jouerAvecAmiClicked = false;
	private boolean jouerContreOrdiClicked = false;
	private Color couleurbouton = new Color(255,0,0);
	private Color couleurBouton = new Color(0, 204, 153);

	public JpanelAcc(ControleurOfficiel ctrl) {
		this.ctrl = ctrl;
		setLayout(new BorderLayout());


		// Configuration de la police et des couleurs
		Font policeTitre = new Font("Calibri", Font.BOLD, 30);
		Font policeBouton = new Font("Calibri", Font.BOLD, 20);
		Color couleurBouton = new Color(0, 204, 153);
		// Création et configuration des boutons
		btnJouerEnLigne = createButton("Jouer en ligne", policeBouton, couleurBouton);
		btnJouerAvecAmi = createButton("Jouer avec un ami", policeBouton, couleurBouton);
		btnJouerContreOrdi = createButton("Jouer contre l'ordi", policeBouton, couleurBouton);
		btnScores = createButton("Me scores", policeBouton, couleurBouton);
		btnScores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				afficherScores();
			}
		});
		btnGameGuides = createButton("Game guides", policeBouton, couleurBouton);
		btnJouerEnLigne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ouvrirLienWeb("https://papergames.io/fr/puissance4");
			}
		});btnGameGuides.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				afficherGuideJeu();
			}
		});
		btnJouerAvecAmi.addActionListener(e -> {
			jouerAvecAmiClicked = true;
			jouerContreOrdiClicked = false;
			mettreEnSurbrillanceBoutons();
		});

		btnJouerContreOrdi.addActionListener(e -> {
			jouerAvecAmiClicked = false;
			jouerContreOrdiClicked = true;
			mettreEnSurbrillanceBoutons();
		});
		// Configuration du label du titre
		JLabel lblTitre = new JLabel("Puissance 4", JLabel.CENTER);
		lblTitre.setFont(policeTitre);
		lblTitre.setForeground(Color.BLACK);

		// Panneau du haut avec le titre
		JPanel panelNord = new JPanel(new BorderLayout());
		panelNord.add(lblTitre, BorderLayout.CENTER);

		// Panneau central avec les boutons
		JPanel panelBoutons = new JPanel();
		panelBoutons.setLayout(new GridLayout(5, 1, 10, 10)); // 5 lignes, 1 colonne
		panelBoutons.add(btnJouerEnLigne);
		panelBoutons.add(btnJouerAvecAmi);
		panelBoutons.add(btnJouerContreOrdi);
		panelBoutons.add(btnScores);
		panelBoutons.add(btnGameGuides);

		// Ajout des panneaux au panel principal
		add(panelNord, BorderLayout.NORTH);
		add(panelBoutons, BorderLayout.CENTER);

		// Configuration des composants enfant
		panelInfosJoueur = new PanelInfosJoueur(ctrl);
		add(panelInfosJoueur, BorderLayout.SOUTH);
		panelInfosJoueur.setVisible(false); // Caché par défaut

		// Gestionnaires d'événements pour les boutons
		btnJouerAvecAmi.addActionListener(e -> panelInfosJoueur.setVisible(true));
		// Ajouter les gestionnaires pour les autres boutons...
	}
	private void mettreEnSurbrillanceBoutons() {
		btnJouerAvecAmi.setBackground(couleurBouton);
		btnJouerContreOrdi.setBackground(couleurBouton);

		// Mettre en surbrillance le bouton cliqué
		if (jouerAvecAmiClicked) {
			btnJouerAvecAmi.setBackground(Color.RED);
		} else if (jouerContreOrdiClicked) {
			btnJouerContreOrdi.setBackground(Color.RED);
		}
	}
	private void afficherScores() {
		FenetreScores fenetreScores = new FenetreScores(ctrl);
		fenetreScores.setVisible(true);
	}
	private void afficherGuideJeu() {
		// Créer une nouvelle JFrame ou JDialog pour afficher l'image
		JDialog guideDialog = new JDialog();
		guideDialog.setTitle("Guide du jeu");

		// Charger l'image et la mettre dans un JLabel
		ImageIcon guideImage = new ImageIcon("ressources/images/Doc1_page-0001.jpg");
		JLabel guideLabel = new JLabel(guideImage);
		guideLabel.setHorizontalAlignment(JLabel.CENTER);
		guideLabel.setVerticalAlignment(JLabel.CENTER);

		guideDialog.getContentPane().add(new JScrollPane(guideLabel), BorderLayout.CENTER); // Ajouter un JScrollPane pour permettre le défilement si l'image est grande

		// Définir la taille maximale du dialogue
		guideDialog.setMaximumSize(new Dimension(800, 600)); // Ajustez selon vos besoins
		guideDialog.pack();
		guideDialog.setLocationRelativeTo(null);
		guideDialog.setVisible(true);
	}
	private JButton createButton(String text, Font font, Color color) {
		JButton button = new JButton(text);
		button.setFont(font);
		button.setBackground(color);
		button.setForeground(Color.WHITE);
		button.setFocusPainted(false);
		button.setBorder(BorderFactory.createRaisedBevelBorder());
		return button;

	}
	private void ouvrirLienWeb(String url) {
		try {
			Desktop.getDesktop().browse(new URI(url));
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Impossible d'ouvrir le lien.", "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.WHITE); // Fond blanc ou utilisez une image de fond si nécessaire
	}

	public void fermerAccueil() {
		this.panelInfosJoueur.fermerAccueil();

		this.removeAll();
		this.setVisible(false);
	}





	private class PanelInfosJoueur extends JPanel implements ActionListener
	{ private static final long serialVersionUID = 1L;
		private ControleurOfficiel ctrl; 			// L'attribut renseigne sur le contrôleur.
		private JTextField txtfldJoueur1; 	// L'attribut renseigne sur le champ de saisie du joueur 1.
		private JTextField txtfldJoueur2; 	// L'attribut renseigne sur le champ de saisie du joueur 2.
		private JButton btnValider; 		// L'attribut renseigne sur le bouton de validation.


		public PanelInfosJoueur(ControleurOfficiel ctrl)
		{
			this.ctrl = ctrl;
			this.setLayout( new GridLayout(3,1) );
			this.setOpaque(false);

			Font policeChamp 	= new Font("Calibri", Font.BOLD, 20);

			JLabel lblPseudoJoueur1 = new JLabel("Pseudo du joueur 1 : ");
			lblPseudoJoueur1.setFont(policeChamp);
			lblPseudoJoueur1.setForeground(Color.BLACK);

			this.txtfldJoueur1 = new JTextField(10);
			this.txtfldJoueur1.setOpaque(false);
			this.txtfldJoueur1.setFont(policeChamp);
			this.txtfldJoueur1.setForeground(Color.BLACK);
			this.txtfldJoueur1.setPreferredSize( new Dimension(20,20) );

			JLabel lblPseudoJoueur2 = new JLabel("Pseudo du joueur 2 : ");
			lblPseudoJoueur2.setForeground(Color.BLACK);
			lblPseudoJoueur2.setFont(policeChamp);

			this.txtfldJoueur2 = new JTextField(10);
			this.txtfldJoueur2.setOpaque(false);
			this.txtfldJoueur2.setFont(policeChamp);
			this.txtfldJoueur2.setForeground(Color.BLACK);

			this.btnValider = new JButton("lancer le jeu");
			this.btnValider.setFont(policeChamp);
			this.btnValider.setForeground(Color.BLACK);
			this.btnValider.setBorder(BorderFactory.createLineBorder(Color.black));

			// Création du panel interne
			JPanel panelChamp = new JPanel();
			panelChamp.setLayout( new GridLayout(2,1) );
			panelChamp.setOpaque(false);

			/*-------------------------------*/
			/* Positionnement des composants */
			/*-------------------------------*/
			panelChamp.add( lblPseudoJoueur1 );
			panelChamp.add( this.txtfldJoueur1 );
			panelChamp.add( lblPseudoJoueur2 );
			panelChamp.add( this.txtfldJoueur2 );

			this.add(panelChamp);
			this.add(this.btnValider);

			/*-------------------------*/
			/* Activation du composant */
			/*-------------------------*/
			this.btnValider.addActionListener(this);

			this.setVisible(true);
		}
		public void fermerAccueil()
		{
			this.removeAll();
			this.setVisible(false);
		}

		public void actionPerformed(ActionEvent e)
		{
			if ( e.getSource() == this.btnValider )
			{
				if ( ! this.txtfldJoueur1.getText().equals("") && ! this.txtfldJoueur2.getText().equals("") ) // peut se traduire aussi par ! isEmpty()
				{
					if ( ! this.txtfldJoueur1.getText().equals(this.txtfldJoueur2.getText()) )
					{
						String pseudoJoueur1 = this.txtfldJoueur1.getText();
						String pseudoJoueur2 = this.txtfldJoueur2.getText();
						boolean jouerContreOrdi = jouerContreOrdiClicked;
						boolean jouerAvecAmi = jouerAvecAmiClicked;

						if (jouerContreOrdi && !jouerAvecAmi) {
							this.ctrl.Lancement(pseudoJoueur1, pseudoJoueur2, true); // Jouer contre l'ordinateur
						} else {
							this.ctrl.Lancement(pseudoJoueur1, pseudoJoueur2, false); // Jouer avec un ami
						}

						this.ctrl.fermerAccueil();
					}
					else if ( this.txtfldJoueur1.getText().equals(this.txtfldJoueur2.getText()) )
					{
						JOptionPane.showMessageDialog(this, "Interdiction d'avoir le même pseudo.", "Erreur de pseudo", JOptionPane.ERROR_MESSAGE);
					}
				}
				else
				{
					int cptJoueur=0;
					String sRet = "Champ manquant pour le joueur ";
					if ( this.txtfldJoueur1.getText().equals("") && this.txtfldJoueur2.getText().equals("") )
					{
						sRet = "Champs manquants pour les joueurs ";
						sRet += Integer.toString(++cptJoueur) + " et " + Integer.toString(++cptJoueur) ;
					}
					else if ( this.txtfldJoueur1.getText().equals("") || this.txtfldJoueur2.getText().equals("") )
					{
						cptJoueur++;
						if ( this.txtfldJoueur1.getText().equals("") )
						{
							sRet += cptJoueur;
						}
						else if ( this.txtfldJoueur2.getText().equals("") )
						{
							sRet += ++cptJoueur;
						}
						cptJoueur--;
					}
					String sRetTitre = "Saisie manquante";

					if ( cptJoueur > 1 )
					{
						sRetTitre = "Plusieurs saisies manquantes";
					}
					JOptionPane.showMessageDialog(this, sRet, sRetTitre, JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	public class FenetreScores extends JFrame {
		private ControleurOfficiel ctrl;
		private JTable tableauScores;

		public FenetreScores(ControleurOfficiel ctrl) {
			this.ctrl = ctrl;
			setTitle("Tableau des Scores");
			setSize(400, 300);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);

			// Récupérer les données depuis la base de données en appelant la méthode de ScoreManager
			String[][] donnees = ScoreManager.recupererScores();

			if (donnees != null) {
				// Entêtes du tableau
				String[] entetes = {"ID", "JOUEUR", "SCORE total"};

				// Initialiser le tableau avec les données récupérées
				tableauScores = new JTable(donnees, entetes);
				getContentPane().add(new JScrollPane(tableauScores));
			} else {
				JOptionPane.showMessageDialog(this, "Aucun score trouvé.", "Erreur", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}