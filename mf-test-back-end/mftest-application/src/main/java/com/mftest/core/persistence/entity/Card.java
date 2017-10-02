package com.mftest.core.persistence.entity;

/**
 * Entity that represents a Card. 
 */
public class Card {

	private long id;
	private String cardNumber;
	private String holderName;
	private String expiryDate;
	private User user;
	
	public Card() {
		super();
	}
	
	public Card(String cardNumber, String holderName, String expiryDate, User user) {
		this.cardNumber = cardNumber;
		this.holderName = holderName;
		this.expiryDate = expiryDate;
		this.user = user;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
