package com.bubblechess.server;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class ServerDriver {
	
	protected static Socket _client;
	protected static ServerSocket _server;
	protected static RequestHandler _rh;
	protected static boolean _testDb = false;
	
	/**
	 * Main method to run the server and add new Request Threads on port 8080
	 * @param args
	 * @throws IOException
	 */
	public static void main (String[] args) throws IOException{
		_server = new ServerSocket(8080);
		
		ChessDB cdb = new ChessDB(false);
		cdb.createTables();
		System.out.println("Server Open");
		
		ServerInstance si = new ServerInstance();
		
		while (true) {
			_client = _server.accept();
		      
			_rh = new RequestHandler(_client, si);
			_rh.start();
		}
	}
	
	/**
	 * Allows the database to be put into test mode
	 * @param testDb
	 */
	public void setTestDB(boolean testDb) {
		_testDb = testDb;
	}
}

