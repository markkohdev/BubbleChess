package com.bubblechess.server;

import java.net.*;
import java.io.*;

public class ServerDriver extends Thread
{
   private ServerSocket serverSocket;
   
   public ServerDriver() throws IOException
   {
	  int port = 4444;
      serverSocket = new ServerSocket(port);
      serverSocket.setSoTimeout(10000);
   }

   public void run()
   {
      while(true)
      {
         try
         {
            System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
            Socket server = serverSocket.accept();
           
            System.out.println("Just connected to "+ server.getRemoteSocketAddress());
            DataInputStream in = new DataInputStream(server.getInputStream());
            
            //Input
            System.out.println(in.readUTF());
            //Json parsing here
            
            DataOutputStream out = new DataOutputStream(server.getOutputStream());
            out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress() + "\nGoodbye!");
            
            server.close();
         }catch(SocketTimeoutException s)
         {
            System.out.println("Socket timed out!");
            break;
         }catch(IOException e)
         {
            e.printStackTrace();
            break;
         }
      }
   }
   public static void main(String [] args)
   {
	  //removed this to keep it static
      //int port = Integer.parseInt(args[0]);
      try
      {
         Thread t = new ServerDriver();
         t.start();
      }catch(IOException e)
      {
         e.printStackTrace();
      }
   }
   
   /**
    * Check login method for users
    * @param userId
    * @param password
    */
   public void checkLogin(int userId, String password) {
		
   }
}