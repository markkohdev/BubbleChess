package com.bubblechess.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bubblechess.client.Board;
import com.bubblechess.client.BoardPiece.Color;
import com.bubblechess.client.ChessBoard;
import com.bubblechess.client.Game;
import com.bubblechess.client.Move;
import com.bubblechess.client.User;

public class MoveValidationTest {
	private Game game;
	private Board chessboard;
	private User user1, user2;
	private int gameid = 1;
	private String fen = "";
	
	/**
	 * Anything needed to be done before all tests
	 */
	@Before
	public void setUp() {
		if (fen.isEmpty()){
			chessboard = new ChessBoard();
		}
		else{
			chessboard = new ChessBoard(fen);
		}		
		
		game = new Game(gameid, user1, user2, chessboard);
	}
	
	/**
	 * Anything needed to be done after all tests
	 */
	@After
	public void tearDown() {
		chessboard = null;
		game = null;
		fen = "";
	}
	
	/**
	 * Test that no generated moves leave the king under attack
	 * Test #16 - 1.5.1
	 */
	@Test
	public void kingInCheck(){
		fen = "K-------/--------/--------/-q------/--------/--------/--------/-------k w KQkq - 0 1";
		setUp();
		
		ArrayList<Move> legalMoves = new ArrayList<Move>();
		legalMoves.add(new Move(new int[]{0,7}, new int[]{0,6}));
		
		ArrayList<Move> generatedMoves = chessboard.getMoves(0,7);
		
		Assert.assertEquals(legalMoves.size(), generatedMoves.size());
		Assert.assertEquals(legalMoves.get(0).from()[0], generatedMoves.get(0).from()[0]);
		Assert.assertEquals(legalMoves.get(0).from()[1], generatedMoves.get(0).from()[1]);
		Assert.assertEquals(legalMoves.get(0).to()[0], generatedMoves.get(0).to()[0]);
		Assert.assertEquals(legalMoves.get(0).to()[1], generatedMoves.get(0).to()[1]);
		
		tearDown();
	}
	
	/**
	 * Tests the ChessBoard.inCheck(Color color) method
	 */
	public void inCheck(){
		fen = "k-------/--------/--------/---q----/--------/---K----/--------/-------- w KQkq - 0 1";
		setUp();
		
		Assert.assertEquals(true, chessboard.inCheck(Color.WHITE));
		
		tearDown();
		
		fen = "K-------/--------/--------/---Q----/--------/---k----/--------/-------- b KQkq - 0 1";
		setUp();
		
		Assert.assertEquals(true, chessboard.inCheck(Color.BLACK));
		
		tearDown();
	}
	
	/**
	 * Test that no generated moves allow the player to move a piece
	 * to occupy the square of a friendly piece
	 * Test #17 - 1.5.2
	 */
	@Test
	public void moveOnFriendlyPiece(){
		fen = "k-------/---p----/--prp---/--------/--------/---p----/--------/-------K b KQkq - 0 1";
		setUp();
		
		ArrayList<Move> generatedMoves = chessboard.getMoves(3,5);
		ArrayList<Move> legalMoves = new ArrayList<Move>();
		legalMoves.add(new Move(new int[]{3,5}, new int[]{3,4}));
		legalMoves.add(new Move(new int[]{3,5}, new int[]{3,3}));

		if (generatedMoves.size()!=legalMoves.size()){
			fail();
		}
		for (int i=0;i<generatedMoves.size();i++){
			Assert.assertEquals(legalMoves.get(i).from()[0], generatedMoves.get(i).from()[0]);
			Assert.assertEquals(legalMoves.get(i).from()[1], generatedMoves.get(i).from()[1]);
			Assert.assertEquals(legalMoves.get(i).to()[0], generatedMoves.get(i).to()[0]);
			Assert.assertEquals(legalMoves.get(i).to()[1], generatedMoves.get(i).to()[1]);
		}
		
		tearDown();
	}
	
	/**
	 * Test that a piece cannot move past an enemy piece
	 * Test #18 - 1.5.3
	 */
	@Test
	public void movePastEnemyPiece(){
		fen = "k-------/---p----/--prp---/--------/--------/---P----/--------/-------K b KQkq - 0 1";
		setUp();
		
		Move illegalMove = new Move(new int[]{3,5}, new int[]{3,1});
		ArrayList<Move> generatedMoves = chessboard.getMoves(3,5);
		
		for (int i=0;i<generatedMoves.size();i++){
			if (illegalMove.from()[0]==generatedMoves.get(i).from()[0] &&
				illegalMove.from()[1]==generatedMoves.get(i).from()[1] &&
				illegalMove.to()[0]==generatedMoves.get(i).to()[0] &&
				illegalMove.to()[1]==generatedMoves.get(i).to()[1]){
				
				fail();
			}
		}

		tearDown();
	}
	
	/**
	 * Test that the king can move one space in any direction
	 * Test #19 - 1.5.4.1
	 */
	@Test
	public void kingMoves(){
		fen = "-----k--/--------/--------/---K----/--------/--------/--P-----/-------- b KQkq - 0 1";
		setUp();
		
		ArrayList<Move> generatedMoves = chessboard.getMoves(3,4);
		ArrayList<Move> legalMoves = new ArrayList<Move>();
		legalMoves.add(new Move(new int[]{3,4}, new int[]{3,5}));
		legalMoves.add(new Move(new int[]{3,4}, new int[]{4,5}));
		legalMoves.add(new Move(new int[]{3,4}, new int[]{4,4}));
		legalMoves.add(new Move(new int[]{3,4}, new int[]{4,3}));
		legalMoves.add(new Move(new int[]{3,4}, new int[]{3,3}));
		legalMoves.add(new Move(new int[]{3,4}, new int[]{2,3}));
		legalMoves.add(new Move(new int[]{3,4}, new int[]{2,4}));
		legalMoves.add(new Move(new int[]{3,4}, new int[]{2,5}));
		
		if (generatedMoves.size()!=legalMoves.size()){
			fail();
		}
		for (int i=0;i<generatedMoves.size();i++){
			Assert.assertEquals(legalMoves.get(i).from()[0], generatedMoves.get(i).from()[0]);
			Assert.assertEquals(legalMoves.get(i).from()[1], generatedMoves.get(i).from()[1]);
			Assert.assertEquals(legalMoves.get(i).to()[0], generatedMoves.get(i).to()[0]);
			Assert.assertEquals(legalMoves.get(i).to()[1], generatedMoves.get(i).to()[1]);
		}
		
		tearDown();
	}
	
	/**
	 * Test that castling is allowed
	 * Test #20 - 1.5.4.2
	 */
	@Test
	public void castling(){
		setUp();
		
		//Sample game
		game.playMove(new Move(new int[]{4,1}, new int[]{4,3}));
		game.playMove(new Move(new int[]{4,6}, new int[]{4,4}));
		game.playMove(new Move(new int[]{6,0}, new int[]{5,2}));
		game.playMove(new Move(new int[]{1,7}, new int[]{2,5}));
		game.playMove(new Move(new int[]{5,0}, new int[]{2,3}));
		game.playMove(new Move(new int[]{5,7}, new int[]{2,4}));
		
		Move whitecastle = new Move(new int[]{4,0}, new int[]{6,0});
		Move blackcastle = new Move(new int[]{4,7}, new int[]{6,7});
		
		ArrayList<Move> whiteKingMoves = chessboard.getMoves(4,0);
		ArrayList<Move> blackKingMoves = chessboard.getMoves(4,7);
		
		for (int i=0;i<whiteKingMoves.size();i++){
			if (whitecastle.from()[0]==whiteKingMoves.get(i).from()[0] &&
				whitecastle.from()[1]==whiteKingMoves.get(i).from()[1] &&
				whitecastle.to()[0]==whiteKingMoves.get(i).to()[0] &&
				whitecastle.to()[1]==whiteKingMoves.get(i).to()[1]){
				
				return;
			}	
		}
		
		fail();
		
		for (int i=0;i<blackKingMoves.size();i++){
			if (blackcastle.from()[0]==blackKingMoves.get(i).from()[0] &&
				blackcastle.from()[1]==blackKingMoves.get(i).from()[1] &&
				blackcastle.to()[0]==blackKingMoves.get(i).to()[0] &&
				blackcastle.to()[1]==blackKingMoves.get(i).to()[1]){
				
				return;
			}	
		}
		fail();
		
		tearDown();
	}
	
	/**
	 * Test that castling is illegal when:
	 *  - castling into check
	 *  - castling through check
	 *  - castling out of check
	 * Test #20 - 1.5.4.2
	 */
	@Test
	public void castlingBlocked(){
		fen = "-----k--/--------/--------/--------/--------/------r-/--------/----K--R w KQkq - 0 1";
		setUp();
		
		Move whitecastle = new Move(new int[]{4,0}, new int[]{6,0});
		ArrayList<Move> whiteKingMoves = chessboard.getMoves(4,0);
		
		for (int i=0;i<whiteKingMoves.size();i++){
			if (whitecastle.from()[0]==whiteKingMoves.get(i).from()[0] &&
				whitecastle.from()[1]==whiteKingMoves.get(i).from()[1] &&
				whitecastle.to()[0]==whiteKingMoves.get(i).to()[0] &&
				whitecastle.to()[1]==whiteKingMoves.get(i).to()[1]){
				
				fail();
			}
		}
		
		tearDown();
		
		fen = "-----k--/--------/--------/--------/--------/-----r--/--------/----K--R w KQkq - 0 1";
		setUp();
		
		whitecastle = new Move(new int[]{4,0}, new int[]{6,0});
		whiteKingMoves = chessboard.getMoves(4,0);
		
		for (int i=0;i<whiteKingMoves.size();i++){
			if (whitecastle.from()[0]==whiteKingMoves.get(i).from()[0] &&
				whitecastle.from()[1]==whiteKingMoves.get(i).from()[1] &&
				whitecastle.to()[0]==whiteKingMoves.get(i).to()[0] &&
				whitecastle.to()[1]==whiteKingMoves.get(i).to()[1]){
				
				fail();
			}
		}
		
		tearDown();
		
		fen = "-----k--/--------/--------/--------/--------/----r---/--------/----K--R w KQkq - 0 1";
		setUp();
		
		whitecastle = new Move(new int[]{4,0}, new int[]{6,0});
		whiteKingMoves = chessboard.getMoves(4,0);
		
		for (int i=0;i<whiteKingMoves.size();i++){
			if (whitecastle.from()[0]==whiteKingMoves.get(i).from()[0] &&
				whitecastle.from()[1]==whiteKingMoves.get(i).from()[1] &&
				whitecastle.to()[0]==whiteKingMoves.get(i).to()[0] &&
				whitecastle.to()[1]==whiteKingMoves.get(i).to()[1]){
				
				fail();
			}
		}
		
		tearDown();		
	}
	
	/**
	 * Test that castling is illegal if either the king or rook has moved
	 * Test #20 - 1.5.4.2
	 */
	@Test
	public void castlingIfMoved(){
		fen = "r----k--/--------/--------/--------/--------/--------/--------/R---K--R w KQkq - 0 1";
		setUp();
		
		Move kingside = new Move(new int[]{4,0}, new int[]{6,0});
		Move queenside = new Move(new int[]{4,0}, new int[]{2,0});
		Move blackQueenside = new Move(new int[]{4,7}, new int[]{2,7});
		
	    game.playMove(new Move(new int[]{7,0}, new int[]{7,1}));
	    game.playMove(new Move(new int[]{5,7}, new int[]{4,7}));
	    game.playMove(new Move(new int[]{7,1}, new int[]{7,0}));
		
		ArrayList<Move> whiteKingMoves = chessboard.getMoves(4,0);
		ArrayList<Move> blackKingMoves = chessboard.getMoves(4,7);
		
		for (int i=0;i<whiteKingMoves.size();i++){
			if (kingside.from()[0]==whiteKingMoves.get(i).from()[0] &&
				kingside.from()[1]==whiteKingMoves.get(i).from()[1] &&
				kingside.to()[0]==whiteKingMoves.get(i).to()[0] &&
				kingside.to()[1]==whiteKingMoves.get(i).to()[1]){
				
				fail();
			}
		}
		
		for (int i=0;i<blackKingMoves.size();i++){
			if (blackQueenside.from()[0]==blackKingMoves.get(i).from()[0] &&
				blackQueenside.from()[1]==blackKingMoves.get(i).from()[1] &&
				blackQueenside.to()[0]==blackKingMoves.get(i).to()[0] &&
				blackQueenside.to()[1]==blackKingMoves.get(i).to()[1]){
				
				fail();
			}
		}
		
		for (int i=0;i<whiteKingMoves.size();i++){
			if (queenside.from()[0]==whiteKingMoves.get(i).from()[0] &&
				queenside.from()[1]==whiteKingMoves.get(i).from()[1] &&
				queenside.to()[0]==whiteKingMoves.get(i).to()[0] &&
				queenside.to()[1]==whiteKingMoves.get(i).to()[1]){
				
				return;
			}
		}
		
		fail();
		
		tearDown();
	}
	
	/**
	 * Test that the rook can move horizontally and vertically
	 * Test #21 - 1.5.5
	 */
	@Test
	public void rookMoves(){
		fen = "k-------/--------/--------/----R---/--------/--------/--------/-------K w KQkq - 0 1";
		setUp();
		
		ArrayList<Move> generatedMoves = chessboard.getMoves(4,4);
		ArrayList<Move> legalMoves = new ArrayList<Move>();
		legalMoves.add(new Move(new int[]{4,4}, new int[]{4,5}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{4,6}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{4,7}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{5,4}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{6,4}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{7,4}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{4,3}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{4,2}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{4,1}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{4,0}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{3,4}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{2,4}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{1,4}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{0,4}));

		if (generatedMoves.size()!=legalMoves.size()){
			fail();
		}
		for (int i=0;i<generatedMoves.size();i++){
			Assert.assertEquals(legalMoves.get(i).from()[0], generatedMoves.get(i).from()[0]);
			Assert.assertEquals(legalMoves.get(i).from()[1], generatedMoves.get(i).from()[1]);
			Assert.assertEquals(legalMoves.get(i).to()[0], generatedMoves.get(i).to()[0]);
			Assert.assertEquals(legalMoves.get(i).to()[1], generatedMoves.get(i).to()[1]);
		}
		
		tearDown();
	}
	
	/**
	 * Test that the knight can move in an L-shape
	 * Test #22 - 1.5.6
	 */
	@Test
	public void knightMoves(){
		fen = "k-------/--------/--------/----N---/--------/--------/--------/-------K w KQkq - 0 1";
		setUp();
		
		ArrayList<Move> generatedMoves = chessboard.getMoves(4,4);
		ArrayList<Move> legalMoves = new ArrayList<Move>();
		legalMoves.add(new Move(new int[]{4,4}, new int[]{5,6}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{6,5}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{6,3}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{5,2}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{3,2}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{2,3}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{2,5}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{3,6}));

		if (generatedMoves.size()!=legalMoves.size()){
			fail();
		}
		for (int i=0;i<generatedMoves.size();i++){
			Assert.assertEquals(legalMoves.get(i).from()[0], generatedMoves.get(i).from()[0]);
			Assert.assertEquals(legalMoves.get(i).from()[1], generatedMoves.get(i).from()[1]);
			Assert.assertEquals(legalMoves.get(i).to()[0], generatedMoves.get(i).to()[0]);
			Assert.assertEquals(legalMoves.get(i).to()[1], generatedMoves.get(i).to()[1]);
		}
		
		tearDown();
	}
	
	/**
	 * Test that the bishop can move diagonally
	 * Test #23 - 1.5.7
	 */
	@Test
	public void bishopMoves(){
		fen = "k-------/--------/--------/----B---/--------/--------/--------/-------K w KQkq - 0 1";
		setUp();
		
		ArrayList<Move> generatedMoves = chessboard.getMoves(4,4);
		ArrayList<Move> legalMoves = new ArrayList<Move>();
		legalMoves.add(new Move(new int[]{4,4}, new int[]{5,5}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{6,6}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{7,7}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{5,3}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{6,2}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{7,1}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{3,3}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{2,2}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{1,1}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{0,0}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{3,5}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{2,6}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{1,7}));

		if (generatedMoves.size()!=legalMoves.size()){
			fail();
		}
		for (int i=0;i<generatedMoves.size();i++){
			Assert.assertEquals(legalMoves.get(i).from()[0], generatedMoves.get(i).from()[0]);
			Assert.assertEquals(legalMoves.get(i).from()[1], generatedMoves.get(i).from()[1]);
			Assert.assertEquals(legalMoves.get(i).to()[0], generatedMoves.get(i).to()[0]);
			Assert.assertEquals(legalMoves.get(i).to()[1], generatedMoves.get(i).to()[1]);
		}
		
		tearDown();
	}
	
	/**
	 * Test that the queen can move horizontally, vertically, and diagonally
	 * Test #24 - 1.5.8
	 */
	@Test
	public void queenMoves(){
		fen = "k-------/--------/--------/----Q---/--------/--------/--------/-------K w KQkq - 0 1";
		setUp();
		
		ArrayList<Move> generatedMoves = chessboard.getMoves(4,4);
		ArrayList<Move> legalMoves = new ArrayList<Move>();
		legalMoves.add(new Move(new int[]{4,4}, new int[]{4,5}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{4,6}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{4,7}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{5,5}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{6,6}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{7,7}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{5,4}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{6,4}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{7,4}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{5,3}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{6,2}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{7,1}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{4,3}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{4,2}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{4,1}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{4,0}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{3,3}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{2,2}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{1,1}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{0,0}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{3,4}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{2,4}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{1,4}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{0,4}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{3,5}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{2,6}));
		legalMoves.add(new Move(new int[]{4,4}, new int[]{1,7}));

		if (generatedMoves.size()!=legalMoves.size()){
			fail();
		}
		for (int i=0;i<generatedMoves.size();i++){
			Assert.assertEquals(legalMoves.get(i).from()[0], generatedMoves.get(i).from()[0]);
			Assert.assertEquals(legalMoves.get(i).from()[1], generatedMoves.get(i).from()[1]);
			Assert.assertEquals(legalMoves.get(i).to()[0], generatedMoves.get(i).to()[0]);
			Assert.assertEquals(legalMoves.get(i).to()[1], generatedMoves.get(i).to()[1]);
		}
		
		tearDown();
	}
	
	/**
	 * Test that the pawn can move one space forward, and two spaces forward
	 * if it hasn't yet moved
	 * Test #25 - 1.5.9.1
	 */
	@Test
	public void pawnMoves(){
		fen = "k-------/--------/--------/--------/--------/--------/---PP---/-------K w KQkq - 0 1";
		setUp();
		
		game.playMove(new Move(new int[]{3,1}, new int[]{3,2}));
		
		ArrayList<Move> generatedMoves = chessboard.getMoves(4,1);
		ArrayList<Move> legalMoves = new ArrayList<Move>();
		
		legalMoves.add(new Move(new int[]{4,1}, new int[]{4,2}));
		legalMoves.add(new Move(new int[]{4,1}, new int[]{4,3}));
		
		if (generatedMoves.size()!=legalMoves.size()){
			fail();
		}
		for (int i=0;i<generatedMoves.size();i++){
			Assert.assertEquals(legalMoves.get(i).from()[0], generatedMoves.get(i).from()[0]);
			Assert.assertEquals(legalMoves.get(i).from()[1], generatedMoves.get(i).from()[1]);
			Assert.assertEquals(legalMoves.get(i).to()[0], generatedMoves.get(i).to()[0]);
			Assert.assertEquals(legalMoves.get(i).to()[1], generatedMoves.get(i).to()[1]);
		}
		
		generatedMoves = chessboard.getMoves(3,2);
		legalMoves.clear();
		legalMoves.add(new Move(new int[]{3,2}, new int[]{3,3}));
		
		if (generatedMoves.size()!=legalMoves.size()){
			fail();
		}
		for (int i=0;i<generatedMoves.size();i++){
			Assert.assertEquals(legalMoves.get(i).from()[0], generatedMoves.get(i).from()[0]);
			Assert.assertEquals(legalMoves.get(i).from()[1], generatedMoves.get(i).from()[1]);
			Assert.assertEquals(legalMoves.get(i).to()[0], generatedMoves.get(i).to()[0]);
			Assert.assertEquals(legalMoves.get(i).to()[1], generatedMoves.get(i).to()[1]);
		}
		
		tearDown();
	}
	
	/**
	 * Test that the pawn can move one space forward diagonally when capturing
	 * an enemy piece, and cannot capture a piece directly ahead
	 * Test #26 - 1.5.9.2
	 */
	@Test
	public void pawnCapture(){
		fen = "k-------/--------/--------/---nn---/--------/----P---/--------/-------K w KQkq - 0 1";
		setUp();
		
		game.playMove(new Move(new int[]{4,2}, new int[]{4,3}));
		
		ArrayList<Move> generatedMoves = chessboard.getMoves(4,3);
		ArrayList<Move> legalMoves = new ArrayList<Move>();
		
		legalMoves.add(new Move(new int[]{4,3}, new int[]{3,4}));

		if (generatedMoves.size()!=legalMoves.size()){
			fail();
		}
		for (int i=0;i<generatedMoves.size();i++){
			Assert.assertEquals(legalMoves.get(i).from()[0], generatedMoves.get(i).from()[0]);
			Assert.assertEquals(legalMoves.get(i).from()[1], generatedMoves.get(i).from()[1]);
			Assert.assertEquals(legalMoves.get(i).to()[0], generatedMoves.get(i).to()[0]);
			Assert.assertEquals(legalMoves.get(i).to()[1], generatedMoves.get(i).to()[1]);
		}
		
		tearDown();
	}
	
	/**
	 * Test that the pawn can capture en passant if an enemy pawn moves forward
	 * two spaces through the attacked square
	 * Test #27 - 1.5.9.3
	 */
	@Test
	public void enPassant(){
		setUp();
		
		game.playMove(new Move(new int[]{4,1}, new int[]{4,3}));
		game.playMove(new Move(new int[]{4,6}, new int[]{4,5}));
		game.playMove(new Move(new int[]{4,3}, new int[]{4,4}));
		game.playMove(new Move(new int[]{3,6}, new int[]{3,4}));
		
		ArrayList<Move> generatedMoves = chessboard.getMoves(4,4);
		ArrayList<Move> legalMoves = new ArrayList<Move>();
		
		legalMoves.add(new Move(new int[]{4,4}, new int[]{3,5}));
		
		if (generatedMoves.size()!=legalMoves.size()){
			fail();
		}
		for (int i=0;i<generatedMoves.size();i++){
			Assert.assertEquals(legalMoves.get(i).from()[0], generatedMoves.get(i).from()[0]);
			Assert.assertEquals(legalMoves.get(i).from()[1], generatedMoves.get(i).from()[1]);
			Assert.assertEquals(legalMoves.get(i).to()[0], generatedMoves.get(i).to()[0]);
			Assert.assertEquals(legalMoves.get(i).to()[1], generatedMoves.get(i).to()[1]);
		}
		
		game.playMove(generatedMoves.get(0));
		
		ArrayList<Move> empty = new ArrayList<Move>();
		
		//Roundabout way to check if the pawn has been removed
		Assert.assertEquals(empty.size(), chessboard.getMoves(3,4).size());
		
		tearDown();
	}
	
	/**
	 * Test that the pawn can promote to a queen, rook, knight, or bishop when
	 * advanced to the last rank
	 * Test #28 - 1.5.9.4
	 */
	@Test
	public void promotion(){
		//TODO: Enhancement
	}
}
