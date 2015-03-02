package com.bubblechess.server;

import java.net.Socket;

public class GameThread extends Thread {
	
	//User1 is white user2 is black
	private Socket _user1;
	private Socket _user2;
	
	public GameThread(int id, Socket user) {
		setUser(id, user);
	}
	
	//Getters
	public Socket getUser(int id) {
		if(id == 1) {
			return _user1;
		}
		else if(id == 2) {
			return _user2;
		}
		else {
			return null;
		}
	}
	//Setters
	public void setUser(int id, Socket user) {
		if(id == 1) {
			_user1 = user;
		}
		else if(id ==2) {
			_user2 = user;
		}
	}
	//Methods
	
}
