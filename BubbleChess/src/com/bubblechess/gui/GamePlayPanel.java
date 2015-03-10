package com.bubblechess.gui;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;

import com.bubblechess.GUIBridge;
import com.bubblechess.client.BoardPiece;
import com.bubblechess.client.Move;

public class GamePlayPanel extends JPanel {

	//State of the panel
	protected enum PanelState {PLAYER,OPPONENT,HIGHLIGHTED};
	
	protected GameBoard board;
	protected int playerNumber;
	protected PanelState state;
	protected GUIBridge bridge;
	protected ArrayList<Move> PlayableMoves;
	
	
	// Flip Board if player is black pieces
	/**
	 * Creates a GamePlayPanel, initializes board, 
	 * @param playerNum
	 */
	public GamePlayPanel() {
		MainApplicationWindow mainWin = MainApplicationWindow.getInstance();
		bridge = mainWin.getBridge();
		
		playerNumber = bridge.GetPlayerNumber();
		
		//White goes first
		if (playerNumber == 1) {
			this.state = PanelState.PLAYER;
		}
		else {
			this.state = PanelState.OPPONENT;
		}
		
		board = new GameBoard(this,playerNumber);
		setBackground(Color.LIGHT_GRAY);
		setPreferredSize(new Dimension(1024,768));
		setLayout(null);
		board.setLocation(50, 50);	
		add(board);
		
		refreshBoard();
		
		
	}
	
	
	public void refreshBoard() {
		if (!bridge.EndState()) {
			board.RefreshBoard(getClientBoard());
			//updateCapturedList();
		}
		else {
			//MainAppWindow.setPaneResult(8);
		}
	}
	
	public int getPlayerNum(){
		return playerNumber;
	}

	
	public BoardPiece[][] getClientBoard() {
		MainApplicationWindow mainWin = MainApplicationWindow.getInstance();
		GUIBridge bridge = mainWin.getBridge();
		BoardPiece[][] clientBoard = bridge.GetBoard();
		return clientBoard;
	}
	
	
	public synchronized void SquareClicked(int col, int row) {
		if (state == PanelState.PLAYER){
			PlayableMoves = bridge.getMoves(col, row);
			int[][] highlight = new int[PlayableMoves.size()][2];
			for (int i=0;i<PlayableMoves.size();i++){
				highlight[i][0] = PlayableMoves.get(i).colTo();
				highlight[i][0] = PlayableMoves.get(i).rowTo();
			}
			board.HighlightSquares(highlight);
			
			this.state = PanelState.HIGHLIGHTED;
		}
		else if (state == PanelState.HIGHLIGHTED){
			Move play = null;
			for(Move m : PlayableMoves) {
				if (m.colTo() == col && m.rowTo() == row){
					play = m;
				}
			}
			
			if (play != null) {
				if (bridge.PlayMove(play)) {
					System.out.println("Move play successful.");
					PlayableMoves = null;
					
					refreshBoard();
					//Wait for the opponent turn
					OpponentTurn();
				}
			}
			else {
				board.clearHighlights();
				//PlayableMoves = null;
				this.state = PanelState.PLAYER;
				SquareClicked(col, row);
			}
		}
		else if (state == PanelState.OPPONENT) {
			//Opponent's turn - do nothing
		}
		
	}
	
	public void OpponentTurn(){
		state = PanelState.OPPONENT;
		bridge.WaitForNextMove();
		refreshBoard();
		
	}
}
