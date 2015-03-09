package com.bubblechess.tests;

//import junit.framework.Assert;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.bubblechess.client.Board;
import com.bubblechess.client.ChessBoard;
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
		game.playMove(new Move(new int[]{5,1}, new int[]{5,2}));
		game.playMove(new Move(new int[]{4,6}, new int[]{4,4}));
		game.playMove(new Move(new int[]{6,1}, new int[]{6,3}));
		game.playMove(new Move(new int[]{3,7}, new int[]{7,3}));
		
		String result = game.getBoard().getState();
		Assert.assertEquals("Checkmate", result);
	}
	
	/**
	 * Test that the board automatically recognizes stalemate
	 * Test #11 - 1.4.3.3
	 */
	@Test
	public void recognizeStalemate() {
		game.playMove(new Move(new int[]{4,1}, new int[]{4,2}));
		game.playMove(new Move(new int[]{0,6}, new int[]{0,4}));
		game.playMove(new Move(new int[]{3,0}, new int[]{7,4}));
		game.playMove(new Move(new int[]{0,7}, new int[]{0,5}));
		game.playMove(new Move(new int[]{7,4}, new int[]{0,4}));
		game.playMove(new Move(new int[]{7,6}, new int[]{7,4}));
		game.playMove(new Move(new int[]{7,1}, new int[]{7,3}));
		game.playMove(new Move(new int[]{0,5}, new int[]{7,5}));
		game.playMove(new Move(new int[]{0,4}, new int[]{2,6}));
		game.playMove(new Move(new int[]{5,6}, new int[]{5,5}));
		game.playMove(new Move(new int[]{2,6}, new int[]{3,6}));
		game.playMove(new Move(new int[]{4,7}, new int[]{5,6}));
		game.playMove(new Move(new int[]{3,6}, new int[]{1,6}));
		game.playMove(new Move(new int[]{3,7}, new int[]{3,2}));
		game.playMove(new Move(new int[]{1,6}, new int[]{1,7}));
		game.playMove(new Move(new int[]{3,2}, new int[]{7,6}));
		game.playMove(new Move(new int[]{1,7}, new int[]{2,7}));
		game.playMove(new Move(new int[]{5,6}, new int[]{6,5}));
		game.playMove(new Move(new int[]{2,7}, new int[]{4,5}));
		
		String result = game.getBoard().getState();
		Assert.assertEquals(result, "Stalemate");
	}
	
	/**
	 * Test that the game is automatically ended in the case of insufficient
	 * mating material
	 * Test #12 - 1.4.3.4
	 */
	@Test
	public void insufficientMaterial(){
		//TODO
	}
	
	/**
	 * Test that the game is automatically ended in the case of three fold repetition 
	 * Test #13 - 1.4.3.5
	 */
	@Test
	public void threeFoldRepetition(){
		//TODO
	}
	
}
