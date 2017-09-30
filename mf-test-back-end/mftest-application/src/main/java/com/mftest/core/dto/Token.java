package com.mftest.core.dto;

/**
 * Class that represents the token information exchanged with the clients. 
 */
public class Token {
	
	/**
	 * Cryptographic string containing session information.
	 */
	private String token;
	
	/**
	 * User Name.
	 */
	private String userName;
	
	/**
	 * The code for the user's role in the system.
	 */
	private int userRole;
	
	/**
	 * The description for the user's role in the system.
	 */
	private String userRoleDesc;
	
	public Token(String cryptToken, String userName, int userRole, String userRoleDescription) {
		this.token = cryptToken;
		this.userName = userName;
		this.userRole = userRole;
		this.userRoleDesc = userRoleDescription;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserRole() {
		return userRole;
	}

	public void setUserRole(int userRole) {
		this.userRole = userRole;
	}
	
	public String getUserRoleDesc() {
		return userRoleDesc;
	}

	public void setUserRoleDesc(String userRoleDesc) {
		this.userRoleDesc = userRoleDesc;
	}
	
}
