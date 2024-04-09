package code_principale.ENREGISTREMENT_partie;

import code_principale.JEU.JEU4;
import java.io.Serializable;

public class GetterEnregistrement implements Serializable
{
	private String nomJeu;
	private JEU4 metier;

	public GetterEnregistrement(String nomJeu, JEU4 metier)
	{
		this.nomJeu = nomJeu;
		this.metier = metier;
	}

	public String getNom()
	{
		return this.nomJeu;
	}

	public JEU4 getJeu()
	{
		return this.metier;
	}

	public boolean equals(GetterEnregistrement getterEnregistrement)
	{
		return this.nomJeu.equals(getterEnregistrement.getNom());
	}

	public String toString()
	{
		String sRet = "";
		sRet += " nom jeu  : " + this.nomJeu + "\n";
		return sRet;
	}
}
