package com.mftest.core.responsemodel;

/**
 * Class that exposes the User data shared with the clients.
 *
 */
public class UserInfo {
	
	private String userName;
	private long userId;
	private int roleId;
	private String roleDescription;
	
	public UserInfo(String userName, long userId, int roleId, String roleDescription) {
		this.userName = userName;
		this.userId = userId;
		this.roleId = roleId;
		this.roleDescription = roleDescription;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleDescription() {
		return roleDescription;
	}
	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}
	
}
