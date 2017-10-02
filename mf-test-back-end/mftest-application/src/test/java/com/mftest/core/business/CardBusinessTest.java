package com.mftest.core.business;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import com.mftest.core.enumerations.OperationStatus;
import com.mftest.core.enumerations.Roles;
import com.mftest.core.exception.InvalidFieldCoreException;
import com.mftest.core.exception.PersistenceCoreException;
import com.mftest.core.exception.UserNotFoundCoreException;
import com.mftest.core.persistence.CardPersistenceInterface;
import com.mftest.core.persistence.UserPersistenceInterface;
import com.mftest.core.persistence.entity.Card;
import com.mftest.core.persistence.entity.Role;
import com.mftest.core.persistence.entity.User;
import com.mftest.core.responsemodel.CardInfo;

public class CardBusinessTest {

	Mockery mockery;

	private CardPersistenceInterface cardPersistence;
	private UserPersistenceInterface userPersistence;

	@Before
	public void init() {
		mockery = new Mockery();
		cardPersistence = mockery.mock(CardPersistenceInterface.class);
		userPersistence = mockery.mock(UserPersistenceInterface.class);
	}

	// Scenarios to test "insertCard"
	
	@Test
	public void testInsertCard_SuccessInsertion() throws PersistenceCoreException {
		User user = new User("john.cena", "1234", new Role(Roles.USER.getCode(), Roles.USER.getDescription()));
		
		mockery.checking(new Expectations() {{
			oneOf(userPersistence).find(with(99l));
			will(returnValue(user));
			
			oneOf(cardPersistence).findByCardData(with("1111222233334444"), with(99l));
			will(returnValue(null));
			
			oneOf(cardPersistence).save(with(any(Card.class)));
		}});

		OperationStatus status = OperationStatus.UPDATED;
		
		CardBusiness cardBusiness = new CardBusiness(cardPersistence, userPersistence);
		try {
			status = cardBusiness.insertCard(99l, "1111222233334444", "John Cena", "30/11");
		} catch (UserNotFoundCoreException | InvalidFieldCoreException | PersistenceCoreException e) {
			fail();
		}
		
		assertEquals(status,  OperationStatus.INSERTED);
	}

	@Test
	public void testInsertCard_SuccessUpdateCardExists() throws PersistenceCoreException {
		User user = new User("john.cena", "1234", new Role(Roles.USER.getCode(), Roles.USER.getDescription()));
		
		mockery.checking(new Expectations() {{
			oneOf(userPersistence).find(with(99l));
			will(returnValue(user));
			
			oneOf(cardPersistence).findByCardData(with("1111222233334444"), with(99l));
			will(returnValue(new Card()));
			
			oneOf(cardPersistence).update(with(any(Card.class)));
		}});

		OperationStatus status = OperationStatus.INSERTED;
		
		CardBusiness cardBusiness = new CardBusiness(cardPersistence, userPersistence);
		try {
			status = cardBusiness.insertCard(99l, "1111222233334444", "John Cena", "30/11");
		} catch (UserNotFoundCoreException | InvalidFieldCoreException | PersistenceCoreException e) {
			fail();
		}
		
		assertEquals(status,  OperationStatus.UPDATED);
	}
	
	@Test
	public void testInsertCard_UserDoesNotExist() throws PersistenceCoreException {
		mockery.checking(new Expectations() {{
			oneOf(userPersistence).find(with(99l));
			will(returnValue(null));
		}});

		CardBusiness cardBusiness = new CardBusiness(cardPersistence, userPersistence);
		try {
			cardBusiness.insertCard(99l, "1111222233334444", "John Cena", "30/11");
			fail();
		} catch (UserNotFoundCoreException e) {
			assertNotNull(e);
		} catch (InvalidFieldCoreException e) {
			fail();
		} catch (PersistenceCoreException e) {
			fail();
		}
	}
	
	@Test
	public void testInsertCard_InvalidCardNumber() throws PersistenceCoreException {
		User user = new User("john.cena", "1234", new Role(Roles.USER.getCode(), Roles.USER.getDescription()));
		
		mockery.checking(new Expectations() {{
			oneOf(userPersistence).find(with(99l));
			will(returnValue(user));
		}});

		CardBusiness cardBusiness = new CardBusiness(cardPersistence, userPersistence);
		try {
			cardBusiness.insertCard(99l, "11112222333x", "John Cena", "30/11");
			fail();
		} catch (UserNotFoundCoreException e) {
			fail();
		} catch (InvalidFieldCoreException e) {
			assertNotNull(e);
		} catch (PersistenceCoreException e) {
			fail();
		}
	}
	
	@Test
	public void testInsertCard_InvalidExpiryDate() throws PersistenceCoreException {
		User user = new User("john.cena", "1234", new Role(Roles.USER.getCode(), Roles.USER.getDescription()));
		
		mockery.checking(new Expectations() {{
			oneOf(userPersistence).find(with(99l));
			will(returnValue(user));
		}});

		CardBusiness cardBusiness = new CardBusiness(cardPersistence, userPersistence);
		try {
			cardBusiness.insertCard(99l, "1111222233334444", "John Cena", "30/13");
			fail();
		} catch (UserNotFoundCoreException e) {
			fail();
		} catch (InvalidFieldCoreException e) {
			assertNotNull(e);
		} catch (PersistenceCoreException e) {
			fail();
		}
	}
	
	
	// Scenarios to test "searchCard"
	
	@Test
	public void testSearchCard_RegularUser() throws PersistenceCoreException {
		User user = new User("john.cena", "1234", new Role(Roles.USER.getCode(), Roles.USER.getDescription()));
		
		Card card = new Card("1111222233334444", "John Cena", "25/12", user);
		
		List<Card> cards = new ArrayList<>();
		cards.add(card);
		
		mockery.checking(new Expectations() {{
			oneOf(userPersistence).find(with(99l));
			will(returnValue(user));
			
			oneOf(cardPersistence).search(with("11112222"), with(99l));
			will(returnValue(cards));
		}});

		List<CardInfo> cardInfos = new ArrayList<>();
		
		CardBusiness cardBusiness = new CardBusiness(cardPersistence, userPersistence);
		try {
			cardInfos = cardBusiness.searchCard(99l, "11112222");
		} catch (UserNotFoundCoreException | PersistenceCoreException e) {
			fail();
		}
		
		assertEquals(cardInfos.size(), 1);
		assertEquals(cardInfos.get(0).getCardNumber(), card.getCardNumber());
		assertEquals(cardInfos.get(0).getHolderName(), card.getHolderName());
		assertEquals(cardInfos.get(0).getExpiryDate(), card.getExpiryDate());
	}
	
	@Test
	public void testSearchCard_Administrator() throws PersistenceCoreException {
		User user = new User("admin", "1234", new Role(Roles.ADMIN.getCode(), Roles.ADMIN.getDescription()));
		
		Card card = new Card("1111222233334444", "John Cena", "25/12", user);
		
		List<Card> cards = new ArrayList<>();
		cards.add(card);
		
		mockery.checking(new Expectations() {{
			oneOf(userPersistence).find(with(99l));
			will(returnValue(user));
			
			oneOf(cardPersistence).search(with("11112222"));
			will(returnValue(cards));
		}});

		List<CardInfo> cardInfos = new ArrayList<>();
		
		CardBusiness cardBusiness = new CardBusiness(cardPersistence, userPersistence);
		try {
			cardInfos = cardBusiness.searchCard(99l, "11112222");
		} catch (UserNotFoundCoreException | PersistenceCoreException e) {
			fail();
		}
		
		assertEquals(cardInfos.size(), 1);
		assertEquals(cardInfos.get(0).getCardNumber(), card.getCardNumber());
		assertEquals(cardInfos.get(0).getHolderName(), card.getHolderName());
		assertEquals(cardInfos.get(0).getExpiryDate(), card.getExpiryDate());
	}
	
	@Test
	public void testSearchCard_UserNotFound() throws PersistenceCoreException {
		
		mockery.checking(new Expectations() {{
			oneOf(userPersistence).find(with(99l));
			will(returnValue(null));
		}});

		CardBusiness cardBusiness = new CardBusiness(cardPersistence, userPersistence);
		try {
			cardBusiness.searchCard(99l, "11112222");
			fail();
		} catch (UserNotFoundCoreException e) {
			assertNotNull(e);
		} catch (PersistenceCoreException e) {
			fail();
		}
	}
	
}
