package com.univ.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Emprunt {
	
	private Date date_emprunt;
	private Date date_retour;
	private long idEmprunt;
	private Etudiant etudiant;
	private Livre livre;
	
		
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getIdEmprunt() {
		return idEmprunt;
	}
	
	public void setIdEmprunt(long idemprunt) {
		this.idEmprunt = idemprunt;
	}
	
	@Temporal(TemporalType.DATE)
	public Date getDate_emprunt() {
		return date_emprunt;
	}

	public void setDate_emprunt(Date date) {
		this.date_emprunt = date;
	}

	@Temporal(TemporalType.DATE)
	public Date getDate_retour() {
		return date_retour;
	}

	public void setDate_retour(Date date_retour) {
		this.date_retour = date_retour;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	public Etudiant getEtudiant() {
		return etudiant;
	}

	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}

	@ManyToOne
	@JsonIgnore
	public Livre getLivre() {
		return livre;
	}

	public void setLivre(Livre livre) {
		this.livre = livre;
	}

	
	
}
