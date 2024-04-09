package code_principale.VUE;

import code_principale.ControleurOfficiel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JpanelMenu extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private ControleurOfficiel ctrl;
	private JMenuBar menuBar;
	private JMenu menuPartie;
	private JMenuItem menuItemEnregistrer;
	private JMenuItem menuItemOuvrir;
	private JMenuItem menuItemQuitter;
	private JMenuItem menuItemGameGuides; // L'élément de menu pour les guides de jeu
	private Object menuItemRegles;

	public JpanelMenu(ControleurOfficiel ctrl) {
		this.ctrl = ctrl;
		setLayout(new FlowLayout());
		setBackground(Color.DARK_GRAY);

		// Création de la barre de menu
		menuBar = new JMenuBar();
		menuBar.setBackground(Color.lightGray);
		menuBar.setBorder(BorderFactory.createLineBorder(Color.lightGray));

		// Création et configuration du menu Partie
		menuPartie = new JMenu("Menu");
		stylizeMenu(menuPartie, new Font("Calibri", Font.BOLD, 20), Color.WHITE);

		// Création des éléments de menu
		menuItemEnregistrer = new JMenuItem("Enregistrer");
		menuItemOuvrir = new JMenuItem("Ouvrir");
		menuItemGameGuides = new JMenuItem("Game Guides");
		menuItemQuitter = new JMenuItem("Quitter");

		// Ajout des éléments au menu Partie
		menuPartie.add(menuItemEnregistrer);
		menuPartie.add(menuItemOuvrir);
		// Ajout d'un séparateur visuel avant l'option Quitter
		menuPartie.addSeparator();
		menuPartie.add(menuItemGameGuides);
		menuPartie.add(menuItemQuitter);

		// Ajout du menu à la barre de menu
		menuBar.add(menuPartie);

		// Configuration des éléments de menu
		stylizeMenuItem(menuItemEnregistrer, new Font("Calibri", Font.PLAIN, 17), Color.WHITE, Color.DARK_GRAY);
		stylizeMenuItem(menuItemOuvrir, new Font("Calibri", Font.PLAIN, 17), Color.WHITE, Color.DARK_GRAY);
		stylizeMenuItem(menuItemGameGuides, new Font("Calibri", Font.PLAIN, 17), Color.WHITE, Color.DARK_GRAY);
		stylizeMenuItem(menuItemQuitter, new Font("Calibri", Font.PLAIN, 17), Color.WHITE, Color.DARK_GRAY);

		// Ajout de la barre de menu au panel
		add(menuBar);

		// Configuration des écouteurs d'événements
		menuItemEnregistrer.addActionListener(this);
		menuItemOuvrir.addActionListener(this);
		menuItemQuitter.addActionListener(this);
		menuItemGameGuides.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				afficherGuideJeu();
			}
		});

		setVisible(true);
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
		guideDialog.setMaximumSize(new Dimension(600, 500)); // Ajustez selon vos besoins
		guideDialog.pack();
		guideDialog.setLocationRelativeTo(null);
		guideDialog.setVisible(true);
	}
	private void stylizeMenu(JMenu menu, Font font, Color textColor) {
		menu.setFont(font);
		menu.setForeground(textColor);
	}

	private void stylizeMenuItem(JMenuItem menuItem, Font font, Color textColor, Color bgColor) {
		menuItem.setFont(font);
		menuItem.setForeground(textColor);
		menuItem.setBackground(bgColor);
	}
	public void fermerPanelMenu()
	{
		this.removeAll();
		this.setVisible(false);
	}
	public void actionPerformed(ActionEvent e)
	{
		/*-----------------------------*/
		/* Enregistrement d'une partie */
		/*-----------------------------*/ 
		if ( e.getSource() == this.menuItemEnregistrer)
		{
			String nomJeu = JOptionPane.showInputDialog(this,"Entrer le nom de la partie : ", "Enregistrement d'une partie", JOptionPane.QUESTION_MESSAGE);

			if (nomJeu != null && !nomJeu.equals("")) {
				this.ctrl.sauvegarder(nomJeu);
				JOptionPane.showMessageDialog(this, "La partie '" + nomJeu + "' a été enregistrée.", "Enregistrement réussi", JOptionPane.INFORMATION_MESSAGE);
			} else if (nomJeu != null) {
				JOptionPane.showMessageDialog(this, "Vous devez entrer un nom pour la partie.", "Enregistrement annulé", JOptionPane.WARNING_MESSAGE);
			}

		}
		/*------------------------------------*/
		/* Ouverture d'une partie enregistrée */
		/*------------------------------------*/
		if ( e.getSource() == this.menuItemOuvrir) 
		{
			String[] ensJeux = this.ctrl.getJeuxEnregistres();
			if( ! (ensJeux == null) )
			{
				JComboBox<String> cbox = new JComboBox<>(ensJeux);
				cbox.setEditable(true);
				int ouvertureOk = JOptionPane.showConfirmDialog(this, cbox, "Ouverture d'une partie", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if ( ouvertureOk == JOptionPane.OK_OPTION)
				{
					Object partieEnregistre = cbox.getSelectedItem();
					this.ctrl.ouvrir(partieEnregistre.toString());
				}
			}
			else
			{
				JOptionPane.showMessageDialog(this, "Aucune partie enregistrée", "Ouverture d'une partie", JOptionPane.ERROR_MESSAGE);
			}
		}
		/*-------------------*/
		/* Quitter la partie */
		/*-------------------*/
		if ( e.getSource() == this.menuItemQuitter) 
		{
			this.ctrl.fermerJeu();
		}
	}
}