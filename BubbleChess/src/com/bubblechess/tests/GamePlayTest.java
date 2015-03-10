package com.bubblechess.tests;

//import junit.framework.Assert;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.bubblechess.client.Board;
import com.bubblechess.client.ChessBoard;
import com.bubblechess.client.ChessPiece;
import com.bubblechess.client.Game;
import com.bubblechess.client.Move;
import com.bubblechess.client.User;

public class GamePlayTest {
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
	
	/*public static junit.framework.Test suite(){
		return new junit.framework.JUnit4TestAdapter(GamePlayTest.class);
	}*/
	
	/**
	 * Test that the board automatically recognizes checkmate
	 * Test #10 - 1.4.3.2
	 */
	@Test
	public void recognizeCheckmate() {
		//Fool's Mate!
		String fen = "rnb-kbnr/pppp-ppp/--------/----p---/-----PPq/--------/PPPPP--P/RNBQKBNR w KQkq - 0 2";
		chessboard = new ChessBoard(fen);
		
		String result = chessboard.getState();
		Assert.assertEquals("Checkmate", result);
		
		//Scholar's Mate
		fen = "r-bqk-nr/pppp-Qpp/--n-----/--b-p---/--B-P---/--------/PPPP-PPP/RNB-K-NR b KQkq - 0 3";
		chessboard = new ChessBoard(fen);
		result = chessboard.getState();
		
		Assert.assertEquals("Checkmate", result);
	}
	
	/**
	 * Test that the board automatically recognizes stalemate
	 * Test #11 - 1.4.3.3
	 */
	@Test
	public void recognizeStalemate() {
		String fen = "-----bnr/----p-pq/----Qpkr/-------p/-------P/----P---/PPPP-PP-/RNB-KBNR b KQkq - 0 10";
		chessboard = new ChessBoard(fen);
		
		String result = chessboard.getState();
		Assert.assertEquals("Stalemate", result);
		
		fen = "--------/--------/--------/--------/--------/----k---/----p---/----K--- w KQkq - 0 60";
		chessboard = new ChessBoard(fen);
		result = chessboard.getState();
		
		Assert.assertEquals("Stalemate", result);
	}
	
	/**
	 * Test "load" constructor with starting position
	 */
	@Test
	public void loadGame(){
		String fen = "rnbqkbnr/pppppppp/--------/--------/--------/--------/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
		chessboard = new ChessBoard(fen);
		
		ChessBoard newGame = new ChessBoard();
			
		for (int col=0;col<8;col++){
			for (int row=0;row<8;row++){
				ChessPiece expected = (ChessPiece)newGame.getBoard()[col][row];
				ChessPiece result = (ChessPiece)chessboard.getBoard()[col][row];
				
				if (expected!=null && result==null){
					fail();
				}
				else if (expected==null && result!=null){
					fail();
				}
				else if (expected!=null && result!=null){
					Assert.assertEquals(expected.getType(), result.getType());
					Assert.assertEquals(expected.getColor(), result.getColor());
				}			
			}
		}
		
		Assert.assertEquals("White to Move", chessboard.getState());
	}
	
	/**
	 * Test that the game is automatically ended in the case of insufficient
	 * mating material
	 * Test #12 - 1.4.3.4
	 */
	@Test
	public void insufficientMaterial(){
		String fen = "--------/---K----/--------/--n-----/--------/----k---/--------/-------- w KQkq - 0 1";
		chessboard = new ChessBoard(fen);
		
		Assert.assertEquals("Draw", chessboard.getState());
		
		fen = "--------/---K----/--------/--------/-----B--/----k---/--------/-------- w KQkq - 0 1";
		chessboard = new ChessBoard(fen);
		
		Assert.assertEquals("Draw", chessboard.getState());
	}
	
	/**
	 * Test that the game is automatically ended in the case of three fold repetition 
	 * Test #13 - 1.4.3.5
	 */
	@Test
	public void threeFoldRepetition(){
		//TODO: Enhancement
	}
	
}
