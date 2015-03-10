package com.bubblechess.client;

public class User {

	protected int userID;
	protected String username;
	
	/**
	 * Constructor for the User object
	 * @param userID
	 * @param username
	 */
	public User(int userID, String username){
		this.userID = userID;
		this.username = username;
	}
	
	/**
	 * Gets the userID of the user
	 * @return
	 */
	public int getID(){
		return userID;
	}
	
	/**
	 * Gets the username of the user
	 * @return
	 */
	public String getUsername(){
		return username;
	}
	
}
