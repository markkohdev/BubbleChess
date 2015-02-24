package com.bubblechess.client;

public class Move {
	
	//Coordinates are passed as [col,row] => [x,y]
	
	private int[] coordinateFrom;
	private int[] coordinateTo;
	
	public Move(int[] from, int[] to) throws Exception {
		if(from.length != 2 || to.length != 2){
			throw new Exception("Error: Invalid coordinate.");
		}
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
}
