package com.bubblechess.server;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class ServerDriver {
	
	protected static Socket _client;
	protected static ServerSocket _server;
	protected static RequestHandler _rh;
	/**
	 * Main method to run the server on port 430
	 * @param args
	 * @throws IOException
	 */
	public static void main (String[] args) throws IOException{
		_server = new ServerSocket(8080);
		
		ChessDB cdb = new ChessDB();
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
	 * Closes all of the open connections in the server
	 */
	public void closeConnections() {
		_rh.interrupt();
		try {
			_client.close();
			_server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

