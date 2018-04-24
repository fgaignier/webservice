package com.univ.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Livre {

	private long id;
	private String titre;
	private String auteur;
	private String editeur;
	private List<Emprunt> emprunts = new ArrayList<Emprunt>();
	private boolean disponibility;
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	
	public String getEditeur() {
		return editeur;
	}

	public void setEditeur(String editeur) {
		this.editeur = editeur;
	}

	@OneToMany(mappedBy="livre", cascade=CascadeType.ALL)
	public List<Emprunt> getEmprunts() {
		return emprunts;
	}

	public void addEmprunt(Emprunt e) {
		emprunts.add(e);
	}
	
	public void setReturned(Date date_retour) {
		this.setDisponibility(true);
		for(int i = 0; i<emprunts.size();i++) {
			Emprunt e = emprunts.get(i);
			if(!e.isReturned()) {
				e.setReturned(true);
				e.setDate_retour(date_retour);
			}
		}
	}
	
	public void setEmprunts(List<Emprunt> emprunts) {
		this.emprunts = emprunts;
	}

	public boolean getDisponibility() {
		return disponibility;
	}

	public void setDisponibility(boolean disponibility) {
		this.disponibility = disponibility;
	}


	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}
	

	public String getAuteur() {
		return auteur;
	}

	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}


	
	
}

