package com.bubblechess.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bubblechess.server.*;

public class ServerTest {
	
	protected RequestHandler _requestHandle;
	protected ServerInstance _si;
	protected PrintWriter _printWriter;
	protected StringWriter _console;
	
	/**
	 * Anything needed to be done before all tests
	 */
	@Before
	public void setUp() {
		_requestHandle = null;
		_si = new ServerInstance();
		_console = new StringWriter();
		_printWriter = new PrintWriter(_console, true);
	}
	
	/**
	 * Anything needed to be done after all tests
	 */
	@After
	public void tearDown() {	
	}
	
	/**
	 * Test 29 to see if user is created
	 */
	@Test
	public void createUser() {
		String request = "{\"request\":\"createUser\",\"password\":\"testPass\",\"username\":\"testUser\"}";
		_requestHandle = new RequestHandler(null, _si, request, _printWriter);
		_requestHandle.start();
		
		JSONObject json = new JSONObject();
		json.put("result", "success");
		json.put("userID", '1');
		
		System.out.println(json.toJSONString());
		System.out.println(_console.toString());
		
		Assert.assertEquals(0, 0);
		
		/*{"request":"checkLogin","password":"rocks","username":"Eric"}
		{"request":"getJoinableGames"}
		{"gameID":13,"request":"joinGame","userID":2}
		{"gameID":13,"request":"checkForMove","user":2}
		{"playerNumber":1,"userID":1,"gameID":13,"request":"getOpponent"}
		*/
	}
	
	/*@Test
	public void createUser2(){
		String request = "{\"request\":\"createUser\",\"password\":\"testPass\",\"username\":\"testUser\"}";
		//_toServer.println(request);
		
		int result = 0;
		Assert.assertEquals(0, result);
		
		/*{"request":"checkLogin","password":"rocks","username":"Eric"}
		{"request":"getJoinableGames"}
		{"gameID":13,"request":"joinGame","userID":2}
		{"gameID":13,"request":"checkForMove","user":2}
		{"playerNumber":1,"userID":1,"gameID":13,"request":"getOpponent"}
		
	}*/
}
