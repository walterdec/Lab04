package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {
	
	StudenteDAO studenteDAO = new StudenteDAO();
	
	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				Corso corso = new Corso(codins, numeroCrediti, nome, periodoDidattico);
				corsi.add(corso);
			}
			conn.close();
			return corsi;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	public void getCorso(Corso corso) {
		try {
			Connection conn = ConnectDB.getConnection();
			String query = "SELECT * FROM corso WHERE codins = ?";
		
			PreparedStatement st = conn.prepareStatement(query);
			st.setString(1, corso.getCodins());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
			}
			
		} catch(SQLException e) {
			throw new RuntimeException("Errore Db");
		}
	}

	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public String getStudentiIscrittiAlCorso(Corso corso) {
try {
		Connection conn = ConnectDB.getConnection();
			
			String query = "SELECT * FROM iscrizione WHERE codins = ?";
			
			PreparedStatement st = conn.prepareStatement(query);
			st.setString(1, corso.getCodins());
			ResultSet rs = st.executeQuery();
			
			String result="";

			while (rs.next()) {
			/*	String sql = "SELECT * FROM studente WHERE matricola = ?";
				Connection con = ConnectDB.getConnection();
				PreparedStatement stat = con.prepareStatement(sql);
				st.setInt(1, rs.getInt("matricola"));
				ResultSet resultSet = st.executeQuery();
				*/
				
				result+=rs.getInt("matricola")+"\n";
			}
			conn.close();
			return result;
			
		} catch(SQLException e) {
			throw new RuntimeException("Errore Db");
		}
	}
	
	public String getCodiceCorso(String nomeCorso) {
		try{
			Connection conn = ConnectDB.getConnection();
			String query = " SELECT * FROM corso WHERE nome=? ";
			PreparedStatement st = conn.prepareStatement(query);
			st.setString(1, nomeCorso);
			ResultSet rs = st.executeQuery();
			
			String codice="";
			if(rs.next()) {
				codice = rs.getString("codins");
			}
			conn.close();
			return codice;
	
		}
		catch(SQLException e) {
			throw new RuntimeException("Errore DB");
		}
	}
}
