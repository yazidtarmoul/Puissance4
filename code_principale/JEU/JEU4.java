package code_principale.JEU;

import java.io.Serializable;
import java.util.Arrays;

import code_principale.ENREGISTREMENT_partie.GetterEnregistrement;

import code_principale.ENREGISTREMENT_partie.Enregistrement;

public class JEU4 implements Serializable
{
	private static final long serialVersionUID = 1L;
	private static final int NB_LIGNE_PAR_DEFAUT = 6;
	private static final int NB_COLONNE_PAR_DEFAUT = 7;

	private char[][] grille;
	private int nbLigne;
	private int nbColonne;
	private boolean partieGagne;
	private Joueur[] tabJoueur;
	private boolean couleurAffichage;

	public JEU4()
	{
		this.nbLigne = JEU4.NB_LIGNE_PAR_DEFAUT;
		this.nbColonne = JEU4.NB_COLONNE_PAR_DEFAUT;
		this.grille = new char[this.nbLigne][this.nbColonne];
		this.partieGagne = false;
		this.tabJoueur = new Joueur[2];
		this.couleurAffichage = false;

		for(int cptLigne=0; cptLigne<this.nbLigne; cptLigne++)
		{
			for(int cptColonne=0; cptColonne<this.nbColonne; cptColonne++)
			{
				this.grille[cptLigne][cptColonne] = 'V';
			}
		}

		this.initJoueurs();
	}

	public JEU4(String nomJeu)
	{
		GetterEnregistrement getterEnregistrement = new Enregistrement().chargerJeu(nomJeu);
		this.nbLigne = getterEnregistrement.getJeu().getNbLigne();
		this.nbColonne = getterEnregistrement.getJeu().getNbColonne();
		this.grille = new char[this.nbLigne][this.nbColonne];
		this.partieGagne = getterEnregistrement.getJeu().getPartieGagne();
		this.tabJoueur = new Joueur[2];
		this.couleurAffichage = getterEnregistrement.getJeu().getCouleurAffichage();

		for(int cptLigne=0; cptLigne<this.nbLigne; cptLigne++)
		{
			for(int cptColonne=0; cptColonne<this.nbColonne; cptColonne++)
			{
				this.grille[cptLigne][cptColonne] = getterEnregistrement.getJeu().getJeton(cptLigne, cptColonne);
			}
		}

		for(int cptJoueur = 0; cptJoueur<this.tabJoueur.length; cptJoueur++)
		{
			this.tabJoueur[cptJoueur] = getterEnregistrement.getJeu().getJoueur(cptJoueur);
		}
	}

	public int getNbLigne()
	{
		return this.nbLigne;
	}

	public int getNbColonne()
	{
		return this.nbColonne;
	}

	public int getNbJoueur()
	{
		return this.tabJoueur.length;
	}

	public char getJeton(int numLigne, int numColonne)
	{
		return this.grille[numLigne][numColonne];
	}

	public boolean getPartieGagne()
	{
		return this.partieGagne;
	}

	public boolean getGrillePleine()
	{
		for(int cptLigne=0; cptLigne<this.nbLigne; cptLigne++)
		{
			for(int cptColonne=0; cptColonne<this.nbColonne; cptColonne++)
			{
				if (this.grille[cptLigne][cptColonne] == 'V')
				{
					return false;
				}
			}
		}
		return true;
	}
	public char[][] getGrille() {
		char[][] copy = new char[nbLigne][];
		for (int i = 0; i < nbLigne; i++) {
			copy[i] = Arrays.copyOf(grille[i], nbColonne);
		}
		return copy;
	}

	public Joueur getJoueur(int indiceJoueur)
	{
		return this.tabJoueur[indiceJoueur];
	}

	public Joueur getJoueurActif()
	{
		for(int cptJoueur=0; cptJoueur<this.tabJoueur.length; cptJoueur++)
		{
			if (this.tabJoueur[cptJoueur].getActif())
			{
				return this.getJoueur(cptJoueur);
			}
		}
		return null;
	}

	public boolean getCouleurAffichage()
	{
		return this.couleurAffichage;
	}

	public boolean setJeton(int numColonne)
	{
		if (numColonne < 0 || numColonne > this.nbColonne)
		{
			return false;
		}

		if (this.grille[0][numColonne] != 'V')
		{
			return false;
		}

		int cptLigne = this.nbLigne - 1;
		while(this.grille[cptLigne][numColonne] != 'V' && cptLigne >= 0)
		{
			cptLigne--;
		}
		this.grille[cptLigne][numColonne] = this.getJoueurActif().getCouleur();

		return true;
	}

	public void setJoueurSuivant()
	{
		for (int cptJoueur=0; cptJoueur<this.tabJoueur.length; cptJoueur++)
		{
			this.tabJoueur[cptJoueur].setActif();
		}
	}

	public void setCouleurAffichage()
	{
		this.couleurAffichage = !this.couleurAffichage;
	}

	public void reinitialiserPartie()
	{
		for(int cptLigne=0; cptLigne<this.nbLigne; cptLigne++)
		{
			for(int cptColonne=0; cptColonne<this.nbColonne; cptColonne++)
			{
				this.grille[cptLigne][cptColonne] = 'V';
			}
		}
	}

	private void initJoueurs()
	{
		char[] tabCouleur = {'J', 'R'};
		int joueurCouleur = (int) (Math.random() * this.getNbJoueur());
		for (int cpt=0; cpt<this.tabJoueur.length; cpt++)
		{
			if (cpt%2 == 0)
			{
				this.tabJoueur[cpt] = new Joueur(tabCouleur[joueurCouleur]);
			}
			else
			{
				joueurCouleur = joueurCouleur < cpt ? ++joueurCouleur : --joueurCouleur;
				this.tabJoueur[cpt] = new Joueur(tabCouleur[joueurCouleur]);
			}
		}
		for (int cpt=0; cpt<this.tabJoueur.length; cpt++)
		{
			if (this.tabJoueur[cpt].getNumJoueur() > this.getNbJoueur())
			{
				this.tabJoueur[cpt].setNumJoueur(cpt+1);
			}
		}
		int joueurActif = (int) (Math.random() * this.getNbJoueur());
		this.tabJoueur[joueurActif].setActif();
	}

	public boolean estGagnant()
	{
		final int ligMax = this.nbLigne;
		final int colMax = this.nbColonne;
		for(int indiceLig=0; indiceLig<this.nbLigne; indiceLig++)
		{
			for(int indiceCol=0; indiceCol<this.nbColonne; indiceCol++)
			{
				if (this.grille[indiceLig][indiceCol] != 'V')
				{
					if ((indiceCol<=colMax && compterJeton(this.grille, indiceLig, indiceCol, 1, 1) == 4) || (indiceCol<=colMax && compterJeton(this.grille, indiceLig, indiceCol, -1, 1) == 4) || (indiceLig<=ligMax && compterJeton(this.grille, indiceLig, indiceCol, 0, 1) == 4) || (indiceLig<= ligMax && compterJeton(this.grille, indiceLig, indiceCol, 1, 0) == 4))
					{
						return true;
					}
				}
			}
		}
		return false;
	}

	private int compterJeton(char[][] grille, int lig, int col, int ligDir, int colDir)
	{
		int cpt = 0;
		int ligCpt = lig;
		int colCpt = col;
		while(ligCpt >= 0 && ligCpt < this.nbLigne && colCpt >= 0 && colCpt < this.nbColonne && grille[ligCpt][colCpt] == grille[lig][col])
		{
			ligCpt += ligDir;
			colCpt += colDir;
			cpt++;
		}
		return cpt;
	}
}
