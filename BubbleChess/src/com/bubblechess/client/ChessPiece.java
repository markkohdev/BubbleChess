package com.bubblechess.client;

import java.util.ArrayList;

public abstract class ChessPiece implements BoardPiece {

	public Color color;
	
	protected int[] N = {0,1};
	protected int[] E = {1,0};
	protected int[] S = {0,-1};
	protected int[] W = {-1,0};
	protected int[] NE = {1,1};
	protected int[] SE = {1,-1};
	protected int[] SW = {-1,-1};
	protected int[] NW = {-1,1};
	
	public abstract ArrayList<Move> getMoves(int x, int y);

	public String getColor() {
		switch(color) {
		case WHITE: return "WHITE"; 
		case BLACK: return "BLACK";
		default: return "ERROR";
		}
	}
	
	public BoardPiece clone(){
		return this;
	}
}
