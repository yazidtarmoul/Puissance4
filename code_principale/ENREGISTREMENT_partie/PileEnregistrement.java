package code_principale.ENREGISTREMENT_partie;

import java.io.Serializable;
import java.util.Stack;

public class PileEnregistrement implements Serializable
{
	private Stack<GetterEnregistrement> pileGetterEnregistrement;

	public PileEnregistrement()
	{
		this.pileGetterEnregistrement = new Stack<GetterEnregistrement>();
	}

	public Stack<GetterEnregistrement> getPileJeux()
	{
		if (this.pileGetterEnregistrement.isEmpty())
		{
			return null;
		}
		return this.pileGetterEnregistrement;
	}

	public GetterEnregistrement JeuRecup(String nomJeu)
	{
		for(GetterEnregistrement getterEnregistrement : this.pileGetterEnregistrement)
		{
			if (getterEnregistrement.getNom().equals(nomJeu))
			{
				return getterEnregistrement;
			}
		}

		return null;
	}

	public void ajouterJeu(GetterEnregistrement getterEnregistrementAjoute)
	{
		boolean bOk = false;

		for(GetterEnregistrement getterEnregistrement : this.pileGetterEnregistrement)
		{
			if (getterEnregistrement.equals(getterEnregistrementAjoute))
			{
				bOk = true;
			}
		}

		if (!bOk)
		{
			this.pileGetterEnregistrement.push(getterEnregistrementAjoute);
		}
		else
		{
			for(int cpt = 0; cpt < this.pileGetterEnregistrement.size(); cpt++)
			{
				if (this.pileGetterEnregistrement.get(cpt).equals(getterEnregistrementAjoute))
				{
					this.pileGetterEnregistrement.set(cpt, getterEnregistrementAjoute);
				}
			}
		}
	}

	public String toString()
	{
		String sRet = "";

		for(GetterEnregistrement getterEnregistrement : this.pileGetterEnregistrement)
		{
			sRet += " " + getterEnregistrement.getNom() + "\n";
		}

		return sRet;
	}
}
