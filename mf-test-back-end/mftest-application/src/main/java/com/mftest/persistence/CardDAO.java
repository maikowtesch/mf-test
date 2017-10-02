package com.mftest.persistence;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.mftest.core.exception.PersistenceCoreException;
import com.mftest.core.persistence.CardPersistenceInterface;
import com.mftest.core.persistence.entity.Card;

public class CardDAO implements CardPersistenceInterface {

	@Autowired
	@Qualifier("myPersistence")
	EntityManager em;
	
	/**
	 * Save a card.
	 */
	@Override
	public long save(Card card) throws PersistenceCoreException {
		try {
			em.getTransaction().begin();
			em.persist(card);
			em.getTransaction().commit();
		} catch(EntityExistsException | IllegalArgumentException | IllegalStateException e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			
			throw new PersistenceCoreException(e.getMessage());
		}
		
		return card.getId();
	}

	/**
	 * Update an existing card.
	 */
	@Override
	public void update(Card card) throws PersistenceCoreException {
		try {
			em.getTransaction().begin();
			em.merge(card);
			em.getTransaction().commit();
		} catch(EntityExistsException | IllegalArgumentException | IllegalStateException e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			
			throw new PersistenceCoreException(e.getMessage());
		}
	}

	/**
	 * Find a card by its number.
	 */
	@Override
	public Card findByCardData(String number, long userId) throws PersistenceCoreException {
		String hql = "select c from Card c where c.cardNumber = :number and c.user.id = :userId";
		
		Query query = em.createQuery(hql);
		query.setParameter("number", number);
		query.setParameter("userId", userId);
		
		try {
			return (Card) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException e) {
			throw new PersistenceCoreException(e.getMessage());
		}
	}

	/**
	 * Find cards by a search string representing a card number (or part of it).<br>
	 * Return all cards that match the search string.
	 */
	@Override
	public List<Card> search(String searchString) throws PersistenceCoreException {
		return search(searchString, -1);
	}

	/**
	 * Find cards by a search string representing a card number (or part of it).<br>
	 * Return only the cards that belong to the specified user.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Card> search(String searchString, long userId) throws PersistenceCoreException {
		String hql = "select c from Card c where c.cardNumber like concat('%',:searchString,'%')";
		hql += (userId > 0) ? " and c.user.id = :userId" : "";
		
		Query query = em.createQuery(hql);
		query.setParameter("searchString", searchString);
		if (userId > 0) {
			query.setParameter("userId", userId);
		}
		
		try {
			return (List<Card>) query.getResultList();
		} catch (PersistenceException e) {
			throw new PersistenceCoreException(e.getMessage());
		}
	}

}
