package com.bubblechess.client;

import java.util.ArrayList;
import java.util.Arrays;

import com.bubblechess.client.pieces.*;
import com.bubblechess.client.BoardPiece.Color;

public class ChessBoard implements Board {
	
	/**
	 * To access a piece, board is stored as [col][row] where [0][0] is the 
	 * lower lefthand corner of the board
	 */
	protected BoardPiece[][] board;
	
	// Fill in the state
	protected enum STATE { WHITE_MOVE, BLACK_MOVE, CHECKMATE, STALEMATE };
	
	protected ArrayList<BoardPiece> captured;
	protected int boardWidth = 8;
	protected int boardHeight = 8;
	protected STATE state;
	
	/**
	 * Constructor
	 */
	public ChessBoard(){
		init();
	}
	
	public ChessBoard(BoardPiece[][] board,BoardPiece[] captured, STATE state){
		this.board = board;
		this.captured = new ArrayList<BoardPiece>(Arrays.asList(captured));
		this.state = state;
	}

	/**
	 * Board initializer. Sets up pieces and initial state.
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
		
		state = STATE.WHITE_MOVE;
	}
	

	@Override
	public BoardPiece[][] getBoard() {
		BoardPiece[][] newBoard = new BoardPiece[boardWidth][boardHeight];
		
		//Run through the board and populate a new board 		
		for (int col=0;col<boardHeight;col++){
			for (int row=0;row<boardWidth;row++){
				newBoard[col][row] = board[col][row].clone();
			}
		}
		
		return newBoard;
	}
	
	/**
	 * We wanna use BoardPiece[] here because it makes copies and not references
	 */
	public BoardPiece[] getCaptured() {
		return (BoardPiece[])this.captured.toArray();
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
				//Update the state
				updateState();
				
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
		updateState();

		return newBoard;
	}

	/**
	 * Performs a deep copy of a ChessBoard
	 * @return An identical ChessBoard
	 */
	@Override
	public Board clone() {	
		ChessBoard newBoard = new ChessBoard(this.getBoard(),this.getCaptured(),this.state);
		
		return newBoard;
	}

	/**
	 * Returns a list of legal moves for a piece in the given location
	 * @param col x-coordinate
	 * @param row y-coordinate
	 * @return An ArrayList of Moves
	 */
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

	/**
	 * @return The current state as a string
	 */
	@Override
	public String getState() {
		switch(this.state) {
		case WHITE_MOVE:
			return "White to Move";
		case BLACK_MOVE:
			return "Black to Move";
		case CHECKMATE:
			return "Checkmate";
		case STALEMATE:
			return "Stalemate";
		default:
			return null;
		}
	}

	/**
	 * Checks if the board is an end-state (checkmate or stalemate)
	 * @return True if there is checkmate or stalemate, False otherwise
	 */
	@Override
	public boolean endState() {
		if (this.state==STATE.CHECKMATE || this.state==STATE.STALEMATE) {
			return true;
		}
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

	/**
	 * Preliminary move validation. There is an important distinction between whether
	 * a piece can attack a square and whether a piece can move to that square.
	 * For instance, a pawn attacks a square diagonally but can only move there if
	 * an enemy piece occupies it. It is important to recognize these cases because
	 * the enemy king cannot move into a pawn's attack even though the pawn
	 * cannot actually move there legally. 
	 * 
	 * As another example, a king cannot move into a square attacked by a knight that
	 * is pinned to its own king, even though the knight cannot move to occupy that square.
	 * 
	 * This method is to verify attacking squares.
	 * @param m A move
	 * @return True if the piece can attack this square, False otherwise
	 */
	protected boolean validAttack(Move m){
		//TODO: Validate move here
		/**
		 * A move consists of an origin and a destination
		 * For a move to be psuedo-legal:
		 * 1. The piece must be able to reach the destination square
		 * 		- if knight, check destination
		 * 			- if friendly, return false
		 * 			- otherwise, goto 2
		 * 		- if king, check destination
		 * 			- is this castling? perhaps checked separately
		 * 			- if friendly, return false
		 * 			- otherwise, goto 2
		 * 		- if queen, rook, or bishop
		 * 			- check path from origin to destination
		 * 				- if blocked by any piece, return false
		 * 				- if open, check destination
		 * 					- if friendly, return false
		 * 					- otherwise, goto 2
		 * 		- if pawn
		 * 			- tbd
		 */
		
		//Create a reference to the piece to work with
		ChessPiece piece = (ChessPiece)board[m.colFrom()][m.rowFrom()];
		if (piece == null) {
			return false;
		}
		else if (piece instanceof Knight) {
			//TODO
		}
		else if (piece instanceof King) {
			//TODO
		}
		else if (piece instanceof Queen || piece instanceof Bishop 
				|| piece instanceof Rook) {
			//TODO
		}
		else { // piece instanceof Pawn
			//TODO
		}
		
			
		return false;
	}
	
	/**
	 * 
	 * @param m A move
	 * @return True if move is legal, False otherwise
	 */
	protected boolean validMove(Move m){
		/** Check to see if move leaves OR places king into check (under attack)
		 * 		- note that this requires a loose definition of "check"
		 * 		- achieve this by using applyMoveCloning and checking new state
		 * 		- if move results in "check"
		 * 			- return false
		 * 			- otherwise, return true
		 */
		
		//First determine if validAttack is true
		if (!validAttack(m)) {
			return false;
		} //continue
		
		//Then determine if this is also a valid move (see if it leaves us in check, etc)
		ChessBoard newBoard = (ChessBoard)applyMoveCloning(m);
		
		//TODO: call to inCheck()
		
		return false;
	}
	
	/**
	 * Helper method for validMove
	 * @param color Color.WHITE or Color.BLACK
	 * @return True if the player's king is attacked, False otherwise
	 */
	protected boolean inCheck(Color color){
		int[] kingLoc = new int[2];
		
		//Find the king
		for (int col=0;col<boardWidth;col++){
			for (int row=0;row<boardHeight;row++){
				ChessPiece piece = (ChessPiece)board[col][row];
				if (piece instanceof King && piece.color == color){
					kingLoc[0] = col;
					kingLoc[1] = row;
					break;
				}
			}
		}
		
		//TODO generate all valid attacks for opponent (ignore leaving king in danger)
		//if any of their destination squares are the same as kingLoc,
		//it means that we are in check.
		
		
		
		return false;
	}
	
	protected boolean handleSpecialCase(Move m){
		//TODO: Handle special case logic
		//Castling, en passant, promotion??? (requires an extra call to player)
		
		//Also handle state changes??
		return false;
	}
	
	protected void updateState(){
		//TODO: update STATE (recognize checkmate and stalemate)
	}

}
