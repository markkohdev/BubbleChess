package com.bubblechess.client;

import java.util.ArrayList;

public interface BoardPiece {

	public enum Color {WHITE, BLACK};
		
	public BoardPiece clone();
	
	public ArrayList<Move> getMoves(int x, int y);
	
	public ArrayList<Move> getSpecialMoves(int x, int y);
	
	public Color getColor();
}
