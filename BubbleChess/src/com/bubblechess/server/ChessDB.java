package com.bubblechess.server;

import java.sql.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ChessDB {
	
	private boolean _isTest;
	
	/**
	 * Constructor for the ChessDB
	 */
	public ChessDB(boolean isTest) {
		_isTest = isTest;
	}
	
	/**
	 * Sets up connection to the sqlite database
	 * @return
	 */
	public Connection dbConnect() {
		 Connection c = null;
		 try {
		   	Class.forName("org.sqlite.JDBC");
		    
		   	String fileName = "";
		   	
		   	if(_isTest) {
		   		c = DriverManager.getConnection("jdbc:sqlite:Test.db");
		   	}
		   	else {
		   		c = DriverManager.getConnection("jdbc:sqlite:Chess.db");
		   	}
		   	
		    c.setAutoCommit(true);
		    //System.out.println("Opened database successfully");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Database failed to open :(");
			e.printStackTrace();
		}
		 return c;
	}
	
	/**
	 * Creates the tables in the database
	 */
	public void createTables() {
		//do I need this?
		Connection c = dbConnect();
		Statement stmt = null;
	    try {
	    	c = dbConnect();

	    	//Users Table
	    	stmt = c.createStatement();
	    	String sql = "CREATE TABLE IF NOT EXISTS USERS " +
	    				"(ID INTEGER PRIMARY KEY	AUTOINCREMENT," +
	    				" USERNAME           TEXT   NOT NULL," + 
	    				" PASSWORD           TEXT   NOT NULL)";
	    	stmt.executeUpdate(sql);
	    	stmt.close();
	    	
	    	//Moves Table
	    	stmt = c.createStatement();
	    	sql = "CREATE TABLE IF NOT EXISTS MOVES " +
	    			"(ID INTEGER PRIMARY 	KEY   	AUTOINCREMENT," +
	    			"GAMEID           		INTEGER   NOT NULL," + 
	    			"USERID           		INTEGER   NOT NULL," + 
	    			"COLFROM           		INTEGER   NOT NULL," + 
	    			"ROWFROM           		INTEGER   NOT NULL," + 
	    			"COLTO           		INTEGER   NOT NULL," + 
	    			"ROWTO           		INTEGER   NOT NULL)";
	    	stmt.executeUpdate(sql);
	    	stmt.close();
	    	
	    	//Games Table
	    	stmt = c.createStatement();
	    	sql = "CREATE TABLE IF NOT EXISTS GAMES " +
	    			"(ID INTEGER PRIMARY KEY	   	AUTOINCREMENT," +
	    			"USER1ID           		INTEGER," + 
	    			"USER2ID           		INTEGER," + 
	    			"GAMESTATUS           	INTEGER   	NOT NULL)";
	    	stmt.executeUpdate(sql);
	    	stmt.close();
	    	
	    	c.close();
	    } catch ( Exception e ) {
	    	System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    	System.exit(0);
	    }
	    //System.out.println("Tables created successfully");
	}
	
	//User information
	/**
	 * Returns a userID from a username
	 * @param userName
	 * @return
	 */
	public int getUser(String userName) {
		Connection c = dbConnect();
		int userId = -1;
		try {
			Statement stmt = c.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery( "SELECT * FROM USERS WHERE USERNAME = '"+userName+"'");
		    while (rs.next()) {
		    	userId = rs.getInt("ID");
		    }
		    rs.close();
		    stmt.close();
		    c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userId;
	}
	
	/**
	 * Returns a username from a userID
	 * @param userId
	 * @return
	 */
	public String getUsername(int userId) {
		Connection c = dbConnect();
		
		String username = "";
		
		try {
			Statement stmt = c.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery( "SELECT * FROM USERS WHERE ID = '"+userId+"'");
		    while (rs.next()) {
		    	username = rs.getString("USERNAME");
		    }
		    rs.close();
		    stmt.close();
		    c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return username;
	}
	
	/**
	 * Creates a user in the database
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
						 "VALUES ('"+userName+"', '"+password+"');";
			stmt.executeUpdate(sql);
			stmt.close();
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Checks the login information of a user
	 * @param userId
	 * @param password
	 */
	public boolean checkLogin(int userId, String password) {
		Connection c = dbConnect();
		boolean result = false;
		
		try {
			Statement stmt = c.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery( "SELECT * FROM USERS WHERE ID = '"+userId+"';");
		   
			while ( rs.next() ) {
				String dbUserName = rs.getString("ID");
		    	String dbPass = rs.getString("PASSWORD");
		    	
		    	if(password.equals(dbPass)) {
		    		rs.close();
				    stmt.close();
				    c.close();
		    		return true;
		    	}
		    }
		    rs.close();
		    stmt.close();
		    c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	//Move information
	/**
	 * Gets all moves from a specific game
	 * @param gameId
	 * @return
	 */
	public String getAllMoves(int gameId) {
		Connection c = dbConnect();
		JSONArray moves =  null;
		
		try {
			Statement stmt = c.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery( "SELECT * FROM MOVES WHERE GAMEID = "+gameId+";");
		   
			moves = new JSONArray();
			while ( rs.next() ) {
		    	//int userId, int colFrom, int rowFrom, int colTo, int rowTo
		    	int userId = rs.getInt("USERID");
		    	int colFrom = rs.getInt("COLFROM");
		    	int rowFrom = rs.getInt("ROWFROM");
		    	int colTo = rs.getInt("COLTO");
		    	int rowTo = rs.getInt("ROWTO");
		    	
		    	JSONObject json = new JSONObject();
        		json.put("userID",userId);
        		json.put("colFrom",colFrom);
        		json.put("rowFrom",rowFrom);
        		json.put("colTo",colTo);
        		json.put("rowTo",rowTo);
        		
        		moves.add(json);
		    }
		    rs.close();
		    stmt.close();
		    c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return moves.toJSONString();
	}
	
	/**
	 * Inserts a move into the database for a game
	 * @param userId
	 * @param gameId
	 * @param colFrom
	 * @param rowFrom
	 * @param colTo
	 * @param rowTo
	 */
	public boolean insertMove(int userId, int gameId, int colFrom, int rowFrom, int colTo, int rowTo)
	{
		Connection c = dbConnect();
		Statement stmt = null;
		try {
			stmt = c.createStatement();
			String sql = "INSERT INTO MOVES (USERID, GAMEID, COLFROM, ROWFROM, COLTO, ROWTO) " +
						 "VALUES ("+userId+", "+gameId+", "+colFrom+", "+rowFrom+", "+colTo+", "+rowTo+");"; 
			stmt.executeUpdate(sql);
			stmt.close();
			c.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	   
	}
	
	//Game Information
	/**
	 * Returns a game as a JSON string
	 * @param gameId
	 * @return
	 */
	public String getGame(int gameId) {
		Connection c = dbConnect();
		
		String game = "";
		
		try {
			Statement stmt = c.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery( "SELECT * FROM GAMES WHERE ID = '"+gameId+"'");
		    while (rs.next()) {
		    	int user1Id = rs.getInt("USER1ID");
		    	int user2Id = rs.getInt("USER2ID");
		    	int gameStatus = rs.getInt("GAMESTATUS");
		    	
		    	JSONObject json = new JSONObject();
		    	json.put("user1ID", user1Id);
		    	json.put("user2ID", user1Id);
		    	json.put("gameStatus", gameStatus);
		    	
		    	game = json.toJSONString();
		    }
		    rs.close();
		    stmt.close();
		    c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return game;
	}
	
	/**
	 * Inserts a game into the database
	 * @param userId
	 * @param playerNumber
	 */
	public int insertGame(int userId, int playerNumber)
	{
		Connection c = dbConnect();
		Statement stmt = null;
		int gameId = -1;
		try {
			stmt = c.createStatement();
			
			String sql = "";
			if(playerNumber == 1) {
				sql = "INSERT INTO GAMES (USER1ID, GAMESTATUS) " +
						 	 "VALUES ('"+userId+"', '0');";
			}
			else if(playerNumber == 2) {
				sql = "INSERT INTO GAMES (USER2ID, GAMESTATUS) " +
					 	     "VALUES ('"+userId+"', '0');";
			}
			stmt.executeUpdate(sql);
			stmt.close();
			
			stmt = c.createStatement();
			sql = "SELECT last_insert_rowid()";
			
			ResultSet generatedKeys = stmt.executeQuery(sql);
			if(generatedKeys.next()) {
				gameId = (int) generatedKeys.getLong(1);
			}
			
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return gameId;
	}
	
	/** 
	 * Adds an opponent to a game in the DB
	 * @param userId
	 * @param playerNumber
	 * @param gameId
	 */
	public void addOpponent(int userId, int playerNumber, int gameId) {
		Connection c = dbConnect();
		Statement stmt = null;
		try {
			stmt = c.createStatement();
			
			String sql = "";
			if(playerNumber == 1) {
				sql = "UPDATE GAMES SET USER1ID='"+userId+"', GAMESTATUS='0' " +
						 	 "WHERE ID = '"+gameId+"';";
			}
			else if(playerNumber == 2) {
				sql = "UPDATE GAMES SET USER2ID='"+userId+"', GAMESTATUS='0' " +
					 	 "WHERE ID = '"+gameId+"';";
			}
			
			stmt.executeUpdate(sql);
			stmt.close();
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
