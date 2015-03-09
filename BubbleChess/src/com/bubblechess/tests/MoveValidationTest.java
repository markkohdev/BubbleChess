package com.bubblechess.tests;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bubblechess.client.Board;
import com.bubblechess.client.ChessBoard;
import com.bubblechess.client.Game;
import com.bubblechess.client.Move;
import com.bubblechess.client.User;

public class MoveValidationTest {
	private Game game;
	private Board chessboard;
	private User user1, user2;
	private int gameid = 1;
	
	/**
	 * Anything needed to be done before all tests
	 */
	@Before
	public void setUp() {
		chessboard = new ChessBoard();
		game = new Game(gameid, user1, user2, chessboard);
	}
	
	/**
	 * Anything needed to be done after all tests
	 */
	@After
	public void tearDown() {
		
	}
	
	/**
	 * Test that no generated moves leave the king under attack
	 * Test #16 - 1.5.1
	 */
	@Test
	public void kingInCheck(){
		String fen = "K-------/--------/--------/-q------/--------/--------/--------/-------k w KQkq - 0 1";
		chessboard = new ChessBoard(fen);
		
		ArrayList<Move> legalMoves = new ArrayList<Move>();
		legalMoves.add(new Move(new int[]{0,7}, new int[]{0,6}));
		
		ArrayList<Move> generatedMoves = chessboard.getMoves(0,7);
		
		Assert.assertEquals(legalMoves, generatedMoves);
	}
	
	/**
	 * Test that no generated moves allow the player to move a piece
	 * to occupy the square of a friendly piece
	 * Test #17 - 1.5.2
	 */
	@Test
	public void moveOnFriendlyPiece(){
		//TODO
	}
	
	/**
	 * Test that a piece cannot move past an enemy piece
	 * Test #18 - 1.5.3
	 */
	@Test
	public void movePastEnemyPiece(){
		//TODO
	}
	
	/**
	 * Test that the king can move one space in any direction
	 * Test #19 - 1.5.4.1
	 */
	@Test
	public void kingMoves(){
		//TODO
	}
	
	/**
	 * Test that castling is allowed
	 * Test #20 - 1.5.4.2
	 */
	@Test
	public void castling(){
		//TODO
	}
	
	/**
	 * Test that the rook can move horizontally and vertically
	 * Test #21 - 1.5.5
	 */
	@Test
	public void rookMoves(){
		//TODO
	}
	
	/**
	 * Test that the knight can move in an L-shape
	 * Test #22 - 1.5.6
	 */
	@Test
	public void knightMoves(){
		//TODO
	}
	
	/**
	 * Test that the bishop can move diagonally
	 * Test #23 - 1.5.7
	 */
	@Test
	public void bishopMoves(){
		//TODO
	}
	
	/**
	 * Test that the queen can move horizontally, vertically, and diagonally
	 * Test #24 - 1.5.8
	 */
	@Test
	public void queenMoves(){
		//TODO
	}
	
	/**
	 * Test that the pawn can move one space forward, and two spaces forward
	 * if it hasn't yet moved
	 * Test #25 - 1.5.9.1
	 */
	@Test
	public void pawnMoves(){
		//TODO
	}
	
	/**
	 * Test that the pawn can move one space forward diagonally when capturing
	 * an enemy piece
	 * Test #26 - 1.5.9.2
	 */
	@Test
	public void pawnCapture(){
		//TODO
	}
	
	/**
	 * Test that the pawn can capture en passant if an enemy pawn moves forward
	 * two spaces through the attacked square
	 * Test #27 - 1.5.9.3
	 */
	@Test
	public void enPassant(){
		//TODO
	}
	
	/**
	 * Test that the pawn can promote to a queen, rook, knight, or bishop when
	 * advanced to the last rank
	 * Test #28 - 1.5.9.4
	 */
	@Test
	public void promotion(){
		//TODO
	}
}
