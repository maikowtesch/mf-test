package com.mftest.services;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mftest.core.dto.Token;
import com.mftest.security.JWTToken;


/**
 * Endpoint implementation for Login services.
 *
 */
@CrossOrigin
@RestController
@RequestMapping(path="/services")
public class LoginService {

	/**
	 * Service for user login validation.
	 * @param user
	 * @param password
	 * @return Token containing a cryptography string and user information
	 */
	@RequestMapping(path="/login", method=RequestMethod.POST)
	Token userLogin(@RequestParam("user") String user, @RequestParam("password") String password) {
		
		//TODO Implement login correctly
		if (user.equals("john.cena") && password.equals("1234")) {
			// Create token using user, userId and userRole
			return new Token(JWTToken.create("john.cena", 12, 1), "John Cena", 1, "Leader");
		} else {
			return new Token(JWTToken.create("john.cena", 12, 1), "John Cena", 1, "Leader");
//			return new Token("", "", 0, "");
		}
	}
	
	
	/**
	 * Service for user login creation.
	 * @param user
	 * @param password
	 * @return true if the login was created, false if the user already exists.
	 */
	@RequestMapping(path="/createlogin", method=RequestMethod.POST)
	boolean createUserLogin(@RequestParam("user") String user, @RequestParam("password") String password) {
		
		//TODO Implement create login correctly
		if (user.equals("john.cena")) {
			return false;
		} else {
			return true;
		}
	}
	
}
