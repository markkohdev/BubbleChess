package com.bubblechess.client;

public class User {

	protected int userID;
	protected String username;
	
	public User(int userID, String username){
		this.userID = userID;
		this.username = username;
	}
	
	public int getID(){
		return userID;
	}
	
	public String getUsername(){
		return username;
	}
	
}
