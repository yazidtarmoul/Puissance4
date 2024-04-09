package code_principale;
import code_principale.JEU.IA;
import code_principale.JEU.JEU4;
import code_principale.JEU.Joueur;
import code_principale.VUE.JframeAcc;
import code_principale.VUE.JframePlat;
import java.awt.Dimension;
import code_principale.ENREGISTREMENT_partie.GetterEnregistrement;

import code_principale.ENREGISTREMENT_partie.Enregistrement;
import java.util.Stack;

public class ControleurOfficiel
{
	private JEU4 metier;
	private IA IAjeu;

	private JframeAcc ihmAccueil;
	private JframePlat ihmPlateau;

	public ControleurOfficiel()
	{
		Dimension dimEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int hauteurEcran = (int) dimEcran.getHeight();
		int largeurEcran = (int) dimEcran.getWidth();

		this.ihmAccueil = new JframeAcc(this, largeurEcran, hauteurEcran);
	}

	public void Lancement(String pseudoJoueur1, String pseudoJoueur2, boolean IA)
	{
		BDD.ScoreManager.ajouterOuMettreAJourJoueur(pseudoJoueur1);
		BDD.ScoreManager.ajouterOuMettreAJourJoueur(pseudoJoueur2);
		Dimension dimEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int hauteurEcran = (int) dimEcran.getHeight();
		int largeurEcran = (int) dimEcran.getWidth();

		if (this.metier == null)
		{
			this.metier = new JEU4();
		}

		this.ihmPlateau = new JframePlat(this, largeurEcran, hauteurEcran);

		for(int cptJoueur = 0; cptJoueur < this.getNbJoueur(); cptJoueur++)
		{
			if ((cptJoueur % 2) == 0)
			{
				this.getJoueur(cptJoueur).setPseudo(pseudoJoueur1);
			}
			else
			{
				this.getJoueur(cptJoueur).setPseudo(pseudoJoueur2);
				this.getJoueur(cptJoueur).setIA(IA);

			}
		}

		this.majGraphique();
	}
	public int jouerIA() {
		IA ia = new IA();
		char[][] grille = this.metier.getGrille();

		// Affichage de la grille
		for (int i = 0; i < grille.length; i++) {
			for (int j = 0; j < grille[i].length; j++) {
				System.out.print(grille[i][j] + " ");
			}
			System.out.println();
		}

		int colonneChoisie = ia.jouerCoup(grille);
		return colonneChoisie;
	}

	public void Relancement(Joueur joueur1, Joueur joueur2)
	{
		Dimension dimEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int hauteurEcran = (int) dimEcran.getHeight();
		int largeurEcran = (int) dimEcran.getWidth();

		for(int cptJoueur = 0; cptJoueur < this.getNbJoueur(); cptJoueur++)
		{
			if ((cptJoueur % 2) == 0)
			{
				this.getJoueur(cptJoueur).setPseudo(joueur1.getPseudoJoueur());
				this.getJoueur(cptJoueur).setCouleur(joueur1.getCouleur());
				this.getJoueur(cptJoueur).setNumJoueur(joueur1.getNumJoueur());
				this.getJoueur(cptJoueur).setActifJoueur(joueur1.getActif());
			}
			else
			{
				this.getJoueur(cptJoueur).setPseudo(joueur2.getPseudoJoueur());
				this.getJoueur(cptJoueur).setCouleur(joueur2.getCouleur());
				this.getJoueur(cptJoueur).setNumJoueur(joueur2.getNumJoueur());
				this.getJoueur(cptJoueur).setActifJoueur(joueur2.getActif());
			}
		}

		this.majGraphique();
	}


	public int getNbLigne()
	{
		return this.metier.getNbLigne();
	}

	public int getNbColonne()
	{
		return this.metier.getNbColonne();
	}

	public int getNbJoueur()
	{
		return this.metier.getNbJoueur();
	}

	public char getJeton(int numLigne, int numColonne)
	{
		return this.metier.getJeton(numLigne, numColonne);
	}



	public boolean getGrillePleine()
	{
		return this.metier.getGrillePleine();
	}

	public Joueur getJoueur(int indiceJoueur)
	{
		return this.metier.getJoueur(indiceJoueur);
	}

	public Joueur getJoueurActif()
	{
		return this.metier.getJoueurActif();
	}



	public boolean estGagnant()
	{
		return this.metier.estGagnant();
	}

	public boolean setJeton(int numColonne)
	{
		return this.metier.setJeton(numColonne);
	}

	public void setJoueurSuivant()
	{
		this.metier.setJoueurSuivant();
		//System.out.println(this.metier.getJoueurActif());
		if (getJoueurActif().estIA()) {
			ihmPlateau.simulerClicBouton3();
		}
	}

	public void reinitialiserPartie()
	{
		this.metier.reinitialiserPartie();
	}

	public void majGraphique()
	{
		this.ihmPlateau.majGraphique();
	}

	public void setModeSombre()
	{
		this.metier.setCouleurAffichage();
		this.ihmPlateau.setModeSombre();
	}
	public void fermerAccueil()
	{
		this.ihmAccueil.fermerAccueil();
	}

	public void fermerJeu()
	{
		this.ihmAccueil = null;
		this.metier = null;
		this.ihmPlateau.fermerFramePlateau();
		this.ihmPlateau = null;
	}

	private void nettoyerJeuOuverture()
	{
		this.metier = null;
		if (this.ihmPlateau != null)
		{
			this.ihmPlateau.fermerFramePlateau();
		}
		this.ihmPlateau = null;
	}

	public String[] getJeuxEnregistres()
	{
		Stack<GetterEnregistrement> ensJeuxes = new Enregistrement().getPileJeux();
		if (ensJeuxes != null)
		{
			String[] tabNomJeu = new String[ensJeuxes.size()];
			for(int cpt = 0; cpt < tabNomJeu.length; cpt++)
			{
				tabNomJeu[cpt] = ensJeuxes.get(cpt).getNom();
			}
			return tabNomJeu;
		}
		return null;
	}

	public void sauvegarder(String nomJeu)
	{
		System.out.println("V-Ctrl BEFORE appel");
		Enregistrement enregistrement = new Enregistrement();
		System.out.println("V-Ctrl after appel");
		enregistrement.sauvegardeJeu(nomJeu, this.metier);
	}

	public void ouvrir(String nomJeu)
	{
		nettoyerJeuOuverture();
		Dimension dimEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int hauteurEcran = (int) dimEcran.getHeight();
		int largeurEcran = (int) dimEcran.getWidth();
		if (this.metier == null && this.ihmPlateau == null)
		{
			this.metier = new JEU4(nomJeu);
			this.ihmPlateau = new JframePlat(this, largeurEcran, hauteurEcran);
		}
		if (this.metier.getCouleurAffichage())
		{
			this.setModeSombre();
		}
		this.majGraphique();
	}

	public static void main(String[] args)
	{
		new ControleurOfficiel();
	}
}
