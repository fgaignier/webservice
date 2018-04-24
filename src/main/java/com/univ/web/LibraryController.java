package com.univ.web;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.univ.model.Livre;
import com.univ.model.Etudiant;
import com.univ.model.Emprunt;

@RestController
public class LibraryController {
		
	EntityManager entityManager;
	
	public LibraryController(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("manager1");
		entityManager = emf.createEntityManager();
	}
	
	// retourne tous les etudiants
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value = "/etudiants", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Etudiant> listOfStudents(){
		List<Etudiant> etudiants = entityManager.createQuery("select e from Etudiant e").getResultList();
		return etudiants;
	}

	// retourne un etudiant
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value = "/etudiant/{numEtudiant}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Etudiant getStudent(@PathVariable("numEtudiant") long numEtudiant){
		Etudiant etudiant = (Etudiant)entityManager.find(Etudiant.class, numEtudiant);
		return etudiant;
	}

		
	// voir tous les livres
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value = "/livres", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Livre> listOfLivres(){
		List<Livre> livres = entityManager.createQuery("select l from Livre l").getResultList();
		
		return livres;
	}
		
	// selectionner les livres par titre et/ou auteur
	// ou titre partiel et/ou auteur partiel
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value = "/livres/selection", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Livre> livreSelection(@RequestParam("auteur") String auteur, @RequestParam("titre") String titre) throws Exception{
		List<Livre> livres;
		if(!auteur.equals("") && titre.equals("")) {
			TypedQuery<Livre> query = entityManager.createQuery("select l from Livre l where l.auteur like :searchKeyword", Livre.class);
			query.setParameter("searchKeyword", "%"+auteur+"%");
			livres = query.getResultList();
		} else if(!titre.equals("") && auteur.equals("")) {
			TypedQuery<Livre> query = entityManager.createQuery("select l from Livre l where l.titre like :searchKeyword", Livre.class);
			query.setParameter("searchKeyword", "%"+titre+"%");
		    livres = query.getResultList();
		} else {
			TypedQuery<Livre> query = entityManager.createQuery("select l from Livre l where l.auteur like :searchAuteur and l.titre like :searchTitre", Livre.class);
			query.setParameter("searchAuteur", "%"+auteur+"%");
			query.setParameter("searchTitre", "%"+titre+"%");
			livres = query.getResultList();
		}
		
		return livres;
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value = "/livres/{id}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Livre emprunt(@PathVariable(name="id") long id, @RequestParam("sid") long student_id) {
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		try {
			//Etudiant etudiant = (Etudiant) entityManager.createQuery("select e from Etudiant e  where e.numEtudiant like :id").setParameter("id", student_id ).getSingleResult();
			Etudiant etudiant = (Etudiant) entityManager.find(Etudiant.class, student_id);
			Livre livre = (Livre) entityManager.createQuery("select l from Livre l where l.id like :id").setParameter("id", id ).getSingleResult();
			if(livre.getDisponibility()) {
				// Date d'emprunt = aujourd'hui
				// retour 2 semaines plus tard
				Emprunt e = new Emprunt();
				Date today = new Date();
				System.out.println(today);
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, 7);
				Date retour = cal.getTime();
				System.out.println(retour);
				e.setDate_emprunt(today);
				e.setDate_retour(retour);
				e.setEtudiant(etudiant);
				e.setLivre(livre);
				livre.addEmprunt(e);
				livre.setDisponibility(false);
				entityManager.persist(livre);
				tx.commit();
				return livre;
			}
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}
		return null;
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value = "/livres/{id}", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Livre retour(@PathVariable(name="id") long id) {
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		try {
			Livre livre = (Livre) entityManager.createQuery("select l from Livre l where l.id like :id").setParameter("id", id ).getSingleResult();
			//livre.setDisponibility(true);
			Date today = new Date();
			livre.setReturned(today);
			entityManager.persist(livre);
			tx.commit();
			return livre;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}
		return null;
	}
}
