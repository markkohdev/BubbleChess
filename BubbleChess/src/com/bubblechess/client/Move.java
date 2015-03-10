package com.bubblechess.client;
import org.json.simple.*;

public class Move {
	
	//Coordinates are passed as [col,row] => [x,y]
	
	private int[] coordinateFrom;
	private int[] coordinateTo;
	
	/**
	 * Constructor for the move object
	 * @param from
	 * @param to
	 */
	public Move(int[] from, int[] to) {
		coordinateFrom = new int[] {from[0],from[1]};
		coordinateTo = new int[] {to[0],to[1]};
	}
	
	/**
	 * Gets the from coordinates
	 * @return
	 */
	public int[] from(){
		return coordinateFrom;
	}
	
	/**
	 * Gets the to coordinates
	 * @return
	 */
	public int[] to(){
		return coordinateTo;
	}
	
	/**
	 * Gets the column the move will be from
	 * @return
	 */
	public int colFrom(){
		return coordinateFrom[0];
	}
	
	/**
	 * Gets the row the move will be from
	 * @return
	 */
	public int rowFrom(){
		return coordinateFrom[1];
	}
	
	/**
	 * Gets the column the move will be to
	 * @return
	 */
	public int colTo(){
		return coordinateTo[0];
	}
	
	/**
	 * Gets the row the move will be to
	 * @return
	 */
	public int rowTo(){
		return coordinateTo[1];
	}
	
	/**
	 * Converts the move object to a string
	 * @return
	 */
	public String toString(){
		String str = String.format("(%d,%d),(%d,%d)", colFrom(),rowFrom(),colTo(),rowTo());
		return str;
	}
	
	/**
	 * Converts the move object to JSONObject
	 * @return
	 */
	public JSONObject toJSON(){
		JSONObject json = new JSONObject();
		json.put("colFrom", colFrom());
		json.put("rowFrom", rowFrom());
		json.put("colTo", colTo());
		json.put("rowTo", rowTo());
		return json;
		
	}
}
