package com.bubblechess.server;

import java.sql.*;

public class ChessDB {
	
	/**
	 * Constructor
	 */
	public ChessDB() {
	}
	
	/**
	 * Method to set up db connection
	 * @return
	 */
	public Connection dbConnect() {
		 Connection c = null;
		 try {
		   	Class.forName("org.sqlite.JDBC");
		    c = DriverManager.getConnection("jdbc:sqlite:test.db");
		    c.setAutoCommit(false);
		    System.out.println("Opened database successfully");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Database failed to open :(");
			e.printStackTrace();
		}
		 return c;
	}
	/**
	 * Method to create basic tables
	 */
	public void createTables() {
		//do I need this?
		Connection c = dbConnect();
	}
	
	//User information
	/**
	 * Method to return a users id by username
	 * @param userName
	 * @return
	 */
	public int getUser(String userName) {
		Connection c = dbConnect();
		int userId = -1;
		try {
			Statement stmt = c.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery( "SELECT * FROM USERS WHERE USERNAME = "+userName+";");
		    while ( rs.next() ) {
		    	userId = rs.getInt("USERID");
		    }
		    rs.close();
		    stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userId;
	}
	
	/**
	 * Method to create a user in the database
	 * @param userName
	 * @param password
	 */
	public void insertUser(String userName, String password)
	{
		Connection c = dbConnect();
		Statement stmt = null;
		try {
			stmt = c.createStatement();
			String sql = "INSERT INTO USERS (USERNAME, PASSWORD) " +
						 "VALUES ("+userName+", "+password+");"; 
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Method to check user login information
	 * @param userId
	 * @param password
	 */
	public boolean checkLogin(int userId, String password) {
		//TODO: Make this work
		return true;
	}
	
	//Move information
	/**
	 * Method to get all moves from a game id
	 * @param gameId
	 * @return
	 */
	public String getAllMoves(int gameId) {
		Connection c = dbConnect();
		String moves = "";
		try {
			Statement stmt = c.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery( "SELECT * FROM MOVES WHERE GAMEID = "+gameId+";");
		    while ( rs.next() ) {
		    	//Uhhh?
		    }
		    rs.close();
		    stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return moves;
		
	}
	
	/**
	 * Method to insert a move into the database
	 * @param userId
	 * @param gameId
	 * @param colFrom
	 * @param rowFrom
	 * @param colTo
	 * @param rowTo
	 */
	public void insertMove(int userId, int gameId, int colFrom, int rowFrom, int colTo, int rowTo)
	{
		Connection c = dbConnect();
		Statement stmt = null;
		try {
			stmt = c.createStatement();
			String sql = "INSERT INTO MOVES (USERID, GAMEID, COLFROM, ROWFROM, COLTO, ROWTO) " +
						 "VALUES ("+userId+", "+gameId+", "+colFrom+", "+rowFrom+", "+colTo+", "+rowTo+");"; 
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	}
}
