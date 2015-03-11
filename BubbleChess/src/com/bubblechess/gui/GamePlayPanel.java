package com.bubblechess.gui;

import javax.swing.DefaultListModel;
import javax.swing.JList;
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
	protected BoardPiece.PieceColor playerColor;
	protected PanelState state;
	protected GUIBridge bridge;
	protected ArrayList<Move> PlayableMoves;
	
	protected DefaultListModel playerCaptureList;
	protected DefaultListModel opponentCaptureList; 
	
	
	// Flip Board if player is black pieces
	/**
	 * Creates a GamePlayPanel, initializes board, 
	 * @param playerNum
	 */
	public GamePlayPanel() {
		
		//Get our GUIBridge object
		MainApplicationWindow mainWin = MainApplicationWindow.getInstance();
		bridge = mainWin.getBridge();
		
		playerNumber = bridge.GetPlayerNumber();
		
		System.out.println("Game started.  We are player " + playerNumber);
		
		//White goes first
		if (playerNumber == 1) {
			this.state = PanelState.PLAYER;
			this.playerColor = BoardPiece.PieceColor.WHITE;
		}
		else {
			this.state = PanelState.OPPONENT;
			this.playerColor = BoardPiece.PieceColor.BLACK;
		}
		
		initUI();
		
		//Call refreshboard and put all the pieces on the board
		refreshBoard();
		
	}
	
	/**
	 * Set up our user interface
	 */
	public void initUI() {
		board = new GameBoard(this,playerNumber);
		setBackground(Color.LIGHT_GRAY);
		setPreferredSize(new Dimension(1024,768));
		setLayout(null);
		board.setLocation(50, 50);
		add(board);
		
		//Add our captured lists
		playerCaptureList = new DefaultListModel();
		JList playerCaptureListContainer = new JList(playerCaptureList);
		playerCaptureListContainer.setBounds(750, 50, 200, 250);
		add(playerCaptureListContainer);
		JLabel playerCaptureListLabel = new JLabel("Player's captured peices:");
		playerCaptureListLabel.setBounds(750,25,200,15);
		add(playerCaptureListLabel);
		
		opponentCaptureList = new DefaultListModel();
		JList opponentCaptureListContainer = new JList(opponentCaptureList);
		opponentCaptureListContainer.setBounds(750, 350, 200, 250);
		add(opponentCaptureListContainer);
		JLabel opponentCaptureListLabel = new JLabel("Opponent's captured peices:");
		opponentCaptureListLabel.setBounds(750,325,200,15);
		add(opponentCaptureListLabel);
	}
	
	
	/**
	 * Use the bridge to get the updated board and update the GUI
	 */
	public void refreshBoard() {
		//Make sure we're not in end state
		if (!bridge.EndState()) {
			board.RefreshBoard(bridge.GetBoard());
			updateCapturedList();
			this.revalidate();
		}
		else {
			//We're in end state.  Bring us back to the menu
			MainApplicationWindow mainWin = MainApplicationWindow.getInstance();
			mainWin.setPaneResult(8);
		}
	}
	
	/**
	 * Get our players playernumber
	 * @return The player number - 1 for white, 2 for black
	 */
	public int getPlayerNum(){
		return playerNumber;
	}
	
	/**
	 * Called by the board when a square is clicked.  Since the panel knows the 
	 * state of the game, it can decide the appropriate action
	 * @param col The column of the cell clicked
	 * @param row The row of the cell clicked
	 */
	public void SquareClicked(int col, int row) {
		//It's our turn, no moves are currently highlighted
		if (state == PanelState.PLAYER){
			//Make sure the user clicked one of our pieces
			if (playerNumber == board.GetPieceColor(col, row)) {
				System.out.println("Possible moves:");
				
				//Get all possible moves for the piece and generate a list of 
				//pieces to highlight
				PlayableMoves = bridge.getMoves(col, row);
				int[][] highlight = new int[PlayableMoves.size()][2];
				for (int i=0;i<PlayableMoves.size();i++){
					Move m = PlayableMoves.get(i);
					highlight[i][0] = m.colTo();
					highlight[i][1] = m.rowTo();
					System.out.println(m.toString());
				}
				board.HighlightSquares(highlight);
				
				this.state = PanelState.HIGHLIGHTED;
			}
		}
		else if (state == PanelState.HIGHLIGHTED){
			Move play = null;
			for(Move m : PlayableMoves) {
				if (m.colTo() == col && m.rowTo() == row){
					play = m;
				}
			}
			
			//If the user clicked a highlighted square, play the move
			if (play != null) {
				//Play the move
				if (bridge.PlayMove(play)) {
					System.out.println("Playing move: " + play.toString());
					PlayableMoves = null;
					
					//Show us our move
					refreshBoard();
					
					//Wait for the opponent turn
					OpponentTurn();
				}
			}
			else {
				//Clear the highlights and highlight the new square
				board.clearHighlights();
				this.state = PanelState.PLAYER;
				SquareClicked(col, row);
			}
		}
		else if (state == PanelState.OPPONENT) {
			//Do nothing here.
		}
		
	}
	
	/**
	 * Update our state and spin off a listener thread to wait for an opponent move
	 */
	public void OpponentTurn(){
		//Make clicking the board useless until we recieve a move
		state = PanelState.OPPONENT;
		
		System.out.print("Waiting for opponent move...");
		
		//Spin off a thread so we can exit this statement
		Thread listener = new Thread(new OpponentMoveListener(this,bridge));
		listener.start();
	}
	
	/**
	 * Called by OpponentMoveListener to let us know that we recieved a move
	 * and can update the board
	 */
	public void OpponentMoveRecieved(Move m) {
		System.out.println("Recieved move from opponent: " + m.toString());
		
		//We've gotten a move, show it to us
		refreshBoard();
		
		//Allow us to make moves again
		state = PanelState.PLAYER;
	}
	
	/**
	 * Update the captured pieces list for both teams
	 */
	public void updateCapturedList() {
		//Clear all the pieces from the displayed capture lists
		playerCaptureList.removeAllElements();
		opponentCaptureList.removeAllElements();
		
		//Get the list of our captured pieces
		BoardPiece[] piecesCaptured = bridge.GetCaptured();
		
		//Determine our color
		BoardPiece.PieceColor ourColor;
		if (playerNumber == 1)
			ourColor = BoardPiece.PieceColor.WHITE;
		else 
			ourColor = BoardPiece.PieceColor.WHITE;
		
		for(BoardPiece piece : piecesCaptured) {
			int pieceNum = piece.getPieceID();
			BoardPiece.PieceColor pieceColor = piece.getColor();
			String[] pieces = {"King", "Queen", "Rook", "Bishop", "Knight", "Pawn" };
			if (ourColor.equals(pieceColor)) {
				playerCaptureList.addElement(pieces[pieceNum]);
			}
			else {
				opponentCaptureList.addElement(pieces[pieceNum]);
			}
		}
	}
}
