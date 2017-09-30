package com.mftest.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mftest.core.dto.CardInfo;
import com.mftest.core.dto.SearchResult;
import com.mftest.core.dto.Status;
import com.mftest.core.enumerations.CardOperationStatus;
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
	
	/**
	 * Insert or update a card.
	 * @param token
	 * @param cardNumber
	 * @param holderName
	 * @param expiryDate
	 * @return the request status. 
	 */
	@RequestMapping(path="/insert", method=RequestMethod.POST)
	Status insertCard(@RequestHeader(value="token") String token,
						@RequestParam("cardNumber") String cardNumber, 
						@RequestParam("holderName") String holderName,
						@RequestParam("expiryDate") String expiryDate) {
		// Validate token
		Jws<Claims> claims = JWTToken.decode(token);
		claims.getBody().get("user");
		claims.getBody().get("userId");
		claims.getBody().get("userRole");
		
		return new Status(CardOperationStatus.INSERTED.getCode(), CardOperationStatus.INSERTED.getMessage());
	}
	
	/**
	 * Search cards by number.
	 * @param token
	 * @param searchString
	 * @return search results.
	 */
	@RequestMapping(path="/search/{searchString}", method=RequestMethod.GET)
	SearchResult searchCard(@RequestHeader(value="token") String token,
							@PathVariable("searchString") String searchString) {
		
		// Validate token
		Jws<Claims> claims = JWTToken.decode(token);
		claims.getBody().get("user");
		claims.getBody().get("userId");
		claims.getBody().get("userRole");
		
		Status status = new Status(CardOperationStatus.SEARCH_SUCCESS.getCode(), CardOperationStatus.SEARCH_SUCCESS.getMessage());
		//Status status = new Status(CardOperationStatus.SEARCH_NO_RESULTS.getCode(), CardOperationStatus.SEARCH_NO_RESULTS.getMessage());
		
		List<CardInfo> cards = new ArrayList<>();
		CardInfo card = new CardInfo("2222333344445555", "John Snow", "22/10");
		cards.add(card);
		card = new CardInfo("3333444455556666", "Arbian Ahmed", "18/03");
		cards.add(card);
		card = new CardInfo("4444555566667777", "Stwart Little", "19/09");
		cards.add(card);
		
		return new SearchResult(status, cards);
	}

}
