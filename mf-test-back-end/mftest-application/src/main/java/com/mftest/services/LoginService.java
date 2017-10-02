package com.mftest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mftest.core.boundary.LoginBusinessInterface;
import com.mftest.core.enumerations.OperationStatus;
import com.mftest.core.exception.PersistenceCoreException;
import com.mftest.core.exception.UserAlreadyExistsCoreException;
import com.mftest.core.exception.UserNotFoundCoreException;
import com.mftest.core.responsemodel.UserInfo;
import com.mftest.dto.Status;
import com.mftest.dto.Token;
import com.mftest.security.JWTToken;

/**
 * Endpoint implementation for Login services.
 *
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/services")
public class LoginService {

	private static final String USER_ALREADY_EXISTS = "User already exists.";
	private static final String INTERNAL_ERROR = "It wasn't possible to perform the operation. Internal error.";
	
	/**
	 * Spring will inject the correct core implementation for the login interface.
	 */
	@Autowired
	LoginBusinessInterface loginBusiness;

	/**
	 * Service for user login validation.
	 * 
	 * @param user
	 * @param password
	 * @return Token containing a cryptography string and user information. Token
	 *         value "" means user not found, "1" means internal error.
	 */
	@RequestMapping(path = "/login", method = RequestMethod.POST)
	Token userLogin(@RequestParam("user") String user, @RequestParam("password") String password) {

		UserInfo userInfo;
		try {
			// Call the core business method for login validation
			userInfo = loginBusiness.validateLogin(user, password);
			
		} catch (UserNotFoundCoreException e) {
			return new Token("", "", "");
		} catch (PersistenceCoreException e) {
			return new Token("1", "", "");
		}

		// Create encrypted token
		String cryptToken = JWTToken.create(userInfo.getUserName(), (int) userInfo.getUserId(), userInfo.getRoleId());

		return new Token(cryptToken, userInfo.getUserName(), userInfo.getRoleDescription());
	}

	/**
	 * Service for user login creation.
	 * 
	 * @param user
	 * @param password
	 * @return true if the login was created, false if the user already exists.
	 */
	@RequestMapping(path = "/createlogin", method = RequestMethod.POST)
	Status createUserLogin(@RequestParam("user") String user, @RequestParam("password") String password) {
		
		OperationStatus status;
		try {
			// Call the core business method for login creation
			status = loginBusiness.createLogin(user, password);
			
		} catch (UserAlreadyExistsCoreException e) {
			return new Status(1, USER_ALREADY_EXISTS);
		} catch (PersistenceCoreException e) {
			return new Status(1, INTERNAL_ERROR);
		}
		
		return new Status(0, status.getMessage());
	}

}
