package com.mftest.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mftest.core.boundary.CardBusinessInterface;
import com.mftest.core.enumerations.OperationStatus;
import com.mftest.core.exception.InvalidFieldCoreException;
import com.mftest.core.exception.PersistenceCoreException;
import com.mftest.core.exception.UserNotFoundCoreException;
import com.mftest.core.responsemodel.CardInfo;
import com.mftest.dto.SearchResult;
import com.mftest.dto.Status;
import com.mftest.security.JWTToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

/**
 * Endpoint implementation for Credit Card services.
 *
 */
@CrossOrigin
@RestController
@RequestMapping(path="/services/card")
public class CardService {
	
	private static final String NO_RESULTS = "No results found for the search.";
	private static final String INTERNAL_ERROR = "It wasn't possible to perform the operation. Internal error.";
	private static final String USER_NOT_FOUND = "User not found.";
	
	/**
	 * Spring will inject the correct core implementation for the card interface.
	 */
	@Autowired
	CardBusinessInterface cardBusiness;
	
	/**
	 * Insert or update a card.
	 * 
	 * @param token
	 * @param cardNumber
	 * @param holderName
	 * @param expiryDate
	 * @return the request status. Code 0=success, 1=error or 2=warning.
	 */
	@RequestMapping(path="/insert", method=RequestMethod.POST)
	Status insertCard(@RequestHeader(value="token") String token,
						@RequestParam("cardNumber") String cardNumber, 
						@RequestParam("holderName") String holderName,
						@RequestParam("expiryDate") String expiryDate) {
		// Validate token
		Jws<Claims> claims = JWTToken.decode(token);
		
		OperationStatus status;
		try {
			// Call the core business method for insertion
			status = cardBusiness.insertCard((int) claims.getBody().get("userId"), cardNumber, holderName, expiryDate);
			
		} catch (UserNotFoundCoreException e) {
			return new Status(1, USER_NOT_FOUND);
		} catch (PersistenceCoreException e) {
			return new Status(1, INTERNAL_ERROR);
		} catch (InvalidFieldCoreException e) {
			return new Status(1, e.getMessage());
		}
		
		return new Status(0, status.getMessage());
	}
	
	/**
	 * Search cards by number.
	 * 
	 * @param token
	 * @param searchString
	 * @return search results and status. Status code 0=success, 1=error or 2=warning.
	 */
	@RequestMapping(path="/search/{searchString}", method=RequestMethod.GET)
	SearchResult searchCard(@RequestHeader(value="token") String token,
							@PathVariable("searchString") String searchString) {
		
		// Validate token
		Jws<Claims> claims = JWTToken.decode(token);
		
		Status status = new Status(0, "");
		
		List<CardInfo> searchResults = new ArrayList<>();
		try {
			// Call the core business method that performs the search
			searchResults = cardBusiness.searchCard((int) claims.getBody().get("userId"), searchString);
			
		} catch (UserNotFoundCoreException e) {
			status = new Status(1, USER_NOT_FOUND);
		} catch (PersistenceCoreException e) {
			status = new Status(1, INTERNAL_ERROR);
		}
		
		// Set appropriate status according to search results
		if (searchResults.isEmpty() && status.getCode() == 0) {
			status = new Status(2, NO_RESULTS);
		}
		
		return new SearchResult(status, searchResults);
	}

}
