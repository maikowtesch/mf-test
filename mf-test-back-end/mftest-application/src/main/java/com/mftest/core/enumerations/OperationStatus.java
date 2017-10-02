package com.mftest.core.enumerations;

/**
 * Encapsulates all possible statuses coming from Card operations.  
 *
 */
public enum OperationStatus {
	
	INSERTED(1,"Inserted succesfully."),
	UPDATED(2,"Updated succesfully.");
	
	int code;
	String message;
	
	private OperationStatus(int code, String description) {
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
