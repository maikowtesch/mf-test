package com.mftest.core.boundary;

import java.util.List;

import com.mftest.core.enumerations.OperationStatus;
import com.mftest.core.exception.InvalidFieldCoreException;
import com.mftest.core.exception.PersistenceCoreException;
import com.mftest.core.exception.UserNotFoundCoreException;
import com.mftest.core.responsemodel.CardInfo;

/**
 * This interface exposes card business methods to the systems using the core. 
 *
 */
public interface CardBusinessInterface {
	
	/**
	 * Insert a new card, or update if it already exists.
	 *  
	 * @return The status INSERTED or UPDATED.
	 * @throws UserNotFoundCoreException
	 * @throws PersistenceCoreException
	 * @throws InvalidFieldCoreException
	 */
	OperationStatus insertCard(long userId, String cardNumber, String holderName, String expiryDate)
			throws UserNotFoundCoreException, PersistenceCoreException, InvalidFieldCoreException;
	
	/**
	 * Perform a card search.
	 * 
	 * @return List of cards found. Empty list if no cards were found.
	 * @throws UserNotFoundCoreException
	 * @throws PersistenceCoreException
	 */
	List<CardInfo> searchCard(long userId, String searchString)
			throws UserNotFoundCoreException, PersistenceCoreException;
}
