package com.mftest.core.persistence;

import java.util.List;

import com.mftest.core.exception.PersistenceCoreException;
import com.mftest.core.persistence.entity.Card;

/**
 * This interface specifies the persistence methods to be implemented by systems using the core.
 */
public interface CardPersistenceInterface {
	
	/**
	 * Save a card.
	 * @param card
	 * @return Newly created id.
	 * @throws PersistenceCoreException
	 */
	long save(Card card) throws PersistenceCoreException;
	
	/**
	 * Update an existing card.
	 * @param card
	 * @throws PersistenceCoreException
	 */
	void update(Card card) throws PersistenceCoreException;
	
	/**
	 * Find a card by its number.
	 * @param number
	 * @param userId
	 * @return null if couldn't find entity.
	 * @throws PersistenceCoreException
	 */
	Card findByCardData(String number, long userId) throws PersistenceCoreException;
	
	/**
	 * Find cards by a search string representing a card number (or part of it).<br>
	 * Return all cards that match the search string.
	 * @param searchString
	 * @return list of found cards.
	 * @throws PersistenceCoreException
	 */
	List<Card> search(String searchString) throws PersistenceCoreException;
	
	/**
	 * Find cards by a search string representing a card number (or part of it).<br>
	 * Return only the cards that belong to the specified user.
	 * @param searchString
	 * @param userId
	 * @return
	 * @throws PersistenceCoreException
	 */
	List<Card> search(String searchString, long userId) throws PersistenceCoreException;
	
}
