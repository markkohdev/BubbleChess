package com.bubblechess.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bubblechess.server.*;

public class ServerTest {
	protected Socket _socket;
	protected PrintWriter _toServer;
	protected BufferedReader _fromServer;
	private ServerDriver _driver;
	
	/**
	 * Anything needed to be done before all tests
	 */
	@Before
	public void setUp() {
		
		//open server
		_driver = new ServerDriver();
		_driver.setTestDB(true);
		
		try {
			_driver.main(null);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//connect to it
		try {
			_socket = new Socket("127.0.0.1",8080);
			_toServer = new PrintWriter(_socket.getOutputStream(),true);
			_fromServer = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
		}
		catch(UnknownHostException e){
			System.out.println("Error: Not connected to server.  Unknown host.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Anything needed to be done after all tests
	 */
	@After
	public void tearDown() {	
		//Should shut down the server
		_driver.closeConnections();
		System.out.println("Test Complete");
	}
	
	/**
	 * Test 29 to see if user is created
	 */
	@Test
	public void createUser(){
		String request = "{'request':'createUser','password':'testPass','username':'testUser'}";
		
		_toServer.println(request);
		System.out.println(_fromServer);
		
		int result = 0;
		Assert.assertEquals(0, result);
		
		/*{"request":"checkLogin","password":"rocks","username":"Eric"}
		{"request":"getJoinableGames"}
		{"gameID":13,"request":"joinGame","userID":2}
		{"gameID":13,"request":"checkForMove","user":2}
		{"playerNumber":1,"userID":1,"gameID":13,"request":"getOpponent"}
		*/
	}
}
