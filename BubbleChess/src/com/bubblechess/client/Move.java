package com.bubblechess.client;
import org.json.simple.*;

public class Move {
	
	//Coordinates are passed as [col,row] => [x,y]
	
	private int[] coordinateFrom;
	private int[] coordinateTo;
	
	public Move(int[] from, int[] to) {
		coordinateFrom = new int[] {from[0],from[1]};
		coordinateTo = new int[] {to[0],to[1]};
	}
	
	public int[] from(){
		return coordinateFrom;
	}
	
	public int[] to(){
		return coordinateTo;
	}
	
	public int colFrom(){
		return coordinateFrom[0];
	}
	
	public int rowFrom(){
		return coordinateFrom[1];
	}
	
	public int colTo(){
		return coordinateTo[0];
	}
	
	public int rowTo(){
		return coordinateTo[1];
	}
	
	public String toString(){
		String str = String.format("(%d,%d),(%d,%d)", colFrom(),rowFrom(),colTo(),rowTo());
		return str;
	}
	
	public JSONObject toJSON(){
		JSONObject json = new JSONObject();
		json.put("colFrom", colFrom());
		json.put("rowFrom", rowFrom());
		json.put("colTo", colTo());
		json.put("rowTo", rowTo());
		return json;
		
	}
}
