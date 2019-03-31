package it.polito.tdp.lab04.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;
import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SegreteriaStudentiController {
	
	CorsoDAO corsoDAO;
	StudenteDAO studenteDAO;
	Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> corsoChoiceBox;

    @FXML
    private Button btnCercaIscrittiCorso;

    @FXML
    private TextField txtFieldMatricola;

    @FXML
    private Button btnStudente;

    @FXML
    private TextField txtFieldNome;

    @FXML
    private TextField txtFieldCognome;

    @FXML
    private Button btnCercaCorsi;

    @FXML
    private Button btnIscrivi;

    @FXML
    private TextArea txtArea;

    @FXML
    private Button btnReset;

    @FXML
    void doCercaCorsi(ActionEvent event) {
    	txtArea.clear();
    	String nomeCorsoSelezionato = corsoChoiceBox.getSelectionModel().getSelectedItem();
    	if (model.cercaIscritti(nomeCorsoSelezionato).equals("") && !txtFieldMatricola.getText().equals("")) {
    		int matricola=0;
    		try {
    		matricola = Integer.parseInt(txtFieldMatricola.getText());
    		} catch(NumberFormatException e) {
    			txtArea.setText("Inserire un numero di matricola valido! Sono ammessi solo numeri 0-9 (no caratteri alfabetici/speciali).");
    			return;
    		}
    		
    		if(studenteDAO.autocompleta(matricola)==null) {
    			txtArea.setText("Errore: matricola studente inesistente!");
    			return;
    		}
    		
    		txtArea.appendText(studenteDAO.getCorsiByMatricola(matricola));
    	}
    	if(!model.cercaIscritti(nomeCorsoSelezionato).equals("") && !txtFieldMatricola.getText().equals("")) {
    		int matricola=0;
    		try {
    			matricola = Integer.parseInt(txtFieldMatricola.getText());
    		} catch(NumberFormatException e) {
    			txtArea.setText("Inserire un numero di matricola valido! Sono ammessi solo numeri 0-9 (no caratteri alfabetici/speciali).");
    			return;
    		}
    		
    		if(studenteDAO.autocompleta(matricola)==null) {
    			txtArea.setText("Errore: matricola studente inesistente!");
    			return;
    		}
    		
    		String codins = corsoDAO.getCodiceCorso(nomeCorsoSelezionato);
    		
    		if(studenteDAO.isIscritto(matricola, codins)) {
    			txtArea.appendText("Lo studente "+studenteDAO.getNomeCognomebyMatricola(matricola)+" ("+matricola+")"+" risulta iscritto al corso "+corsoDAO.getNomeCorso(codins)+" ("+codins+").");
    		}
    		else {
    			txtArea.appendText("Lo studente "+studenteDAO.getNomeCognomebyMatricola(matricola)+" ("+matricola+")"+" NON risulta iscritto al corso "+corsoDAO.getNomeCorso(codins)+" ("+codins+").");
    		}
    	}
    }

    @FXML
    void doCercaIscritti(ActionEvent event) {
    	txtArea.clear();
    	String nomeCorsoSelezionato = corsoChoiceBox.getSelectionModel().getSelectedItem();
    	if (model.cercaIscritti(nomeCorsoSelezionato).equals("")) {
    		txtArea.setText("Nessun corso selezionato!");
    	}
    	if(model.cercaIscritti(nomeCorsoSelezionato)!=null) {
    		txtArea.appendText(model.cercaIscritti(nomeCorsoSelezionato));
    	} 
    	else {
    		txtArea.appendText("Nessun corso selezionato!");
    	}
    }

    @FXML
    void doCercaStudente(ActionEvent event) {
    	txtFieldCognome.clear();
    	txtFieldNome.clear();
    	int matricola;
    	try{
    		matricola = Integer.parseInt(txtFieldMatricola.getText());
    	}catch(NumberFormatException e) {
    		txtArea.setText("Inserire un numero di matricola valido");
    		return;
    	}
    	if(studenteDAO.autocompleta(matricola)!=null) {
    		Studente s = studenteDAO.autocompleta(matricola);
    		txtFieldCognome.setText(s.getCognome());
        	txtFieldNome.setText(s.getNome());
    	}
    	else {
    		txtArea.setText("Studente inesistente");
    		return;
    	}
    	
    }

    @FXML
    void doIscriviStudente(ActionEvent event) {
    	String nomeCorsoSelezionato = corsoChoiceBox.getSelectionModel().getSelectedItem();
    	if(!model.cercaIscritti(nomeCorsoSelezionato).equals("") && !txtFieldMatricola.getText().equals("")) {
    		int matricola = Integer.parseInt(txtFieldMatricola.getText());
    		boolean iscrizioneConSuccesso = model.iscrivi(matricola, nomeCorsoSelezionato);
    		if(iscrizioneConSuccesso) {
    			txtArea.setText("Studente "+studenteDAO.getNomeCognomebyMatricola(matricola)+" ("+matricola+") iscritto correttamente al corso "+nomeCorsoSelezionato+" ("+corsoDAO.getCodiceCorso(nomeCorsoSelezionato)+").");
    		}
    		else {
    			txtArea.setText("Studente "+studenteDAO.getNomeCognomebyMatricola(matricola)+" ("+matricola+") già iscritto precedentemente al corso "+nomeCorsoSelezionato+" ("+corsoDAO.getCodiceCorso(nomeCorsoSelezionato)+").");
    		}
    	} 
    	else {
    		txtArea.setText("Errore: per iscrivere uno studente è necessario inserire un corso e una matricola!");
    	}
    }

    @FXML
    void doReset(ActionEvent event) {
    	txtArea.clear();
    	txtFieldNome.clear();
    	txtFieldCognome.clear();
    	txtFieldMatricola.clear();
    	corsoChoiceBox.setValue("");
    }

    @FXML
    void initialize() {
        assert corsoChoiceBox != null : "fx:id=\"corsoChoiceBox\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnCercaIscrittiCorso != null : "fx:id=\"btnCercaIscrittiCorso\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtFieldMatricola != null : "fx:id=\"txtFieldMatricola\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnStudente != null : "fx:id=\"btnStudente\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtFieldNome != null : "fx:id=\"txtFieldNome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtFieldCognome != null : "fx:id=\"txtFieldCognome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnCercaCorsi != null : "fx:id=\"btnCercaCorsi\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnIscrivi != null : "fx:id=\"btnIscrivi\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtArea != null : "fx:id=\"txtArea\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        corsoDAO = new CorsoDAO();
        studenteDAO= new StudenteDAO();
        model= new Model();
        ObservableList<String> corsi = FXCollections.observableArrayList();
        for(Corso c : corsoDAO.getTuttiICorsi()) {
        	corsi.add(c.getNome());
        }
        corsoChoiceBox.setValue("");
        corsoChoiceBox.setItems(corsi);

    }
}
