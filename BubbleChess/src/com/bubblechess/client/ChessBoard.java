package com.bubblechess.client;

import java.util.ArrayList;

public class ChessBoard implements Board {
	
	/**
	 * To access a piece, board is stored as [col][row] where [0][0] is the 
	 * lower lefthand corner of the board
	 */
	protected BoardPiece[][] board;
	
	
	protected ArrayList<BoardPiece> captured;
	protected int boardWidth = 8;
	protected int boardHeight = 8;
	
	/**
	 * Constructor
	 */
	public ChessBoard(){
		init();
	}

	/**
	 * Board initializer.  Place all the board pieces in and jawn
	 */
	protected void init(){
		board = new BoardPiece[boardWidth][boardHeight];
		//TODO: Add piece initialization here
		
	}
	

	@Override
	public BoardPiece[][] getBoard() {
		BoardPiece[][] newBoard = new BoardPiece[boardWidth][boardHeight];
		
		//Run through the board and populate a new board 
		
		for (int col=0;col<board.length;col++){
			for (int row=0;row<board[col].length;row++){
				newBoard[col][row] = board[col][row].clone();
			}
		}
		
		return newBoard;
	}
	
	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return boardWidth;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return boardHeight;
	}

	@Override
	public boolean applyMove(Move m) {
		if (validMove(m)) {
			
			//Apply the move
			boolean specialCase = handleSpecialCase(m);
			if (specialCase) {
				return true;
			}
			else {
				//Check if anything got captured
				if(getPiece(m.to()) != null){
					//Add piece to captured list, remove it from the board
					captured.add(getPiece(m.to()));
					board[m.colTo()][m.rowTo()] = null;
				}
				
				//Move the piece
				board[m.colTo()][m.rowTo()] = board[m.colFrom()][m.rowFrom()];
				board[m.colFrom()][m.rowFrom()] = null;
				
				return true;
			}
		}

		return false;
	}

	@Override
	public Board applyMoveCloning(Move m) {
		Board newBoard = this.clone();
		newBoard.applyMove(m);

		return newBoard;
	}

	@Override
	public Board clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Move> getAllMoves() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Move> getAllMoves(Player player) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Move> getMovesForPiece(int col, int row) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Move> getMovesForPiece(char col, char row) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean endState() {
		// TODO Auto-generated method stub
		return false;
	}
	
	protected BoardPiece getPiece(int[] coord){
		if (coord.length != 2)
			return null;
		return board[coord[0]][coord[1]];
	}

	
	protected BoardPiece getPiece(int col, int row){
		return board[col][row];
	}

	protected boolean validMove(Move m){
		//TODO: Validate move here
		return false;
	}
	
	protected boolean handleSpecialCase(Move m){
		//TODO: Handle special case logic
		//Castling, etc.
		return false;
	}

	@Override
	public BoardPiece[] getCaptured() {
		// TODO Auto-generated method stub
		return null;
	}

}
