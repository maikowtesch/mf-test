package com.mftest.core.persistence;

import com.mftest.core.exception.PersistenceCoreException;
import com.mftest.core.persistence.entity.User;

/**
 * This interface specifies the persistence methods to be implemented by systems using the core.
 * 
 */
public interface UserPersistenceInterface {
	
	/**
	 * Save a user.
	 * @param user
	 * @return Newly created id.
	 * @throws PersistenceCoreException
	 */
	long save(User user) throws PersistenceCoreException;
	
	/**
	 * Find a user.
	 * @param userId
	 * @return null if couldn't find entity.
	 * @throws PersistenceCoreException
	 */
	User find(long userId) throws PersistenceCoreException;
	
	/**
	 * Find a user by the user name.
	 * @param userName
	 * @return null if couldn't find registers.
	 * @throws PersistenceCoreException
	 */
	User findByUserData(String userName) throws PersistenceCoreException;
	
	/**
	 * Find a user by user name and password.
	 * @param userName
	 * @param password
	 * @return null if couldn't find registers.
	 * @throws PersistenceCoreException
	 */
	// retorna nulo se nao encontrou
	User findByUserData(String userName, String password) throws PersistenceCoreException;

}
