package com.bubblechess.tests;

import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PrintWriter;
import java.io.StringReader;

import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

import com.bubblechess.client.ServerHandler;

import junit.framework.*;

public class ServerHandlerTest extends ServerHandler {
	

	public ServerHandlerTest() {
		super(null, 0);
		// TODO Auto-generated constructor stub
	}

	@Before
	public void setup(){
		
	}
	
	@After
	public void teardown(){
		
	}
	
	
	@Override
	protected void SetupConnection() {
		//Port our output to System.out
		this.toServer = new PrintWriter(System.out);
	}
	
	@Override
	protected void CloseConnection() {
		
	}
	
	protected void SendAsServer(String s){
		fromServer = new BufferedReader(new StringReader(s));
	}
	protected void SendAsServer(JSONObject json){
		fromServer = new BufferedReader(new StringReader(json.toJSONString()));
	}
	
	/**
	 * #0 - Test login funcitonality
	 */
	@Test
	public void testLogin() {
		JSONObject serverResult;
		int result;
		
		//Test correct login
		serverResult = new JSONObject();
		serverResult.put("result","success");
		serverResult.put("userID",1);
		SendAsServer(serverResult);
		result = Login("testuser","testpass");
		Assert.assertEquals(result, 1);
		
		//Incorrect password
		serverResult = new JSONObject();
		serverResult.put("result","incorrect password");
		SendAsServer(serverResult);
		result = Login("testuser","wrongpass");
		Assert.assertEquals(result, -1);
		
		//User not found
		serverResult = new JSONObject();
		serverResult.put("result","user not found");
		SendAsServer(serverResult);
		result = Login("notuser","testpass");
		Assert.assertEquals(result, -2);
		
		//Unexpected
		serverResult = new JSONObject();
		serverResult.put("result","unexpected");
		SendAsServer(serverResult);
		result = Login("testuser","wrongpass");
		Assert.assertEquals(result, -3);
	}
	
	/**
	 * #0 - Test for ContinueAsGuestTest
	 */
	@Test
	public void testContinueAsGuest() {
		JSONObject serverResult;
		int result;
		
		//Test correct login
		serverResult = new JSONObject();
		serverResult.put("result","success");
		serverResult.put("userID",5);
		SendAsServer(serverResult);
		result = ContinueAsGuest();
		Assert.assertEquals(result, 5);
		
		//Incorrect password
		serverResult = new JSONObject();
		serverResult.put("result","failure");
		SendAsServer(serverResult);
		result = ContinueAsGuest();
		Assert.assertEquals(result, -1);
	}
	
	

}
