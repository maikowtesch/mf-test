package com.mftest.core.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity that represents a Role.
 */
@Entity
@Table(name="role")
public class Role {
	
	@Id
	@Column(name="role_id")
	private long id;
	
	@Column(name="description")
	private String description;

	public Role() {
		super();
	}
	
	public Role(long id, String description) {
		this.id = id;
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
