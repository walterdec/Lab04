package it.polito.tdp.lab04.model;

public class Studente {
	
	private int matricola;
	private String cognome;
	private String nome;
	private String cds;
	
	public Studente(int matricola, String nome, String cognome, String cds) {
		this.matricola = matricola;
		this.cognome = nome;
		this.nome = cognome;
		this.cds=cds;
	}

	public int getMatricola() {
		return matricola;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public String getCds() {
		return cds;
	}
	
}
