package com.mftest.core.enumerations;

/**
 * Enum that encapsulate all possible messages coming from Card operations.  
 *
 */
public enum CardOperationStatus {
	
	INSERTED(1,"Card inserted succesfully."),
	UPDATED(2,"Card updated succesfully."),
	SEARCH_SUCCESS(3, "Search performed successfully."),
	
	INVALID_NUMBER(80,"Invalid card number."),
	INVALID_EXPIRY_DATE(81,"Ivalid expiry date."),
	SEARCH_NO_RESULTS(82, "No results found."),
	
	FAIL(91,"Operation failed.");
	
	int code;
	String message;
	
	private CardOperationStatus(int code, String description) {
		this.code = code;
		this.message = description;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public String getMessage() {
		return this.message;
	}
}
