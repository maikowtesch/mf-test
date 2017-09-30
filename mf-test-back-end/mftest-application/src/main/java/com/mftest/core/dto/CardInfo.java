package com.mftest.core.dto;

/**
 * Class that exposes the Credit Card data shared with the clients.  
 *
 */
public class CardInfo {
	
	private String cardNumber;
	private String holderName;
	private String expiryDate;
	
	public CardInfo(String cardNumber, String holderName, String expiryDate) {
		this.cardNumber = cardNumber;
		this.holderName = holderName;
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
	
	public String getExpiryDate() {
		return expiryDate;
	}
	
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	
}
