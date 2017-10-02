package com.mftest.persistence;

import java.util.ArrayList;
import java.util.List;

import com.mftest.core.exception.PersistenceCoreException;
import com.mftest.core.persistence.CardPersistenceInterface;
import com.mftest.core.persistence.entity.Card;
import com.mftest.core.persistence.entity.Role;
import com.mftest.core.persistence.entity.User;

public class CardDAO implements CardPersistenceInterface {

	/**
	 * Save a card.
	 */
	@Override
	public long save(Card card) throws PersistenceCoreException {
		return 0;
	}

	/**
	 * Update an existing card.
	 */
	@Override
	public void update(Card card) throws PersistenceCoreException {
		
	}

	/**
	 * Find a card by its number.
	 */
	@Override
	public Card findByCardData(String number, long userId) throws PersistenceCoreException {
		return null;
	}

	/**
	 * Find cards by a search string representing a card number (or part of it).<br>
	 * Return all cards that match the search string.
	 */
	@Override
	public List<Card> search(String searchString) throws PersistenceCoreException {
		return new ArrayList<>();
	}

	/**
	 * Find cards by a search string representing a card number (or part of it).<br>
	 * Return only the cards that belong to the specified user.
	 */
	@Override
	public List<Card> search(String searchString, long userId) throws PersistenceCoreException {
		List<Card> cards = new ArrayList<>();
		cards.add(new Card("2222333344445555", "Fulano", "22/12", new User("admin", "admin", new Role(1, "Admin"))));
		cards.add(new Card("3333444455556666", "Ciclano", "22/12", new User("admin", "admin", new Role(1, "Admin"))));
		cards.add(new Card("4444555566667777", "Beltrano", "22/12", new User("admin", "admin", new Role(1, "Admin"))));
		
		return cards;
	}

}
