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

public class StateRecognitionTest {
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
	 * Test that the state is BLACK_MOVE after White makes a move
	 */
	@Test
	public void checkBlacktoMove(){
		setUp();
		game.playMove(new Move(new int[]{4,1}, new int[]{4,3}));
		Assert.assertEquals("Black to Move", game.getBoardState());
		tearDown();
	}
	
	/**
	 * Test that the state is WHITE_MOVE after Black makes a move
	 */
	@Test
	public void checkWhitetoMove(){
		setUp();
		game.playMove(new Move(new int[]{4,1}, new int[]{4,3}));
		game.playMove(new Move(new int[]{4,6}, new int[]{4,4}));
		Assert.assertEquals("White to Move", game.getBoardState());
		tearDown();
	}
	
	/**
	 * Test that the board automatically recognizes checkmate
	 * Test #10 - 1.4.3.2
	 */
	@Test
	public void recognizeCheckmate() {
		//Fool's Mate!
		fen = "rnb-kbnr/pppp-ppp/--------/----p---/-----PPq/--------/PPPPP--P/RNBQKBNR w KQkq - 0 2";
		setUp();
		
		String result = chessboard.getState();
		Assert.assertEquals("Checkmate", result);
		
		tearDown();
		
		//Scholar's Mate
		fen = "r-bqk-nr/pppp-Qpp/--n-----/--b-p---/--B-P---/--------/PPPP-PPP/RNB-K-NR b KQkq - 0 3";
		setUp();
		
		Assert.assertEquals("Checkmate", chessboard.getState());
		
		tearDown();
	}
	
	/**
	 * Test that the board automatically recognizes stalemate
	 * Test #11 - 1.4.3.3
	 */
	@Test
	public void recognizeStalemate() {
		fen = "-----bnr/----p-pq/----Qpkr/-------p/-------P/----P---/PPPP-PP-/RNB-KBNR b KQkq - 0 10";
		setUp();
		
		Assert.assertEquals("Stalemate", chessboard.getState());
		
		tearDown();
		
		fen = "--------/--------/--------/--------/--------/----k---/----p---/----K--- w KQkq - 0 60";
		setUp();
		
		Assert.assertEquals("Stalemate", chessboard.getState());
		
		tearDown();
	}
	
	/**
	 * Test "load" constructor with starting position
	 */
	@Test
	public void loadGame(){
		fen = "rnbqkbnr/pppppppp/--------/--------/--------/--------/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
		setUp();
		
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
		
		tearDown();
	}
	
	/**
	 * Test that the game is automatically ended in the case of insufficient
	 * mating material
	 * Test #12 - 1.4.3.4
	 */
	@Test
	public void insufficientMaterial(){
		fen = "--------/---K----/--------/--n-----/--------/----k---/--------/-------- w KQkq - 0 1";
		setUp();
		
		Assert.assertEquals("Draw", chessboard.getState());
		
		tearDown();
		
		fen = "--------/---K----/--------/--------/-----B--/----k---/--------/-------- w KQkq - 0 1";
		setUp();
		
		Assert.assertEquals("Draw", chessboard.getState());
		
		tearDown();
	}
	
}
