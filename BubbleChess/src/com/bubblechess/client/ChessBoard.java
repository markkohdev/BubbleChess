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
				
				updateState(); //??
				
				return true;
			}
			else {
				//Check if anything got captured
				if(getPiece(m.to()) != null){
					//Add piece to captured list, remove it from the board
					captured.add(getPiece(m.to()));
					board[m.colTo()][m.rowTo()] = null;
				}
				
				((ChessPiece)board[m.colTo()][m.rowTo()]).hasMoved = true;
				//Move the piece
				board[m.colTo()][m.rowTo()] = board[m.colFrom()][m.rowFrom()];
				board[m.colFrom()][m.rowFrom()] = null;

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
			pieceMoves.addAll(board[col][row].getSpecialMoves(col, row));
			
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
	 * Returns true if the piece on the origin square attacks the destination square,
	 * e.g. it is unblocked
	 * Note that for castling and moving a pawn forward two spaces this function
	 * will return true, even though they don't attack the destination square
	 * @param m A move
	 * @return True if the piece can attack this square, False otherwise
	 */
	protected boolean validAttack(Move m){
		ArrayList<int[]> squares = new ArrayList<int[]>();
		ChessPiece piece = (ChessPiece)board[m.colFrom()][m.rowFrom()];
		
		if (piece == null){
			return false;
		}
		
		//For the Knight, origin and destination squares are "adjacent"
		//Also I'm including pawn and king here because for castling and moving
		//forward two squares the path must be unblocked for a legal move
		if (!(piece instanceof Knight)) {
			squares = getSquaresOnPath(m);
			
			//All squares must be empty
			for (int[] sq : squares){
				if ((ChessPiece)board[sq[0]][sq[1]] != null){
					return false;
				}
			}
		}
			
		return true;
	}
	
	/**
	 * Determine if the given move is legal
	 * @param m A move
	 * @return True if move is legal, False otherwise
	 */
	protected boolean validMove(Move m){
		//validAttack=true is a prerequisite for validMove
		if (!validAttack(m)){
			return false;
		}
		
		ChessPiece piece = (ChessPiece)board[m.colFrom()][m.rowFrom()]; 
		ChessPiece dest = (ChessPiece)board[m.colTo()][m.rowTo()];
		
		//Check destination square for a friendly piece
		if (dest != null){ //occupied
			if (piece.getColor()==dest.getColor()){ //friendly
				return false;
			}
			else { //enemy
				//Cannot move a pawn forward if destination occupied by an enemy piece
				if (piece instanceof Pawn && m.colFrom()-m.colTo()==0){
					return false;
				}
				//Cannot castle when destination square is enemy piece
				if (piece instanceof King && Math.abs(m.colFrom()-m.colTo())==2){
					return false;
				}
			}
		}
		else { //empty
			//Destination square is empty so remove pawn non-captures
			//unless...
			//TODO: Allow for en passant. This requires knowledge of the previous move
			if (piece instanceof Pawn && m.colFrom()!=m.colTo()){
				return false;
			}
		}
		
		//Check for castling (cannot move through check or out of check)
		if (piece instanceof King && Math.abs(m.colFrom()-m.colTo())==2){
			//TODO
		}

		ChessBoard newBoard = (ChessBoard)applyMoveCloning(m);
		
		//If this move puts or leaves us in check, it is illegal
		if(newBoard.inCheck(piece.getColor())){
			return false;
		}
		
		return true;
	}
	
	/**
	 * Return true if the given player is in check
	 * @param color Color.WHITE or Color.BLACK
	 * @return True if the player's king is attacked, False otherwise
	 */
	protected boolean inCheck(Color color){
		ArrayList<Move> moves = new ArrayList<Move>();
		ArrayList<Move> validAttacks = new ArrayList<Move>();
		int[] kingLoc = new int[2];
		
		//Cycle through the board
		for (int col=0;col<boardWidth;col++){
			for (int row=0;row<boardHeight;row++){
				ChessPiece piece = (ChessPiece)board[col][row];
				if (piece == null){
					break;
				}
				
				//Get attacking moves
				if (piece.color != color){
					//Don't consider special moves (castling, move pawn forward 2)
					//because they don't attack destination squares
					moves = board[col][row].getMoves(col, row);
					for (Move m : moves){
						if (validAttack(m)){
							validAttacks.add(m);
						}
					}
				}
				
				//Find the king
				if (piece instanceof King && piece.color == color){
					kingLoc[0] = col;
					kingLoc[1] = row;
				}
			}
		}
		
		//Determine if any of the valid attacking moves threaten the king
		for (Move m : validAttacks){
			if (m.colTo()==kingLoc[0] && m.rowTo()==kingLoc[1]){
				return true;
			}
		}
				
		return false;
	}
	
	/**
	 * Return an ordered list of squares between origin and destination squares
	 * or an empty list if the squares are adjacent
	 * @param m A move
	 * @return A list of squares between origin and destination
	 */
	protected ArrayList<int[]> getSquaresOnPath(Move m){
		ArrayList<int[]> squares = new ArrayList<int[]>();
		int[] from = {m.colFrom(), m.rowFrom()};
		int[] to = {m.colTo(), m.rowTo()};
		int[] sel = new int[2];

		//Directional vectors
		int i = m.colTo()-m.colFrom();
		int j = m.rowTo()-m.rowFrom();
		
		//Normalize to -1, 0, 1
		if (i!=0){
			i = i/i;
		}
		if (j!=0){
			j = j/j;
		}
		
		//Next selection
		sel[0] = from[0]+i;
		sel[1] = from[1]+j;
		
		while (!(sel[0]==to[0] && sel[1]==to[1])){
			squares.add(sel);
			sel[0] += i;
			sel[1] += j;
		}
		
		return squares;
	}
	
	/**
	 * TODO: Recognize and handle special cases - deprecate?!
	 * @param m A move
	 * @return
	 */
	protected boolean handleSpecialCase(Move m){
		// TODO: execute castling and en passant (but dont need to validate)
		// TODO: recognize and handle promotion here

		return false;
	}
	
	/**
	 * Updates the board state variable
	 * WHITE_MOVE, BLACK_MOVE, CHECKMATE, or STALEMATE
	 */
	protected void updateState(){
		//TODO: update STATE (recognize checkmate and stalemate)
		
		//if not, then flip player to move
		if (state==STATE.WHITE_MOVE){
			state = STATE.BLACK_MOVE;
		}
		if (state==STATE.BLACK_MOVE){
			state = STATE.WHITE_MOVE;
		}
	}

}
