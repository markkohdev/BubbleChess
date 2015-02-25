package com.bubblechess.client;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.*;

import org.json.simple.*;

public class ServerHandler {
	
	protected String hostname;
	protected int port;
	protected Socket socket;
	protected PrintWriter out;
	protected BufferedReader in;
	

	public ServerHandler(String hostname, int port){
		this.hostname = hostname;
		this.port = port;
		
		try {
			socket = new Socket(hostname,port);
			out = new PrintWriter(socket.getOutputStream(),true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}
		catch(UnknownHostException e){
			System.out.println("Error: Not connected to server.  Unknown host.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void SendMove(Move m){
		
	}
	
	
	
}
