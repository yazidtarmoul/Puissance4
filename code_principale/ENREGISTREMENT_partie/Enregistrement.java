package code_principale.ENREGISTREMENT_partie;
import BDD.DatabaseConnection;
import java.sql.Connection;

import java.sql.SQLException;
import java.sql.*;
import code_principale.JEU.JEU4;

import java.util.Stack;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;

import java.io.ObjectInputStream;
import java.sql.PreparedStatement;


public class Enregistrement
{
	private PileEnregistrement pileEnregistrement;

	public Enregistrement() {
		System.out.println("V-debut consctructeur Enregistrement");
		this.pileEnregistrement = new PileEnregistrement();
		System.out.println("V-constructeur after pile");
		try (Connection conn = DatabaseConnection.connect()) {
			System.out.println("V-onstructeur after connect");
			String sql = "SELECT nom, jeu_serialized FROM jeux";
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				System.out.println("V-first try");
				try (ResultSet rs = pstmt.executeQuery()) {
					System.out.println("V-second try");
					while (rs.next()) {
						System.out.println("V-debut while");
						String nomJeu = rs.getString("nom");
						byte[] serializedJeu = rs.getBytes("jeu_serialized");
						System.out.println("V-after serialization");
						GetterEnregistrement getterEnregistrement = deserialize(serializedJeu);
						System.out.println("V-after getter serialized");
						this.pileEnregistrement.ajouterJeu(getterEnregistrement);
						System.out.println("V-after pile add serial");
					}
				}
			}
		} catch (SQLException e) {
			System.out.print("V-catch constructeur");
			e.printStackTrace();
		}
	}
	private GetterEnregistrement deserialize(byte[] bytes) {
		System.out.println("V-debut serialize");
		try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			 ObjectInputStream ois = new ObjectInputStream(bis)) {
			System.out.println("V-after gros try");
			return (GetterEnregistrement) ois.readObject();
		} catch (Exception e) {
			System.out.println("V-catch deserialize");
			e.printStackTrace();
			return null;
		}
	}


	public Stack<GetterEnregistrement> getPileJeux()
	{
		return this.pileEnregistrement.getPileJeux();
	}

	public GetterEnregistrement chargerJeu(String nomJeu)
	{
		return this.pileEnregistrement.JeuRecup(nomJeu);
	}

	public void sauvegardeJeu(String nomJeu, JEU4 metier) {
		System.out.print("V-sauvegardeJEu enregistrement");

		GetterEnregistrement getterEnregistrement = new GetterEnregistrement(nomJeu, metier);
		System.out.print("V-after new getterEnregistrement");
		this.pileEnregistrement.ajouterJeu(getterEnregistrement);
		System.out.print("V-after Pile ajouterjeu");
		try (Connection conn = DatabaseConnection.connect()) {
			System.out.print("V-after connection in sauvegardeJeu");
			String sql = "INSERT INTO jeux (nom, jeu_serialized) VALUES (?, ?)";
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setString(1, nomJeu);
				pstmt.setBytes(2, serialize(getterEnregistrement));
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private byte[] serialize(Object obj) {
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
			 ObjectOutputStream oos = new ObjectOutputStream(bos)) {
			oos.writeObject(obj);
			return bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
