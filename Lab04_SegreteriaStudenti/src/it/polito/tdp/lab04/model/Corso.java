package it.polito.tdp.lab04.model;

public class Corso {
	
	private String codins;
	private int crediti;
	private String nome;
	private int pd;
	
	public Corso(String codins, int crediti, String nome, int pd) {
		this.codins = codins;
		this.crediti = crediti;
		this.nome = nome;
		this.pd = pd;
	}

	public String getCodins() {
		return codins;
	}

	public int getCrediti() {
		return crediti;
	}

	public String getNome() {
		return nome;
	}

	public int getPd() {
		return pd;
	}
	
	

}
