package com.bubblechess.client;

import java.util.ArrayList;

public interface BoardPiece {

	public enum Color {WHITE, BLACK};
		
	public BoardPiece clone();
	
	public ArrayList<Move> getAllMoves(int x, int y);
	
	public Color getColor();
}
