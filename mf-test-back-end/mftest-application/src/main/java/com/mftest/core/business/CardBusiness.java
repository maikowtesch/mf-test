package com.mftest.core.business;

import java.util.ArrayList;
import java.util.List;

import com.mftest.core.boundary.CardBusinessInterface;
import com.mftest.core.enumerations.OperationStatus;
import com.mftest.core.enumerations.Roles;
import com.mftest.core.exception.InvalidFieldCoreException;
import com.mftest.core.exception.PersistenceCoreException;
import com.mftest.core.exception.UserNotFoundCoreException;
import com.mftest.core.persistence.CardPersistenceInterface;
import com.mftest.core.persistence.UserPersistenceInterface;
import com.mftest.core.persistence.entity.Card;
import com.mftest.core.persistence.entity.User;
import com.mftest.core.responsemodel.CardInfo;
import com.mftest.core.validators.CardValidator;

/**
 * Implements the business methods exposed by the interface.
 */
public class CardBusiness implements CardBusinessInterface {

	private static final String USER_DOESN_T_EXIST = "User doesn't exist";

	// Persistence interfaces to be injected with custom implementations.
	CardPersistenceInterface cardPersistence;
	UserPersistenceInterface userPersistence;

	/**
	 * Allows clients to specify a custom implementation for persistence interfaces.
	 */
	public CardBusiness(CardPersistenceInterface cardPersistence, UserPersistenceInterface userPersistence) {
		this.cardPersistence = cardPersistence;
		this.userPersistence = userPersistence;
	}

	/**
	 * Insert a new card, or update if it already exists.
	 */
	public OperationStatus insertCard(long userId, String cardNumber, String holderName, String expiryDate)
			throws UserNotFoundCoreException, PersistenceCoreException, InvalidFieldCoreException {

		// Check if the user exists
		User user = userPersistence.find(userId);
		if (user == null) {
			throw new UserNotFoundCoreException(USER_DOESN_T_EXIST);
		}
		
		// Validate the fields
		if (!CardValidator.validateCardNumber(cardNumber)) {
			throw new InvalidFieldCoreException("Invalid card number.");
		}
		if (!CardValidator.validateExpiryDate(expiryDate)) {
			throw new InvalidFieldCoreException("Invalid expiry date.");
		}

		// Check if the card exists
		Card card = cardPersistence.findByCardData(cardNumber, userId);

		if (card == null) {
			// Insert a new card
			card = new Card(cardNumber, holderName, expiryDate, user);
			cardPersistence.save(card);
			return OperationStatus.INSERTED;
		} else {
			// Update existing card
			card.setExpiryDate(expiryDate);
			cardPersistence.update(card);
			return OperationStatus.UPDATED;
		}
	}

	/**
	 * Perform a card search.
	 */
	public List<CardInfo> searchCard(long userId, String searchString)
			throws UserNotFoundCoreException, PersistenceCoreException {

		// Check if the user exists
		User user = userPersistence.find(userId);
		if (user == null) {
			throw new UserNotFoundCoreException(USER_DOESN_T_EXIST);
		}

		List<Card> cards = new ArrayList<>();
		if (user.getRole().getId() == Roles.ADMIN.getCode()) {
			// If the user is Admin, search all cards
			cards = cardPersistence.search(searchString);
		} else {
			// If the user is NOT Admin, search only his cards
			cards = cardPersistence.search(searchString, userId);
		}

		List<CardInfo> cardInfos = new ArrayList<>();

		// Convert the returning entity into the appropriate type in the response model
		cards.forEach((card) -> cardInfos
				.add(new CardInfo(card.getCardNumber(), card.getHolderName(), card.getExpiryDate())));

		return cardInfos;
	}

}
