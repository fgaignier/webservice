package com.univ.web;

import java.util.ArrayList;
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
	
	// voir tous les livres
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value = "/livres", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Livre> listOfLivres(){
		return entityManager.createQuery("select l from Livre l").getResultList();
	}
	
	// selectionner les livres par titre (non complet)
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value = "/livres/{titre}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Livre> aLivre(@PathVariable("titre") String titre) throws Exception{
		//return entityManager.createQuery("select l from Livre l where l.titre like '*" + titre + "*'").getResultList();
		TypedQuery<Livre> query = entityManager.createQuery("select l from Livre l where l.titre like :searchKeyword", Livre.class);
		query.setParameter("searchKeyword", "%"+titre+"%");
	    return query.getResultList();
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value = "/livres/{id}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Livre emprunt(@PathVariable(name="id") long id, @RequestParam(name="emprunter", required=true) boolean emprunt) {
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		try {
			Livre livre = (Livre) entityManager.createQuery("select l from Livre l where l.id like :id").setParameter("id", id ).getSingleResult();
			if(livre.getDisponibility()) {
				// terminer. Date de debut = aujourd'hui
				// retour 2 semaines plus tard
				Emprunt emprunt = new Emprunt();
				emprunt.setDate_emprunt(Calendar.getInstance().getTime());
				emprunts.add(emprunt3);
				emprunt3.setLivre(livre);
				livre.setDisponibility(true);
				} else {
					emprunts.remove(0);
					livre.setDisponibility(false);
				}
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

}
