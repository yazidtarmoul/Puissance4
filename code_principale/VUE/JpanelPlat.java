package code_principale.VUE;

/*----L'-import-pour-le-contrôleur----*/
import code_principale.ControleurOfficiel;

/*----L'-import-pour-le-metier----*/
import code_principale.JEU.Joueur;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.BasicStroke;

import javax.swing.*;
import javax.swing.BorderFactory;

import java.io.File;

import static BDD.ScoreManager.mettreAJourScore;

public class JpanelPlat extends JPanel
{
	private static final long serialVersionUID = 1L;
	private PanelGrille 		panelGrille; 			// L'attribut renseigne sur le PanelGrille.
	private PanelMessageJoueur 	panelMessageJoueur; 	// L'attribut renseigne sur le PanelMessageJoueur.
	private JPanel messagePanel;

	public ControleurOfficiel ctrl;
	public JpanelPlat(ControleurOfficiel ctrl) {
		this.ctrl = ctrl;

		// Informations sur le panel plateau
		this.setLayout(new BorderLayout(0, 10));
		this.setBackground(Color.DARK_GRAY);

		// Création des composants
		this.panelGrille = new PanelGrille(ctrl);
		this.panelMessageJoueur = new PanelMessageJoueur(ctrl);

		// Positionnement des composants
		// Création d'un panel pour contenir la grille et ajouter un décalage de 30 pixels vers la droite
		JPanel gridContainer = new JPanel(new BorderLayout());
		gridContainer.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		gridContainer.add(this.panelGrille, BorderLayout.CENTER);

		// Création d'un panel pour contenir les boutons, disposés verticalement
		JPanel buttonPanel = new JPanel(new GridLayout(1, 0));
		for (JButton btn : this.panelGrille.getBtnJeton()) {
			buttonPanel.add(btn);
		}

		// Création d'un nouveau panel pour contenir la ligne de boutons et la grille décalée
		JPanel contentPanel = new JPanel(new BorderLayout());
		contentPanel.add(buttonPanel, BorderLayout.SOUTH);
		contentPanel.add(gridContainer, BorderLayout.CENTER);

		// Création d'un panel pour le PanelMessageJoueur avec un FlowLayout pour aligner le texte à gauche
		this.messagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		messagePanel.add(this.panelMessageJoueur);

		// Ajout du PanelMessageJoueur en haut
		contentPanel.add(messagePanel, BorderLayout.NORTH);

		this.add(contentPanel);

		// Réglage des couleurs pour le message joueur et les boutons
		updatePlayerColors();

		this.setVisible(true);
	}
	private void updatePlayerColors() {
		Color playerColor = this.ctrl.getJoueurActif().getCouleur() == 'J' ? Color.ORANGE : Color.RED;

		// Changer la couleur de fond du PanelMessageJoueur
		this.panelMessageJoueur.setBackground(playerColor);

		// Changer la couleur de fond du JPanel contenant le texte
		this.messagePanel.setBackground(playerColor);

		// Changer la couleur de fond des boutons de la grille
		for (JButton btn : this.panelGrille.getBtnJeton()) {
			btn.setBackground(playerColor);
		}
	}
	public void joueurActifChange() {
		updatePlayerColors();
	}

	public void majGraphique()
	{
		this.panelGrille.majGraphique();
		this.panelMessageJoueur.majGraphique();
		this.joueurActifChange();
	}
	public void setModeSombre()
	{
		this.panelGrille.setModeSombre();
		this.panelMessageJoueur.setModeSombre();
		this.setBackground(Color.DARK_GRAY);
	}

	public void fermerPanelPlateau()
	{
		this.panelGrille.fermerPanelGrille();
		this.panelMessageJoueur.fermerMessageJoueur();

		this.removeAll();
		this.setVisible(false);
	}

	private class PanelGrille extends JPanel implements ActionListener
	{
		private static final long serialVersionUID = 1L;

		private ControleurOfficiel ctrl;
		private JPanel 		panelEnsBtn; 			// L'attribut renseigne sur le panel contenant les boutons.
		private JButton[] 	tabBtnJeton; 			// L'attribut renseigne sur les boutons jetons.
		public PanelGrille(ControleurOfficiel ctrl)
		{
			/*----------------------------------*/
			/* Informations sur le panel grille */
			/*----------------------------------*/
			this.ctrl = ctrl;
			this.setLayout( new BorderLayout() );
			this.setBackground( Color.DARK_GRAY );


			/*-------------------------*/
			/* Création des composants */
			/*-------------------------*/
			this.panelEnsBtn = new JPanel();
			this.panelEnsBtn.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
			this.panelEnsBtn.setLayout( new GridLayout(1, this.ctrl.getNbColonne()) );
			this.panelEnsBtn.setBackground( Color.DARK_GRAY);

			this.tabBtnJeton = new JButton[this.ctrl.getNbColonne()];

			File 		fichierBtnJeton 	= new File("ressources/images/fleche.png");
			ImageIcon 	imgIconBtnJeton 	= new ImageIcon(fichierBtnJeton.getPath());

			// Redimension de l'icon pour les boutons
			Image 		imgBtnJetonPrepare 	= imgIconBtnJeton.getImage();
			Image 		imgBtnJetonResize 	= imgBtnJetonPrepare.getScaledInstance(90,30, Image.SCALE_SMOOTH);
			imgIconBtnJeton 				= new ImageIcon(imgBtnJetonResize);

			for(int cptColonne=0; cptColonne<this.ctrl.getNbColonne(); cptColonne++)
			{
				this.tabBtnJeton[cptColonne] = new JButton(imgIconBtnJeton);
			}

			/*-------------------------------*/
			/* Positionnement des composants */
			/*-------------------------------*/
			for(int cptColonne=0; cptColonne<this.ctrl.getNbColonne(); cptColonne++)
			{
				this.panelEnsBtn.add(this.tabBtnJeton[cptColonne]);
			}

			this.add(this.panelEnsBtn, BorderLayout.SOUTH);

			/*---------------------------*/
			/* Activation des composants */
			/*---------------------------*/
			for(int cptColonne=0; cptColonne<this.ctrl.getNbColonne(); cptColonne++)
			{
				this.tabBtnJeton[cptColonne].addActionListener(this);
			}

			this.setVisible(true);
		}
		public JButton[] getBtnJeton() {
			return this.tabBtnJeton;
		}

		public void paintComponent(Graphics g)
		{
			String     sImage;
			Image      img;
			Graphics2D g2 = (Graphics2D) g;

			super.paintComponent(g);

			// Réactif avec les pourcentages
			int largeurEspace 	= 0;
			int hauteurEspace 	= 0;
			int tailleLargeur 	= 0;
			int tailleHauteur 	= 0;

			// Dessine les jetons
			largeurEspace 	= (int) ( this.getWidth() 	* ( (double) 4/100 	) );
			tailleLargeur 	= (int) ( this.getWidth() 	* ( (double) 13/100 ) );
			tailleHauteur 	= (int) ( this.getHeight() 	* ( (double) 15/100 ) );

			for(int cptColonne=0; cptColonne<this.ctrl.getNbColonne(); cptColonne++)
			{
				hauteurEspace 	= (int) ( this.getHeight() 	* ( (double) 9/100 	) );
				for(int cptLigne=0; cptLigne<this.ctrl.getNbLigne(); cptLigne++)
				{
					char sCouleur = this.ctrl.getJeton(cptLigne, cptColonne);
					img = getToolkit().getImage ( "./ressources/images/jeton_" + sCouleur + ".png" );

					g2.drawImage ( img, largeurEspace, hauteurEspace, tailleLargeur, tailleHauteur, this );
					hauteurEspace += (int) ( this.getHeight() * ( (double) 15/100 ) );
				}
				largeurEspace += (int) ( this.getWidth() * ( (double) 13/100 ) );
			}

			largeurEspace = 0;
			hauteurEspace = 0;
			tailleLargeur = 0;
			tailleHauteur = 0;

			// Bordure du plateau
			largeurEspace = (int) ( this.getWidth() 	* ( (double) 4/100 ) );
			hauteurEspace = (int) ( this.getHeight() 	* ( (double) 9/100 ) );
			tailleLargeur = (int) ( this.getWidth() 	* ( (double) 90/100 ) );
			tailleHauteur = (int) ( this.getHeight() 	* ( (double) 89/100 ) );

			int bordureEpaisseur = 5;

			g2.setStroke( new BasicStroke(bordureEpaisseur, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND ) );
			g2.setColor( new Color(0,41,255) );
			g2.drawRect( largeurEspace, hauteurEspace, tailleLargeur, tailleHauteur );

			// Réajuste le panel contenant les boutons
			int margeGauche = (int) ( this.getWidth() 	* ( (double) 4/100 	) );
			int margeDroite = (int) ( this.getWidth() 	* ( (double) 5/100 	) );
			this.panelEnsBtn.setBorder( BorderFactory.createEmptyBorder(0, margeGauche, 0, margeDroite) );

			// Redimensionne les boutons
			tailleLargeur 	= (int) ( this.getWidth() 	* ( (double) 5/100 ) );
			tailleHauteur 	= (int) ( this.getHeight() 	* ( (double) 6/100 ) );
			for(int cptColonne=0; cptColonne<this.ctrl.getNbColonne(); cptColonne++)
			{
				this.tabBtnJeton[cptColonne].setPreferredSize( new Dimension(tailleLargeur, tailleHauteur) );
			}
		}
		public void majGraphique()
		{
			this.repaint();
		}
		public void setModeSombre()
		{
			this.panelEnsBtn.setBackground(Color.DARK_GRAY);
			this.setBackground(Color.DARK_GRAY);
		}
		public void fermerPanelGrille()
		{
			this.removeAll();
			this.setVisible(false);
		}
		public void actionPerformed(ActionEvent e)
		{
			for(int cptColonne=0; cptColonne<this.tabBtnJeton.length; cptColonne++)
			{
				if ( e.getSource() == this.tabBtnJeton[cptColonne] )
				{
					boolean bAction = this.ctrl.setJeton(cptColonne);

					if ( bAction )
					{
						// Mis à jour du plateau après l'action jouée
						this.ctrl.majGraphique();

						// Vérification du gagnant ou Vérification égalité
						if ( this.ctrl.estGagnant() || this.ctrl.getGrillePleine() )
						{
							if ( this.ctrl.estGagnant() )
							{
								this.optionPaneVictoire();
							}
							else
							{
								this.optionPaneEgalite();
							}
						}
						else
						{
							// Joueur suivant
							this.ctrl.setJoueurSuivant();

							// Mis à jour du plateau pour le label message joueur
							this.ctrl.majGraphique();
						}
					}
					else
					{
						String pseudoJoueur = this.ctrl.getJoueurActif().getPseudoJoueur();
						String sMessErreur = pseudoJoueur + ", cette colonne est remplie de jetons.";
						JOptionPane.showMessageDialog(this, sMessErreur, "Action impossible", JOptionPane.ERROR_MESSAGE);
					}

				}
			}
		}
		private void optionPaneVictoire()
		{
			String 	messagePartie 	= "";
			String 	messageTitre 	= "";
			int 	optConfirme 	= -1;
			String 	pseudoJoueur 	= this.ctrl.getJoueurActif().getPseudoJoueur();

			messagePartie 	= "Bravo " + pseudoJoueur + ", vous avez gagné";
			messagePartie 	+= "\n" + "Voulez vous rejouer ?";
			messageTitre 	= "Victoire";
			optConfirme 	= JOptionPane.showConfirmDialog(this, messagePartie, messageTitre, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

			if ( optConfirme == JOptionPane.YES_OPTION )
			{
				this.lancerNouvellePartie();
			}
			else
			{
				this.ctrl.fermerJeu();
			}
		}
		private void optionPaneEgalite()
		{
			String 	messagePartie 	= "";
			String 	messageTitre 	= "";
			int 	optConfirme 	= -1;

			messagePartie = "Vous êtes à égalité, la grille est pleine";
			messagePartie += "\n" + "Voulez vous rejouer ?";
			messageTitre 	= "Égalité";
			optConfirme = JOptionPane.showConfirmDialog(this, messagePartie, messageTitre, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

			if ( optConfirme == JOptionPane.YES_OPTION)
			{
				this.lancerNouvellePartie();
			}
			else
			{
				this.ctrl.fermerJeu();
			}
		}

		private void lancerNouvellePartie()
		{
			Joueur joueur1 = null;
			Joueur joueur2 = null;

			for(int cptJoueur=0; cptJoueur<this.ctrl.getNbJoueur(); cptJoueur++)
			{
				if ( (cptJoueur % 2) == 0 )
				{
					joueur1 = this.ctrl.getJoueur(cptJoueur);
				}
				else
				{
					joueur2 = this.ctrl.getJoueur(cptJoueur);
				}
			}

			this.ctrl.reinitialiserPartie();
			this.ctrl.Relancement(joueur1, joueur2);
			this.majGraphique();
		}
	}

	private class PanelMessageJoueur extends JPanel
	{
		private static final long serialVersionUID = 1L;
		private ControleurOfficiel ctrl; 					// L'attribut renseigne sur le contrôleur.
		private JPanel panelLblMessageJoueur; 		// L'attribut renseigne sur le panel contenant le message pour les joueurs.
		private JLabel lblMessage; 					// L'attribut renseigne sur le message du label.
		public PanelMessageJoueur(ControleurOfficiel ctrl)
		{
			/*------------------------------------------*/
			/* Informations sur le panel message joueur */
			/*------------------------------------------*/
			this.ctrl = ctrl;
			this.setLayout( new BorderLayout() );

			/*-------------------------*/
			/* Création des composants */
			/*-------------------------*/
			this.panelLblMessageJoueur = new JPanel();
			this.panelLblMessageJoueur.setLayout( new FlowLayout() );
			this.panelLblMessageJoueur.setBackground(Color.WHITE);

			String pseudoJoueur = "";

			this.lblMessage = new JLabel(pseudoJoueur + ", c'est à vous de jouer !");

			/*-------------------------------*/
			/* Positionnement des composants */
			/*-------------------------------*/
			this.panelLblMessageJoueur.add(this.lblMessage, JLabel.CENTER);
			this.add(this.panelLblMessageJoueur, BorderLayout.CENTER);


			this.setVisible(true);
		}

		public void majGraphique()
		{
			String pseudoJoueur = this.ctrl.getJoueurActif().getPseudoJoueur();

			String messageJoueur = ", à votre tour";

			if ( this.ctrl.estGagnant() || this.ctrl.getGrillePleine() )
			{
				if ( this.ctrl.getGrillePleine() )
				{
					pseudoJoueur 	= "";
					messageJoueur 	= "Égalité";
				}
				else
				{
					messageJoueur 	= " : à gagné";
					mettreAJourScore(pseudoJoueur, 20);
				}
			}

			this.lblMessage.setText(pseudoJoueur + messageJoueur);

			if ( this.ctrl.getJoueurActif().getCouleur() == 'J' && (! (this.ctrl.getGrillePleine()) ) )
			{
				this.lblMessage.setForeground( Color.BLACK );
			}
			else if ( this.ctrl.getJoueurActif().getCouleur() == 'R' && (! (this.ctrl.getGrillePleine()) ) )
			{
				this.lblMessage.setForeground( Color.BLACK );
			}
			else
			{
				this.lblMessage.setForeground( Color.BLACK );
			}
		}
		public void setModeSombre()
		{
			this.panelLblMessageJoueur.setBackground(Color.DARK_GRAY);
			this.setBackground(Color.DARK_GRAY);
		}
		public void fermerMessageJoueur()
		{
			this.removeAll();
			this.setVisible(false);
		}

	}
	public void simulerClicBouton3() {
		System.out.println("clic IA marche");
		JButton[] tabBtnJeton = this.panelGrille.getBtnJeton();
		// Créer une instance de l'IA avec la couleur du joueur actif
		//IA ia = new IA(this.ctrl.getJoueurActif().getCouleur());
		//Puissance4 jeu = ia.copierJeu();
		// Appeler la méthode jouerCoup pour choisir la colonne

		//int colonneChoisie = this.ctrl.getIA().jouerCoup(this.ctrl.getMetier());
		int colonneChoisie = this.ctrl.jouerIA();
		// Cliquer sur le bouton de la colonne choisie
		tabBtnJeton[colonneChoisie].doClick();
	}

}