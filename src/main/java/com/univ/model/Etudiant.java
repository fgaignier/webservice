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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Etudiant {
	
	long numEtudiant;
	String nom;
	String prenom;
	List<Emprunt> emprunts = new ArrayList<Emprunt>();
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getNumEtudiant() {
		return numEtudiant;
	}
	
	public void setNumEtudiant(long numEtudiant) {
		this.numEtudiant = numEtudiant;
	}
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	@OneToMany(mappedBy="etudiant")
	@JsonIgnore
	public List<Emprunt> getEmprunts() {
		return emprunts;
	}

	public void setEmprunts(List<Emprunt> emprunts) {
		this.emprunts = emprunts;
	}
	
	


	
}
