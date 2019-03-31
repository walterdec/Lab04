package it.polito.tdp.lab04.model;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {
	
	CorsoDAO corsoDAO = new CorsoDAO();
	StudenteDAO studenteDAO = new StudenteDAO();
	
	public String cercaIscritti(String nomeCorsoSelezionato) {
		String result="";
    	Corso corsoSelezionato=null;
    	if(nomeCorsoSelezionato.equals("")) {
    		return "";
    	}
    	for(Corso c : corsoDAO.getTuttiICorsi()) {
        	if(c.getNome().equals(nomeCorsoSelezionato)) {
        		corsoSelezionato=c;
        	}
        }
    	if(corsoSelezionato!=null) {
    		result = corsoDAO.getStudentiIscrittiAlCorso(corsoSelezionato);
    		return result;
    	}
    	return result;
	}

	public boolean iscrivi(int matricola, String nomeCorsoSelezionato) {
		String codins = corsoDAO.getCodiceCorso(nomeCorsoSelezionato);
		if(!studenteDAO.isIscritto(matricola, codins)) {
			studenteDAO.iscrivi(matricola, codins);
			return true;
		}
		else {
			return false;
		}
	}

}
