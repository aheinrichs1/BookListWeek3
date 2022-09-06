package controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import model.ListItem;
import java.util.List;

/**
 * @author alexh - aheinrichs
 * CIS175 - Fall 2022
 * Sep 6, 2022
 */
public class ListItemHelper {
	//global instance of EntityManagerFactory
	static EntityManagerFactory emfactory = 
					Persistence.createEntityManagerFactory("BookList");

	//inserts item into table within database
	public void insertItem(ListItem li) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		em.persist(li);
		em.getTransaction().commit();
		em.close();
	}
	
	//returns a list of objects from the database
	public List<ListItem> showAllItems() {
		EntityManager em = emfactory.createEntityManager();
		List<ListItem> allItems = em.createQuery("SELECT i FROM ListItem i").getResultList();
		em.close();
		return allItems;
	}
	
	//deletes item from table within database
	public void deleteItem(ListItem toDelete) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<ListItem> typedQuery = em.createQuery("select li from ListItem li where li.title = :selectedTitle and li.author = :selectedAuthor and li.category = :selectedCategory", ListItem.class);
		
		//Substitute parameter with actual data from the toDelete item
		typedQuery.setParameter("selectedTitle", toDelete.getTitle());
		typedQuery.setParameter("selectedAuthor", toDelete.getAuthor());
		typedQuery.setParameter("selectedCategory", toDelete.getCategory());
		
		//we only want one result
		typedQuery.setMaxResults(1);
		
		//get the result and save it into a new list item
		ListItem result = typedQuery.getSingleResult();
		
		//remove it
		em.remove(result);
		em.getTransaction().commit();
		em.close();
	}

	
	public void updateItem(ListItem toEdit) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		em.merge(toEdit);
		em.getTransaction().commit();
		em.close();
	}
	
	public ListItem searchForItemById(int idToEdit) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		ListItem found = em.find(ListItem.class, idToEdit);
		em.close();
		return found;
	}

	public List<ListItem> searchForItemByTitle(String titleName) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<ListItem> typedQuery = em.createQuery("select li from ListItem li where li.title = :selectedTitle", ListItem.class);
		typedQuery.setParameter("selectedTitle", titleName);
		List<ListItem> foundItems = typedQuery.getResultList();
		em.close();
		return foundItems;
	}
	
	public List<ListItem> searchForItemByAuthor(String authorName) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<ListItem> typedQuery = em.createQuery("select li from ListItem li where li.author = :selectedAuthor", ListItem.class);
		typedQuery.setParameter("selectedAuthor", authorName);
		List<ListItem> foundItems = typedQuery.getResultList();
		em.close();
		return foundItems;
	}
	
	public List<ListItem> searchForItemByCategory(String categoryName) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<ListItem> typedQuery = em.createQuery("select li from ListItem li where li.category = :selectedCategory", ListItem.class);
		typedQuery.setParameter("selectedCategory", categoryName);
		List<ListItem> foundItems = typedQuery.getResultList();
		em.close();
		return foundItems;
	}
	
	public void cleanUp(){
		emfactory.close();
	}
}
