package com.bubblechess.server;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class ServerDriver {
	/**
	 * Main method to run the server on port 430
	 * @param args
	 * @throws IOException
	 */
	public static void main (String[] args) throws IOException{
		ServerSocket server = new ServerSocket(8080);
		
		ChessDB cdb = new ChessDB();
		cdb.createTables();
		System.out.println("Server Open");
		
		ServerInstance si = new ServerInstance();
		
		while (true) {
			Socket client = server.accept();
		      
			RequestHandler rh = new RequestHandler(client, si);
			rh.start();
		}
	}
}

