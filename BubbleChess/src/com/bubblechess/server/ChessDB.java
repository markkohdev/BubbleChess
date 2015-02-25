package com.bubblechess.server;

import java.sql.*;

public class ChessDB {
	
	/**
	 * Constructor for sqllite db
	 */
	public ChessDB() {
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
	}
	
	/**
	 * Method to create basic tables
	 */
	public void createTables() {
		//do I need this?
	}
	
	//User information
	public void getUsers() {
		
	}
	public void getUser() {
		
	}
	public void insertUser(Connection c, String userName, String password)
	{
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
	
	//Move information
	public void getMoves(int gameId) {
		
	}
	public void insertMove(Connection c, int userId, int gameId, int colFrom, int rowFrom, int colTo, int rowTo)
	{
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
