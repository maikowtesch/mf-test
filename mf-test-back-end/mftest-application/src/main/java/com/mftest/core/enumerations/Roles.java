package com.mftest.core.enumerations;

/**
 * This enum holds all possible user Roles in the system. 
 */
public enum Roles {
	ADMIN(1, "Admin"), 
	USER(2, "User");
	
	int cod;
	String description;
	
	Roles(int cod, String desc) {
		this.cod = cod;
		this.description = desc;
	}
	
	public int getCode() {
		return this.cod;
	}

	public String getDescription() {
		return description;
	}

}
