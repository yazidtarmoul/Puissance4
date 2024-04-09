package code_principale.VUE;

import code_principale.ControleurOfficiel;

import java.awt.BorderLayout;

import javax.swing.*;

public class JframeAcc extends JFrame
{
	private static final long serialVersionUID = 1L;

	private JpanelAcc jpanelAcc;

	public JframeAcc(ControleurOfficiel ctrl, int largeurEcran, int hauteurEcran)
	{
		this.setTitle("Puissance -4");

		double coeffLargeur = (largeurEcran / (double) 900);
		double coeffHauteur = (hauteurEcran / (double) 500);
		int largeurFrameAccueil = (int) (largeurEcran / coeffLargeur);
		int hauteurFrameJoueur = (int) (hauteurEcran / coeffHauteur);

		this.setSize(largeurFrameAccueil,hauteurFrameJoueur);
		this.setLocation((largeurEcran/2)-(this.getWidth()/2),(hauteurEcran/2)-(this.getHeight()/2));
		this.setLayout( new BorderLayout() );

		this.jpanelAcc = new JpanelAcc(ctrl);

		this.add(jpanelAcc);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public void fermerAccueil()
	{
		this.jpanelAcc.fermerAccueil();
		this.removeAll();
		this.setVisible(false);
		this.dispose();
	}
}
