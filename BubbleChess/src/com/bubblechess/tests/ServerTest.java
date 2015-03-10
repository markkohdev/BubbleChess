package com.bubblechess.tests;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
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
	
	/**
	 * Anything needed to be done before all tests
	 */
	@Before
	public void setUp() {
		_requestHandle = null;
		_si = new ServerInstance();
		ChessDB cdb = new ChessDB(true);
		cdb.createTables();
	}
	
	/**
	 * Anything needed to be done after all tests
	 */
	@After
	public void tearDown() {	
	}
	
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
		return _requestHandle.getResults();
	}
	
	/**
	 * Test 29 to see if user is created
	 */
	@Test
	public void createUser() {
		String request = "{\"request\":\"createUser\",\"password\":\"testPass\",\"username\":\"testUser\"}";
		_requestHandle = new RequestHandler(null, _si, request, System.out);
		_requestHandle.start();
		
		JSONObject json = new JSONObject();
		json.put("result", "success");
		json.put("userID", '1');

		Assert.assertEquals(json.toJSONString(), getResult(_requestHandle));
		
		/*{"request":"checkLogin","password":"rocks","username":"Eric"}
		{"request":"getJoinableGames"}
		{"gameID":13,"request":"joinGame","userID":2}
		{"gameID":13,"request":"checkForMove","user":2}
		{"playerNumber":1,"userID":1,"gameID":13,"request":"getOpponent"}
		*/
	}
	
	@Test
	public void createUserFail() {
		String request = "{\"request\":\"createUser\",\"password\":\"testPass\",\"username\":\"testUser\"}";
		_requestHandle = new RequestHandler(null, _si, request, System.out);
		_requestHandle.start();
		
		JSONObject json = new JSONObject();
		json.put("result", "username already exists");

		Assert.assertEquals(json.toJSONString(), getResult(_requestHandle));	
	}
}
