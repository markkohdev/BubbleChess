package com.bubblechess.tests;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bubblechess.server.*;

public class ServerTest {
	
	protected ServerInstance _si;
	
	/**
	 * Anything needed to be done before all tests
	 */
	@Before
	public void setUp() {
		_si = new ServerInstance();
		ChessDB cdb = new ChessDB(true);
		cdb.createTables();
	}
	
	/**
	 * Anything needed to be done after all tests
	 */
	@After
	public void tearDown() {	
		try {
    		File file = new File("Test.db");
 
    		if(file.delete()){
    			//System.out.println(file.getName() + " is deleted!");
    		} else {
    			System.out.println("Delete operation is failed.");
    		}
 
    	} catch(Exception e){
    		e.printStackTrace();
    	}
		System.out.println("-----");
	}
	
	//Helper functions
	/**
	 * Returns result from the request handler
	 * @return
	 */
	private String getResult(RequestHandler requestHandle) {
		while (requestHandle.getResults() == null) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return requestHandle.getResults();
	}
	
	/**
	 * Method to create a user in the datbase
	 * @return
	 */
	private RequestHandler createUser(int userNum) {
		String request = "{\"request\":\"createUser\",\"password\":\"testPass\",\"username\":\"testUser"+userNum+"\"}";
		RequestHandler requestHandle = new RequestHandler(null, _si, request, System.out);
		requestHandle.start();
		
		return requestHandle;
	}
	
	/**
	 * Creates a game 
	 * @return
	 */
	private RequestHandler createGame() {
		String request = "{\"playerNumber\":1,\"userID\":1,\"request\":\"createGame\"}";
		RequestHandler requestHandle = new RequestHandler(null, _si, request, System.out);
		requestHandle.start();
		
		return requestHandle;
	}
	
	/** 
	 * Joins a game
	 * @return
	 */
	private RequestHandler joinGame(int userId) {
		String request = "{\"gameID\":1,\"request\":\"joinGame\",\"userID\":"+userId+"}";
		RequestHandler requestHandle = new RequestHandler(null, _si, request, System.out);
		requestHandle.start();
		
		return requestHandle;
	}
	
	//Test cases
	/**
	 * Test 29 to see if user is created
	 */
	@Test
	public void createUserTest() {
		RequestHandler requestHandle = createUser(1);
		
		JSONObject json = new JSONObject();
		json.put("result", "success");
		json.put("userID", '1');

		Assert.assertEquals(json.toJSONString(), getResult(requestHandle));

	}
	
	/** 
	 * #29 - Tests the create user function if a user already exists
	 */
	@Test
	public void createUserFail() {
		getResult(createUser(1));
		
		String request = "{\"request\":\"createUser\",\"password\":\"testPass\",\"username\":\"testUser1\"}";
		RequestHandler requestHandle = new RequestHandler(null, _si, request, System.out);
		requestHandle.start();
		
		JSONObject json = new JSONObject();
		json.put("result", "username already exists");

		Assert.assertEquals(json.toJSONString(), getResult(requestHandle));	
	}
	
	/** 
	 * #30 - Tests the login with a successful login
	 */
	@Test
	public void loginTest() {
		getResult(createUser(1));
		
		String request = "{\"request\":\"checkLogin\",\"password\":\"testPass\",\"username\":\"testUser1\"}";
		RequestHandler requestHandle = new RequestHandler(null, _si, request, System.out);
		requestHandle.start();
		
		JSONObject json = new JSONObject();
		json.put("result", "success");
		json.put("userID", 1);

		Assert.assertEquals(json.toJSONString(), getResult(requestHandle));	
	}
	
	/**
	 * #30 - Tests the login with a bad username
	 */
	@Test
	public void loginBadUsername() {
		getResult(createUser(1));
		
		String request = "{\"request\":\"checkLogin\",\"password\":\"testPass\",\"username\":\"FailUser\"}";
		RequestHandler requestHandle = new RequestHandler(null, _si, request, System.out);
		requestHandle.start();
		
		JSONObject json = new JSONObject();
		json.put("result", "user not found");

		Assert.assertEquals(json.toJSONString(), getResult(requestHandle));	
	}
	
	/**
	 * #30 - Tests the login with a bad password
	 */
	@Test
	public void loginBadPassword() {
		getResult(createUser(1));
		
		String request = "{\"request\":\"checkLogin\",\"password\":\"FailPass\",\"username\":\"testUser1\"}";
		RequestHandler requestHandle = new RequestHandler(null, _si, request, System.out);
		requestHandle.start();
		
		JSONObject json = new JSONObject();
		json.put("result", "incorrect password");

		Assert.assertEquals(json.toJSONString(), getResult(requestHandle));	
	}
	
	/**
	 * #32 - Successfully creates a game
	 */
	@Test
	public void createGameTest() {
		getResult(createUser(1));
		RequestHandler requestHandle = createGame();
		
		JSONObject json = new JSONObject();
		json.put("result", "success");
		json.put("gameID", 1);
		
		Assert.assertEquals(json.toJSONString(), getResult(requestHandle));	
	}
	
	/**
	 * #32 - List of joinable games
	 */
	@Test
	public void joinableGames() {
		getResult(createUser(1));
		getResult(createGame());
		
		String request = "{\"request\":\"getJoinableGames\"}";
		RequestHandler requestHandle = new RequestHandler(null, _si, request, System.out);
		requestHandle.start();
		
		ArrayList<Integer> joinableGames = new ArrayList<Integer>();
		joinableGames.add(1);
		JSONArray games = new JSONArray();
		games.add(joinableGames.get(0));
		
		JSONObject json = new JSONObject();
		json.put("result", "success");
		json.put("games", games);
		
		Assert.assertEquals(json.toJSONString(), getResult(requestHandle));	
	}
	
	/**
	 * #32 - Successfully joins a game
	 */
	@Test
	public void joinGameTest() {
		getResult(createUser(1));
		getResult(createGame());
		getResult(createUser(2));
		
		RequestHandler requestHandle = joinGame(2);
		
		JSONObject json = new JSONObject();
		json.put("result", "success");
		json.put("userID", 1);
		json.put("username", "testUser1");
		json.put("playerNumber", 1);
		
		Assert.assertEquals(json.toJSONString(), getResult(requestHandle));	
	}
	
	/**
	 * #34 - Gets opponent information 
	 */
	@Test
	public void getOpponent() {
		getResult(createUser(1));
		getResult(createGame());
		getResult(createUser(2));
		getResult(joinGame(2));
		
		String request = "{\"playerNumber\":1,\"userID\":1,\"gameID\":1,\"request\":\"getOpponent\"}";
		RequestHandler requestHandle = new RequestHandler(null, _si, request, System.out);
		requestHandle.start();
		
		JSONObject json = new JSONObject();
		json.put("result", "success");
		json.put("userID", 2);
		json.put("username", "testUser2");
		json.put("playerNumber", 2);
		
		Assert.assertEquals(json.toJSONString(), getResult(requestHandle));	
	}
	
	/**
	 * #36 - Sends move to server 
	 */
	/*@Test
	public void insertMove() {
		getResult(createUser(1));
		getResult(createGame());
		getResult(createUser(2));
		getResult(joinGame(2));
		
		String request = "{\"gameID\":1,\"userID\":1,\"colFrom\":0,\"rowFrom\":0,\"colTo\":0,\"colTo\":1,\"request\":\"insertMove\"}";
		RequestHandler requestHandle = new RequestHandler(null, _si, request, System.out);
		requestHandle.start();
		
		JSONObject json = new JSONObject();
		json.put("result", "success");
		
		Assert.assertEquals(json.toJSONString(), getResult(requestHandle));	
	}*/
}
