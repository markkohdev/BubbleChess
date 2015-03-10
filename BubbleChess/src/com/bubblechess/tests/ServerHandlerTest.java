package com.bubblechess.tests;

import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

import com.bubblechess.client.Move;
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
	 * #1 - Test login funcitonality
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
		
		//unexpected
		serverResult = new JSONObject();
		serverResult.put("result","failure");
		SendAsServer(serverResult);
		result = ContinueAsGuest();
		Assert.assertEquals(result, -1);
	}
	
	/**
	 * #0 - Test for Register
	 */
	@Test
	public void testRegister() {
		JSONObject serverResult;
		int result;
		
		//Test correct register
		serverResult = new JSONObject();
		serverResult.put("result","success");
		serverResult.put("userID",10);
		SendAsServer(serverResult);
		result = Register("testuser","testpass");
		Assert.assertEquals(result, 10);
		
		//Existent username
		serverResult = new JSONObject();
		serverResult.put("result","username already exists");
		SendAsServer(serverResult);
		result = Register("testuser","testpass");
		Assert.assertEquals(result, -1);
		
		//Unexpected
		serverResult = new JSONObject();
		serverResult.put("result","unexpected");
		SendAsServer(serverResult);
		result = Register("testuser","testpass");
		Assert.assertEquals(result, -2);
	}
	
	/**
	 * #2 - Test for CreateGame
	 */
	@Test
	public void testCreateGame() {
		JSONObject serverResult;
		int result;
		
		//Valid game creation
		serverResult = new JSONObject();
		serverResult.put("result","success");
		serverResult.put("gameID",1);
		SendAsServer(serverResult);
		result = CreateGame(1,1);
		Assert.assertEquals(result, 1);
		
		//Invalid player number
		serverResult = new JSONObject();
		serverResult.put("result","failure");
		SendAsServer(serverResult);
		result = CreateGame(1,-1);
		Assert.assertEquals(result, -1);
	}
	
	/**
	 * #2 - Test for GetOpponent
	 */
	@Test
	public void testGetOpponent() {
		JSONObject serverResult;
		String[] result;
		
		//Valid game creation
		serverResult = new JSONObject();
		serverResult.put("result","waiting");
		SendAsServer(serverResult);

		//Valid game creation
		serverResult = new JSONObject();
		serverResult.put("result","success");
		serverResult.put("userID",1);
		serverResult.put("username","testopponent");
		SendAsServer(serverResult);
		result = GetOpponent(1,1,1);
		Assert.assertEquals(result[0], "1");
		Assert.assertEquals(result[1], "testopponent");
	}
	
	/**
	 * #3 - Test for JoinGame
	 */
	@Test
	public void testJoinGame() {
		JSONObject serverResult;
		String[] result;
		
		//Valid game creation
		serverResult = new JSONObject();
		serverResult.put("result","success");
		serverResult.put("userID",1);
		serverResult.put("username","testopponent");
		serverResult.put("playerNumber",1);
		SendAsServer(serverResult);
		result = JoinGame(1,1);
		Assert.assertEquals(result[0], "1");
		Assert.assertEquals(result[1], "testopponent");
		Assert.assertEquals(result[2], "1");
	}
	
	/**
	 * #3 - Test for GetJoinableGames
	 */
	@Test
	public void testGetJoinableGames() {
		JSONObject serverResult;
		ArrayList<Integer> result;
		
		//Valid game creation
		serverResult = new JSONObject();
		JSONArray games = new JSONArray();
		games.add(1);
		serverResult.put("result","success");
		serverResult.put("games",games);
		SendAsServer(serverResult);
		result = GetJoinableGames();
		Assert.assertEquals(result.size(), 1);
		Assert.assertEquals((int)result.get(0), 1);
	}
	
	/**
	 * #46 - Test for SendMove
	 */
	@Test
	public void testSendMove() {
		JSONObject serverResult;
		boolean result;
		
		//Valid game creation
		serverResult = new JSONObject();
		serverResult.put("result","success");
		
		SendAsServer(serverResult);
		result = SendMove(new Move(new int[] {0,0}, new int[]{1,1}),1,1);
		Assert.assertEquals(result, true);
	}
	
	
	/**
	 * #46 - Test for CheckForMove
	 */
	@Test
	public void testCheckForMove() {
		JSONObject serverResult;
		Move result;
		
		//Valid game creation
		serverResult = new JSONObject();
		serverResult.put("result","waiting");
		SendAsServer(serverResult);

		//Valid game creation
		serverResult = new JSONObject();
		serverResult.put("result","success");
		serverResult.put("colFrom",0);
		serverResult.put("rowFrom",0);
		serverResult.put("colTo",1);
		serverResult.put("rowTo",1);

		SendAsServer(serverResult);
		result = CheckForMove(1,1);
		Assert.assertEquals(result.colFrom(), 0);
		Assert.assertEquals(result.rowFrom(), 0);
		Assert.assertEquals(result.colTo(), 1);
		Assert.assertEquals(result.rowTo(), 1);
	}

}
