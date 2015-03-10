package com.bubblechess.client;

import java.util.ArrayList;
import java.util.Arrays;

import com.bubblechess.client.pieces.*;
import com.bubblechess.client.BoardPiece.Color;

public class ChessBoard implements Board, Cloneable {
	
	/**
	 * To access a piece, board is stored as [col][row] where [0][0] is the 
	 * lower lefthand corner of the board
	 */
	protected BoardPiece[][] board;
	
	protected enum STATE { WHITE_MOVE, BLACK_MOVE, CHECKMATE, STALEMATE, DRAW };
	
	protected ArrayList<BoardPiece> captured;
	protected int boardWidth = 8;
	protected int boardHeight = 8;
	protected STATE state;
	protected boolean enPassantEligible;
	protected int[] enPassantSquare = new int[2];
	protected int[] enPassantPawn = new int[2];
	protected int[] castlingRookOrigin = new int[2];
	protected int[] castlingRookDest = new int[2];
	protected boolean enPassant;
	protected boolean castling;
	protected boolean promotion;
	
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
	 * Allows the user to load a game with all the information needed
	 * to restart the game from the given position
	 * Undefined behavior for invalid FEN strings
	 * @param fen A string in modified Forsyth-Edwards notation
	 */
	public ChessBoard(String fen){
		String pieces, toMove, castling, enPassant, halfMoveClock, moveNumber;
		String[] tokens = fen.split(" ");
		
		pieces = tokens[0];
		toMove = tokens[1];
		castling = tokens[2];
		enPassant = tokens[3];
		halfMoveClock = tokens[4];
		moveNumber = tokens[5];
		
		String[] ranks = pieces.split("/");
		board = new BoardPiece[boardWidth][boardHeight];
		
		//at least some data validation
		if (ranks.length==8){
			for (int row=7;row>=0;row--){
				for (int col=0;col<boardHeight;col++){
					char piece = ranks[row].toCharArray()[col];
					
					switch(piece){
					case 'r':
						board[col][Math.abs(row-7)] = new Rook(Color.BLACK); break;
					case 'R':
						board[col][Math.abs(row-7)] = new Rook(Color.WHITE); break;
					case 'n':
						board[col][Math.abs(row-7)] = new Knight(Color.BLACK); break;
					case 'N':
						board[col][Math.abs(row-7)] = new Knight(Color.WHITE); break;
					case 'b':
						board[col][Math.abs(row-7)] = new Bishop(Color.BLACK); break;
					case 'B':
						board[col][Math.abs(row-7)] = new Bishop(Color.WHITE); break;
					case 'k':
						board[col][Math.abs(row-7)] = new King(Color.BLACK); break;
					case 'K':
						board[col][Math.abs(row-7)] = new King(Color.WHITE); break;
					case 'q':
						board[col][Math.abs(row-7)] = new Queen(Color.BLACK); break;
					case 'Q':
						board[col][Math.abs(row-7)] = new Queen(Color.WHITE); break;
					case 'p':
						board[col][Math.abs(row-7)] = new Pawn(Color.BLACK); break;
					case 'P':
						board[col][Math.abs(row-7)] = new Pawn(Color.WHITE); break;
					default:
						board[col][Math.abs(row-7)] = null;
					}
					
				}
			}
		}
		
		//Need to set opposite color to let updateState do it's thing
		if (toMove.equals("w")){
			state = STATE.BLACK_MOVE;
		}
		else{
			state = STATE.WHITE_MOVE;
		}
		
		updateState();
		
		if (castling.equals("-")){
			//TODO
		}
		else{
			//TODO: parse castling input and do things
		}
		
		if (enPassant.equals("-")){
			this.enPassant = false;
		}
		else{
			this.enPassant = true;
			enPassantSquare = convertToCoord(enPassant);
		}
		
		//TODO: do something with halfmove clock and move numbers?
	}
	
	/**
	 * Converts a square to a coordinate, ex: e3 -> [4,2]
	 * @param square
	 * @return The square in coordiante form
	 */
	protected int[] convertToCoord(String square){
		int[] result = new int[2];
		
		result[0] = square.toCharArray()[0] - 97;
		result[1] = square.toCharArray()[1];
		
		return result;
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
		board[7][7] = new Rook(Color.BLACK);	
		
		state = STATE.WHITE_MOVE;
		captured = new ArrayList<BoardPiece>();
		enPassantEligible = false;
	}

	@Override
	public BoardPiece[][] getBoard() {
		BoardPiece[][] newBoard = new BoardPiece[boardWidth][boardHeight];
		
		//Run through the board and populate a new board 		
		for (int col=0;col<boardHeight;col++){
			for (int row=0;row<boardWidth;row++){
				if (board[col][row]!=null){
					newBoard[col][row] = board[col][row].clone();
				}				
			}
		}
		
		return newBoard;
	}
	
	/**
	 * We wanna use BoardPiece[] here because it makes copies and not references
	 */
	public BoardPiece[] getCaptured() {
		if (this.captured==null){			
			return new BoardPiece[0];
		}
		BoardPiece[] result = new BoardPiece[this.captured.size()];
		this.captured.toArray(result);
		return result;
	}
	
	@Override
	public int getWidth() {
		return boardWidth;
	}

	@Override
	public int getHeight() {
		return boardHeight;
	}

	public boolean applyMove(Move m){
		return applyMove(m, true);
	}
	
	@Override
	public boolean applyMove(Move m, boolean validate) {
		if (!validate || validMove(m)) {
			
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
				
				//Set enPassantEligible
				if ((ChessPiece)getPiece(m.from()) instanceof Pawn 
						&& Math.abs(m.rowFrom()-m.rowTo())==2){
					enPassantEligible = true;
					enPassantSquare[0] = m.colTo();
					//Destination square is between origin and destination
					enPassantSquare[1] = Math.abs(m.rowFrom()+m.rowTo())/2;
					enPassantPawn[0] = m.colTo();
					enPassantPawn[1] = m.rowTo();
				}
				else {
					enPassantEligible = false;
				}
				
				//Move the piece
				board[m.colTo()][m.rowTo()] = board[m.colFrom()][m.rowFrom()];
				board[m.colFrom()][m.rowFrom()] = null;
				
				//Update hasMoved
				((ChessPiece)board[m.colTo()][m.rowTo()]).hasMoved = true;
				
				return true;
			}
		}

		return false;
	}
	
	public Board applyMoveCloning(Move m){
		return applyMoveCloning(m, true);
	}

	/**
	 * Returns a new copy of the board with the given move applied
	 * @return A new, udpated ChessBoard
	 */
	@Override
	public Board applyMoveCloning(Move m, boolean validate) {
		Board newBoard = this.clone();
		newBoard.applyMove(m, validate);

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
		case DRAW:
			return "Draw";
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
		if (this.state==STATE.CHECKMATE || this.state==STATE.STALEMATE || this.state==STATE.DRAW) {
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
		ChessPiece piece = (ChessPiece)getPiece(m.from());
		ChessPiece dest = (ChessPiece)getPiece(m.to());
		
		if (piece == null){
			return false;
		}
		
		//Pawn does not "attack" the square ahead, it can only move to occupy it
		if (piece instanceof Pawn && m.colFrom()==m.colTo() && dest!= null){
			return false;
		}
		
		//For the Knight, origin and destination squares are "adjacent"
		//Also I'm including pawn and king here because for castling and moving
		//forward two squares the path must be unblocked for a legal move
		if (!(piece instanceof Knight)) {
			squares = getSquaresOnPath(m);
			
			//All squares must be empty
			for (int[] sq : squares){
				if ((ChessPiece)getPiece(sq) != null){
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
		//Reset special move flags
		enPassant = false;
		castling = false;
		promotion = false;
		
		//validAttack=true is a prerequisite for validMove
		if (!validAttack(m)){
			return false;
		}
		
		ChessPiece piece = (ChessPiece)getPiece(m.from()); 
		ChessPiece dest = (ChessPiece)getPiece(m.to());
		
		//Check destination square for a friendly piece
		//Validate moves this way by applying the logic as a "filter"
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
			//Destination square is empty so remove pawn non-captures beside en passant
			if (piece instanceof Pawn && m.colFrom()!=m.colTo()){
				if (enPassantEligible && enPassantSquare[0]==m.colTo() && enPassantSquare[1]==m.rowTo()){
					enPassant = true;
				}
				else {
					return false;
				}
			}
		}
		
		//Check for promotion
		if (piece instanceof Pawn && (m.rowTo()==0 || m.rowTo()==7)){
			promotion = true;
		}
		
		//Check for castling (cannot move through check or out of check) (and rook)
		if (piece instanceof King && Math.abs(m.colFrom()-m.colTo())==2){
			castling = true;
			
			ChessPiece rook;
			int dir = (m.colTo()-m.colFrom())/2; //-1 for queenside, +1 for kingside
			if (dir==-1){
				rook = (ChessPiece)getPiece(0, m.rowTo());
				castlingRookOrigin[0] = 0;
			}
			else {
				rook = (ChessPiece)getPiece(7, m.rowTo());
				castlingRookOrigin[0] = 7;
			}
			castlingRookOrigin[1] = m.rowTo();
			
			//Rook must exist, must be same color, and must be unmoved
			if (rook==null || !(rook instanceof Rook) || rook.getColor()!=piece.getColor() || rook.hasMoved==true){
				return false;
			}
			
			//Cannot castle out of check
			if (inCheck(piece.getColor())){
				return false;
			}
			
			//Cannot castle through check
			int[] from = {m.colFrom(), m.rowFrom()};
			int[] to = {Math.abs(m.colFrom()+m.colTo())/2 , m.rowTo()};
			
			//This square is also the rook's destination square
			castlingRookDest[0] = to[0];
			castlingRookDest[1] = to[1];
			
			Move temp = new Move(from, to);
			ChessBoard newBoard = (ChessBoard)applyMoveCloning(temp, false);
			if (newBoard.inCheck(piece.getColor())){
				return false;
			}
		}

		ChessBoard newBoard = (ChessBoard)applyMoveCloning(m, false);
		
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
	public boolean inCheck(Color color){
		ArrayList<Move> moves = new ArrayList<Move>();
		ArrayList<Move> validAttacks = new ArrayList<Move>();
		int[] kingLoc = new int[2];
		
		//Cycle through the board
		for (int col=0;col<boardWidth;col++){
			for (int row=0;row<boardHeight;row++){
				
				ChessPiece piece = (ChessPiece)this.getPiece(col, row);
				if (piece != null){
					//Get attacking moves
					if (piece.color != color){
						//Don't consider special moves (castling, move pawn forward 2)
						//because they don't attack destination squares
						moves = this.getPiece(col, row).getMoves(col, row);
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
			i = i/Math.abs(i);
		}
		if (j!=0){
			j = j/Math.abs(j);
		}
		
		//Next selection
		sel[0] = from[0]+i;
		sel[1] = from[1]+j;
		
		while (!(sel[0]==to[0] && sel[1]==to[1])){
			
			//Check board bounds to make sure we're not wandering off into space
			if (sel[0]<0 || sel[0]>7 || sel[1]<0 || sel[1]>7){
				//Invalid move path supplied - return empty.
				return new ArrayList<int[]>();
			}
			
			squares.add(new int[] {sel[0],sel[1]});
			sel[0] += i;
			sel[1] += j;
		}
		
		return squares;
	}
	
	/**
	 * Recognize and handle castling, en passant, and promotion
	 * @param m A move
	 * @return True if special case if recognized and handled, False otherwise
	 */
	protected boolean handleSpecialCase(Move m){
		if (!(castling || enPassant || promotion)){
			return false;
		}
		
		if (castling){
			//Move the king
			board[m.colTo()][m.rowTo()] = board[m.colFrom()][m.rowFrom()];
			board[m.colFrom()][m.rowFrom()] = null;
			//Move the rook
			board[castlingRookDest[0]][castlingRookDest[1]] = board[castlingRookOrigin[0]][castlingRookOrigin[1]];
			board[castlingRookOrigin[0]][castlingRookOrigin[1]] = null;
		}
		
		if (enPassant){
			//Move the pawn
			board[m.colTo()][m.rowTo()] = board[m.colFrom()][m.rowFrom()];
			board[m.colFrom()][m.rowFrom()] = null;
			//Remove the enemy pawn and add it to captured pieces list
			captured.add(getPiece(enPassantPawn));
			board[enPassantPawn[0]][enPassantPawn[1]] = null;
		}
		
		if (promotion){
			//Check if anything got captured
			if(getPiece(m.to()) != null){
				//Add piece to captured list, remove it from the board
				captured.add(getPiece(m.to()));
				board[m.colTo()][m.rowTo()] = null;
			}
			
			//TODO: Prompt user for promotion piece - default queen
			ChessPiece promotedPiece = new Queen(getPiece(m.from()).getColor());
			board[m.colTo()][m.rowTo()] = promotedPiece;
			board[m.colFrom()][m.rowFrom()] = null;
		}
		
		return true;
	}
	
	/**
	 * Updates the board state variable
	 * WHITE_MOVE, BLACK_MOVE, CHECKMATE, or STALEMATE
	 */
	@Override
	public void updateState(){
		if(checkInsufficientMaterial()){
			state = STATE.DRAW;
			return;
		}
		
		if (state==STATE.WHITE_MOVE){
			
			if (hasValidMove(Color.BLACK)){
				state = STATE.BLACK_MOVE;
				return;
			}
			
			if (inCheck(Color.BLACK)){	
				state = STATE.CHECKMATE;
			}
			else {
				state = STATE.STALEMATE;
			}			
		}
		
		if (state==STATE.BLACK_MOVE){
						
			if (hasValidMove(Color.WHITE)){
				state = STATE.WHITE_MOVE;
				return;
			}
			
			if (inCheck(Color.WHITE)){
				state = STATE.CHECKMATE;
			}
			else {
				state = STATE.STALEMATE;
			}
		}
	}
	
	/**
	 * Returns true if there is an insufficient mating material case:
	 * 	- KB vs K
	 *  - KN vs K
	 *  - K vs K
	 * @return True if there is insufficient mating material
	 */
	protected boolean checkInsufficientMaterial(){
		ArrayList<BoardPiece> pieces = new ArrayList<BoardPiece>();	
		boolean knightOrBishop = false;
		
		for (int col=0;col<boardHeight;col++){
			for (int row=0;row<boardWidth;row++){
				ChessPiece piece = (ChessPiece)board[col][row];
				if (piece != null){
					pieces.add(piece);
					//Record if there is a knight or bishop on the board
					if (piece instanceof Knight || piece instanceof Bishop){
						knightOrBishop = true;
					}
				}
			}
		}
		
		if (pieces.size()==2 && pieces.get(0) instanceof King && pieces.get(1) instanceof King){
			return true;
		}
		
		if (pieces.size()==3 && knightOrBishop){
			return true;
		}
		
		return false;
	}
	
	/**
	 * Checks if the given side has at least one legal move
	 * @param color Color.WHITE or Color.BLACK
	 * @return True if given side has at least one legal move, False otherwise
	 */
	protected boolean hasValidMove(Color color) {
		for (int col=0;col<boardWidth;col++){
			for (int row=0;row<boardHeight;row++){
				ChessPiece piece = (ChessPiece)getPiece(col, row);
				if (piece != null && piece.getColor()==color){
					if (!getMoves(col, row).isEmpty()){
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
}
