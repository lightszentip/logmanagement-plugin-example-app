package com.lightszentip.sample.logmanagementapp.model;

public class User {

	private final long id;

	private final String username;

	public User(long id, String username) {
		super();
		this.id = id;
		this.username = username;
	}

	public long getId() {
		return this.id;
	}

	public String getUsername() {
		return this.username;
	}

}
