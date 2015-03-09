package com.bubblechess.tests;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bubblechess.GUIBridge;
import com.bubblechess.client.ServerHandler;

import junit.framework.*;

public class GUIBridgeTest extends TestCase {
	
	private ServerHandler server;
	private GUIBridge bridge;
	private String testuser;
	private String testpass;
	
	/**
	 * Anything needed to be done before all tests
	 */
	@Before
	protected void setUp() {
		server = new ServerHandler("testserver",8080);
		bridge = new GUIBridge(server);
		testuser = "testuser";
		testpass = "testpass";
	}
	
	/**
	 * Anything needed to be done after all tests
	 */
	@After
	protected void tearDown() {
		
	}
	
	/**
	 * Test that the registered user returns 0
	 */
	@Test
	protected void registerUser(){
		int result = bridge.Register(testuser, testpass);
		Assert.assertEquals(0, result);
	}
}
