package code_principale.JEU;

/*----L'-import-pour-la-serialisation----*/
import java.io.Serializable;

public class Joueur implements Serializable
{

	private static final long serialVersionUID = 1L;


	private boolean IA;
	private static 	int 	nbJoueur; 			// L'attribut renseigne sur le nombre de joueurs (numéro séquentiel auto-incrementé).
	private 		int 	numJoueur; 			// L'attribut renseigne sur le numéro du joueur.
	private 		String 	pseudoJoueur; 		// L'attribut renseigne sur le pseudo du joueur.
	private 		char 	couleur; 			// L'attribut renseigne sur la couleur du joueur (jaune ou rouge).
	private 		boolean estActif; 			// L'attribut renseigne si le joueur doit jouer.


	public Joueur(char couleur)
	{
		/*----------------------*/
		/* Les caractéristiques */
		/*----------------------*/
		this.numJoueur 		= ++Joueur.nbJoueur; 	// Le numéro du joueur.
		this.pseudoJoueur 	= "";					// Le joueur possède un pseudo qu'il communique à l'accueil du jeu.
		this.couleur 		= couleur; 				// La couleur du joueur (jaune ou rouge).
		this.estActif 		= false; 				// Au début de la partie, le joueur n'est pas actif.
	}
	public void setIA(boolean ia)
	{
		this.IA = ia;
	}
	public boolean estIA()
	{
		return this.IA;
	}


	public int getNumJoueur()
	{
		return this.numJoueur;
	}

	public String getPseudoJoueur()
	{
		return this.pseudoJoueur;
	}


	public char getCouleur()
	{
		return this.couleur;
	}


	public String getCouleurComplete()
	{
		if ( this.couleur == 'J' )
		{
			return "jaune";
		}

		if ( this.couleur == 'R' )
		{
			return "rouge";
		}

		return null;
	}


	public boolean getActif()
	{
		return this.estActif;
	}
	public void setNumJoueur(int numJoueur)
	{
		this.numJoueur = Joueur.nbJoueur = numJoueur;
	}


	public void setPseudo(String pseudo)
	{
		if ( pseudo != null )
		{
			this.pseudoJoueur = pseudo;
		}
		else
		{
			this.pseudoJoueur = "Joueur" + this.numJoueur;
		}
	}


	public void setCouleur(char couleur)
	{
		if ( couleur == 'J' || couleur == 'R' )
		{
			this.couleur = couleur;
		}
	}


	public void setActif()
	{
		if ( ! this.estActif )
		{
			this.estActif = true;
		}
		else
		{
			this.estActif = false;
		}
	}


	public void setActifJoueur(boolean bActifJoueur)
	{
		this.estActif = bActifJoueur;
	}


	public String toString()
	{
		return "Joueur numéro " + this.numJoueur + " pseudo : " + this.pseudoJoueur + " couleur : " + this.couleur + " estActif : " + this.estActif;
	}
}