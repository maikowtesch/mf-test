package com.mftest.persistence;

import com.mftest.core.exception.PersistenceCoreException;
import com.mftest.core.persistence.UserPersistenceInterface;
import com.mftest.core.persistence.entity.Role;
import com.mftest.core.persistence.entity.User;

public class UserDAO implements UserPersistenceInterface {

	/**
	 * Save a user.
	 */
	@Override
	public long save(User user) throws PersistenceCoreException {
		return 0;
	}

	/**
	 * Find a user.
	 */
	@Override
	public User find(long userId) throws PersistenceCoreException {
		return new User("john.cena", "123", new Role(2, "User"));
	}

	/**
	 * Find a user by the user name.
	 */
	@Override
	public User findByUserData(String userName) throws PersistenceCoreException {
		return null;
	}

	/**
	 * Find a user by user name and password.
	 */
	@Override
	public User findByUserData(String userName, String password) throws PersistenceCoreException {
		return new User("john.cena", "123", new Role(2, "User"));
	}

}
