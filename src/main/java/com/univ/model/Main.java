package com.univ.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Hello world!
 *
 */
public class Main {
    public static void main( String[] args ) {
    	String file = "C:\\Users\\Fabrice\\eclipse-workspace\\library\\listelivres.csv";
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("manager1");
		EntityManager entityManager = emf.createEntityManager();
		
		EntityTransaction tx = entityManager.getTransaction();
				
		try {
			tx.begin();
			
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
			   System.out.println(line);
			   List<String> items = Arrays.asList(line.split("\\s*;\\s*"));
			   Livre livre = new Livre();
			   livre.setAuteur(items.get(0));
			   livre.setTitre(items.get(1));
			   livre.setEditeur(items.get(2));
			   livre.setDisponibility(true);
			   
			   entityManager.persist(livre);
			   
			}
			
			Etudiant pierre = new Etudiant();
			pierre.setNom("Dupont");
			pierre.setPrenom("Pierre");
			pierre.setNumEtudiant(123445);
			entityManager.persist(pierre);
			
			Etudiant anne = new Etudiant();
			anne.setNom("Dupond");
			anne.setPrenom("Anne");
			anne.setNumEtudiant(1978666);
			entityManager.persist(anne);
			
			Etudiant julie = new Etudiant();
			julie.setNom("Glgghjg");
			julie.setPrenom("Julie");
			julie.setNumEtudiant(354576865);
			entityManager.persist(julie);
			
			Etudiant paul = new Etudiant();
			paul.setNom("Durant");
			paul.setPrenom("Paul");
			paul.setNumEtudiant(353465768);
			entityManager.persist(paul);
			
			tx.commit();
			br.close();
		} catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
    	
	}
}
