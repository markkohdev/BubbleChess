package com.bubblechess.client;

import java.util.ArrayList;

import com.bubblechess.client.BoardPiece.Color;

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
	 * Board initializer. Place all board pieces.
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
		ChessBoard chessboard = new ChessBoard();
		chessboard.boardWidth = this.boardWidth;
		chessboard.boardHeight = this.boardHeight;
		chessboard.captured = this.captured;
		
		// copy board contents
		for (int row=0;row<chessboard.boardHeight;row++) {
			for (int col=0;col<chessboard.boardWidth;col++) {
				chessboard.board[col][row] = this.board[col][row];
			}
		}
		
		return chessboard;
	}

	/**
	 * Returns a list of all possible moves
	 * Not guaranteed to be legal
	 * @return An ArrayList of Moves
	 */
	@Override
	public ArrayList<Move> getAllMoves() {
		ArrayList<Move> moves = new ArrayList<Move>();
		moves.addAll(getAllMoves(Color.WHITE));
		moves.addAll(getAllMoves(Color.BLACK));
		
		return moves;
	}
	
	/**
	 * Returns a list of all possible moves for one side
	 * Not guaranteed to be legal
	 * @param color The color of the ChessPiece
	 * @return An ArrayList of Moves
	 */
	@Override
	public ArrayList<Move> getAllMoves(Color color) {
		ArrayList<Move> moves = new ArrayList<Move>();
		for (int row=0;row<boardHeight;row++) {
			for (int col=0;col<boardWidth;col++) {
				if (board[col][row] != null && board[col][row].getColor() == color) {
					moves.addAll(board[col][row].getAllMoves(col,row));
				}
			}
		}
		
		return moves;
	}

	/**
	 * Returns a list of all possible moves for the piece at a location
	 * @param col The column
	 * @param row The row
	 * @return A list of possible moves for the piece at a location, empty if null
	 */
	@Override
	public ArrayList<Move> getMovesForPiece(int col, int row) {
		if (board[col][row] == null) {
			return new ArrayList<Move>();
		}
		return board[col][row].getAllMoves(col,row);
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

	/**
	 * Returns an arraylist of captured BoardPieces
	 */
	@Override
	public ArrayList<BoardPiece> getCaptured() {
		return captured;
	}

}
