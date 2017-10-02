package com.mftest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mftest.core.boundary.CardBusinessInterface;
import com.mftest.core.boundary.LoginBusinessInterface;
import com.mftest.core.business.CardBusiness;
import com.mftest.core.business.LoginBusiness;
import com.mftest.core.persistence.CardPersistenceInterface;
import com.mftest.core.persistence.UserPersistenceInterface;
import com.mftest.persistence.CardDAO;
import com.mftest.persistence.UserDAO;

@Configuration
public class SpringConfiguration {

	// Instantiate business interfaces with core classes, to be used by the services.
	@Bean
	public CardBusinessInterface cardBusiness(CardPersistenceInterface cardPersistence,
			UserPersistenceInterface userPersistence) {
		return new CardBusiness(cardPersistence, userPersistence);
	}

	// Instantiate business interfaces with core classes, to be used by the services.
	@Bean
	public LoginBusinessInterface loginBusiness(UserPersistenceInterface userPersistence) {
		return new LoginBusiness(userPersistence);
	}

	
	// Specify custom persistence interface to be used by the core.
	@Bean
	public CardPersistenceInterface cardPersistence() {
		return new CardDAO();
	}

	// Specify custom persistence interface to be used by the core.
	@Bean
	public UserPersistenceInterface userPersistence() {
		return new UserDAO();
	}

}
