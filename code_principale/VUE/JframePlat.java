package code_principale.VUE;

import code_principale.ControleurOfficiel;

import java.awt.BorderLayout;
import java.awt.event.*;

import javax.swing.*;

public class JframePlat extends JFrame
{
	private static final long serialVersionUID = 1L;

	private ControleurOfficiel ctrl;
	private JpanelMenu jpanelMenu;
	private JpanelPlat jpanelPlat;

	public JframePlat(ControleurOfficiel ctrl, int largeurEcran, int hauteurEcran)
	{
		this.ctrl = ctrl;
		this.setTitle("Le jeu Puissance 4");
		double coeffLargeur = (largeurEcran / (double) 700);
		double coeffHauteur = (hauteurEcran / (double) 600);

		int largeurFrameJeu = (int) (largeurEcran / coeffLargeur);
		int hauteurFrameJeu = (int) (hauteurEcran / coeffHauteur);

		this.setSize(largeurFrameJeu, hauteurFrameJeu);
		this.setLocation((largeurEcran/2)-(this.getWidth()/2), (hauteurEcran/2)-(this.getHeight()/2));
		this.setLayout(new BorderLayout());

		this.jpanelMenu = new JpanelMenu(ctrl);
		this.jpanelPlat = new JpanelPlat(ctrl);

		this.add(jpanelMenu, BorderLayout.NORTH);
		this.add(jpanelPlat, BorderLayout.CENTER);

		this.addWindowListener(new FermetureFrame());

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	public void simulerClicBouton3() {
		jpanelPlat.simulerClicBouton3();
	}

	public void majGraphique()
	{
		this.jpanelPlat.majGraphique();
	}

	public void setModeSombre()
	{

		this.jpanelPlat.setModeSombre();
	}

	public void fermerFramePlateau()
	{
		this.jpanelMenu.fermerPanelMenu();
		this.jpanelPlat.fermerPanelPlateau();
		this.dispose();
	}

	private class FermetureFrame extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			int confirmed = JOptionPane.showConfirmDialog(
					null,
					"Voulez-vous vraiment quitter le jeu ?",
					"Confirmation de fermeture",
					JOptionPane.YES_NO_OPTION
			);

			if (confirmed == JOptionPane.YES_OPTION) {
				JframePlat.this.ctrl.fermerJeu();
			}
		}
	}

}
