package com.mftest.core.boundary;

import com.mftest.core.enumerations.OperationStatus;
import com.mftest.core.exception.PersistenceCoreException;
import com.mftest.core.exception.UserAlreadyExistsCoreException;
import com.mftest.core.exception.UserNotFoundCoreException;
import com.mftest.core.responsemodel.UserInfo;

/**
 * This interface exposes login business methods to the systems using the core.
 *
 */
public interface LoginBusinessInterface {
	
	/**
	 * Validates the existence of user name and checks password match. 
	 * 
	 * @return Info about the login's owner.
	 * @throws UserNotFoundCoreException
	 * @throws PersistenceCoreException
	 */
	public UserInfo validateLogin(String userName, String password)
			throws UserNotFoundCoreException, PersistenceCoreException;
	
	/**
	 * Creates a new login with the specified information.
	 * 
	 * @return The status INSERTED, if successful.
	 * @throws UserAlreadyExistsCoreException
	 * @throws PersistenceCoreException
	 */
	public OperationStatus createLogin(String userName, String password)
			throws UserAlreadyExistsCoreException, PersistenceCoreException;
}
