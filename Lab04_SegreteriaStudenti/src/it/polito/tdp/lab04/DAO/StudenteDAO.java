package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {
	
	
	public Studente autocompleta(int matricola){
		try {
			Connection conn = ConnectDB.getConnection();
			String sql = "SELECT * FROM studente WHERE matricola = ? ";
			
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, matricola);
			ResultSet rs = st.executeQuery();
			
			Studente s=null;
			while (rs.next()) {
				s = new Studente (matricola, rs.getString("cognome"), rs.getString("nome"), rs.getString("cds"));
			}
			rs.close();
			st.close();
			conn.close();
			return s;
			
		} catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore DB");
			
		}
	}
	
	public String getCorsiByMatricola(int matricola){
		try{
			Connection conn = ConnectDB.getConnection();
			String sql = "SELECT * FROM iscrizione WHERE matricola = ? ";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, matricola);
			ResultSet rs = st.executeQuery();
			
			String result="";
			
			while (rs.next()) {
				String query = "SELECT * FROM corso WHERE codins = ?";
				PreparedStatement stat = conn.prepareStatement(query);
				stat.setString(1, rs.getString("codins"));
				ResultSet rs2 = stat.executeQuery();
				
				if (rs2.next()) {
					result+=rs.getString("codins")+"           "+rs2.getInt("crediti")+"             "+rs2.getString("nome")+"            "+rs2.getInt("pd")+"\n";
				}
				
			}
			conn.close();
			return result;
			
			
		} catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore DB");
		}
	}
	
	
	
	public boolean isIscritto(int matricola, String codins) {
		try{
			Connection conn = ConnectDB.getConnection();
			String sql = "SELECT * FROM iscrizione WHERE matricola = ? AND codins = ? ";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, matricola);
			st.setString(2, codins);
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				return true;
			}
				return false;
		} catch(SQLException e) {
			throw new RuntimeException("Errore DB");
		}
		
	}
	
	public void iscrivi(int matricola, String codins) {
		try{
			Connection conn = ConnectDB.getConnection();
			String sql = "INSERT INTO iscrizione VALUES (?, ?)";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, matricola);
			st.setString(2, codins);
			st.execute();
		
		} catch(SQLException e) {
			e.printStackTrace();
			return;
		}
	}
		
		
	
	public String getNomeCognomebyMatricola(int matricola) {
		try{
			String query = "SELECT * FROM studente WHERE matricola = ? ";
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, matricola);
			ResultSet rs = st.executeQuery();
			
			String nome = "";
			while(rs.next()) {
				nome = rs.getString("cognome")+" "+rs.getString("nome");
			}
			conn.close();
			return nome;
			
		} catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
}
