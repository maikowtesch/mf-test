package com.mftest.core.business;

import com.mftest.core.boundary.LoginBusinessInterface;
import com.mftest.core.enumerations.OperationStatus;
import com.mftest.core.enumerations.Roles;
import com.mftest.core.exception.PersistenceCoreException;
import com.mftest.core.exception.UserAlreadyExistsCoreException;
import com.mftest.core.exception.UserNotFoundCoreException;
import com.mftest.core.persistence.UserPersistenceInterface;
import com.mftest.core.persistence.entity.Role;
import com.mftest.core.persistence.entity.User;
import com.mftest.core.responsemodel.UserInfo;

/**
 * Implements the business methods exposed by the interface.
 */
public class LoginBusiness implements LoginBusinessInterface {

	private static final String USER_ALREADY_EXISTS = "User already exists.";
	private static final String USER_DOESN_T_EXIST = "User doesn't exist";

	// Persistence interface to be injected with custom implementation.
	UserPersistenceInterface userPersistence;

	/**
	 * Allows clients to specify a custom implementation for persistence interface.
	 */
	public LoginBusiness(UserPersistenceInterface userPersistence) {
		this.userPersistence = userPersistence;
	}

	/**
	 * Validates the existence of user name and checks password match.
	 */
	public UserInfo validateLogin(String userName, String password)
			throws UserNotFoundCoreException, PersistenceCoreException {

		// Check if the user exists 
		User user = userPersistence.findByUserData(userName, password);
		if (user == null) {
			// Fail if doesn't exist
			throw new UserNotFoundCoreException(USER_DOESN_T_EXIST);
		}

		// Return user's data
		return new UserInfo(user.getName(), user.getId(), (int) user.getRole().getId(),
							user.getRole().getDescription());
	}

	/**
	 * Creates a new login with the specified information.
	 */
	public OperationStatus createLogin(String userName, String password)
			throws UserAlreadyExistsCoreException, PersistenceCoreException {
		
		// Check if the user name is in use
		User user = userPersistence.findByUserData(userName);
		if (user != null) {
			// Fail if it is
			throw new UserAlreadyExistsCoreException(USER_ALREADY_EXISTS);
		}
		
		// Save new user
		user = new User(userName, password, new Role(Roles.USER.getCode(), Roles.USER.getDescription()));
		userPersistence.save(user);

		return OperationStatus.INSERTED;
	}

}
