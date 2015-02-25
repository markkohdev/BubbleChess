package com.bubblechess.client;

import java.util.ArrayList;
import java.util.Arrays;

public class ChessBoard implements Board {
	
	/**
	 * To access a piece, board is stored as [col][row] where [0][0] is the 
	 * lower lefthand corner of the board
	 */
	protected BoardPiece[][] board;
	
	
	protected ArrayList<BoardPiece> captured;
	protected int boardWidth = 8;
	protected int boardHeight = 8;
	
	// Fill in the state
	public enum STATE {};
	
	/**
	 * Constructor
	 */
	public ChessBoard(){
		init();
	}
	
	public ChessBoard(BoardPiece[][] board,BoardPiece[] captured){
		this.board = board;
		this.captured = new ArrayList<BoardPiece>(Arrays.asList(captured));
	}

	/**
	 * Board initializer.  Place all the board pieces in and jawn
	 */
	protected void init(){
		board = new BoardPiece[boardWidth][boardHeight];
		for (int i=0;i<8;i++) {
			board[i][1] = new Pawn(Color.WHITE);
			board[i][6] = new Pawn(Color.BLACK);
		}
		board[0][0] = new Rook(Color.WHITE);
		board[0][7] = new Rook(Color.BLACK);
		board[1][0] = new Knight(Color.WHITE);
		board[1][7] = new Knight(Color.BLACK);
		board[2][0] = new Bishop(Color.WHITE);
		board[2][7] = new Bishop(Color.BLACK);
		board[3][0] = new Queen(Color.WHITE);
		board[3][7] = new Queen(Color.BLACK);
		board[4][0] = new King(Color.WHITE);
		board[4][7] = new King(Color.BLACK);
		board[5][0] = new Bishop(Color.WHITE);
		board[5][7] = new Bishop(Color.BLACK);
		board[6][0] = new Knight(Color.WHITE);
		board[6][7] = new Knight(Color.BLACK);
		board[7][0] = new Rook(Color.WHITE);
		board[7][0] = new Rook(Color.BLACK);		
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
	
	public BoardPiece[] getCaptured() {
		return (BoardPiece[]) this.captured.toArray();
	}
	
	@Override
	public int getWidth() {
		return boardWidth;
	}

	@Override
	public int getHeight() {
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

	/**
	 * Returns a new copy of the board with the given move applied
	 * @return A new, udpated ChessBoard
	 */
	@Override
	public Board applyMoveCloning(Move m) {
		Board newBoard = this.clone();
		newBoard.applyMove(m);

		return newBoard;
	}

	/**
	 * Performs a deep copy of a ChessBoard
	 * @return An identical ChessBoard
	 */
	@Override
	public Board clone() {
		
		ChessBoard newBoard = new ChessBoard(this.getBoard(),this.getCaptured());
		
		return newBoard;
	}

	@Override
	public ArrayList<Move> getMoves(int col, int row) {
		//Make sure the square isn't empty.  If it is, return an empty list
		if(board[col][row] == null){
			return new ArrayList<Move>();
		}
		else {
			//Return the moves available from the piece
			ArrayList<Move> pieceMoves = board[col][row].getMoves(col, row);
			
			ArrayList<Move> validMoves = new ArrayList<Move>();
			
			//Iterate through the moves given to us and make sure none are illegal
			for(Move m: pieceMoves){
				if (validMove(m)){
					validMoves.add(m);
				}
			}
			
			return validMoves;
		}
	}

	@Override
	public ArrayList<Move> getMoves(char col, char row) {
		//col will be letter from a-z, convert to 0-n
		int x = col-97;
		//row will be number
		int y = Character.getNumericValue(row);
		return getMoves(x, y);
	}

	@Override
	public String getState() {
		
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
		
		//Also handle state changes??
		return false;
	}
	
	protected void updateState(){
		
	}

}
